import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.swing.JTable;

// import oracle.jdbc.OracleTypes;





public class DBconnector {
// ("jdbc:oracle:thin:@localhost:2909:xe", "system", "system");
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:2909:xe";
	private static final String DB_ADMIN = "system"; //pass: system
	private static final String DB_USER = "app_user"; //pass: 111111
	private Connection con;
	private Statement stmt;
	private CallableStatement procstmt;
	// private ResultSet rs;

	public DBconnector(String user, String password) {
		try {
			// step1 load the driver class
			Class.forName(DB_DRIVER);
			System.out.println("Driver loaded");

			// step2 create the connection object
			if (user.equals("admin")) {
				con = DriverManager.getConnection(DB_CONNECTION, DB_ADMIN, password);
				stmt = con.createStatement();
				new AdminDashboardWin();
			}
			else if (user.equals("app_user")) {
				con = DriverManager.getConnection(DB_CONNECTION, DB_USER, password);
				stmt = con.createStatement();
				new UserDasboardWin();
			}
			System.out.println("Database connected");

			// step3 create the statement object
			//stmt = con.createStatement();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertIntoTable(int numOfSeats) {
		try {
			String insertSQL = "insert into TABLE_ values (TABLE_ID_SEQ.nextval, " + numOfSeats + ", 'false', 0)";
			System.out.println(insertSQL);

			// execute insert SQL statement
			stmt.executeUpdate(insertSQL);
			System.out.println("Record is inserted.");
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
	}
	
	public void insertIntoDish(String name, String type, String description) {
		try {
			String insertSQL = "insert into DISH values (DISH_ID_SEQ.nextval, " + "'" + name + "'" + ", "+ "'" + type + "'" + ", " + "'" + description + "'" + ")";
			System.out.println(insertSQL);

			// execute insert SQL statement
			stmt.executeUpdate(insertSQL);
			System.out.println("Record is inserted.");
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
	}
	
	public void insertIntoEmployee(String firstName, String lastName, String address, String telNum, String role) {
		try {
			String insertSQL = "insert into EMPLOYEE values (EMPLOYEE_ID_SEQ.nextval, " + "'" + firstName + "'" + ", "+ "'" + lastName + "'" + ", " + "'" + address + "'" + ", " + "'" + telNum + "'" + ", " + "'" + role + "'" +  ", " + "SYSDATE"+")";
			System.out.println(insertSQL);

			// execute insert SQL statement
			stmt.executeUpdate(insertSQL);
			System.out.println("Record is inserted.");
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
	}
	
	public void insertIntoCustomer(String firstName, String lastName, String telNum) {
		try {
			String insertSQL = "insert into CUSTOMER values (CUSTOMER_ID_SEQ.nextval, " + "'" + firstName + "'" + ", "+ "'" + lastName + "'" + ", " + "'" + telNum + "'" + ")";
			System.out.println(insertSQL);

			// execute insert SQL statement
			stmt.executeUpdate(insertSQL);
			System.out.println("Record is inserted.");
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
	}
	
	public void insertIntoReservation(int[] customerVal, int[] dishVal) {
		for (int j = 0; j < dishVal.length; j++) {
			try {
				String insertSQL = "insert into RESERVATION values (RESERVATION_ID_SEQ.nextval, " + customerVal[0] + ", " + dishVal[j] + " )";
				System.out.println(insertSQL);
	
				// execute insert SQL statement
				stmt.executeUpdate(insertSQL);
				System.out.println("Record is inserted.");
			} catch (SQLException e) {
	
				System.out.println(e.getMessage());
	
			}
		}
	}

	public ResultSet getTablesData(String order) {
		ResultSet rs = null;
		String query;
		try {
			if(order != null) {
				query = "select * from SYSTEM.TABLE_ order by TABLE_ID " + order;
			}
			else {
				query = "select * from SYSTEM.TABLE_";
			}
			System.out.println(query);

			// execute insert SQL statement
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public ResultSet getCustomerData(String order) {
		ResultSet rs = null;
		String query;
		try {
			if(order != null) {
				query = "select * from SYSTEM.CUSTOMER order by CUSTOMER_ID " + order;
			}
			else {
				query = "select * from SYSTEM.CUSTOMER";
			}
			System.out.println(query);

			// execute insert SQL statement
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public ResultSet getDishData(String order) {
		ResultSet rs = null;
		String query;
		try {
			if(order != null) {
				query = "select * from SYSTEM.DISH order by DISH_ID " + order;
			}
			else {
				query = "select * from SYSTEM.DISH";
			}
			System.out.println(query);

			// execute insert SQL statement
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public ResultSet getReservationData(String order) {
		ResultSet rs = null;
		String query;
		try {
			if(order != null) {
				query = "select * from SYSTEM.RESERVATION order by RESERVATION_ID " + order;
			}
			else {
				query = "select * from SYSTEM.RESERVATION";
			}
			System.out.println(query);

			// execute insert SQL statement
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public ResultSet getEmployeesData(String order) {
		ResultSet rs = null;
		String query;
		try {
			if(order != null) {
				query = "select * from SYSTEM.EMPLOYEE order by EMPLOYEE_ID " + order;
			}
			else {
				query = "select * from SYSTEM.EMPLOYEE";
			}
			System.out.println(query);

			// execute insert SQL statement
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	

	public ResultSet deleteTableRecord(int[] rows) {
		ResultSet rs = null;

		try {
			String query = "DELETE FROM TABLE_ WHERE TABLE_ID= " + rows[0] + "";
			System.out.println(query);

			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		
		return rs;
	}
	
	public ResultSet deleteDishRecord(int[] rows) {
		ResultSet rs = null;
		try {
			String query = "DELETE FROM DISH WHERE DISH_ID= " + rows[0] + "";
			System.out.println(query);

			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public ResultSet deleteEmployeeRecord(int[] rows) {
		ResultSet rs = null;
		try {
			String query = "DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID= " + rows[0] + "";
			System.out.println(query);

			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public ResultSet deleteCustomerRecord(int[] rows) {
		ResultSet rs = null;
		try {
			String query = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID= " + rows[0] + "";
			System.out.println(query);

			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public ResultSet deleteReservationRecord(int[] rows) {
		ResultSet rs = null;
		try {
			String query = "DELETE FROM RESERVATION WHERE RESERVATION_ID= " + rows[0] + "";
			System.out.println(query);

			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public ResultSet getReservationsCountInDay(int day, int month, int year) {
		ResultSet rs = null;
		try {
			String query = "select count(*) from RESERVATION where CUSTOMER_ID in (SELECT customer_id from TABLE_EMP_CUST where DATE_OF_RESERVATION like TO_DATE('"+day+"/"+month+"/" +year+"', 'DD/MM/YYYY'))";
			System.out.println(query);

			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public ResultSet getPersonnelStatus() {
		ResultSet rs = null;
		try {
			String query = "select role_ AS \"Role\", count(*) AS \"Employed\" from EMPLOYEE group by ROLE_";
			System.out.println(query);

			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public ResultSet getFilter(String min, String max) {
		ResultSet rs = null;
		try {
			String query = "Select * from TABLE_ where (NUM_OF_SEATS >= " + min + " AND NUM_OF_SEATS <= " + max + ") order by TABLE_ID ASC";
			System.out.println(query);	

			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public ResultSet amountsPerDishToday() {
		ResultSet rs = null;
		try {
			String query = "SELECT * FROM ordered_today";
			System.out.println(query);	

			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public String findTableForReservation(int numOfGuests) {
		String res = null;
		try {
			CallableStatement cst = con.prepareCall("{CALL FIND_TABLE(?,?)}");
			cst.registerOutParameter(2, Types.INTEGER);
			cst.setInt(1, numOfGuests);
			cst.execute();
			res = "" + cst.getInt(2);
			cst.close();
			System.out.println(cst);	
			
		} catch (SQLException e) {
			res = e.getMessage();
			System.out.println(e.getMessage());
		}
		
		return res;
	}
	
	public ResultSet getTableEmpCustData() {//created 21.2.18
		ResultSet rs = null;
		try {
			String query = "select table_id, EMPLOYEE_ID, CUSTOMER_ID, to_char(DATE_OF_RESERVATION, 'DD/MM/YYYY HH24:MI') as \"time of reservation\"  from TABLE_EMP_CUST";
			System.out.println(query);

			// execute insert SQL statement
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return rs;
	}
	
	public String getCustomerByOrderedTable(int tableId, String date) {//created 3.3.18
		String name = null;
		try {
			procstmt = con.prepareCall("{call ? := GET_CUSTOMER_BY_ORDERED_TABLE(?, to_date(?, 'DD/MM/YYYY HH24:MI'))}");
			procstmt.registerOutParameter (1, Types.VARCHAR); //register out parameter //TP BE REENTERED
			procstmt.setInt(2, tableId);
			procstmt.setString(3, date);
			
			System.out.println( "executing Function: {call ? := GET_CUSTOMER_BY_ORDERED_TABLE(?, to_date('?', 'DD/MM/YYYY HH24:MI'))}   with parameters: "+tableId+ ", " +date);
			
			// execute PL\SQL call statement
			procstmt.execute ();
			name = (String)procstmt.getObject (1);
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}
		return name;
	}
	
	public void setTableOccupancy(int tableId, String bool) { // created 3.3.18
		if (bool.toLowerCase().equals("true") || bool.toLowerCase().equals("false")) {
			try {
				procstmt = con.prepareCall("{call TABLE_UPDATE__TABLE_OCCUPATION(?,?)}");
				procstmt.setInt(1, tableId);
				procstmt.setString(2, bool);
				System.out.println("executing Stored Procedure: {call TABLE_UPDATE__TABLE_OCCUPATION(?,?)} with params: "+tableId+ ", " +bool);
				
				procstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
