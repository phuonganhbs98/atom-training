package com.atom.training.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreConnUtils {
	public static Connection getConnection()
			throws ClassNotFoundException, SQLException {
		String hostName = "localhost";
		String dbName = "training";
		String userName = "postgres";
		String password = "pa123";
		return getConnection(hostName, dbName, userName, password);
	}

	public static Connection getConnection(String hostName, String dbName,
			String userName, String password) throws SQLException,
			ClassNotFoundException {

		Class.forName("org.postgresql.Driver");

		String connectionURL = "jdbc:postgresql://" + hostName + ":5432/" + dbName;

		Connection conn = DriverManager.getConnection(connectionURL, userName,
				password);
		return conn;
	}

}
