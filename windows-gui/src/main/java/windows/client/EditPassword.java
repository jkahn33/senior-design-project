package windows.client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import windows.client.objects.ResponseObject;
import windows.client.objects.ReturnAdmin;
import windows.client.objects.StaticValues;
import windows.client.utils.RequestUtils;
import javax.swing.JPasswordField;

public class EditPassword {

	private JFrame frame;
	private JPasswordField passNew;
	private JLabel lblNewPassword;
	private JLabel lblOldPass;
	private JPasswordField passOld;
	private JButton btnCancel;
	private JPasswordField passConfirm;
	
	private ReturnAdmin admin;

	/**
	 * Create the application.
	 */
	public EditPassword(ReturnAdmin admin) {
		this.admin = admin;
		
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
		
		JLabel lblEditPass = new JLabel("Edit Password");
		lblEditPass.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEditPass.setBounds(56, 51, 169, 21);
		frame.getContentPane().add(lblEditPass);
		
		passNew = new JPasswordField();
		passNew.setBounds(56, 273, 275, 28);
		frame.getContentPane().add(passNew);
		passNew.setColumns(10);
		
		lblNewPassword = new JLabel("New Password");
		lblNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewPassword.setBounds(56, 250, 100, 13);
		frame.getContentPane().add(lblNewPassword);
		
		lblOldPass = new JLabel("Old Password");
		lblOldPass.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblOldPass.setBounds(56, 130, 93, 13);
		frame.getContentPane().add(lblOldPass);
		
		passOld = new JPasswordField();
		passOld.setColumns(10);
		passOld.setBounds(56, 153, 275, 28);
		frame.getContentPane().add(passOld);
		
		passConfirm = new JPasswordField();
		passConfirm.setColumns(10);
		passConfirm.setBounds(56, 397, 275, 28);
		frame.getContentPane().add(passConfirm);
		
		JLabel lblConfirmPassword = new JLabel("Confirm New Password");
		lblConfirmPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblConfirmPassword.setBounds(56, 374, 158, 13);
		frame.getContentPane().add(lblConfirmPassword);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(passNew.getPassword().length == 0) {
					JOptionPane.showMessageDialog(frame, "Please enter old password.");
				}
				else if(passOld.getPassword().length == 0) {
					JOptionPane.showMessageDialog(frame, "Please enter new password.");
				}
				else if(passConfirm.getPassword().length == 0) {
					JOptionPane.showMessageDialog(frame, "Please confirm new password.");
				}
				else {
					if(isValid()){
						if(!Arrays.equals(passNew.getPassword(), passConfirm.getPassword())) {
							JOptionPane.showMessageDialog(frame, "New password and confirmed password do not match.");
						}
						else {
							int input = JOptionPane.showOptionDialog(null, "Are you sure you want to change your password?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
							if(input == JOptionPane.YES_OPTION) {
								changePass();
							}
						}
					}
					else {
						JOptionPane.showMessageDialog(frame, "Old password is not correct.");
					}
				}
			}
		});
		btnSubmit.setBounds(56, 517, 85, 21);
		frame.getContentPane().add(btnSubmit);
		
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
	public boolean isValid() {
		try {
			JSONObject object = new JSONObject();
			
			object.put("ext", admin.getFiveDigExt());
			object.put("password", String.valueOf(passOld.getPassword()));
			
			HttpResponse response = RequestUtils.doPost(object, "/validateAdmin");
			if(response == null) {
				//TODO display error
				System.out.println(":(");
				return false;
			}
			else {
				boolean success = Boolean.parseBoolean(EntityUtils.toString(response.getEntity()));
                 if(!success) {
                	 return false;
                 }
                 else {
                	 return true;
                 }
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public void changePass() {
		JSONObject object = new JSONObject();
		try {
			object.put("oldExt", admin.getFiveDigExt());
			object.put("newPass", String.valueOf(passNew.getPassword()));
			
			HttpResponse response = RequestUtils.doPost(object, "/editAdmin");
			
			ObjectMapper mapper = new ObjectMapper();
			ResponseObject responseObject = mapper.readValue(EntityUtils.toString(response.getEntity()), ResponseObject.class);
			
			if(responseObject.isSuccess()) {
				int successInput = JOptionPane.showOptionDialog(null, "Sucessfully changed password.", "Success", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

				if(successInput == JOptionPane.OK_OPTION || successInput == JOptionPane.CANCEL_OPTION || successInput == JOptionPane.CLOSED_OPTION)
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
