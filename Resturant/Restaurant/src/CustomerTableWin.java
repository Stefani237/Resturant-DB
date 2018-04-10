import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;

public class CustomerTableWin extends JFrame {
	private JTable table;
	public CustomerTableWin() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(571, 343);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		getContentPane().setLayout(null);
		
		JLabel lblSelectAReservation = new JLabel("Select a reservation you wish to view its customer:");
		lblSelectAReservation.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
		lblSelectAReservation.setBounds(10, 11, 414, 23);
		getContentPane().add(lblSelectAReservation);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 36, 535, 223);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		displayTableEmpCustData();
		
		JLabel error_lbl = new JLabel("Select row");
		error_lbl.setVisible(false);
		error_lbl.setForeground(Color.RED);
		error_lbl.setBounds(208, 275, 111, 13);
		getContentPane().add(error_lbl);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if(selectedRow > -1) {
					error_lbl.setVisible(false);
					int tableId = (int) table.getModel().getValueAt(selectedRow, 0);
					String date = (String) table.getModel().getValueAt(selectedRow, 3);
					
					String custName = Main.dbCon.getCustomerByOrderedTable(tableId, date); 
					JOptionPane.showMessageDialog(null, "The selected reservation belongs to " + custName);
				}
				else {
					error_lbl.setVisible(true);
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
		btnCancel.setBounds(367, 270, 89, 23);
		getContentPane().add(btnCancel);
		

	}
	
	private void closeFrame() {
		super.dispose();
	}
	
	private void displayTableEmpCustData(){
		
		ResultSet rs = Main.dbCon.getTableEmpCustData(); 
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
			    int empId = rs.getInt(2);
			    int custId = rs.getInt(3);
			    String date = rs.getString(4);
			    System.out.println(tableId + " " +empId + " " +custId + " " +date + " ");
			    model.addRow(new Object[]{tableId, empId, custId, date});
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
