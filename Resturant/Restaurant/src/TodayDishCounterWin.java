import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;

public class TodayDishCounterWin extends JFrame{
	private JTable table;
	private JLabel lblNewLabel;
	
	public TodayDishCounterWin() {
		setTitle("Today's dishes counter");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(506, 304);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 10, 486, 255);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(17, 0, 452, 245);
		panel.add(scrollPane);
		
		lblNewLabel = new JLabel("No dishes were ordered today.");
		lblNewLabel.setVisible(false);
		scrollPane.setColumnHeaderView(lblNewLabel);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		showView();
	}
	
	private void showView() {
		ResultSet rs = Main.dbCon.amountsPerDishToday();
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
			    lblNewLabel.setVisible(true);
			}
			while(rs.next())
			{
				lblNewLabel.setVisible(false);
			    int counter = rs.getInt(1);
			    String dishName = rs.getString(2);
			    System.out.println(counter + " " +dishName);
			    model.addRow(new Object[]{counter, dishName});
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
