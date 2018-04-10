import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FilterResultWin extends JFrame{
	private JTable table;
	private String min = FilterWin.min;
	private String max = FilterWin.max;
	
	public FilterResultWin() {
		setTitle("Suitable tables");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(512, 303);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 476, 245);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 5, 452, 230);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		showResults();
	}
	private void showResults() {
		ResultSet rs = Main.dbCon.getFilter(min, max);
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
}
