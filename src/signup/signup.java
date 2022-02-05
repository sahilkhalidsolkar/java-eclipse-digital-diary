package signup;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class signup extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField emailField;
	private JTextField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					signup frame = new signup();
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
	public signup() {
		con=(Connection)DB.dbconnect();
		System.out.println(con);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSignup = new JLabel("Signup Form");
		lblSignup.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSignup.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignup.setBounds(134, 11, 151, 21);
		contentPane.add(lblSignup);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setBounds(80, 101, 73, 14);
		contentPane.add(lblName);
		
		JLabel lblNewLabel = new JLabel("Email");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(80, 151, 73, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPassword.setBounds(80, 199, 73, 14);
		contentPane.add(lblPassword);
		
		nameField = new JTextField();
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameField.setBounds(199, 100, 136, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		emailField.setBounds(199, 150, 136, 20);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passwordField.setBounds(199, 198, 136, 20);
		contentPane.add(passwordField);
		passwordField.setColumns(10);
		
		JButton btnSignup = new JButton("Signup");
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name,email,password;
				name=nameField.getText().trim();
				email=emailField.getText().trim();
				password=passwordField.getText().trim();
				System.out.println(name);
				System.out.println(email);
				System.out.println(password);
//				validate the entered input
				if(name.length()==0||email.length()==0||password.length()==0) {
					JOptionPane.showMessageDialog(null, "Please enter values correctly");
					return;
				}
//				check weather user has already registered
				try {
					PreparedStatement pst = con.prepareStatement("select * from users where Email=? and Password=?");
				
					pst.setString(1, email);
					pst.setString(2, password);
					ResultSet r = pst.executeQuery();
					
					if(r.next()) {
						System.out.println(r.getString(2)+" from database");
						JOptionPane.showMessageDialog(null, "Already registered \n Try logging  in");
						return;

					}else {
						
							pst = con.prepareStatement("insert into users(Name,Email,Password) values (?,?,?)");
							pst.setString(1, name);
							pst.setString(2, email);
							pst.setString(3, password);
							pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Signup successful \n Click on login");
							
						
					}
					}
					
					catch (Exception e1) {
						e1.printStackTrace();
						return;
					}

				
				
				
			}
		});
		btnSignup.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSignup.setBounds(145, 266, 89, 23);
		contentPane.add(btnSignup);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login login = new login();
				login.setVisible(true);
				dispose();
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.setBounds(246, 266, 89, 23);
		contentPane.add(btnLogin);
	}
}
