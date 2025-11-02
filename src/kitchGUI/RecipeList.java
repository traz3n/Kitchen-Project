package kitchGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RecipeList extends JFrame {

	private JPanel contentPane;

	private JTable table_2;
	DefaultTableModel dtm2 = new DefaultTableModel();
	final Object[] rowfields2 = new Object[5];

	private JTable table_3;
	DefaultTableModel dtm3 = new DefaultTableModel();
	final Object[] rowfields3 = new Object[1];

	final Object[] rowfields4 = new Object[1];

	final Object[] recipe1rf = new Object[5];

	private JTextField ILproducttf;
	private JTextField ILbrandtf;
	private JTextField ILcategorytf;
	private JTextField ILquantitytf;
	private JTextField ILexpirytf;
	private JTextField ILsearchtf;
	private JTextField RLsteptf;
	private JTextField RLsearchtf;

	public int state; // state of recipe list

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecipeList frame = new RecipeList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RecipeList() {
		setForeground(SystemColor.textHighlight);
		setBackground(SystemColor.textHighlight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 717, 465);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 54, 319, 185);
		contentPane.add(scrollPane);

		table_2 = new JTable();

		table_2.addMouseListener(new MouseAdapter() { // enables row selection
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedrow = table_2.getSelectedRow();
				ILproducttf.setText((String) table_2.getValueAt(selectedrow, 0));
				ILbrandtf.setText((String) table_2.getValueAt(selectedrow, 1));
				ILcategorytf.setText((String) table_2.getValueAt(selectedrow, 2));
				ILquantitytf.setText((String) table_2.getValueAt(selectedrow, 3));
				ILexpirytf.setText((String) table_2.getValueAt(selectedrow, 4));
			}
		});

		scrollPane.setViewportView(table_2);
		Object[] columns2 = { "Product", "Brand", "Category", "Quantity", "Expiry" };
		dtm2.setColumnIdentifiers(columns2);
		table_2.setModel(dtm2);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(337, 54, 351, 185);
		contentPane.add(scrollPane_1);

		table_3 = new JTable();

		table_3.addMouseListener(new MouseAdapter() { // enables row selection
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedrow3 = table_3.getSelectedRow();
				RLsteptf.setText((String) table_3.getValueAt(selectedrow3, 0));

			}
		});

		scrollPane_1.setViewportView(table_3);
		Object[] columns3 = { "Steps" };
		dtm3.setColumnIdentifiers(columns3);
		table_3.setModel(dtm3);

		JButton btnHome = new JButton("Home");
		btnHome.setForeground(Color.BLACK);
		btnHome.setBackground(Color.WHITE);
		btnHome.addActionListener(new ActionListener() { // button to swap to home window
			public void actionPerformed(ActionEvent e) {
				GUIDesign mainwindow = new GUIDesign();
				mainwindow.setVisible(true);
				dispose();
			}
		});
		btnHome.setBounds(571, 13, 117, 29);
		contentPane.add(btnHome);

		JLabel lblNewLabel = new JLabel("Recipe List");
		lblNewLabel.setBounds(294, 18, 81, 16);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Product");
		lblNewLabel_1.setBounds(16, 251, 61, 16);
		contentPane.add(lblNewLabel_1);

		JLabel lblBrand = new JLabel("Brand");
		lblBrand.setBounds(16, 278, 61, 16);
		contentPane.add(lblBrand);

		JLabel lblNewLabel_1_1 = new JLabel("Category");
		lblNewLabel_1_1.setBounds(16, 303, 61, 16);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Quantity");
		lblNewLabel_1_1_1.setBounds(16, 331, 61, 16);
		contentPane.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Expiry");
		lblNewLabel_1_1_1_1.setBounds(16, 359, 61, 16);
		contentPane.add(lblNewLabel_1_1_1_1);

		ILproducttf = new JTextField();
		ILproducttf.setColumns(10);
		ILproducttf.setBounds(89, 246, 130, 26);
		contentPane.add(ILproducttf);

		JLabel lblInventoryList = new JLabel("Ingredients");
		lblInventoryList.setBounds(125, 35, 71, 16);
		contentPane.add(lblInventoryList);

		ILbrandtf = new JTextField();
		ILbrandtf.setColumns(10);
		ILbrandtf.setBounds(89, 273, 130, 26);
		contentPane.add(ILbrandtf);

		ILcategorytf = new JTextField();
		ILcategorytf.setColumns(10);
		ILcategorytf.setBounds(89, 303, 130, 26);
		contentPane.add(ILcategorytf);

		ILquantitytf = new JTextField();
		ILquantitytf.setColumns(10);
		ILquantitytf.setBounds(89, 331, 130, 26);
		contentPane.add(ILquantitytf);

		ILexpirytf = new JTextField();
		ILexpirytf.setColumns(10);
		ILexpirytf.setBounds(89, 359, 130, 26);
		contentPane.add(ILexpirytf);

		// to immediately load last save state
		IngredientsLoadStart();
		RLLoadStart();

		JButton ILadd = new JButton("Add"); // add function for ingredients
		ILadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String ILProduct = ILproducttf.getText();
				String ILBrand = ILbrandtf.getText();
				String ILCategory = ILcategorytf.getText();
				String ILQuantity = ILquantitytf.getText();
				String ILExpiry = ILexpirytf.getText();

				rowfields2[0] = ILProduct;
				rowfields2[1] = ILBrand;
				rowfields2[2] = ILCategory;
				rowfields2[3] = ILQuantity;
				rowfields2[4] = ILExpiry;

				dtm2.addRow(rowfields2);

				ILproducttf.setText(null);
				ILbrandtf.setText(null);
				ILcategorytf.setText(null);
				ILquantitytf.setText(null);
				ILexpirytf.setText(null);

				if (state == 0) {
					Ingredients1Save();

				}

				else if (state == 1) {
					Ingredients2Save();

				}

			}
		});
		ILadd.setForeground(Color.BLACK);
		ILadd.setBackground(Color.WHITE);
		ILadd.setBounds(6, 389, 91, 27);
		contentPane.add(ILadd);

		JButton ILdelete = new JButton("Delete"); // delete function for ingredients
		ILdelete.setForeground(Color.BLACK);
		ILdelete.setBackground(Color.WHITE);
		ILdelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selectedRow = table_2.getSelectedRow();
				dtm2.removeRow(selectedRow);

				ILproducttf.setText(null);
				ILbrandtf.setText(null);
				ILcategorytf.setText(null);
				ILquantitytf.setText(null);
				ILexpirytf.setText(null);

				if (state == 0) {
					Ingredients1Save();

				}

				else if (state == 1) {
					Ingredients2Save();

				}

			}
		});
		ILdelete.setBounds(99, 389, 91, 27);
		contentPane.add(ILdelete);

		JButton ILupdate = new JButton("Update"); // update function for ingredients
		ILupdate.setForeground(Color.BLACK);
		ILupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String ILProduct = ILproducttf.getText();
				String ILBrand = ILbrandtf.getText();
				String ILCategory = ILcategorytf.getText();
				String ILQuantity = ILquantitytf.getText();
				String ILExpiry = ILexpirytf.getText();

				dtm2.setValueAt(ILProduct, table_2.getSelectedRow(), 0);
				dtm2.setValueAt(ILBrand, table_2.getSelectedRow(), 1);
				dtm2.setValueAt(ILCategory, table_2.getSelectedRow(), 2);
				dtm2.setValueAt(ILQuantity, table_2.getSelectedRow(), 3);
				dtm2.setValueAt(ILExpiry, table_2.getSelectedRow(), 4);

				ILproducttf.setText(null);
				ILbrandtf.setText(null);
				ILcategorytf.setText(null);
				ILquantitytf.setText(null);
				ILexpirytf.setText(null);

				if (state == 0) {
					Ingredients1Save();

				}

				else if (state == 1) {
					Ingredients2Save();

				}

			}
		});
		ILupdate.setBackground(Color.WHITE);
		ILupdate.setBounds(191, 389, 91, 27);
		contentPane.add(ILupdate);

		ILsearchtf = new JTextField();
		ILsearchtf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { // dynamic search for ingredients

				DefaultTableModel searchtable2 = (DefaultTableModel) table_2.getModel();
				String search = ILsearchtf.getText().toLowerCase();
				TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(searchtable2);
				table_2.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search));

			}
		});
		ILsearchtf.setColumns(10);
		ILsearchtf.setBounds(234, 278, 91, 26);
		contentPane.add(ILsearchtf);

		JLabel lblRecipeSteps = new JLabel("Recipe List");
		lblRecipeSteps.setBounds(466, 35, 104, 16);
		contentPane.add(lblRecipeSteps);

		JLabel lblNewLabel_1_2 = new JLabel("Step");
		lblNewLabel_1_2.setBounds(494, 251, 61, 16);
		contentPane.add(lblNewLabel_1_2);

		RLsteptf = new JTextField();
		RLsteptf.setColumns(10);
		RLsteptf.setBounds(337, 273, 351, 26);
		contentPane.add(RLsteptf);

		JButton ILadd_1 = new JButton("Add"); // add function for recipe
		ILadd_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String RLstep = RLsteptf.getText();

				rowfields3[0] = RLstep;

				dtm3.addRow(rowfields3);

				RLsteptf.setText(null);

				if (state == 0) {
					Recipe1Save();
				}

				else if (state == 1) {
					Recipe2Save();
				}

			}
		});
		ILadd_1.setForeground(Color.BLACK);
		ILadd_1.setBackground(Color.WHITE);
		ILadd_1.setBounds(337, 303, 91, 27);
		contentPane.add(ILadd_1);

		JButton ILdelete_1 = new JButton("Delete"); // delete function for recipe
		ILdelete_1.setBackground(Color.WHITE);
		ILdelete_1.setForeground(Color.BLACK);
		ILdelete_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selectedRow = table_3.getSelectedRow();
				dtm3.removeRow(selectedRow);

				RLsteptf.setText(null);

				if (state == 0) {
					Recipe1Save();
				}

				else if (state == 1) {
					Recipe2Save();
				}

			}
		});
		ILdelete_1.setBounds(464, 303, 91, 27);
		contentPane.add(ILdelete_1);

		JButton ILupdate_1 = new JButton("Update"); // update function for recipe
		ILupdate_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String RLstep = RLsteptf.getText();

				dtm3.setValueAt(RLstep, table_3.getSelectedRow(), 0);

				RLsteptf.setText(null);

				if (state == 0) {
					Recipe1Save();
				}

				else if (state == 1) {
					Recipe2Save();
				}

			}
		});
		ILupdate_1.setBackground(Color.WHITE);
		ILupdate_1.setBounds(585, 303, 91, 27);
		contentPane.add(ILupdate_1);

		JLabel lblNewLabel_1_2_1 = new JLabel("Search");
		lblNewLabel_1_2_1.setBounds(259, 262, 46, 16);
		contentPane.add(lblNewLabel_1_2_1);

		RLsearchtf = new JTextField();
		RLsearchtf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { // search function for recipe

				DefaultTableModel searchtable3 = (DefaultTableModel) table_3.getModel();
				String search3 = RLsearchtf.getText().toLowerCase();
				TableRowSorter<DefaultTableModel> tr3 = new TableRowSorter<DefaultTableModel>(searchtable3);
				table_3.setRowSorter(tr3);
				tr3.setRowFilter(RowFilter.regexFilter(search3));

			}
		});
		RLsearchtf.setColumns(10);
		RLsearchtf.setBounds(585, 359, 91, 26);
		contentPane.add(RLsearchtf);

		JLabel lblNewLabel_1_2_1_1 = new JLabel("Search");
		lblNewLabel_1_2_1_1.setBounds(608, 341, 46, 16);
		contentPane.add(lblNewLabel_1_2_1_1);

		JButton ILadd_1_1 = new JButton("Recipe 1"); // determines which recipe is selected and changes state of the
														// window
		ILadd_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				state = 0;
				Ingredients1LoadStart();
				Recipe1Load();

			}
		});
		ILadd_1_1.setForeground(Color.BLACK);
		ILadd_1_1.setBackground(Color.WHITE);
		ILadd_1_1.setBounds(335, 389, 91, 27);
		contentPane.add(ILadd_1_1);

		JButton ILadd_1_2 = new JButton("Recipe 2"); // determines which recipe is selected and changes state of the
														// window
		ILadd_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				state = 1;
				Ingredients2LoadStart();
				Recipe2Load();

			}
		});
		ILadd_1_2.setForeground(Color.BLACK);
		ILadd_1_2.setBackground(Color.WHITE);
		ILadd_1_2.setBounds(435, 389, 91, 27);
		contentPane.add(ILadd_1_2);

		JButton btnIngredientChecker = new JButton("Checker"); // automatic add button to shopping list if ingredients
																// are insufficient upon scanning inventory
		btnIngredientChecker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (state == 0) {
					ILChecker1();
				}

				else if (state == 1) {
					ILChecker2();
				}

			}
		});
		btnIngredientChecker.setBackground(Color.WHITE);
		btnIngredientChecker.setBounds(231, 331, 94, 44);
		contentPane.add(btnIngredientChecker);
	}

	private static class __Tmp {
		private static void __tmp() {
			javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}

	// I made many methods to make things a bit easier

	// save function for ingredients
	public void IngredientsSave() {

		int rownum = dtm2.getRowCount();

		try {

			File f = new File("ingredients.txt");

			FileOutputStream in = new FileOutputStream(f);

			PrintWriter w = new PrintWriter(in);

			for (int x = 0; x < rownum; x++) {

				for (int y = 0; y < 5; y++) {

					w.println((String) dtm2.getValueAt(x, y));

				}
			}

			w.close();
		}

		catch (Exception e) {
		}

	}

	// load function for ingredients
	private void IngredientsLoadStart() {

		try {
			File f1 = new File("ingredients.txt");
			FileReader in = new FileReader(f1);
			BufferedReader r = new BufferedReader(in);

			File f2 = new File("ingredients.txt");
			FileReader in2 = new FileReader(f2);
			BufferedReader r2 = new BufferedReader(in2);

			int lines = 0;
			while (r2.readLine() != null) {
				lines++;
			}
			r2.close();

			for (int rw = 0; rw < lines / 5; rw++) {
				for (int cl = 0; cl < 5; cl++) {
					rowfields2[cl] = r.readLine();
				}
				dtm2.addRow(rowfields2);

			}
			r.close();

		} catch (Exception e2) {

		}

	}

	// save function for recipe
	public void RLSave() {

		int rownum = dtm3.getRowCount();

		try {

			File f = new File("recipelist.txt");

			FileOutputStream in = new FileOutputStream(f);

			PrintWriter w = new PrintWriter(in);

			for (int x = 0; x < rownum; x++) {

				for (int y = 0; y < 1; y++) {

					w.println((String) dtm3.getValueAt(x, y));

				}
			}

			w.close();
		}

		catch (Exception e) {
		}

	}

	// load function for recipe
	private void RLLoadStart() {

		try {
			File f1 = new File("recipelist.txt");
			FileReader in = new FileReader(f1);
			BufferedReader r = new BufferedReader(in);

			File f2 = new File("recipelist.txt");
			FileReader in2 = new FileReader(f2);
			BufferedReader r2 = new BufferedReader(in2);

			int lines = 0;
			while (r2.readLine() != null) {
				lines++;
			}
			r2.close();

			for (int rw = 0; rw < lines / 1; rw++) {
				for (int cl = 0; cl < 1; cl++) {
					rowfields3[cl] = r.readLine();
				}
				dtm3.addRow(rowfields3);

			}
			r.close();

		} catch (Exception e2) {

		}

	}

	// save function for ingredients if recipe1 is selected
	public void Ingredients1Save() {

		int rownum = dtm2.getRowCount();

		try {

			File f = new File("ingredients1.txt");

			FileOutputStream in = new FileOutputStream(f);

			PrintWriter w = new PrintWriter(in);

			for (int x = 0; x < rownum; x++) {

				for (int y = 0; y < 5; y++) {

					w.println((String) dtm2.getValueAt(x, y));

				}
			}

			w.close();
		}

		catch (Exception e) {
		}

	}

	// load function for ingredients if recipe1 is selected
	private void Ingredients1LoadStart() {

		try {
			dtm2.setNumRows(0);

			File f1 = new File("ingredients1.txt");
			FileReader in = new FileReader(f1);
			BufferedReader r = new BufferedReader(in);

			File f2 = new File("ingredients1.txt");
			FileReader in2 = new FileReader(f2);
			BufferedReader r2 = new BufferedReader(in2);

			int lines = 0;
			while (r2.readLine() != null) {
				lines++;
			}
			r2.close();

			for (int rw = 0; rw < lines / 5; rw++) {
				for (int cl = 0; cl < 5; cl++) {
					rowfields2[cl] = r.readLine();
				}
				dtm2.addRow(rowfields2);

			}
			r.close();

		} catch (Exception e2) {

		}

	}

	// save function for ingredients if recipe2 is selected
	public void Ingredients2Save() {

		int rownum = dtm2.getRowCount();

		try {

			File f = new File("ingredients2.txt");

			FileOutputStream in = new FileOutputStream(f);

			PrintWriter w = new PrintWriter(in);

			for (int x = 0; x < rownum; x++) {

				for (int y = 0; y < 5; y++) {

					w.println((String) dtm2.getValueAt(x, y));

				}
			}

			w.close();
		}

		catch (Exception e) {
		}

	}

	// load function for ingredients if recipe3 is selected
	private void Ingredients2LoadStart() {

		try {

			dtm2.setNumRows(0);

			File f1 = new File("ingredients2.txt");
			FileReader in = new FileReader(f1);
			BufferedReader r = new BufferedReader(in);

			File f2 = new File("ingredients2.txt");
			FileReader in2 = new FileReader(f2);
			BufferedReader r2 = new BufferedReader(in2);

			int lines = 0;
			while (r2.readLine() != null) {
				lines++;
			}
			r2.close();

			for (int rw = 0; rw < lines / 5; rw++) {
				for (int cl = 0; cl < 5; cl++) {
					rowfields2[cl] = r.readLine();
				}
				dtm2.addRow(rowfields2);

			}
			r.close();

		} catch (Exception e2) {

		}

	}

	// save function for recipe if recipe1 is selected
	public void Recipe1Save() {

		int rownum = dtm3.getRowCount();

		try {

			File f = new File("recipe1.txt");

			FileOutputStream in = new FileOutputStream(f);

			PrintWriter w = new PrintWriter(in);

			for (int x = 0; x < rownum; x++) {

				for (int y = 0; y < 1; y++) {

					w.println((String) dtm3.getValueAt(x, y));

				}
			}

			w.close();
		}

		catch (Exception e) {
		}

	}

	// load function for recipe if recipe1 is selected
	private void Recipe1Load() {

		try {

			dtm3.setNumRows(0);

			File f1 = new File("recipe1.txt");
			FileReader in = new FileReader(f1);
			BufferedReader r = new BufferedReader(in);

			File f2 = new File("recipe1.txt");
			FileReader in2 = new FileReader(f2);
			BufferedReader r2 = new BufferedReader(in2);

			int lines = 0;
			while (r2.readLine() != null) {
				lines++;
			}
			r2.close();

			for (int rw = 0; rw < lines / 1; rw++) {
				for (int cl = 0; cl < 1; cl++) {
					rowfields4[cl] = r.readLine();
				}
				dtm3.addRow(rowfields4);

			}
			r.close();

		} catch (Exception e2) {

		}

	}

	// save function for recipe if recipe2 is selected
	public void Recipe2Save() {

		int rownum = dtm3.getRowCount();

		try {

			File f = new File("recipe2.txt");

			FileOutputStream in = new FileOutputStream(f);

			PrintWriter w = new PrintWriter(in);

			for (int x = 0; x < rownum; x++) {

				for (int y = 0; y < 1; y++) {

					w.println((String) dtm3.getValueAt(x, y));

				}
			}

			w.close();
		}

		catch (Exception e) {
		}

	}

	// load function for recipe if recipe2 is selected
	private void Recipe2Load() {

		try {

			dtm3.setNumRows(0);

			File f1 = new File("recipe2.txt");
			FileReader in = new FileReader(f1);
			BufferedReader r = new BufferedReader(in);

			File f2 = new File("recipe2.txt");
			FileReader in2 = new FileReader(f2);
			BufferedReader r2 = new BufferedReader(in2);

			int lines = 0;
			while (r2.readLine() != null) {
				lines++;
			}
			r2.close();

			for (int rw = 0; rw < lines / 1; rw++) {
				for (int cl = 0; cl < 1; cl++) {
					rowfields4[cl] = r.readLine();
				}
				dtm3.addRow(rowfields4);

			}
			r.close();

		} catch (Exception e2) {

		}

	}

	// function to check inventory for ingredients and adds to shopping list if none
	// are found (for recipe 1)
	private void ILChecker1() {

		try {

			File f1 = new File("ingredients1.txt");
			FileReader in1 = new FileReader(f1);
			BufferedReader r1 = new BufferedReader(in1);

			File f2 = new File("ingredients1.txt");
			FileReader in2 = new FileReader(f2);
			BufferedReader r2 = new BufferedReader(in2);

			File f3 = new File("inventorylist.txt");
			FileReader in3 = new FileReader(f3);
			BufferedReader r3 = new BufferedReader(in3);

			File f4 = new File("inventorylist.txt");
			FileReader in4 = new FileReader(f4);
			BufferedReader r4 = new BufferedReader(in4);

			File f5 = new File("shoppinglist.txt");
			FileReader in5 = new FileReader(f5);
			BufferedReader r5 = new BufferedReader(in5);

			File f6 = new File("shoppinglist.txt");
			FileReader in6 = new FileReader(f6);
			BufferedReader r6 = new BufferedReader(in6);

			File f = new File("shoppinglist.txt");
			FileOutputStream in = new FileOutputStream(f);
			PrintWriter w = new PrintWriter(in);

			int lines = 0;
			while (r2.readLine() != null) {
				lines++;
			}
			r2.close();

			int lines2 = 0;
			while (r4.readLine() != null) {
				lines2++;
			}
			r4.close();

			int lines3 = 0;
			while (r5.readLine() != null) {
				lines3++;
			}
			r5.close();

			int numrows1 = lines / 5;
			int numrows2 = lines2 / 5;
			int numrows3 = lines3 / 5;

			String[] fullingredient = new String[lines];
			String[] fullinventory = new String[lines2];
			String[] productingredient = new String[numrows1];
			String[] productinventory = new String[numrows2];
			String[] fullshopping = new String[lines3];

			for (int x = 0; x < lines; x++) {
				fullingredient[x] = r1.readLine();
			}

			int index = 0;

			for (int x = 0; x < lines; x += 5) {

				productingredient[index] = fullingredient[x];
				index++;

			}

			for (int x = 0; x < lines2; x++) {

				fullinventory[x] = r3.readLine();

			}

			int index2 = 0;

			for (int x = 0; x < lines2; x += 5) {

				productinventory[index2] = fullinventory[x];
				index2++;

			}

			for (int x = 0; x < lines3; x++) {
				fullshopping[x] = r6.readLine();
			}

			for (int index3 = 0; index3 < numrows1; index3++) {
				for (int index4 = 0; index4 < numrows2; index4++) {

					if (productingredient[index3] != productinventory[index4]) {

						for (int x = 0; x < numrows3; x++) { // this part didn't work. writing the original list back
							for (int y = 0; y < 5; y++) {
								w.println(r6.readLine());
							}
						}

						int rownum1 = dtm2.getRowCount();
						for (int x = 0; x < rownum1; x++) {
							for (int y = 0; y < 5; y++) {
								w.println(dtm2.getValueAt(x, y));
							}
						}

						r1.close();
						r3.close();
						r6.close();
						w.close();

					}

				}
			}

		}

		catch (Exception e) {
		}

	}

	// function to check inventory for ingredients and adds to shopping list if none
	// are found (for recipe 2)
	private void ILChecker2() {

		try {

			File f1 = new File("ingredients2.txt");
			FileReader in1 = new FileReader(f1);
			BufferedReader r1 = new BufferedReader(in1);

			File f2 = new File("ingredients2.txt");
			FileReader in2 = new FileReader(f2);
			BufferedReader r2 = new BufferedReader(in2);

			File f3 = new File("inventorylist.txt");
			FileReader in3 = new FileReader(f3);
			BufferedReader r3 = new BufferedReader(in3);

			File f4 = new File("inventorylist.txt");
			FileReader in4 = new FileReader(f4);
			BufferedReader r4 = new BufferedReader(in4);

			File f5 = new File("shoppinglist.txt");
			FileReader in5 = new FileReader(f5);
			BufferedReader r5 = new BufferedReader(in5);

			File f6 = new File("shoppinglist.txt");
			FileReader in6 = new FileReader(f6);
			BufferedReader r6 = new BufferedReader(in6);

			File f = new File("shoppinglist.txt");
			FileOutputStream in = new FileOutputStream(f);
			PrintWriter w = new PrintWriter(in);

			int lines = 0;
			while (r2.readLine() != null) {
				lines++;
			}
			r2.close();

			int lines2 = 0;
			while (r4.readLine() != null) {
				lines2++;
			}
			r4.close();

			int lines3 = 0;
			while (r5.readLine() != null) {
				lines3++;
			}
			r5.close();

			int numrows1 = lines / 5;
			int numrows2 = lines2 / 5;
			int numrows3 = lines3 / 5;

			String[] fullingredient = new String[lines];
			String[] fullinventory = new String[lines2];
			String[] productingredient = new String[numrows1];
			String[] productinventory = new String[numrows2];
			String[] fullshopping = new String[lines3];

			for (int x = 0; x < lines; x++) {
				fullingredient[x] = r1.readLine();
			}

			int index = 0;

			for (int x = 0; x < lines; x += 5) {

				productingredient[index] = fullingredient[x];
				index++;

			}

			for (int x = 0; x < lines2; x++) {

				fullinventory[x] = r3.readLine();

			}

			int index2 = 0;

			for (int x = 0; x < lines2; x += 5) {

				productinventory[index2] = fullinventory[x];
				index2++;

			}

			for (int x = 0; x < lines3; x++) {
				fullshopping[x] = r6.readLine();
			}

			for (int index3 = 0; index3 < numrows1; index3++) {
				for (int index4 = 0; index4 < numrows2; index4++) {

					if (productingredient[index3] != productinventory[index4]) {

						for (int x = 0; x < numrows3; x++) { // this part didn't work. writing the original list back
							for (int y = 0; y < 5; y++) {
								w.println(r6.readLine());
							}
						}

						int rownum1 = dtm2.getRowCount();
						for (int x = 0; x < rownum1; x++) {
							for (int y = 0; y < 5; y++) {
								w.println(dtm2.getValueAt(x, y));
							}
						}

						r1.close();
						r3.close();
						r6.close();
						w.close();

					}

				}
			}

		}

		catch (Exception e) {
		}

	}

}