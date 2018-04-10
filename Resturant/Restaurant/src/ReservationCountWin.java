import java.awt.Dimension;
import java.awt.Toolkit;
import java.time.DayOfWeek;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JPanel;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class ReservationCountWin extends JFrame {
	public ReservationCountWin() {
		setTitle("Reservation Count");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(512, 303);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("View how many dishes were ordered in  a specified date.");
		label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
		label.setBounds(10, 11, 496, 21);
		getContentPane().add(label);
		
		JLabel lblSelectADate = new JLabel("Select a date:");
		lblSelectADate.setBounds(10, 64, 96, 14);
		getContentPane().add(lblSelectADate);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 90, 157, 30);
		getContentPane().add(panel);
		
		DatePickerSettings settings = new DatePickerSettings();
		settings.setFirstDayOfWeek(DayOfWeek.SUNDAY);
		settings.setFormatForDatesCommonEra("yyyy/MM/dd");
		DatePicker datePick = new DatePicker();
		datePick.setDateToToday();
		
		panel.add(datePick);
		
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if (datePick.getDate() != null) {
					int day = datePick.getDate().getDayOfMonth();
					int month = datePick.getDate().getMonthValue();
					int year = datePick.getDate().getYear();
					ResultSet rs = Main.dbCon.getReservationsCountInDay(day, month, year);
					try {
						rs.next();
						JOptionPane.showMessageDialog(null, "Reservations Count on " + day + "/" + month + "/" + year + " is "+rs.getInt(1));
					} catch (HeadlessException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnOk.setBounds(367, 212, 119, 41);
		getContentPane().add(btnOk);
	}
}
