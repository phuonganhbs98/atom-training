package com.atom.training.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.atom.training.beans.User;

public class UserUtils {
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

	public static List<User> findAllUsers(Connection conn) throws SQLException {
		String sql = "Select * from mst_user ";

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();
		List<User> list = new ArrayList<User>();
		while (rs.next()) {
			User u = new User();
			u.setUserId(rs.getString("user_id"));
			u.setPassword(rs.getString("password"));
			u.setFamilyName(rs.getString("family_name"));
			u.setFirstName(rs.getString("first_name"));
			u.setGenderId(rs.getInt("gender_id"));
			u.setAge(rs.getInt("age"));
			u.setAuthorityId(rs.getInt("authority_id"));
			list.add(u);
		}
		return list;
	}

}
