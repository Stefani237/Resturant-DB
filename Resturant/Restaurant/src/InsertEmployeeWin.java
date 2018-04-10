import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class InsertEmployeeWin extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	public InsertEmployeeWin() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(415, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setTitle("Add A New Employee");
		getContentPane().setLayout(null);

		JLabel lblFillInThe = new JLabel("Fill in the following attributes to create a new employee:");
		lblFillInThe.setFont(new Font("Sitka Subheading", Font.BOLD, 14));
		lblFillInThe.setHorizontalAlignment(SwingConstants.LEFT);
		lblFillInThe.setBounds(10, 11, 400, 20);
		getContentPane().add(lblFillInThe);

		JPanel panel = new JPanel();
		panel.setBounds(10, 42, 350, 160);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(5, 2, 0, 0));

		JLabel lblNewLabel = new JLabel("First Name");
		panel.add(lblNewLabel);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Last Name");
		lblNewLabel_1.setToolTipText(lblNewLabel_1.getText());
		panel.add(lblNewLabel_1);

		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblDescription = new JLabel("Address");
		panel.add(lblDescription);
		
		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblTelephoneNumber = new JLabel("Telephone Number");
		panel.add(lblTelephoneNumber);
		
		textField_3 = new JTextField();
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblRole = new JLabel("Role");
		panel.add(lblRole);
		
		textField_4 = new JTextField();
		panel.add(textField_4);
		textField_4.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(151, 213, 209, 37);
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeFrame();
			}
		});
		panel_1.add(btnCancel);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String firstName = textField.getText();
				String lastName = textField_1.getText();
				String Address = textField_2.getText();
				String telNum = textField_3.getText();
				String role = textField_4.getText();
				if (firstName.isEmpty() || lastName.isEmpty() || Address.isEmpty() || telNum.isEmpty() || role.isEmpty()) { //these attributes cannot be null
					if (firstName.isEmpty()) {
						textField.setText("Please enter first name.");
					}
					if (lastName.isEmpty()) { 
						textField_1.setText("Please enter last name.");
					}
					if (Address.isEmpty()) { 
						textField_2.setText("Please enter address details.");
					}
					if (telNum.isEmpty()) { 
						textField_3.setText("Please enter telephone number.");
					}
					if (role.isEmpty()) { 
						textField_4.setText("Please enter a role.");
					}
				} else {
					Main.dbCon.insertIntoEmployee(firstName, lastName, Address, telNum, role);
					closeFrame();
				}

			}
		});
		panel_1.add(btnOk);
	}

	private void closeFrame() {
		super.dispose();
	}
}
