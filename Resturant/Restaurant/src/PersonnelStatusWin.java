import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class PersonnelStatusWin extends JFrame {
	private JTable table;
	public PersonnelStatusWin() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(604, 350);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setTitle("Personnel Status");
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("The following table shows the current status of the restaurant employees.");
		lblNewLabel.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 540, 22);
		getContentPane().add(lblNewLabel);
		
		JLabel lblItDescribesHow = new JLabel("It describes how many employees are employed in each of the restaurant's different roles.");
		lblItDescribesHow.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 14));
		lblItDescribesHow.setBounds(10, 42, 568, 22);
		getContentPane().add(lblItDescribesHow);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 75, 568, 225);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 568, 225);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		displayPersonnelStatusData();
	}
	
	private void displayPersonnelStatusData(){
		
		ResultSet rs = Main.dbCon.getPersonnelStatus(); 
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
			    String role = rs.getString(1);
			    int numOfEmployees = rs.getInt(2);
			    System.out.println(role + " " +numOfEmployees + " " );
			    model.addRow(new Object[]{role, numOfEmployees});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table.setModel(model);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
	}
}
