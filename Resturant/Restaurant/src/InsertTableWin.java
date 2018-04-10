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
import javax.swing.JComboBox;

public class InsertTableWin extends JFrame {
	private final int MAX_NUM_SEATS = 12;
	public static String combo = "" + 2;
	
	public InsertTableWin() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
		this.setVisible(true);
		this.setSize(400, 300);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setTitle("Add A New Table");
		getContentPane().setLayout(null);
		
		JLabel lblFillInThe = new JLabel("Fill in the following attributes to create a new table:");
		lblFillInThe.setFont(new Font("Sitka Subheading", Font.BOLD, 14));
		lblFillInThe.setHorizontalAlignment(SwingConstants.LEFT);
		lblFillInThe.setBounds(10, 11, 400, 20);
		getContentPane().add(lblFillInThe);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 42, 350, 40);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Number of Seats");
		lblNewLabel.setForeground(Color.BLACK);
		panel.add(lblNewLabel);
		
		JComboBox seats_combo = new JComboBox();
		insertValuesToCombo(seats_combo);
		panel.add(seats_combo);
		
		seats_combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    
            	combo = String.valueOf(seats_combo.getSelectedItem());
            }
        });
		
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
				try {
		
						Main.dbCon.insertIntoTable(Integer.parseInt(combo));
						closeFrame();
				}
				catch (NumberFormatException e){
					System.out.println("" + e);
				}
			}
		
		});
		
		panel_1.add(btnOk);
	}
	
	private void closeFrame() {
		super.dispose();
	}
	
	private void insertValuesToCombo(JComboBox combo){
		for (int i = 2; i <= MAX_NUM_SEATS; i+=2) {
			combo.addItem(i);
		}
	}
}
