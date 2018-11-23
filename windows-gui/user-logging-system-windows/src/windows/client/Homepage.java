package windows.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Homepage {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public Homepage() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewAdmin = new JButton("Create New Admin");
		btnNewAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				new NewAdmin();
			}
		});
		btnNewAdmin.setBounds(122, 118, 188, 21);
		frame.getContentPane().add(btnNewAdmin);
		
		JLabel lblWelcome = new JLabel("Administrator Homepage");
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblWelcome.setBounds(106, 26, 220, 21);
		frame.getContentPane().add(lblWelcome);
	}
}
