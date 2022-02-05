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
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import java.awt.Canvas;

public class home extends JFrame {

	private JPanel contentPane;
	private JTextField titleTextField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					home frame = new home();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the frame.
	 */
	Connection con=null;
	private JTextField searchTextField;
//	public home() {
		public home(String msgEmail, String msgUser ) {	
		con=(Connection)DB.dbconnect();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1011, 655);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hello "+msgUser.toUpperCase());//+msg 
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(43, 11, 497, 36);
		contentPane.add(lblNewLabel);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitle.setBounds(87, 102, 193, 29);
		contentPane.add(lblTitle);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(87, 142, 278, 36);
		contentPane.add(scrollPane_1);
		
		titleTextField = new JTextField();
		scrollPane_1.setViewportView(titleTextField);
		titleTextField.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescription.setBounds(87, 199, 193, 29);
		contentPane.add(lblDescription);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(276, 228, -163, 36);
		contentPane.add(textArea);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(87, 239, 312, 244);
		contentPane.add(scrollPane);
		
//		adding label
		JLabel idLabel = new JLabel();
		idLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		idLabel.setBounds(89, 65, 276, 29);
		contentPane.add(idLabel);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(487, 142, 469, 341);
		contentPane.add(scrollPane_2);
//		initializing add button
		JButton btnNewButton = new JButton("Add");
//		adding the description text area
		JTextArea descriptionTextArea = new JTextArea();
		scrollPane.setViewportView(descriptionTextArea);
		descriptionTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel df = (DefaultTableModel)table.getModel();
				int selected = table.getSelectedRow();
				int id = Integer.parseInt(df.getValueAt(selected, 0).toString());
				titleTextField.setText(df.getValueAt(selected, 1).toString());
				descriptionTextArea.setText(df.getValueAt(selected, 2).toString());
				btnNewButton.setEnabled(false);
				idLabel.setText("ID : " + df.getValueAt(selected, 0).toString());
			}
		});
		scrollPane_2.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Title", "Description"
			}
		));
//		end table
//		now updateTable when it loads for first time
		int a;
		PreparedStatement pst;
		try {
			pst = con.prepareStatement("select * from entries where User=?");
			pst.setString(1, msgEmail);
			ResultSet rs = pst.executeQuery();
			ResultSetMetaData rd=(ResultSetMetaData)rs.getMetaData();
			a=rd.getColumnCount();
			DefaultTableModel df = (DefaultTableModel)table.getModel();
			df.setRowCount(0);
			while(rs.next()) {
				Vector v2 =new Vector();
				for(int i=1;i<=a;i++) {
					v2.add(rs.getString("ID"));
					v2.add(rs.getString("Title"));
					v2.add(rs.getString("Description"));
				}
				df.addRow(v2);
				
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		end update the table
		
		
		
//		JTextArea descriptionTextArea = new JTextArea();
//		scrollPane.setViewportView(descriptionTextArea);
//		descriptionTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
		
//		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title,description,user;
				title = titleTextField.getText().trim();
				description = descriptionTextArea.getText();
				user=msgEmail;
				
				System.out.println(title);
				System.out.println(description);
//				validate the entered input
				if(title.length()==0) {
					JOptionPane.showMessageDialog(null, "Please enter Title atleast");
					return;
				}
//				adding data to database
				try {
					PreparedStatement pst = con.prepareStatement("insert into entries (Title,Description,User) values (?,?,?)");
				
					pst.setString(1, title);
					pst.setString(2, description);
					pst.setString(3, user);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Data added successfully");
					}
					
					catch (Exception e1) {
						e1.printStackTrace();
						return;
					}
//				updating the table 
				int a;
				PreparedStatement pst;
				try {
					pst = con.prepareStatement("select * from entries where User=?");
					pst.setString(1, user);
					ResultSet rs = pst.executeQuery();
					ResultSetMetaData rd=(ResultSetMetaData)rs.getMetaData();
					a=rd.getColumnCount();
					DefaultTableModel df = (DefaultTableModel)table.getModel();
					df.setRowCount(0);
					while(rs.next()) {
						Vector v2 =new Vector();
						for(int i=1;i<=a;i++) {
							v2.add(rs.getString("ID"));
							v2.add(rs.getString("Title"));
							v2.add(rs.getString("Description"));
						}
						df.addRow(v2);
						
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(87, 515, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel df = (DefaultTableModel) table.getModel();
				int s= table.getSelectedRow();
				int id= Integer.parseInt(df.getValueAt(s, 0).toString());
				
				String title,description;
				String user;
				title = titleTextField.getText();
				description = descriptionTextArea.getText();
//				user=msgEmail;
				
				System.out.println(title);
				System.out.println(description);
//				adding data to database
				try {
					PreparedStatement pst = con.prepareStatement("update entries set Title=? , Description=? where ID=?");
				
					pst.setString(1, title);
					pst.setString(2, description);
					pst.setInt(3, id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "list updated successfully");
					titleTextField.setText("");
					descriptionTextArea.setText("");
					btnNewButton.setEnabled(true);
					
					
//					now updateTable
					int a;
					try {
						pst = con.prepareStatement("select * from entries where User=?");
						pst.setString(1, msgEmail);
						ResultSet rs = pst.executeQuery();
						ResultSetMetaData rd=(ResultSetMetaData)rs.getMetaData();
						a=rd.getColumnCount();
//						DefaultTableModel df = (DefaultTableModel)table.getModel();
						df.setRowCount(0);
						while(rs.next()) {
							Vector v2 =new Vector();
							for(int i=1;i<=a;i++) {
								v2.add(rs.getString("ID"));
								v2.add(rs.getString("Title"));
								v2.add(rs.getString("Description"));
							}
							df.addRow(v2);
							
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
//					end update the table
					
//					remove id label
					idLabel.setText("");
					
					}
					
					catch (Exception e1) {
						e1.printStackTrace();
						return;
					}
				
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEdit.setBounds(203, 515, 89, 23);
		contentPane.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				delete button
				DefaultTableModel df = (DefaultTableModel) table.getModel();
				int s= table.getSelectedRow();
				int id= Integer.parseInt(df.getValueAt(s, 0).toString());
				
				String title,description;
				String user;
				title = titleTextField.getText();
				description = descriptionTextArea.getText();
//				user=msgEmail;
				
				System.out.println(title);
				System.out.println(description);
//				adding data to database
				try {
					PreparedStatement pst = con.prepareStatement("delete from entries where ID=?");
				
//					pst.setString(1, title);
//					pst.setString(2, description);
					pst.setInt(1, id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Item deleted successfully");
					titleTextField.setText("");
					descriptionTextArea.setText("");
					btnNewButton.setEnabled(true);
					
//					now updateTable
					int a;
					try {
						pst = con.prepareStatement("select * from entries where User=?");
						pst.setString(1, msgEmail);
						ResultSet rs = pst.executeQuery();
						ResultSetMetaData rd=(ResultSetMetaData)rs.getMetaData();
						a=rd.getColumnCount();
//						DefaultTableModel df = (DefaultTableModel)table.getModel();
						df.setRowCount(0);
						while(rs.next()) {
							Vector v2 =new Vector();
							for(int i=1;i<=a;i++) {
								v2.add(rs.getString("ID"));
								v2.add(rs.getString("Title"));
								v2.add(rs.getString("Description"));
							}
							df.addRow(v2);
							
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
//					end update the table
					
//					remove id label
					idLabel.setText("");
					
					}
					
					catch (Exception e1) {
						e1.printStackTrace();
						return;
					}
				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(310, 515, 89, 23);
		contentPane.add(btnDelete);
		
		searchTextField = new JTextField();
		searchTextField.setColumns(10);
		searchTextField.setBounds(487, 85, 312, 34);
		contentPane.add(searchTextField);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchValue = searchTextField.getText();
				DefaultTableModel df = (DefaultTableModel) table.getModel();
				int a;
				if (searchValue.trim()=="") return ;
// 				search table
				
				try {
					PreparedStatement pst = con.prepareStatement("select * from entries where User=? and Title=? or ID=? or Description=?");
					pst.setString(1, msgEmail);
					pst.setString(2, searchValue);
					pst.setString(3, searchValue);
					pst.setString(4, searchValue);
					ResultSet rs = pst.executeQuery();
					ResultSetMetaData rd=(ResultSetMetaData)rs.getMetaData();
					a=rd.getColumnCount();
//					DefaultTableModel df = (DefaultTableModel)table.getModel();
					df.setRowCount(0);
					while(rs.next()) {
						Vector v2 =new Vector();
						for(int i=1;i<=a;i++) {
							v2.add(rs.getString("ID"));
							v2.add(rs.getString("Title"));
							v2.add(rs.getString("Description"));
						}
						df.addRow(v2);
						
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
//				end update the table
				
			}
		});
		searchButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		searchButton.setBounds(809, 89, 83, 23);
		contentPane.add(searchButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				DefaultTableModel df = (DefaultTableModel) table.getModel();
				int a;
				
				try {
					PreparedStatement pst = con.prepareStatement("select * from entries where User=?");
					pst.setString(1, msgEmail);
					ResultSet rs = pst.executeQuery();
					ResultSetMetaData rd=(ResultSetMetaData)rs.getMetaData();
					a=rd.getColumnCount();
//					DefaultTableModel df = (DefaultTableModel)table.getModel();
					df.setRowCount(0);
					while(rs.next()) {
						Vector v2 =new Vector();
						for(int i=1;i<=a;i++) {
							v2.add(rs.getString("ID"));
							v2.add(rs.getString("Title"));
							v2.add(rs.getString("Description"));
						}
						df.addRow(v2);
						
					}
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				searchTextField.setText("");
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(902, 89, 83, 23);
		contentPane.add(btnCancel);
		
		JButton btnNewButton_1 = new JButton("Logout");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login loginPage= new login();
				loginPage.setVisible(true);
				dispose();
				
			}
		});
		btnNewButton_1.setIcon(null);
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_1.setBounds(896, 20, 89, 23);
		contentPane.add(btnNewButton_1);
		
//		JLabel idLabel = new JLabel("ID : " + "");
//		idLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		idLabel.setBounds(89, 65, 276, 29);
//		contentPane.add(idLabel);
//		
//		JScrollPane scrollPane_2 = new JScrollPane();
//		scrollPane_2.setBounds(487, 142, 469, 341);
//		contentPane.add(scrollPane_2);
//		
//		table = new JTable();
//		scrollPane_2.setViewportView(table);
//		table.setModel(new DefaultTableModel(
//			new Object[][] {
//			},
//			new String[] {
//				"Id", "Title", "Description"
//			}
//		));
	}
}
