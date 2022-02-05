package signup;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class login extends JFrame {

	private JPanel contentPane;
	private JTextField emailField;
	private JTextField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	Connection con=null;
	public login() {
		con=(Connection)DB.dbconnect();
		System.out.println(con);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(154, 33, 112, 29);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(72, 113, 60, 29);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(72, 167, 78, 29);
		contentPane.add(lblNewLabel_2);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		emailField.setBounds(202, 119, 126, 20);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		passwordField.setBounds(202, 173, 126, 20);
		contentPane.add(passwordField);
		passwordField.setColumns(10);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String email,password;
				email = emailField.getText().trim();
				password= passwordField.getText().trim();
				System.out.println(email);
				System.out.println(password);
//				validate the entered input
				if(email.length()==0||password.length()==0) {
					JOptionPane.showMessageDialog(null, "Please enter values correctly");
					return;
				}
				
				try {
					PreparedStatement pst = con.prepareStatement("select * from users where Email=? and Password=?");
				
					pst.setString(1, email);
					pst.setString(2, password);
					ResultSet r = pst.executeQuery();
					
					if(r.next()) {
						home h= new home(r.getString("Email"),r.getString("Name"));
						dispose();
						h.setVisible(true);
					}else {

						JOptionPane.showMessageDialog(null, "Invalid email and password");
					}
					}
					
					catch (Exception e1) {
						e1.printStackTrace();
						return;
					}
			
			}
		});
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loginButton.setBounds(154, 232, 89, 23);
		contentPane.add(loginButton);
		
		JButton btnSignup = new JButton("Signup");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signup signup = new signup();
				signup.setVisible(true);
				dispose();
			}
		});
		btnSignup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSignup.setBounds(253, 234, 89, 23);
		contentPane.add(btnSignup);
	}
}
