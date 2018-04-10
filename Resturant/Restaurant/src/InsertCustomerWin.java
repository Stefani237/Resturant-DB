import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class InsertCustomerWin extends JFrame{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	public InsertCustomerWin() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(415, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setTitle("Add A New Customer");
		getContentPane().setLayout(null);

		JLabel lblFillInThe = new JLabel("Fill in the following attributes to create a new customer:");
		lblFillInThe.setFont(new Font("Sitka Subheading", Font.BOLD, 14));
		lblFillInThe.setHorizontalAlignment(SwingConstants.LEFT);
		lblFillInThe.setBounds(10, 11, 400, 20);
		getContentPane().add(lblFillInThe);

		JPanel panel = new JPanel();
		panel.setBounds(10, 42, 350, 160);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(3, 2, 0, 0));

		JLabel lblCustomerName = new JLabel("First Name");
		panel.add(lblCustomerName);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblCustomerLastName = new JLabel("Last Name");
		lblCustomerLastName.setToolTipText(lblCustomerLastName.getText());
		panel.add(lblCustomerLastName);

		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblTelNum = new JLabel("Phone Number");
		panel.add(lblTelNum);

		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(10);
		
	/*	JLabel lblGuestsNum = new JLabel("Number of guests");
		panel.add(lblGuestsNum);

		textField_3 = new JTextField();
		panel.add(textField_3);
		textField_3.setColumns(10);*/

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
				String name = textField.getText();
				String lastName = textField_1.getText();
				String phone = textField_2.getText();
			//	String guestNum = textField_3.getText();
			//	if (name.isEmpty() || lastName.isEmpty() || phone.isEmpty() || guestNum.isEmpty()) {//these attributes cannot be null
				if (name.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {//these attributes cannot be null
					if (name.isEmpty()) {
						textField.setText("Please enter a name.");
					}
					if (lastName.isEmpty()) {
						textField_1.setText("Please enter a last name.");
					}
					if (phone.isEmpty()) { 
						textField_2.setText("Please enter a phone number.");
					}
				/*	if (guestNum.isEmpty()) { 
						textField_3.setText("Please enter a guests number.");
					}*/
				} else {
				//	Main.dbCon.insertIntoCustomer(name, lastName, phone,  Integer.parseInt(guestNum));
					Main.dbCon.insertIntoCustomer(name, lastName, phone);
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
