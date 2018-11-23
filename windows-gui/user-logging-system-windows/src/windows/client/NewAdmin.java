package windows.client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import windows.client.utils.RequestUtils;
import windows.client.utils.ResponseObject;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class NewAdmin {

	private JFrame frame;
	private JTextField txtExt;
	private JPasswordField passInit;
	private JPasswordField passConfirm;
	private JLabel lblExtension;
	private JLabel lblPassword;
	private JLabel lblConfirmPassword;
	private JLabel lblName;
	private JTextField txtName;
	private JButton btnCancel;

	/**
	 * Create the application.
	 */
	public NewAdmin() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 443, 634);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewAdmin = new JLabel("New Administrator");
		lblNewAdmin.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewAdmin.setBounds(56, 25, 169, 21);
		frame.getContentPane().add(lblNewAdmin);
		
		txtExt = new JTextField();
		txtExt.setBounds(56, 192, 275, 28);
		frame.getContentPane().add(txtExt);
		txtExt.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtExt.getText().length() != 5) {
					JOptionPane.showMessageDialog(frame, "Extension must be 5 digits.");
				}
				else {
					if(!Arrays.equals(passInit.getPassword(), passConfirm.getPassword())) {
						JOptionPane.showMessageDialog(frame, "Passwords do not match.");
					}
					else {
						JSONObject object = new JSONObject();
						try {
							object.put("name", txtName.getText());
							object.put("ext", txtExt.getText());
							object.put("password", String.valueOf(passInit.getPassword()));
							
							HttpResponse response = RequestUtils.doPost(object, "/newAdmin");
							
							ObjectMapper mapper = new ObjectMapper();
							ResponseObject responseObject = mapper.readValue(EntityUtils.toString(response.getEntity()), ResponseObject.class);
							
							if(responseObject.isSuccess()) {
								
								int input = JOptionPane.showOptionDialog(null, "Sucessfully created administrator.", "Success", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

								if(input == JOptionPane.OK_OPTION || input == JOptionPane.CANCEL_OPTION || input == JOptionPane.CLOSED_OPTION)
								{
								    frame.dispose();
								    new Homepage();
								}
							}
							else {
								JOptionPane.showMessageDialog(frame, responseObject.getMessage());
							}
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		btnSubmit.setBounds(56, 517, 85, 21);
		frame.getContentPane().add(btnSubmit);
		
		passInit = new JPasswordField();
		passInit.setBounds(56, 306, 275, 28);
		frame.getContentPane().add(passInit);
		
		passConfirm = new JPasswordField();
		passConfirm.setBounds(56, 424, 275, 28);
		frame.getContentPane().add(passConfirm);
		
		lblExtension = new JLabel("Extension");
		lblExtension.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExtension.setBounds(56, 169, 67, 13);
		frame.getContentPane().add(lblExtension);
		
		lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(56, 283, 67, 13);
		frame.getContentPane().add(lblPassword);
		
		lblConfirmPassword = new JLabel("Confirm Password");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConfirmPassword.setBounds(56, 401, 121, 13);
		frame.getContentPane().add(lblConfirmPassword);
		
		lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(56, 70, 49, 13);
		frame.getContentPane().add(lblName);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(56, 93, 275, 28);
		frame.getContentPane().add(txtName);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				new Homepage(); 
			}
		});
		btnCancel.setBounds(246, 517, 85, 21);
		frame.getContentPane().add(btnCancel);
	}
}
