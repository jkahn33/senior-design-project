package windows.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import windows.client.utils.RequestUtils;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField txtExt;
	private JPasswordField txtPass;
	private JLabel lblPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 728, 511);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLoginTitle = new JLabel("USWRIC -- Administrator Login");
		lblLoginTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblLoginTitle.setBounds(210, 66, 282, 29);
		frame.getContentPane().add(lblLoginTitle);
		
		txtExt = new JTextField();
		txtExt.setBounds(104, 189, 490, 46);
		frame.getContentPane().add(txtExt);
		txtExt.setColumns(10);
		
		txtPass = new JPasswordField();
		txtPass.setColumns(10);
		txtPass.setBounds(104, 315, 490, 46);
		frame.getContentPane().add(txtPass);
		
		JLabel lblExt = new JLabel("Extension");
		lblExt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExt.setBounds(104, 166, 133, 13);
		frame.getContentPane().add(lblExt);
		
		lblPass = new JLabel("Password");
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPass.setBounds(104, 292, 85, 13);
		frame.getContentPane().add(lblPass);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtExt.getText().length() != 5) {
					JOptionPane.showMessageDialog(frame, "Extension must be 5 digits long.");
				}
				else {
					try {
						JSONObject object = new JSONObject();
						
						object.put("ext", txtExt.getText());
						object.put("password", String.valueOf(txtPass.getPassword()));
						
						HttpResponse response = RequestUtils.doPost(object, "/validateAdmin");
						if(response == null) {
							System.out.println(":(");
						}
						else {
							boolean success = Boolean.parseBoolean(EntityUtils.toString(response.getEntity()));
	                         if(!success) {
	                        	 JOptionPane.showMessageDialog(frame, "Incorrect extension or password.");
	                         }
	                         else {
	                        	 frame.dispose();
	                        	 new Homepage();
	                         }
						}
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSubmit.setBounds(291, 391, 125, 39);
		frame.getContentPane().add(btnSubmit);
	}
}
