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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import windows.client.objects.ResponseObject;
import windows.client.objects.StaticValues;
import windows.client.objects.ReturnAdmin;
import windows.client.utils.RequestUtils;

public class EditAdmin {

	private JFrame frame;
	private JTextField txtExt;
	private JLabel lblExtension;
	private JLabel lblName;
	private JTextField txtName;
	private JButton btnCancel;
	private JButton btnEditPassword;
	
	private ReturnAdmin admin;

	/**
	 * Create the application.
	 */
	public EditAdmin() {
		initialize();
		fillInfo();
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
		
		JLabel lblEditAdmin = new JLabel("Edit Administrator");
		lblEditAdmin.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblEditAdmin.setBounds(56, 78, 169, 21);
		frame.getContentPane().add(lblEditAdmin);
		
		txtExt = new JTextField();
		txtExt.setBounds(56, 309, 275, 28);
		frame.getContentPane().add(txtExt);
		txtExt.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtExt.getText().length() != 5) {
					JOptionPane.showMessageDialog(frame, "Extension must be 5 digits.");
				}
				else if(txtName.getText().equals("")) {
					JOptionPane.showMessageDialog(frame, "Name must not be blank");
				}
				else {
					int input = JOptionPane.showOptionDialog(null, "Are you sure you want to make these changes?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
					if(input == JOptionPane.YES_OPTION) {
						JSONObject object = new JSONObject();
						try {
							object.put("oldExt", admin.getFiveDigExt());
							object.put("name", txtName.getText());
							object.put("ext", txtExt.getText());
							
							HttpResponse response = RequestUtils.doPost(object, "/editAdmin");
							
							ObjectMapper mapper = new ObjectMapper();
							ResponseObject responseObject = mapper.readValue(EntityUtils.toString(response.getEntity()), ResponseObject.class);
							
							if(responseObject.isSuccess()) {
								int successInput = JOptionPane.showOptionDialog(null, "Sucessfully modified administrator.", "Success", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

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
			}
		});
		btnSubmit.setBounds(56, 517, 85, 21);
		frame.getContentPane().add(btnSubmit);
		
		lblExtension = new JLabel("Extension");
		lblExtension.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExtension.setBounds(56, 286, 67, 13);
		frame.getContentPane().add(lblExtension);
		
		lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(56, 166, 49, 13);
		frame.getContentPane().add(lblName);
		
		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(56, 189, 275, 28);
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
		
		btnEditPassword = new JButton("Edit Password");
		btnEditPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				new EditPassword(admin);
			}
		});
		btnEditPassword.setBounds(96, 407, 191, 21);
		frame.getContentPane().add(btnEditPassword);
	}
	public void fillInfo() {
		JSONObject object = new JSONObject();
		try {
			object.put("oldExt", StaticValues.id);
			
			HttpResponse response = RequestUtils.doPost(object, "/getAdmin");
			
			ObjectMapper mapper = new ObjectMapper();
			admin = mapper.readValue(EntityUtils.toString(response.getEntity()), ReturnAdmin.class);
			
			if(admin != null) {
				txtExt.setText(admin.getFiveDigExt());
				txtName.setText(admin.getName());
			}
			else {
				JOptionPane.showMessageDialog(frame, "Unknown error.");
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Unknown error.");
			e.printStackTrace();
		}
	}

}
