package signup;
import java.sql.DriverManager;
import javax.swing.*;
import com.mysql.jdbc.ResultSetMetaData;

import java.sql.*;
import java.util.*;


public class DB {
	 Connection con=null;
	java.sql.PreparedStatement pst;
	public static Connection dbconnect() {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/passwordandnotesmanager","root","");
			System.out.println("running properly and connected to database");
			return conn;
		}
		catch(Exception e) {
			System.out.println(e);
			return null;
			
		}
	}

}
