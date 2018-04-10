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
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangeOccupancyWin extends JFrame {
	private JTable table;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public ChangeOccupancyWin() {
		setTitle("Change Table Occupancy");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(571, 343);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		getContentPane().setLayout(null);
		
		JLabel lblSelectATable = new JLabel("Select a table and set its occupancy status");
		lblSelectATable.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
		lblSelectATable.setBounds(10, 11, 333, 14);
		getContentPane().add(lblSelectATable);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 36, 535, 170);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 535, 170);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JRadioButton rdbtnOccupied = new JRadioButton("Occupied");
		buttonGroup.add(rdbtnOccupied);
		rdbtnOccupied.setBounds(10, 218, 109, 23);
		getContentPane().add(rdbtnOccupied);
		
		JRadioButton rdbtnNotOccupied = new JRadioButton("Not Occupied");
		buttonGroup.add(rdbtnNotOccupied);
		rdbtnNotOccupied.setBounds(10, 244, 109, 23);
		getContentPane().add(rdbtnNotOccupied);
		
		
		rdbtnOccupied.setSelected(true);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >=0) {
					int tableId = (int) table.getModel().getValueAt(selectedRow, 0);
					String bool = null;
					if (rdbtnOccupied.isSelected()) {
						bool = "TrUe";
					}
					else if (rdbtnNotOccupied.isSelected()) {
						bool = "FaLsE";
					}
					
					Main.dbCon.setTableOccupancy(tableId, bool);
					closeFrame();
				}
			}
		});
		btnOk.setBounds(456, 270, 89, 23);
		getContentPane().add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFrame();
			}
		});
		btnCancel.setBounds(357, 270, 89, 23);
		getContentPane().add(btnCancel);
		
		
		
		displayTablesData();
	}
	
	private void closeFrame() {
		super.dispose();
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
}
