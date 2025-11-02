package kitchgui.ui;

import kitchgui.controller.InventoryService;
import kitchgui.controller.RecipeService;
import kitchgui.model.Item;
import kitchgui.model.ItemTableModel;
import kitchgui.model.Recipe;
import kitchgui.Main;

import javax.swing.*;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Path;

import java.util.Timer;
import java.util.TimerTask;

public class RecipeListUI extends JFrame {

    private final InventoryService inventoryService;
    private final RecipeService recipeService;

    private final ItemTableModel ingredientsModel = new ItemTableModel();
    private final DefaultTableModel stepsModel = new DefaultTableModel(new Object[] { "Steps" }, 0);

    private JTable ingredientsTable, stepsTable;
    private JTextField p, b, c, q, e, searchIng;
    private JTextField stepField, searchStep;

    private int currentId = 1;

    private final JLabel savedToast = new JLabel("Saved ✓", SwingConstants.CENTER);
    private final Timer toastTimer = new Timer(true);

    // Palette from Main
    private final Color APP_BG = Main.OXFORD;
    private final Color CARD_BG = Main.CHARCOAL;
    private final Color HEADER_BG = Main.CAMBRIDGE;
    private final Color ACCENT = Main.OLIVINE;
    private final Color ACCENT_2 = Main.TEAGREEN;
    private final Color TEXT = Main.TEXT_PRIMARY;
    private final Color MUTED = Main.TEXT_MUTED;

    public RecipeListUI(Path dataDir) {
        super("Kitch — Recipe Builder");
        this.inventoryService = new InventoryService(dataDir);
        this.recipeService = new RecipeService(dataDir);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 820);
        setLocationRelativeTo(null);
        getContentPane().setBackground(APP_BG);
        buildUi();
        loadRecipe(1);
        hookAutoSave();
    }

    /** Rounded panel helper */
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

    /** Header renderer (local copy so we don't depend on GUIDesign) */
    private static class TableHeaderRenderer extends DefaultTableCellRenderer {
        private final Color bg;

        TableHeaderRenderer(Color bg) {
            this.bg = bg;
        }

        static void install(JTable table, Color bg) {
            table.getTableHeader().setDefaultRenderer(new TableHeaderRenderer(bg));
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

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout(20, 20));
        root.setBackground(APP_BG);
        root.setBorder(new EmptyBorder(26, 26, 26, 26));
        setContentPane(root);

        // Header (selector left, title center, Home right)
        root.add(buildHeader(), BorderLayout.NORTH);

        // Two main cards
        JPanel top = new JPanel(new GridLayout(1, 2, 20, 20));
        top.setOpaque(false);
        top.add(buildIngredientsCard());
        top.add(buildStepsCard());
        root.add(top, BorderLayout.CENTER);

        // Toast
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

    /** Header with Recipe 1/2 selector and Home button */
    private JComponent buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        // Center title
        JLabel title = new JLabel("KITCH — Recipe Builder", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 28f));
        title.setForeground(TEXT);
        header.add(title, BorderLayout.CENTER);

        // Left segmented selector
        JToggleButton r1 = segButton("Recipe 1");
        JToggleButton r2 = segButton("Recipe 2");
        ButtonGroup g = new ButtonGroup();
        g.add(r1);
        g.add(r2);
        r1.setSelected(true);

        r1.addActionListener(e -> {
            if (r1.isSelected())
                loadRecipe(1);
        });
        r2.addActionListener(e -> {
            if (r2.isSelected())
                loadRecipe(2);
        });

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        left.setOpaque(false);
        left.add(r1);
        left.add(r2);
        header.add(left, BorderLayout.WEST);

        // Shortcuts Cmd/Ctrl+1/2
        int mask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        header.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, mask), "r1");
        header.getActionMap().put("r1", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r1.setSelected(true);
                r1.doClick();
            }
        });
        header.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_2, mask), "r2");
        header.getActionMap().put("r2", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                r2.setSelected(true);
                r2.doClick();
            }
        });

        // Right: Home button
        JButton homeBtn = primaryBtn("← Home");
        homeBtn.addActionListener(e -> {
            new GUIDesign(Path.of("data")).setVisible(true);
            dispose();
        });
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        right.setOpaque(false);
        right.add(homeBtn);
        header.add(right, BorderLayout.EAST);

        return header;
    }

    private JToggleButton segButton(String text) {
        JToggleButton b = new JToggleButton(text);
        b.setUI(new BasicButtonUI());
        b.setBackground(ACCENT_2);
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setFont(b.getFont().deriveFont(Font.BOLD, 16f));
        b.setBorder(new LineBorder(ACCENT, 1, true));
        b.setContentAreaFilled(true);
        b.setOpaque(true);
        b.setMargin(new Insets(12, 22, 12, 22));
        b.addItemListener(e -> b.setBackground(b.isSelected() ? ACCENT : ACCENT_2));
        if (text.endsWith("1"))
            b.setBackground(ACCENT); // initial
        return b;
    }

    private JPanel buildIngredientsCard() {
        RoundedPanel card = new RoundedPanel(new BorderLayout(14, 14), CARD_BG, 16);
        card.setBorder(new EmptyBorder(14, 14, 14, 14));

        JLabel header = new JLabel("Ingredients", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(HEADER_BG);
        header.setForeground(Color.BLACK);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        header.setBorder(new LineBorder(HEADER_BG.darker(), 1, true));
        header.setPreferredSize(new Dimension(10, 44));
        card.add(header, BorderLayout.NORTH);

        JPanel rail = new JPanel(new GridLayout(3, 1, 10, 10));
        rail.setOpaque(false);
        JButton add = primaryBtn("Add");
        JButton update = primaryBtn("Update");
        JButton delete = dangerBtn("Delete");
        rail.add(add);
        rail.add(update);
        rail.add(delete);
        card.add(rail, BorderLayout.WEST);

        ingredientsTable = new JTable(ingredientsModel);
        styleTable(ingredientsTable);
        ingredientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroller = new JScrollPane(ingredientsTable);
        scroller.getViewport().setBackground(CARD_BG);
        scroller.setBorder(new LineBorder(HEADER_BG, 1, true));
        card.add(scroller, BorderLayout.CENTER);

        installProportionalResize(ingredientsTable, scroller,
                new double[] { 0.34, 0.18, 0.18, 0.12, 0.18 },
                new int[] { 200, 120, 120, 80, 130 });

        JPanel form = new JPanel(new GridLayout(3, 4, 10, 10));
        form.setOpaque(false);
        p = field();
        b = field();
        c = field();
        q = field();
        e = field();
        searchIng = field();
        form.add(label("Product"));
        form.add(p);
        form.add(label("Brand"));
        form.add(b);
        form.add(label("Category"));
        form.add(c);
        form.add(label("Quantity"));
        form.add(q);
        form.add(label("Expiry"));
        form.add(e);
        form.add(label("Search"));
        form.add(searchIng);
        card.add(form, BorderLayout.SOUTH);

        final TableRowSorter<ItemTableModel> sorter = new TableRowSorter<>(ingredientsModel);
        ingredientsTable.setRowSorter(sorter);
        searchIng.getDocument().addDocumentListener(new DocumentListener() {
            private void upd() {
                sorter.setRowFilter(RowFilter.regexFilter(searchIng.getText(), 0, 1, 2, 4));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                upd();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                upd();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                upd();
            }
        });

        ingredientsTable.getSelectionModel().addListSelectionListener(event -> {
            if (event.getValueIsAdjusting())
                return;
            int r = ingredientsTable.getSelectedRow();
            if (r < 0)
                return;
            int mr = ingredientsTable.convertRowIndexToModel(r);
            Item it = ingredientsModel.get(mr);
            p.setText(it.getProduct());
            b.setText(it.getBrand());
            c.setText(it.getCategory());
            q.setText(String.valueOf(it.getQuantity()));
            e.setText(it.getExpiry());
        });

        add.addActionListener(e1 -> {
            Item it = fromFields();
            if (it != null) {
                ingredientsModel.add(it);
                autoPersistRecipe();
            }
        });
        update.addActionListener(e12 -> {
            int r = ingredientsTable.getSelectedRow();
            if (r < 0)
                return;
            Item it = fromFields();
            if (it != null) {
                ingredientsModel.update(ingredientsTable.convertRowIndexToModel(r), it);
                autoPersistRecipe();
            }
        });
        delete.addActionListener(e13 -> {
            int r = ingredientsTable.getSelectedRow();
            if (r < 0) {
                showInfo("Select a row to delete");
                return;
            }
            ingredientsModel.remove(ingredientsTable.convertRowIndexToModel(r));
            autoPersistRecipe();
        });

        return card;
    }

    private JPanel buildStepsCard() {
        RoundedPanel card = new RoundedPanel(new BorderLayout(14, 14), CARD_BG, 16);
        card.setBorder(new EmptyBorder(14, 14, 14, 14));

        JLabel header = new JLabel("Recipe Steps", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(HEADER_BG);
        header.setForeground(Color.BLACK);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        header.setBorder(new LineBorder(HEADER_BG.darker(), 1, true));
        header.setPreferredSize(new Dimension(10, 44));
        card.add(header, BorderLayout.NORTH);

        JPanel rail = new JPanel(new GridLayout(3, 1, 10, 10));
        rail.setOpaque(false);
        JButton add = primaryBtn("Add");
        JButton update = primaryBtn("Update");
        JButton delete = dangerBtn("Delete");
        rail.add(add);
        rail.add(update);
        rail.add(delete);
        card.add(rail, BorderLayout.WEST);

        stepsTable = new JTable(stepsModel);
        styleTable(stepsTable);
        JScrollPane scroller = new JScrollPane(stepsTable);
        scroller.getViewport().setBackground(CARD_BG);
        scroller.setBorder(new LineBorder(HEADER_BG, 1, true));
        card.add(scroller, BorderLayout.CENTER);

        installProportionalResize(stepsTable, scroller, new double[] { 1.0 }, new int[] { 320 });

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setOpaque(false);
        stepField = field();
        searchStep = field();
        form.add(label("Step"));
        form.add(stepField);
        form.add(label("Search"));
        form.add(searchStep);
        card.add(form, BorderLayout.SOUTH);

        final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(stepsModel);
        stepsTable.setRowSorter(sorter);
        searchStep.getDocument().addDocumentListener(new DocumentListener() {
            private void upd() {
                sorter.setRowFilter(RowFilter.regexFilter(searchStep.getText(), 0));
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                upd();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                upd();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                upd();
            }
        });

        add.addActionListener(e1 -> {
            String s = stepField.getText().trim();
            if (s.isEmpty())
                return;
            stepsModel.addRow(new Object[] { s });
            stepField.setText("");
            autoPersistRecipe();
        });
        update.addActionListener(e12 -> {
            int r = stepsTable.getSelectedRow();
            if (r < 0)
                return;
            String s = stepField.getText().trim();
            if (s.isEmpty())
                return;
            stepsModel.setValueAt(s, stepsTable.convertRowIndexToModel(r), 0);
            stepField.setText("");
            autoPersistRecipe();
        });
        delete.addActionListener(e13 -> {
            int r = stepsTable.getSelectedRow();
            if (r < 0) {
                showInfo("Select a row to delete");
                return;
            }
            stepsModel.removeRow(stepsTable.convertRowIndexToModel(r));
            autoPersistRecipe();
        });

        return card;
    }

    // ======== Styling helpers (single definitions) ========
    private JButton makeBaseBtn(String text, Color bg, Color fg, Color border) {
        JButton b = new JButton(text);
        b.setUI(new BasicButtonUI());
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setFont(b.getFont().deriveFont(Font.BOLD, 18f));
        b.setBorder(new LineBorder(border, 1, true));
        b.setBorderPainted(true);
        b.setContentAreaFilled(true);
        b.setOpaque(true);
        b.setMargin(new Insets(18, 28, 18, 28));
        b.setPreferredSize(new Dimension(220, 72));
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
        // ✅ Fit to viewport on macOS fullscreen (no cutoff)
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        TableHeaderRenderer.install(table, HEADER_BG);

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

    // ======== Logic & persistence ========
    private Item fromFields() {
        String ps = p.getText().trim();
        if (ps.isEmpty()) {
            showError("Invalid Input", "Product name is required", null);
            return null;
        }
        int qty;
        try {
            qty = Integer.parseInt(q.getText().trim());
        } catch (Exception ex) {
            qty = 0;
        }
        return new Item(ps, b.getText(), c.getText(), qty, e.getText());
    }

    private void loadRecipe(int id) {
        try {
            currentId = id;
            Recipe r = recipeService.load(id, "Recipe " + id);
            ingredientsModel.setAll(r.getIngredients());
            while (stepsModel.getRowCount() > 0)
                stepsModel.removeRow(0);
            for (String s : r.getSteps())
                stepsModel.addRow(new Object[] { s });
        } catch (Exception ex) {
            showError("Load Error", ex.getMessage(), ex);
        }
    }

    private void autoPersistRecipe() {
        try {
            Recipe r = new Recipe(currentId, "Recipe " + currentId);
            r.getIngredients().addAll(ingredientsModel.getRows());
            for (int i = 0; i < stepsModel.getRowCount(); i++) {
                r.getSteps().add(String.valueOf(stepsModel.getValueAt(i, 0)));
            }
            recipeService.save(r);
            flashSaved();
        } catch (Exception ex) {
            showError("Save Error", ex.getMessage(), ex);
        }
    }

    private void hookAutoSave() {
        ingredientsModel.addTableModelListener((TableModelEvent e) -> autoPersistRecipe());
        stepsModel.addTableModelListener((TableModelEvent e) -> autoPersistRecipe());
    }

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
                    // ✅ allow shrinking so nothing gets cut off
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

    // ======== Small dialog helpers (now defined here) ========
    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String title, String detail, Exception ex) {
        System.err.println(title + (detail == null ? "" : (": " + detail)));
        if (ex != null)
            ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                detail == null ? title : (title + "\n" + detail),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
