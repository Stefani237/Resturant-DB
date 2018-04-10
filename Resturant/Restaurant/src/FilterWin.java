import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.DayOfWeek;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class FilterWin extends JFrame{
	private final int MAX_NUM_SEATS = 12;
	private JLabel lblSelectMax;
	public static String min = "" + 2;
	public static String max = "" + 2;
	private JComboBox min_combo;
	private JComboBox max_combo;

	public FilterWin() {
		setTitle("Filter");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(512, 303);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Choose a number of chairs to get the suitable tables.");
		label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
		label.setBounds(10, 11, 496, 21);
		getContentPane().add(label);
		
		JLabel lblSelectMin = new JLabel("Select minimum number of chairs:");
		lblSelectMin.setBounds(10, 65, 196, 14);
		getContentPane().add(lblSelectMin);
		
		min_combo = new JComboBox();
		insertValuesToCombo(min_combo);
		min_combo.setBounds(216, 62, 55, 21);
		getContentPane().add(min_combo);
		
		
		lblSelectMax = new JLabel("Select maximun number of chairs:");
		lblSelectMax.setBounds(10, 115, 196, 14);
		getContentPane().add(lblSelectMax);
		
		
		max_combo = new JComboBox();
		insertValuesToCombo(max_combo);
		max_combo.setBounds(216, 111, 55, 21);
		getContentPane().add(max_combo);
		
		min_combo.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {    
	            	min = String.valueOf(min_combo.getSelectedItem());
	            }
	        });
		max_combo.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) { 
	            	max = String.valueOf(max_combo.getSelectedItem());
	            }
	        });
		
		JLabel error_lbl = new JLabel("Minimum value greater than maximum value");
		error_lbl.setForeground(Color.RED);
		error_lbl.setVisible(false);
		error_lbl.setBounds(10, 156, 261, 13);
		getContentPane().add(error_lbl);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("min = " + Integer.valueOf(min));
				System.out.println("max = " + Integer.valueOf(max));
				if(Integer.valueOf(min) > Integer.valueOf(max)) {
				
					error_lbl.setVisible(true);
				}
				else {
					FilterResultWin win = new FilterResultWin();
					error_lbl.setVisible(false);
				}
			}
			
		});
		btnOk.setBounds(367, 212, 119, 41);
		getContentPane().add(btnOk);
		
		
		
	}
	private void insertValuesToCombo(JComboBox combo){
		for (int i = 2; i <= MAX_NUM_SEATS; i+=2) {
			combo.addItem(i);
		}
	}

}


