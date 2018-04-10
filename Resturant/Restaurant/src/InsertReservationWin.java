import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class InsertReservationWin extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable customerTable;
	private JTable dishTable;
	private String order = AdminDashboardWin.order;

	public InsertReservationWin() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(700, 500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setTitle("Order a Dish");
		getContentPane().setLayout(null);

		JLabel lblFillInThe = new JLabel("Select a customer and a dish to create a new reservation:");
		lblFillInThe.setFont(new Font("Sitka Subheading", Font.BOLD, 14));
		lblFillInThe.setHorizontalAlignment(SwingConstants.LEFT);
		lblFillInThe.setBounds(10, 11, 400, 20);
		getContentPane().add(lblFillInThe);

		JPanel panel = new JPanel();
		//panel.setBounds(10, 42, 350, 160);
		panel.setBounds(10, 42, 664, 358);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane customerScrollPane = new JScrollPane();
		customerScrollPane.setBounds(0, 0, 329, 358);
		customerTable = new JTable();
		customerScrollPane.setViewportView(customerTable);
		panel.add(customerScrollPane);
		showCustomerTable(order);
		customerTable.setRowSelectionAllowed(true);
		customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane dishScrollPane = new JScrollPane();
		dishScrollPane.setBounds(335, 0, 329, 358);
		dishTable = new JTable();
		dishScrollPane.setViewportView(dishTable);
		panel.add(dishScrollPane);
		showDishTable(order);
		dishTable.setRowSelectionAllowed(true);
		dishTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(238,411, 209, 37);
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeFrame();
			}
		});
		panel_1.add(btnCancel);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Dish\n");
				int[] dishVal = getRowsValues(dishTable);
				for (int i = 0; i < dishVal.length; i++) {
					System.out.println("dish = " + dishVal[i]);
				}
				
				System.out.println("Customer\n");
				int[] customerVal = getRowsValues(customerTable);
				for (int i = 0; i < customerVal.length; i++) {
					System.out.println("customer = " + customerVal[i]);
				}
				
				if(dishVal.length != 0 && customerVal.length != 0) {
					Main.dbCon.insertIntoReservation(customerVal, dishVal);
				}
				
				closeFrame();

			}
		});
		panel_1.add(btnOk);
		
		
	}

	private void closeFrame() {
		super.dispose();
	}
	private void showCustomerTable(String order) {
		ResultSet rs = Main.dbCon.getCustomerData(order); 
		if (rs != null) {
		DefaultTableModel model = new DefaultTableModel();
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
			for (int i = 0; i<rsmd.getColumnCount(); i++)
			{
				String name = rsmd.getColumnName(i+1);
				model.addColumn(name);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		 
		
		try {
			if (!rs.isBeforeFirst()) {    
			    System.out.println("No data"); 
			}
			while(rs.next())
			{
			    int custId = rs.getInt(1);
			    String fname = rs.getString(2);
			    String lName = rs.getString(3);
			    String telNum = rs.getString(4);
			    System.out.println(custId + " " + fname + " " + lName + " " +telNum + " ");
			    model.addRow(new Object[]{custId, fname, lName, telNum});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		customerTable.setModel(model);
		System.out.println(model.getColumnName(1));
		}
	}
	private void showDishTable(String order) {
		ResultSet rs = Main.dbCon.getDishData(order); 
		if (rs != null) {
		DefaultTableModel model = new DefaultTableModel();
		ResultSetMetaData rsmd;
		try {
			rsmd = rs.getMetaData();
			for (int i = 0; i<rsmd.getColumnCount(); i++)
			{
				String name = rsmd.getColumnName(i+1);
				model.addColumn(name);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		 
		
		try {
			if (!rs.isBeforeFirst()) {    
			    System.out.println("No data"); 
			}
			while(rs.next())
			{
			    int dishId = rs.getInt(1);
			    String name = rs.getString(2);
			    String type = rs.getString(3);
			    String description = rs.getString(4);
			    System.out.println(dishId + " " +name + " " +type + " " +description + " ");
			    model.addRow(new Object[]{dishId, name, type, description});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dishTable.setModel(model);
		System.out.println(model.getColumnName(1));
		}
	}
	public static int[] getRowsValues(JTable table) {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    int size = table.getSelectedRowCount();
	    int[] rowsID = new int[size];
	    int j = 0;
	    
	    if (table.getRowCount() > 0) {
	        if (table.getSelectedRowCount() > 0) {
	            int selectedRow[] = table.getSelectedRows();
	            for (int i : selectedRow) { 
	                int id = Integer.parseInt(table.getValueAt(i, 0).toString());
	                rowsID[j] = id;
	                j++;
	                //double val1 = Double.parseDouble(table.getValueAt(i, 1).toString());
	                //double val2 = Double.parseDouble(table.getValueAt(i, 2).toString());
	              //  model.removeRow(i);
	            }
	        }
	    }
	    return rowsID;
	}
	
}
