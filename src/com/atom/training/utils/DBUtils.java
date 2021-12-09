package com.atom.training.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.atom.training.beans.User;

public class DBUtils {
	public static User findUser(Connection conn, //
			String userId, String password) throws SQLException {

		String sql = "Select * from mst_user mu " //
				+ " where mu.user_id = ? and mu.password=?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userId);
		pstm.setString(2, password);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			User user = new User();
			user.setPassword(rs.getString("password"));
			user.setFirstName(rs.getString("first_name"));
			user.setFamilyName(rs.getString("family_name"));
			return user;
		}
		return null;
	}

}
