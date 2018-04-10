import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class UserDasboardWin extends JFrame {
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable table;
	private String selectedRadioBtn;

	public UserDasboardWin() {
		setTitle("Welcome");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setBounds(200, 100, 800, 533);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		getContentPane().setLayout(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Main.dbCon.closeConnection();
			}
		});

		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(0, 0, 784, 500);
		getContentPane().add(splitPane);

		JPanel rightSideTablePanel = new JPanel();
		rightSideTablePanel.setBorder(null);
		splitPane.setRightComponent(rightSideTablePanel);
		rightSideTablePanel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		table = new JTable();
		scrollPane.setViewportView(table);
		rightSideTablePanel.add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBounds(0, 31, 104, -21);
		// getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		// Box leftMenuVbox = Box.createVerticalBox();
		Box leftMenuVbox = new Box(BoxLayout.PAGE_AXIS);
		panel.add(leftMenuVbox, BorderLayout.CENTER);

		Box rdbtnVbox = Box.createVerticalBox();
		leftMenuVbox.add(rdbtnVbox);

		splitPane.setLeftComponent(panel);
		splitPane.setDividerLocation(0.175);

		JPanel viewsPanel = new JPanel();
		rdbtnVbox.add(viewsPanel);
		viewsPanel.setLayout(null);

		JLabel lblSelectWhatTo = new JLabel("Select what to view:");
		lblSelectWhatTo.setBounds(0, 0, 154, 43);
		viewsPanel.add(lblSelectWhatTo);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 39, 154, 159);
		viewsPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(5, 1, 0, 0));

		JRadioButton rdbtnTables = new JRadioButton("Tables");
		panel_1.add(rdbtnTables);
		buttonGroup.add(rdbtnTables);
		
				// rdbtnTables.setSelected(true);
		
				JRadioButton rdbtnMenu = new JRadioButton("Menu");
				panel_1.add(rdbtnMenu);
				buttonGroup.add(rdbtnMenu);
				
						JRadioButton rdbtnEmployees = new JRadioButton("Employees");
						panel_1.add(rdbtnEmployees);
						buttonGroup.add(rdbtnEmployees);
						
								JRadioButton rdbtnCustomers = new JRadioButton("Customers");
								panel_1.add(rdbtnCustomers);
								rdbtnCustomers.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										displayCustomersData();
										selectedRadioBtn = "Customer";
									}
								});
								buttonGroup.add(rdbtnCustomers);
								
										JRadioButton rdbtnReservations = new JRadioButton("Reservations");
										panel_1.add(rdbtnReservations);
										buttonGroup.add(rdbtnReservations);
										rdbtnReservations.addActionListener(new ActionListener() {

											@Override
											public void actionPerformed(ActionEvent e) {
												displayReservationData();
												selectedRadioBtn = "Reservation";
											}
										});
						rdbtnEmployees.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								displayEmployeesData();
								selectedRadioBtn = "Employee";
							}
						});
				rdbtnMenu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						displayDishData();
						selectedRadioBtn = "Dish";
					}
				});
		
				rdbtnTables.addActionListener(new ActionListener() {
		
					@Override
					public void actionPerformed(ActionEvent e) {
						displayTablesData();
						selectedRadioBtn = "Table";
					}
				});

		// rdbtnTables.getActionListeners()[0].actionPerformed(new
		// ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {});
	}
	
private void displayTablesData(){
		
		ResultSet rs = Main.dbCon.getTablesData(null); 
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
//			table.setModel(DbUtils.resultSetToTableModel(rs));
			while(rs.next())
			{
			    int tableId = rs.getInt(1);
			    int numOfSeats = rs.getInt(2);
			    String isOccupied = rs.getString(3);
			    int numOfOccupiedSeats = rs.getInt(4);
			    System.out.println(tableId + " " +numOfSeats + " " +isOccupied + " " +numOfOccupiedSeats + " ");
			    model.addRow(new Object[]{tableId, numOfSeats, isOccupied, numOfOccupiedSeats});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table.setModel(model);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		System.out.println(model.getColumnName(1));
		}
	}
	
	public void displayDishData(){
		
		ResultSet rs = Main.dbCon.getDishData(null); 
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
		table.setModel(model);		
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		System.out.println(model.getColumnName(1));
		}
	}
	
	private void displayCustomersData(){
		
		ResultSet rs = Main.dbCon.getCustomerData(null); 
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
		table.setModel(model);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		System.out.println(model.getColumnName(1));
		}
	}
	
	private void displayReservationData(){
		
		ResultSet rs = Main.dbCon.getReservationData(null); 
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
			    int resId = rs.getInt(1);
			    int customerId = rs.getInt(2);
			    int dishId = rs.getInt(3);
			   // String custFName = rs.getString(2);
			   // String custLName = rs.getString(3);
			  //  String telNum = rs.getString(4);
			  //  int numOfGuests = rs.getInt(5);
			 /*   System.out.println(resId + " " +custFName + " " +custLName + " " +telNum + " " + numOfGuests);
			    model.addRow(new Object[]{resId, custFName, custLName, telNum, numOfGuests});*/
			    System.out.println(resId + " " +customerId + " " +dishId);
			    model.addRow(new Object[]{resId, customerId, dishId});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table.setModel(model);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		System.out.println(model.getColumnName(1));
		}
	}
	
	private void displayEmployeesData(){

		ResultSet rs = Main.dbCon.getEmployeesData(null); 
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
			    int empId = rs.getInt(1);
			    String fName = rs.getString(2);
			    String lName = rs.getString(3);
			    String address = rs.getString(4);
			    String telNum = rs.getString(5);
			    String role = rs.getString(6);
			    String hireDate = rs.getString(7);
			    System.out.println(empId + " " +fName + " " +lName + " " +address + " "+telNum + " "+role + " "+hireDate + " ");
			    model.addRow(new Object[]{empId, fName, lName, address, telNum, role, hireDate});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table.setModel(model);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		System.out.println(model.getColumnName(1));
		}
	}
}
