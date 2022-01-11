package com.atom.training.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.atom.training.utils.Prop;

public class PostgreConnUtils {
	public static Connection getConnection()
			throws ClassNotFoundException, SQLException {
		String userName = Prop.getPropValue("userName");
		String password = Prop.getPropValue("password");
		return getConnection(userName, password);
	}

	public static Connection getConnection(String userName, String password)
			throws SQLException, ClassNotFoundException {

		Class.forName("org.postgresql.Driver");

		String connectionURL = Prop.getPropValue("connectDb");

		Connection conn = DriverManager.getConnection(connectionURL, userName,
				password);
		return conn;
	}

}
