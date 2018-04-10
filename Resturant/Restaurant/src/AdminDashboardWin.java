import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.Color;

public class AdminDashboardWin extends JFrame {
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTable table;
	private String selectedRadioBtn;
	private JLabel lb_no_selection;
	public static String order = "ASC";
	
	@SuppressWarnings("serial")
	public AdminDashboardWin() {
		setTitle("Welcome");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setBounds(200, 100, 800, 700);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		getContentPane().setLayout(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e)
		    {
		        Main.dbCon.closeConnection();
		    }
		});
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBounds(0, 0, 784, 550);
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
		//getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		//Box leftMenuVbox = Box.createVerticalBox();
		Box leftMenuVbox = new Box(BoxLayout.PAGE_AXIS);
		panel.add(leftMenuVbox, BorderLayout.CENTER);
		
		Box rdbtnVbox = Box.createVerticalBox();
		leftMenuVbox.add(rdbtnVbox);
		
		splitPane.setLeftComponent(panel);
		//splitPane.setDividerLocation(0.175);
		
		JSeparator separator_1 = new JSeparator();
		rdbtnVbox.add(separator_1);
		
		JPanel viewsPanel = new JPanel();
		rdbtnVbox.add(viewsPanel);
		viewsPanel.setLayout(new GridLayout(0, 1, 0, 1));
		
		JLabel lblSelectWhatTo = new JLabel("Select what to view:");
		viewsPanel.add(lblSelectWhatTo);
		
		JRadioButton rdbtnTables = new JRadioButton("Tables");
		viewsPanel.add(rdbtnTables);
		buttonGroup.add(rdbtnTables);
		
		//rdbtnTables.setSelected(true);
		
		JRadioButton rdbtnMenu = new JRadioButton("Menu");
		viewsPanel.add(rdbtnMenu);
		buttonGroup.add(rdbtnMenu);
		
		JRadioButton rdbtnEmployees = new JRadioButton("Employees");
		viewsPanel.add(rdbtnEmployees);
		buttonGroup.add(rdbtnEmployees);
		
		JRadioButton rdbtnCustomers = new JRadioButton("Customers");
		rdbtnCustomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayCustomersData();
				selectedRadioBtn = "Customer";
			}
		});
		viewsPanel.add(rdbtnCustomers);
		buttonGroup.add(rdbtnCustomers);
		
		JRadioButton rdbtnReservations = new JRadioButton("Reservations");
		viewsPanel.add(rdbtnReservations);
		buttonGroup.add(rdbtnReservations);
		
		
		JPanel rbPanel = new JPanel();
		rbPanel.setLayout(new FlowLayout());
		JRadioButton ascending = new JRadioButton("Ascending");
		ascending.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				order = "ASC";
				getSelectedRadioBtn();
			}
		});
		JRadioButton descending = new JRadioButton("Descending");
		descending.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				order = "DESC";
				displayTablesData();
				getSelectedRadioBtn();
			}
		});
		
		
		ButtonGroup group = new ButtonGroup();
		ascending.setSelected(true);
		group.add(ascending);
		group.add(descending);
		rbPanel.add(ascending);
		rbPanel.add(descending);
		viewsPanel.add(rbPanel);
		

		JSeparator separator = new JSeparator();
		viewsPanel.add(separator);
		
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
		
	//	rdbtnTables.getActionListeners()[0].actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null) {
	 //   });
		
		JPanel buttonsPanel = new JPanel();
		rdbtnVbox.add(buttonsPanel);
		buttonsPanel.setLayout(new GridLayout(6, 1, 0, 3));
		
		JLabel lblInsert = new JLabel("Insert:");
		buttonsPanel.add(lblInsert);
		
		JButton btnTable = new JButton("Table");
		btnTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				java.awt.EventQueue.invokeLater(new Runnable() {
					public void run() {
							InsertTableWin win = new InsertTableWin();
						}
				});
				
			}
		});
		buttonsPanel.add(btnTable);
		btnTable.setToolTipText("Insert a new record of a Table");
		
		JButton btnDish = new JButton("Dish");
		btnDish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertDishWin win = new InsertDishWin();
			}
		});
		buttonsPanel.add(btnDish);
		btnDish.setToolTipText("Insert a new record of a Dish");
		
		JButton btnEmployee = new JButton("Employee");
		btnEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertEmployeeWin win = new InsertEmployeeWin();
			}
		});
		buttonsPanel.add(btnEmployee);
		btnEmployee.setToolTipText("Insert a new record of an Employee");
		
		JButton btnCustomer = new JButton("Customer");
		btnCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertCustomerWin win = new InsertCustomerWin();
			}
		});
		buttonsPanel.add(btnCustomer);
		btnEmployee.setToolTipText("Insert a new record of an Customer");
		
		JButton btnReservation = new JButton("Order a Dish");
		btnReservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertReservationWin win = new InsertReservationWin();
			}
		});
		buttonsPanel.add(btnReservation);
		btnReservation.setToolTipText("Insert a new record of a Reservation");
		
		JSeparator separator_2 = new JSeparator();
		rdbtnVbox.add(separator_2);
		
		JPanel deletePanel = new JPanel();
		rdbtnVbox.add(deletePanel);
		deletePanel.setLayout(new GridLayout(2, 1, 0, 0));
		rdbtnVbox.add(deletePanel);
		
		JButton btnDelete = new JButton("Delete Record");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] rows = InsertReservationWin.getRowsValues(table);
				if(rows.length > 0) {
					switch (selectedRadioBtn) {
			            case "Table":  
			            	Main.dbCon.deleteTableRecord(rows);
			            	displayTablesData();
			                     break;
			            case "Dish":  
			            	Main.dbCon.deleteDishRecord(rows);
			            	displayDishData();
			                     break;
			            case "Employee":
			            	Main.dbCon.deleteEmployeeRecord(rows);
			            	displayEmployeesData();
			                     break;
			            case "Customer":  
			            	Main.dbCon.deleteCustomerRecord(rows);
			            	displayCustomersData();
			                     break;
			            case "Reservation":
			            	Main.dbCon.deleteReservationRecord(rows);
			            	displayReservationData();
			                     break;
					}
					lb_no_selection.setVisible(false);
				}
				else {
					lb_no_selection.setVisible(true);
				}
			}
		});
		
		JPanel panel_1 = new JPanel();
		deletePanel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblDelete = new JLabel("Delete:");
		panel_1.add(lblDelete);
		
		lb_no_selection = new JLabel("Select Row");
		panel_1.add(lb_no_selection);
		lb_no_selection.setForeground(Color.RED);
		lb_no_selection.setVisible(false);
		

		
		deletePanel.add(btnDelete);
		btnDelete.setToolTipText("Delete selected row from table");
		
		JLabel lblMenu = new JLabel("MENU");
		panel.add(lblMenu, BorderLayout.NORTH);
		lblMenu.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 11));
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel actionsPanel = new JPanel();
		actionsPanel.setBounds(0, 593, 774, 68);
		getContentPane().add(actionsPanel);
		actionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		

		JButton btnFilter = new JButton("Filter Tables");
	
		btnFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FilterWin win = new FilterWin();
			}
		});
		actionsPanel.add(btnFilter);
		
		JButton btnOrderCount = new JButton("Reservations Count");
		btnOrderCount.setToolTipText("Select a date to display how many reservations were ordered.");
		btnOrderCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReservationCountWin win = new ReservationCountWin();
			}
		});
		
		actionsPanel.add(btnOrderCount);
		
		JButton btnCountDishes = new JButton("Today's dishes counter");
		btnCountDishes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TodayDishCounterWin win = new TodayDishCounterWin();
			}
		});
		actionsPanel.add(btnCountDishes);
		
		JButton btnPersonnelStatus = new JButton("Personnel Status");
		btnPersonnelStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonnelStatusWin win = new PersonnelStatusWin();
			}
		});
		actionsPanel.add(btnPersonnelStatus);
		
		JButton btnFindTable = new JButton("Find table for reservation");
		btnFindTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindTableWin win = new FindTableWin();
			}
		});
		actionsPanel.add(btnFindTable);
		
		JButton btnCustomersTable = new JButton("Customer's Table");
		btnCustomersTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CustomerTableWin win = new CustomerTableWin();
			}
		});
		btnCustomersTable.setToolTipText("Select a table to view which customer is linked to it.");
		actionsPanel.add(btnCustomersTable);
		
		JButton btnChangeTableOccupancy = new JButton("Change Table Occupancy");
		btnChangeTableOccupancy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeOccupancyWin win = new ChangeOccupancyWin();
			}
		});
		actionsPanel.add(btnChangeTableOccupancy);
		
		JLabel lblMiscActions = new JLabel("Misc. Actions");
		lblMiscActions.setBounds(0, 569, 83, 14);
		getContentPane().add(lblMiscActions);
		
	}
	
	private void getSelectedRadioBtn()
	{
		switch (selectedRadioBtn) {
        case "Table":  
        	displayTablesData();
                 break;
        case "Dish": 
        	displayDishData();
                 break;
        case "Employee":
        	displayEmployeesData();
                 break;
        case "Customer":  
        	displayCustomersData();
                 break;
        case "Reservation":
        	displayReservationData();
                 break;
	}
	}
	
	private void displayTablesData(){
		
		ResultSet rs = Main.dbCon.getTablesData(order); 
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
		table.setModel(model);		
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		System.out.println(model.getColumnName(1));
		}
	}
	
	private void displayCustomersData(){
		
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
			    System.out.println(custId + " " + fname + " " + lName + " " +telNum);
			    model.addRow(new Object[]{custId, fname, lName, telNum});
			   // int guestNum = rs.getInt(5);
			   // System.out.println(custId + " " + fname + " " + lName + " " +telNum + " " +guestNum);
			   // model.addRow(new Object[]{custId, fname, lName, telNum, guestNum});
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
		
		ResultSet rs = Main.dbCon.getReservationData(order); 
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
		
		ResultSet rs = Main.dbCon.getEmployeesData(order); 
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
