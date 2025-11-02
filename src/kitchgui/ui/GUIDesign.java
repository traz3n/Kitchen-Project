package kitchgui.ui;

import kitchgui.controller.InventoryService;
import kitchgui.model.Item;
import kitchgui.model.ItemTableModel;
import kitchgui.Main;

import javax.swing.*;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;

public class GUIDesign extends JFrame {
    private final InventoryService service;

    private final ItemTableModel inventoryModel = new ItemTableModel();
    private final ItemTableModel shoppingModel = new ItemTableModel();

    private JTable inventoryTable, shoppingTable;
    private JTextField p, b, c, q, e, searchInv;
    private JTextField sp, sb, sc, sq, se, searchShop;

    // Status toast
    private final JLabel savedToast = new JLabel("Saved ✓", SwingConstants.CENTER);
    private final Timer toastTimer = new Timer(true);

    // Palette
    private final Color APP_BG = Main.OXFORD;
    private final Color CARD_BG = Main.CHARCOAL;
    private final Color HEADER_BG = Main.CAMBRIDGE;
    private final Color ACCENT = Main.OLIVINE;
    private final Color ACCENT_2 = Main.TEAGREEN;
    private final Color TEXT = Main.TEXT_PRIMARY;
    private final Color MUTED = Main.TEXT_MUTED;

    public GUIDesign(Path dataDir) {
        super("Kitch — Inventory & Shopping");
        this.service = new InventoryService(dataDir);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(APP_BG);
        buildUi();
        loadData();
        hookAutoSave();
    }

    /** Round-corner panel with background fill */
    private static class RoundedPanel extends JPanel {
        private final Color bg;
        private final int arc;

        RoundedPanel(LayoutManager lm, Color bg, int arc) {
            super(lm);
            this.bg = bg;
            this.arc = arc;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout(20, 20));
        root.setBackground(APP_BG);
        root.setBorder(new EmptyBorder(26, 26, 26, 26));
        setContentPane(root);

        // Header with centered title + Recipe Builder button (top-right)
        root.add(buildHeader(), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 20, 20));
        center.setOpaque(false);
        center.add(buildTableCard("Inventory", true));
        center.add(buildTableCard("Shopping", false));
        root.add(center, BorderLayout.CENTER);

        // Toast (overlay at bottom)
        savedToast.setForeground(TEXT);
        savedToast.setFont(savedToast.getFont().deriveFont(Font.BOLD, 14f));
        savedToast.setVisible(false);
        JPanel toastWrap = new RoundedPanel(new BorderLayout(), new Color(0x213040, true), 12);
        toastWrap.add(savedToast, BorderLayout.CENTER);
        toastWrap.setOpaque(false);
        toastWrap.setBorder(new EmptyBorder(8, 14, 8, 14));

        JPanel south = new JPanel(new GridBagLayout());
        south.setOpaque(false);
        south.add(toastWrap);
        root.add(south, BorderLayout.SOUTH);
    }

    /** Header bar with title and a prominent Recipe Builder button on the right */
    private JPanel buildHeader() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);

        JLabel title = new JLabel("KITCH — Inventory & Shopping", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        title.setForeground(TEXT);

        JButton recipes = primaryBtn("Recipe Builder");
        recipes.addActionListener(e -> {
            new RecipeListUI(Path.of("data")).setVisible(true);
            dispose();
        });

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(recipes);

        bar.add(title, BorderLayout.CENTER);
        bar.add(right, BorderLayout.EAST);

        // Keyboard shortcut Ctrl/Cmd+R to open Recipe Builder
        int menuMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        bar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_R, menuMask), "openRecipes");
        bar.getActionMap().put("openRecipes", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipes.doClick();
            }
        });

        return bar;
    }

    private JPanel buildTableCard(String title, final boolean inventory) {
        RoundedPanel card = new RoundedPanel(new BorderLayout(14, 14), CARD_BG, 16);
        card.setBorder(new EmptyBorder(14, 14, 14, 14));

        // Header
        JLabel header = new JLabel(title, SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(HEADER_BG);
        header.setForeground(Color.BLACK);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        header.setBorder(new EmptyBorder(10, 10, 10, 10));
        header.setPreferredSize(new Dimension(10, 44));
        header.setBorder(new LineBorder(HEADER_BG.darker(), 1, true));
        card.add(header, BorderLayout.NORTH);

        // BIG vertical button rail
        JPanel rail = new JPanel(new GridLayout(3, 1, 10, 10));
        rail.setOpaque(false);
        JButton add = primaryBtn("Add");
        JButton update = primaryBtn("Update");
        JButton delete = dangerBtn("Delete");
        rail.add(add);
        rail.add(update);
        rail.add(delete);
        card.add(rail, BorderLayout.WEST);

        // Table
        final JTable table = new JTable(inventory ? inventoryModel : shoppingModel);
        styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroller = new JScrollPane(table);
        scroller.getViewport().setBackground(CARD_BG);
        scroller.setBorder(new LineBorder(HEADER_BG, 1, true));
        card.add(scroller, BorderLayout.CENTER);

        installProportionalResize(table, scroller,
                new double[] { 0.30, 0.20, 0.20, 0.12, 0.18 },
                new int[] { 180, 120, 120, 80, 130 });

        // Form
        JPanel form = new JPanel(new GridLayout(3, 4, 10, 10));
        form.setOpaque(false);
        final JTextField fp = field();
        final JTextField fb = field();
        final JTextField fc = field();
        final JTextField fq = field();
        final JTextField fe = field();
        final JTextField fsearch = field();

        form.add(label("Product"));
        form.add(fp);
        form.add(label("Brand"));
        form.add(fb);
        form.add(label("Category"));
        form.add(fc);
        form.add(label("Quantity"));
        form.add(fq);
        form.add(label("Expiry"));
        form.add(fe);
        form.add(label("Search"));
        form.add(fsearch);
        card.add(form, BorderLayout.SOUTH);

        final TableRowSorter<ItemTableModel> sorter = new TableRowSorter<>(inventory ? inventoryModel : shoppingModel);
        table.setRowSorter(sorter);
        fsearch.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                sorter.setRowFilter(RowFilter.regexFilter(fsearch.getText(), 0, 1, 2, 4));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
        });

        // Selection fill-in
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting())
                    return;
                int r = table.getSelectedRow();
                if (r < 0)
                    return;
                int modelRow = table.convertRowIndexToModel(r);
                Item it = (inventory ? inventoryModel : shoppingModel).get(modelRow);
                fp.setText(it.getProduct());
                fb.setText(it.getBrand());
                fc.setText(it.getCategory());
                fq.setText(String.valueOf(it.getQuantity()));
                fe.setText(it.getExpiry());
            }
        });

        // Actions (auto-save after each)
        add.addActionListener(e1 -> {
            Item it = fromFields(fp, fb, fc, fq, fe);
            if (it == null)
                return;
            (inventory ? inventoryModel : shoppingModel).add(it);
            autoPersist();
        });
        update.addActionListener(e12 -> {
            int r = table.getSelectedRow();
            if (r < 0)
                return;
            Item it = fromFields(fp, fb, fc, fq, fe);
            if (it == null)
                return;
            (inventory ? inventoryModel : shoppingModel).update(table.convertRowIndexToModel(r), it);
            autoPersist();
        });
        delete.addActionListener(e13 -> {
            int r = table.getSelectedRow();
            if (r < 0) {
                showInfo("Select a row to delete");
                return;
            }
            (inventory ? inventoryModel : shoppingModel).remove(table.convertRowIndexToModel(r));
            autoPersist();
        });

        if (inventory) {
            inventoryTable = table;
            p = fp;
            b = fb;
            c = fc;
            q = fq;
            e = fe;
            searchInv = fsearch;

            JButton move = secondaryBtn("Move 0 Qty → Shopping");
            rail.add(move);
            ((GridLayout) rail.getLayout()).setRows(4);
            move.addActionListener(evt -> onGoShopping());
        } else {
            shoppingTable = table;
            sp = fp;
            sb = fb;
            sc = fc;
            sq = fq;
            se = fe;
            searchShop = fsearch;
        }

        return card;
    }

    // ===== Styling helpers =====
    private JButton makeBaseBtn(String text, Color bg, Color fg, Color border) {
        JButton b = new JButton(text);
        b.setUI(new BasicButtonUI()); // remove Nimbus bevel/double border
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setFont(b.getFont().deriveFont(Font.BOLD, 18f));
        b.setBorder(new LineBorder(border, 1, true));
        b.setBorderPainted(true);
        b.setContentAreaFilled(true);
        b.setOpaque(true);
        b.setMargin(new Insets(18, 28, 18, 28)); // generous click target
        b.setPreferredSize(new Dimension(220, 72)); // bigger buttons
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(bg.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(bg);
            }
        });
        return b;
    }

    private JButton primaryBtn(String text) {
        return makeBaseBtn(text, ACCENT, Color.BLACK, ACCENT_2);
    }

    private JButton secondaryBtn(String text) {
        return makeBaseBtn(text, ACCENT_2, Color.BLACK, ACCENT);
    }

    private JButton dangerBtn(String text) {
        return makeBaseBtn(text, new Color(0xD65C5C), Color.WHITE, new Color(0xBF3F3F));
    }

    private JTextField field() {
        JTextField tf = new JTextField();
        tf.setBorder(new LineBorder(ACCENT_2, 1, true));
        tf.setBackground(new Color(0x3D4857));
        tf.setForeground(TEXT);
        tf.setFont(tf.getFont().deriveFont(16f));
        tf.setMargin(new Insets(10, 12, 10, 12));
        return tf;
    }

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(MUTED);
        l.setFont(l.getFont().deriveFont(15f));
        return l;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(32);
        table.setFillsViewportHeight(true);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        // ✅ Make all columns fit the viewport (prevents cutoff on macOS fullscreen)
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JTableHeaderRenderer.install(table, HEADER_BG);

        if (table.getColumnModel().getColumnCount() >= 4) {
            DefaultTableCellRenderer right = new DefaultTableCellRenderer();
            right.setHorizontalAlignment(SwingConstants.RIGHT);
            table.getColumnModel().getColumn(3).setCellRenderer(right);
        }

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private final Color STRIPE = new Color(0x404C5C);

            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus,
                    int row, int col) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setForeground(TEXT);
                setFont(getFont().deriveFont(15f));
                if (!isSelected)
                    setBackground((row % 2 == 0) ? CARD_BG : STRIPE);
                return this;
            }
        });
    }

    // Header renderer
    static class JTableHeaderRenderer extends DefaultTableCellRenderer {
        private final Color bg;

        JTableHeaderRenderer(Color bg) {
            this.bg = bg;
        }

        static void install(JTable table, Color bg) {
            JTableHeaderRenderer r = new JTableHeaderRenderer(bg);
            table.getTableHeader().setDefaultRenderer(r);
            table.getTableHeader().setReorderingAllowed(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(LEFT);
            setBackground(bg);
            setForeground(Color.BLACK);
            setFont(getFont().deriveFont(Font.BOLD, 15f));
            setBorder(new LineBorder(bg.darker(), 1, true));
            setText(value == null ? "" : value.toString());
            return this;
        }
    }

    // ===== Logic & Auto-Save =====
    private Item fromFields(JTextField fp, JTextField fb, JTextField fc, JTextField fq, JTextField fe) {
        String ps = fp.getText().trim();
        if (ps.isEmpty()) {
            showError("Invalid Input", "Product name is required", null);
            return null;
        }
        int qty;
        try {
            qty = Integer.parseInt(fq.getText().trim());
        } catch (Exception ex) {
            qty = 0;
        }
        return new Item(ps, fb.getText(), fc.getText(), qty, fe.getText());
    }

    private void loadData() {
        try {
            service.loadAll();
            inventoryModel.setAll(service.getInventory());
            shoppingModel.setAll(service.getShopping());
        } catch (Exception ex) {
            showError("Load Error", ex.getMessage(), ex);
        }
    }

    /** Persist models immediately and show toast */
    private void autoPersist() {
        try {
            service.getInventory().clear();
            service.getInventory().addAll(inventoryModel.getRows());
            service.getShopping().clear();
            service.getShopping().addAll(shoppingModel.getRows());
            service.saveAll();
            flashSaved();
        } catch (Exception ex) {
            showError("Save Error", ex.getMessage(), ex);
        }
    }

    /** Auto-save on any table cell edit as well */
    private void hookAutoSave() {
        TableModelListener tml = (TableModelEvent e) -> autoPersist();
        inventoryModel.addTableModelListener(tml);
        shoppingModel.addTableModelListener(tml);
    }

    private void onGoShopping() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Move items with quantity ≤ 0 to Shopping with quantity 1?",
                "Go Shopping", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION)
            return;

        service.getInventory().clear();
        service.getInventory().addAll(inventoryModel.getRows());
        service.getShopping().clear();
        service.getShopping().addAll(shoppingModel.getRows());

        service.moveZeroQtyToShopping(1);
        inventoryModel.setAll(service.getInventory());
        shoppingModel.setAll(service.getShopping());
        autoPersist();
        showInfo("Moved out-of-stock items to Shopping 🛒");
    }

    // proportional column resize
    private void installProportionalResize(JTable table, JScrollPane scroller, double[] weights, int[] minWidths) {
        final ComponentAdapter adapter = new ComponentAdapter() {
            private void resizeCols() {
                if (table.getColumnCount() == 0)
                    return;
                if (weights == null || weights.length != table.getColumnCount())
                    return;

                JViewport vp = scroller.getViewport();
                if (vp == null)
                    return;
                int w = vp.getWidth();
                if (w <= 0)
                    return;

                double sum = 0.0;
                for (double wt : weights)
                    sum += wt;
                if (sum <= 0)
                    return;

                TableColumnModel cm = table.getColumnModel();
                int used = 0;
                int[] target = new int[weights.length];

                for (int i = 0; i < weights.length - 1; i++) {
                    int mw = (minWidths != null && i < minWidths.length) ? minWidths[i] : 40;
                    int width = Math.max(mw, (int) Math.round(w * (weights[i] / sum)));
                    target[i] = width;
                    used += width;
                }
                int last = weights.length - 1;
                int mw = (minWidths != null && last < minWidths.length) ? minWidths[last] : 40;
                target[last] = Math.max(mw, w - used);

                for (int i = 0; i < target.length; i++) {
                    TableColumn col = cm.getColumn(i);
                    col.setPreferredWidth(target[i]);
                    // ✅ allow columns to shrink fully to fit viewport
                    col.setMinWidth(10);
                    col.setWidth(target[i]);
                }
                table.revalidate();
            }

            @Override
            public void componentResized(ComponentEvent e) {
                resizeCols();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                resizeCols();
            }
        };
        scroller.getViewport().addComponentListener(adapter);
        SwingUtilities.invokeLater(() -> adapter.componentResized(null));
    }

    private void flashSaved() {
        savedToast.setVisible(true);
        savedToast.getParent().setVisible(true);
        toastTimer.purge();
        toastTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> savedToast.setVisible(false));
            }
        }, 900);
    }

    private void showError(String title, String detail, Exception ex) {
        System.err.println(title + (detail == null ? "" : (": " + detail)));
        if (ex != null)
            ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                detail == null ? title : (title + "\n" + detail),
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
}
