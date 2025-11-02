package kitchGUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.RowFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUIDesign extends JFrame {

	private JPanel contentPane;
	private JTable table;
	DefaultTableModel dtm = new DefaultTableModel();
	final Object[] rowfields = new Object[5];
	
	private JTable table_1;
	DefaultTableModel dtm1 = new DefaultTableModel();
	final Object[] rowfields1 = new Object[5];
	
	final Object[] rowfieldsrestock = new Object[5];
	
	private JTextField ILproducttf;
	private JTextField ILbrandtf;
	private JTextField ILcategorytf;
	private JTextField ILquantitytf;
	private JTextField ILexpirytf;
	private JTextField ILsearchtf;
	private JTextField SLproducttf;
	private JTextField SLbrandtf;
	private JTextField SLcategorytf;
	private JTextField SLquantitytf;
	private JTextField SLexpirytf;
	private JTextField SLsearchtf;
	
	/**
	 * Launch the application.
	 */
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIDesign frame = new GUIDesign();
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
	public GUIDesign() {
		
		setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 712, 463);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 55, 322, 183);
		contentPane.add(scrollPane);
		
		
		table =  new JTable();
		
		
		table.addMouseListener(new MouseAdapter() { //Enables selection of rows
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedrow = table.getSelectedRow();
				ILproducttf.setText((String)table.getValueAt(selectedrow, 0));
				ILbrandtf.setText((String)table.getValueAt(selectedrow, 1));
				ILcategorytf.setText((String)table.getValueAt(selectedrow, 2));
				ILquantitytf.setText((String)table.getValueAt(selectedrow, 3));
				ILexpirytf.setText((String)table.getValueAt(selectedrow, 4));
			}
		});
		
		scrollPane.setViewportView(table);
		Object [] columns = {"Product", "Brand", "Category", "Quantity", "Expiry"};
		dtm.setColumnIdentifiers(columns);
		table.setModel(dtm);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(340, 55, 347, 183);
		contentPane.add(scrollPane_1);
		
		
		table_1 = new JTable();
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { //enables selection of rows
				int selectedrow1 = table_1.getSelectedRow();
				SLproducttf.setText((String)table_1.getValueAt(selectedrow1, 0));
				SLbrandtf.setText((String)table_1.getValueAt(selectedrow1, 1));
				SLcategorytf.setText((String)table_1.getValueAt(selectedrow1, 2));
				SLquantitytf.setText((String)table_1.getValueAt(selectedrow1, 3));
				SLexpirytf.setText((String)table_1.getValueAt(selectedrow1, 4));
			}
		});
		scrollPane_1.setViewportView(table_1);
		Object [] columns1 = {"Product", "Brand", "Category", "Quantity", "Expiry"};
		dtm1.setColumnIdentifiers(columns1);
		table_1.setModel(dtm1);
		
		JButton btnNewButton = new JButton("Recipe List"); //Swaps window to recipe list window
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			RecipeList recipelist = new RecipeList();
			recipelist.setVisible(true);
			
			dispose();
			}
		});
		
		btnNewButton.setBounds(570, 13, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Product");
		lblNewLabel.setBounds(16, 250, 61, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Brand");
		lblNewLabel_1.setBounds(16, 278, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Category");
		lblNewLabel_1_1.setBounds(16, 304, 61, 16);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Quantity");
		lblNewLabel_1_1_1.setBounds(16, 332, 61, 16);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Expiry");
		lblNewLabel_1_1_1_1.setBounds(16, 360, 61, 16);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		ILproducttf = new JTextField();
		ILproducttf.setBounds(89, 245, 130, 26);
		contentPane.add(ILproducttf);
		ILproducttf.setColumns(10);
		
		ILbrandtf = new JTextField();
		ILbrandtf.setColumns(10);
		ILbrandtf.setBounds(89, 273, 130, 26);
		contentPane.add(ILbrandtf);
		
		ILcategorytf = new JTextField();
		ILcategorytf.setColumns(10);
		ILcategorytf.setBounds(89, 299, 130, 26);
		contentPane.add(ILcategorytf);
		
		ILquantitytf = new JTextField();
		ILquantitytf.setColumns(10);
		ILquantitytf.setBounds(89, 327, 130, 26);
		contentPane.add(ILquantitytf);
		
		ILexpirytf = new JTextField();
		ILexpirytf.setColumns(10);
		ILexpirytf.setBounds(89, 355, 130, 26);
		contentPane.add(ILexpirytf);
		
		ILLoadStart();
		SLLoadStart();
		
		JButton ILadd = new JButton("Add"); // add button for inventory list
		ILadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ILProduct = ILproducttf.getText();
				String ILBrand = ILbrandtf.getText();
				String ILCategory = ILcategorytf.getText();
				String ILQuantity = ILquantitytf.getText();
				String ILExpiry = ILexpirytf.getText();
				
				rowfields[0] = ILProduct;
				rowfields[1] = ILBrand;
				rowfields[2] = ILCategory;
				rowfields[3] = ILQuantity;
				rowfields[4] = ILExpiry;
				
				dtm.addRow(rowfields);
				
				ILproducttf.setText(null);
				ILbrandtf.setText(null);
				ILcategorytf.setText(null);
				ILquantitytf.setText(null);
				ILexpirytf.setText(null);
				
				ILSave();
				MinAdd();
				
			}
		});
		ILadd.setForeground(Color.BLACK);
		ILadd.setBackground(Color.WHITE);
		ILadd.setBounds(6, 388, 91, 27);
		contentPane.add(ILadd);
		
		JButton ILdelete = new JButton("Delete"); //delete button for inventory list
		ILdelete.setForeground(Color.BLACK);
		ILdelete.setBackground(Color.WHITE);
		ILdelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				dtm.removeRow(selectedRow);
				
				ILproducttf.setText(null);
				ILbrandtf.setText(null);
				ILcategorytf.setText(null);
				ILquantitytf.setText(null);
				ILexpirytf.setText(null);
				
				ILSave();
				MinAdd();
				
			}
		});
		ILdelete.setBounds(99, 388, 91, 27);
		contentPane.add(ILdelete);
		
		JButton ILupdate = new JButton("Update"); //update button for inventory list
		ILupdate.setForeground(Color.BLACK);
		ILupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String ILProduct = ILproducttf.getText();
				String ILBrand = ILbrandtf.getText();
				String ILCategory = ILcategorytf.getText();
				String ILQuantity = ILquantitytf.getText();
				String ILExpiry = ILexpirytf.getText();
				
				dtm.setValueAt(ILProduct, table.getSelectedRow(), 0);
				dtm.setValueAt(ILBrand, table.getSelectedRow(), 1);
				dtm.setValueAt(ILCategory, table.getSelectedRow(), 2);
				dtm.setValueAt(ILQuantity, table.getSelectedRow(), 3);
				dtm.setValueAt(ILExpiry, table.getSelectedRow(), 4);
				
				ILproducttf.setText(null);
				ILbrandtf.setText(null);
				ILcategorytf.setText(null);
				ILquantitytf.setText(null);
				ILexpirytf.setText(null);
				
				ILSave();
				MinAdd();
				
			}
		});
		ILupdate.setBackground(Color.WHITE);
		ILupdate.setBounds(193, 388, 91, 27);
		contentPane.add(ILupdate);
		
		ILsearchtf = new JTextField();
		ILsearchtf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { //dynamic search function for inventory list
				
				DefaultTableModel searchtable = (DefaultTableModel)table.getModel();
				String search = ILsearchtf.getText().toLowerCase();
				TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(searchtable);
				table.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search));
				
			}
		});
		ILsearchtf.setBounds(237, 278, 91, 26);
		contentPane.add(ILsearchtf);
		ILsearchtf.setColumns(10);
		
		JLabel lblKitchProject = new JLabel("Kitch Project");
		lblKitchProject.setBounds(294, 18, 84, 16);
		contentPane.add(lblKitchProject);
		
		JLabel lblNewLabel_2 = new JLabel("Product");
		lblNewLabel_2.setBounds(350, 250, 61, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_2 = new JLabel("Brand");
		lblNewLabel_1_2.setBounds(350, 278, 61, 16);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Category");
		lblNewLabel_1_1_2.setBounds(350, 304, 61, 16);
		contentPane.add(lblNewLabel_1_1_2);
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("Quantity");
		lblNewLabel_1_1_1_2.setBounds(350, 332, 61, 16);
		contentPane.add(lblNewLabel_1_1_1_2);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Expiry");
		lblNewLabel_1_1_1_1_1.setBounds(350, 360, 61, 16);
		contentPane.add(lblNewLabel_1_1_1_1_1);
		
		SLproducttf = new JTextField();
		SLproducttf.setColumns(10);
		SLproducttf.setBounds(419, 245, 130, 26);
		contentPane.add(SLproducttf);
		
		SLbrandtf = new JTextField();
		SLbrandtf.setColumns(10);
		SLbrandtf.setBounds(419, 273, 130, 26);
		contentPane.add(SLbrandtf);
		
		SLcategorytf = new JTextField();
		SLcategorytf.setColumns(10);
		SLcategorytf.setBounds(419, 299, 130, 26);
		contentPane.add(SLcategorytf);
		
		SLquantitytf = new JTextField();
		SLquantitytf.setColumns(10);
		SLquantitytf.setBounds(419, 327, 130, 26);
		contentPane.add(SLquantitytf);
		
		SLexpirytf = new JTextField();
		SLexpirytf.setColumns(10);
		SLexpirytf.setBounds(419, 355, 130, 26);
		contentPane.add(SLexpirytf);
		
		JButton SLadd = new JButton("Add"); //add button for shopping list
		SLadd.setForeground(Color.BLACK);
		SLadd.setBackground(Color.WHITE);
		SLadd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String SLProduct = SLproducttf.getText();
				String SLBrand = SLbrandtf.getText();
				String SLCategory = SLcategorytf.getText();
				String SLQuantity = SLquantitytf.getText();
				String SLExpiry = SLexpirytf.getText();
				
				rowfields1[0] = SLProduct;
				rowfields1[1] = SLBrand;
				rowfields1[2] = SLCategory;
				rowfields1[3] = SLQuantity;
				rowfields1[4] = SLExpiry;
				
				dtm1.addRow(rowfields1);
				
				SLproducttf.setText(null);
				SLbrandtf.setText(null);
				SLcategorytf.setText(null);
				SLquantitytf.setText(null);
				SLexpirytf.setText(null);
				
				SLSave();
				
			}
		});
		SLadd.setBounds(333, 388, 91, 27);
		contentPane.add(SLadd);
		
		JButton SLdelete = new JButton("Delete"); //delete button for shopping list
		SLdelete.setForeground(Color.BLACK);
		SLdelete.setBackground(Color.WHITE);
		SLdelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow1 = table_1.getSelectedRow();
				dtm1.removeRow(selectedRow1);
				
				SLproducttf.setText(null);
				SLbrandtf.setText(null);
				SLcategorytf.setText(null);
				SLquantitytf.setText(null);
				SLexpirytf.setText(null);
				
				SLSave();
				
			}
		});
		SLdelete.setBounds(429, 388, 91, 27);
		contentPane.add(SLdelete);
		
		JButton SLupdate = new JButton("Update"); //update button for shopping list
		SLupdate.setForeground(Color.BLACK);
		SLupdate.setBackground(Color.WHITE);
		SLupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String SLProduct = SLproducttf.getText();
				String SLBrand = SLbrandtf.getText();
				String SLCategory = SLcategorytf.getText();
				String SLQuantity = SLquantitytf.getText();
				String SLExpiry = SLexpirytf.getText();
				
				dtm1.setValueAt(SLProduct, table_1.getSelectedRow(), 0);
				dtm1.setValueAt(SLBrand, table_1.getSelectedRow(), 1);
				dtm1.setValueAt(SLCategory, table_1.getSelectedRow(), 2);
				dtm1.setValueAt(SLQuantity, table_1.getSelectedRow(), 3);
				dtm1.setValueAt(SLExpiry, table_1.getSelectedRow(), 4);
				
				SLproducttf.setText(null);
				SLbrandtf.setText(null);
				SLcategorytf.setText(null);
				SLquantitytf.setText(null);
				SLexpirytf.setText(null);
				
				SLSave();
				
			}
		});
		SLupdate.setBounds(525, 388, 91, 27);
		contentPane.add(SLupdate);
		
		SLsearchtf = new JTextField();
		SLsearchtf.addKeyListener(new KeyAdapter() { //dynamic search function for shopping list
			@Override
			public void keyReleased(KeyEvent e) {
				
				DefaultTableModel searchtable1 = (DefaultTableModel)table_1.getModel();
				String search = SLsearchtf.getText().toLowerCase();
				TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(searchtable1);
				table_1.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter(search));
				
			}
		});
		SLsearchtf.setColumns(10);
		SLsearchtf.setBounds(570, 278, 91, 26);
		contentPane.add(SLsearchtf);
		
		JLabel lblInventoryList = new JLabel("Inventory List");
		lblInventoryList.setBounds(115, 32, 104, 16);
		contentPane.add(lblInventoryList);
		
		JLabel lblShoppingList = new JLabel("Shopping List");
		lblShoppingList.setBounds(467, 32, 104, 16);
		contentPane.add(lblShoppingList);
		
		JLabel lblNewLabel_3 = new JLabel("Search");
		lblNewLabel_3.setBounds(262, 260, 61, 16);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Search");
		lblNewLabel_3_1.setBounds(599, 260, 61, 16);
		contentPane.add(lblNewLabel_3_1);
		
		JButton btnGoShopping = new JButton("Go Shopping");
		btnGoShopping.setForeground(Color.BLACK);
		btnGoShopping.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //go shopping button
				
				MinAdd();
				
			}
		});
		btnGoShopping.setBackground(Color.WHITE);
		btnGoShopping.setBounds(229, 327, 111, 27);
		contentPane.add(btnGoShopping);
	
	}
	
	//saving function for inventory
	public void ILSave() {

		int rownum = dtm.getRowCount();

		try{

			File f = new File("inventorylist.txt"); 

			FileOutputStream in = new FileOutputStream(f);

			PrintWriter w = new PrintWriter(in); 



			for(int x = 0; x < rownum; x++){

				for(int y = 0 ; y < 5 ; y++){

					w.println((String) dtm.getValueAt(x, y));
					
					}
				}

			w.close();
		}

		catch (Exception e){
		}

	}	
	//saving function for shopping
	private void SLSave() {

		int rownum1 = dtm1.getRowCount();
		
		try{

			File f = new File("shoppinglist.txt"); 

			FileOutputStream in = new FileOutputStream(f);

			PrintWriter w = new PrintWriter(in); 
			
			

			for(int x = 0; x < rownum1; x++){

				for(int y = 0 ; y < 5 ; y++){

					w.println((String) dtm1.getValueAt(x, y));
					
					}
				}

			w.close();
		}

		catch (Exception e){
		}

	}	
	//loading function for inventory
	private void ILLoadStart(){
		
	try
	{
		File f1 = new File( "inventorylist.txt");
		FileReader in = new FileReader (f1);
		BufferedReader r = new BufferedReader(in);

		File f2 = new File( "inventorylist.txt");
		FileReader in2 = new FileReader (f2);
		BufferedReader r2 = new BufferedReader(in2); 
		
		
		int lines = 0;
		while (r2.readLine() != null) 
		{
			lines++;
		}
		r2.close();


		for(int rw = 0; rw < lines/5; rw++)
		{
			for(int cl = 0; cl < 5; cl++)
			{
				rowfields[cl] = r.readLine();
			}
			dtm.addRow(rowfields);

		}
		r.close();

	}
	catch(Exception e2)
	{

	}
	
	}
	//loading function for shopping
	private void SLLoadStart(){
		
	try
	{
		File f1 = new File( "shoppinglist.txt");
		FileReader in = new FileReader (f1);
		BufferedReader r = new BufferedReader(in);

		File f2 = new File( "shoppinglist.txt");
		FileReader in2 = new FileReader (f2);
		BufferedReader r2 = new BufferedReader(in2); 
		
		
		int lines = 0;
		while (r2.readLine() != null) 
		{
			lines++;
		}
		r2.close();


		for(int rw = 0; rw < lines/5; rw++)
		{
			for(int cl = 0; cl < 5; cl++)
			{
				rowfields1[cl] = r.readLine();
			}
			dtm1.addRow(rowfields1);

		}
		r.close();

	}
	catch(Exception e2)
	{

	}
	
	}
	//detection function to automatically check and transfer items to shopping list if insufficient
	private void MinAdd() {
		
		for(int x = 0;x<dtm.getRowCount(); x++) {
			
			//prompt
			if(Integer.parseInt((String) dtm.getValueAt(x, 3)) < 1) {
				
				Object[] ShoppingConfirm = {"Yes","No"};
			    
			    int n = JOptionPane.showOptionDialog(null, "We have found item/s with 0 quantity. Would you like to add to shopping list?", "Choose", 
			    		JOptionPane.YES_NO_OPTION,
			    		JOptionPane.QUESTION_MESSAGE,
			    		null,
			    		ShoppingConfirm,
			    		ShoppingConfirm[0]);
		        
			    if (n == 0)
			    {
			    	//input value
					String minimumconfirm1 = JOptionPane.showInputDialog("How many " + dtm.getValueAt(x, 0) + " would you like to buy?");
					dtm.setValueAt(minimumconfirm1, x, 3);
			    	
					for(int cl = 0; cl < 5; cl++)
					{
						rowfieldsrestock[cl] = dtm.getValueAt(x, cl);
						}
					
					dtm1.addRow(rowfieldsrestock);
					dtm.removeRow(x);

			    }
			    if (n == 1)
			    { 
			    	//retry
			    	JOptionPane.showMessageDialog(null, "Click the Go Shopping button to try again");
			    }
				
			}
		}

		ILSave();
		SLSave();
		
	}
	
	
}


