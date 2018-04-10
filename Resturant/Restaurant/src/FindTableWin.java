import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.JTextField;

public class FindTableWin extends JFrame{
	private JTextField numGuests_txt;
	JLabel error_lbl;
	
	public FindTableWin() {
		setTitle("Find table for reservation");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(512, 303);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("Enter number of guests and get an available table.");
		label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
		label.setBounds(10, 11, 496, 21);
		getContentPane().add(label);
		
		JLabel numGuests_lbl = new JLabel("Enter number of guests:");
		numGuests_lbl.setBounds(10, 78, 186, 14);
		getContentPane().add(numGuests_lbl);
		
		numGuests_txt = new JTextField();
		numGuests_txt.setBounds(182, 62, 151, 30);
		getContentPane().add(numGuests_txt);
		numGuests_txt.setColumns(10);
	
		
		error_lbl = new JLabel("");
		error_lbl.setForeground(Color.RED);
		error_lbl.setVisible(false);
		error_lbl.setBounds(182, 123, 279, 67);
		getContentPane().add(error_lbl);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				try {
				
					String numOfGuests = numGuests_txt.getText();
					System.out.println("number of guests: " + numOfGuests);
					if (numOfGuests.isEmpty()) {
						error_lbl.setText("Please enter number of guests.");
						error_lbl.setVisible(true);
					}
					
					else if(Integer.parseInt(numOfGuests) < 1) {
						error_lbl.setText("Please enter a number from 1 or higher.");
						error_lbl.setVisible(true);
					}
					else { // number from 1 and above
						error_lbl.setVisible(false);
						String rs = Main.dbCon.findTableForReservation(Integer.parseInt(numOfGuests));
						try { // rs is number
							int table = Integer.parseInt(rs);
							System.out.println("Table no. " + table);
							JOptionPane.showMessageDialog(null, "Table no. " + table);
						}
						catch(NumberFormatException ex) { // rs is an exception
							JOptionPane.showMessageDialog(null, rs);			
						}
					}
				}
				catch (NumberFormatException ex) {
					error_lbl.setText("Please enter a number.");
					error_lbl.setVisible(true);
				}
			}
		});
		btnOk.setBounds(367, 212, 119, 41);
		getContentPane().add(btnOk);

		
		
	}
}
