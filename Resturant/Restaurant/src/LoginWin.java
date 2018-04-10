import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class LoginWin extends JFrame {
	private JTextField passwordField;
	private String pass;

	public LoginWin() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(500, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		
		setTitle("Welocme To Restaurant Manager");
		getContentPane().setLayout(null);
		
		JLabel lblPleaseLogIn = new JLabel("Please log in with one of the users:");
		lblPleaseLogIn.setBounds(10, 11, 223, 23);
		getContentPane().add(lblPleaseLogIn);
		
		JRadioButton rdbtnAdmin = new JRadioButton("Admin");
		rdbtnAdmin.setBounds(6, 47, 150, 23);
		getContentPane().add(rdbtnAdmin);
		
		JRadioButton rdbtnUserreadOnly = new JRadioButton("User (Read only)");
		rdbtnUserreadOnly.setBounds(6, 73, 150, 23);
		getContentPane().add(rdbtnUserreadOnly);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnAdmin);
		group.add(rdbtnUserreadOnly);
		rdbtnAdmin.setSelected(true);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 130, 146, 14);
		getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 155, 105, 20);
		getContentPane().add(passwordField);
		
		JLabel error_lbl = new JLabel("");
		error_lbl.setForeground(Color.RED);
		error_lbl.setVisible(false);
		error_lbl.setBounds(143, 158, 150, 17);
		getContentPane().add(error_lbl);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pass = passwordField.getText();
				if(pass.isEmpty()) { 
					error_lbl.setText("Please enter a password");
					error_lbl.setVisible(true);
				}
				else {
					error_lbl.setVisible(false);
					if (rdbtnAdmin.isSelected() && pass.equals("system")) { 
						Main.dbCon = new DBconnector("admin", pass);
						closeFrame();
					}
					else if (rdbtnUserreadOnly.isSelected() && pass.equals("111111")) {
						Main.dbCon = new DBconnector("app_user", pass);
						closeFrame();
					}
					else {
						error_lbl.setText("Wrong Password");
						error_lbl.setVisible(true);
					}
					
				}
			}
		});
		btnLogIn.setBounds(385, 227, 89, 23);
		getContentPane().add(btnLogIn);
	}
	
	private void closeFrame() {
		super.dispose();
	}
}
