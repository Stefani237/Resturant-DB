
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class InsertDishWin extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JLabel error_lbl;

	public InsertDishWin() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(400, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setTitle("Add A New Dish");
		getContentPane().setLayout(null);

		JLabel lblFillInThe = new JLabel("Fill in the following attributes to create a new dish:");
		lblFillInThe.setFont(new Font("Sitka Subheading", Font.BOLD, 14));
		lblFillInThe.setHorizontalAlignment(SwingConstants.LEFT);
		lblFillInThe.setBounds(10, 11, 400, 20);
		getContentPane().add(lblFillInThe);

		JPanel panel = new JPanel();
		panel.setBounds(10, 42, 350, 160);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(3, 2, 0, 2));

		JLabel lblNewLabel = new JLabel("Name");
		panel.add(lblNewLabel);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Type");
		panel.add(lblNewLabel_1);

		JPanel rbPanel = new JPanel();
		rbPanel.setLayout(new FlowLayout());
		JRadioButton starters = new JRadioButton("Starters");
		JRadioButton main = new JRadioButton("Main");
		JRadioButton desserts = new JRadioButton("desserts");
		ButtonGroup group = new ButtonGroup();
		group.add(starters);
		group.add(main);
		group.add(desserts);
		rbPanel.add(starters);
		rbPanel.add(main);
		rbPanel.add(desserts);
		panel.add(rbPanel);

		/*JLabel lblNewLabel_1 = new JLabel("Type (Starters', 'Main' or 'Desserts')");
		lblNewLabel_1.setToolTipText(lblNewLabel_1.getText());
		panel.add(lblNewLabel_1);

		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);*/

		JLabel lblDescription = new JLabel("Description");
		panel.add(lblDescription);

		JTextArea textArea = new JTextArea();
		panel.add(textArea);

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
				String type = getSelectedButton(group);
				//String type = textField_1.getText();
				String description = textArea.getText();
				if (name.isEmpty() || type == null) {//these attributes cannot be null
					if (name.isEmpty()) {
						textField.setText("Please enter a name.");
					}
					if (type == null) { 
						error_lbl.setVisible(true);
					}
				} else {
					Main.dbCon.insertIntoDish(name, type, description);
					error_lbl.setVisible(false);
					closeFrame();
				}

			}
		});
		panel_1.add(btnOk);
		
		error_lbl = new JLabel("Select dish type");
		error_lbl.setForeground(Color.RED);
		error_lbl.setVisible(false);
		error_lbl.setBounds(10, 224, 131, 13);
		getContentPane().add(error_lbl);
	}

	private void closeFrame() {
		super.dispose();
	}

	public String getSelectedButton(ButtonGroup group)
	{  
		for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()) {
				return button.getText();
			}
		}
		return null;
	}
}
