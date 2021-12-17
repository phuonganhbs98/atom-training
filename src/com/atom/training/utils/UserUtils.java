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
			user.setUserId(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setFirstName(rs.getString("first_name"));
			user.setFamilyName(rs.getString("family_name"));
			return user;
		}
		return null;
	}

	public static User findByUserId(Connection conn, //
			String userId) throws SQLException {

		String sql = "Select * from mst_user mu " //
				+ " where mu.user_id = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userId);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setFirstName(rs.getString("first_name"));
			user.setFamilyName(rs.getString("family_name"));
			user.setGenderId(rs.getInt("gender_id"));
			user.setAge(rs.getInt("age"));
			user.setAuthorityId(rs.getInt("authority_id"));
			user.setAdmin(rs.getInt("admin"));
			return user;
		}
		return null;
	}

	public static List<User> search(Connection conn, //
			User u) throws SQLException {

		String sql = "SELECT U.USER_ID,\r\n" +
				"	U.PASSWORD,\r\n" +
				"	U.FAMILY_NAME,\r\n" +
				"	U.FIRST_NAME,\r\n" +
				"	U.GENDER_ID,\r\n" +
				"	(SELECT G.GENDER_NAME\r\n" +
				"		FROM MST_GENDER G\r\n" +
				"		WHERE G.GENDER_ID = U.GENDER_ID) GENDER_NAME,\r\n" +
				"	U.AGE,\r\n" +
				"	U.AUTHORITY_ID,\r\n" +
				"	(SELECT R.AUTHORITY_NAME\r\n" +
				"		FROM MST_ROLE R\r\n" +
				"		WHERE R.AUTHORITY_ID = U.AUTHORITY_ID) ROLE_NAME,\r\n" +
				"	U.ADMIN " +
				"FROM MST_USER U\r\n" +
				"WHERE U.FAMILY_NAME like ?\r\n" +
				"	AND U.FIRST_NAME like ?\r\n order by u.admin desc, u.user_id asc";
		if (u.getAuthorityId() != null) {
			sql = sql + "	AND U.AUTHORITY_ID = ?";
		}

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, "%" + u.getFamilyName() + "%");
		pstm.setString(2, "%" + u.getFirstName() + "%");
		if (u.getAuthorityId() != null) {
			pstm.setInt(3, u.getAuthorityId());
		}
		ResultSet rs = pstm.executeQuery();
		List<User> result = new ArrayList<>();
		while (rs.next()) {
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setFirstName(rs.getString("first_name"));
			user.setFamilyName(rs.getString("family_name"));
			user.setGenderId(rs.getInt("gender_id"));
			user.setGenderName(rs.getString("gender_name"));
			user.setAge(rs.getInt("age"));
			user.setAuthorityId(rs.getInt("authority_id"));
			user.setRoleName(rs.getString("role_name"));
			user.setAdmin(rs.getInt("admin"));
			result.add(user);
		}
		return result;
	}

	public static List<User> findAllUsers(Connection conn) throws SQLException {
		String sql = "SELECT U.USER_ID,\r\n" +
				"	U.PASSWORD,\r\n" +
				"	U.FAMILY_NAME,\r\n" +
				"	U.FIRST_NAME,\r\n" +
				"	U.GENDER_ID,\r\n" +
				"	(SELECT G.GENDER_NAME\r\n" +
				"		FROM MST_GENDER G\r\n" +
				"		WHERE G.GENDER_ID = U.GENDER_ID) GENDER_NAME,\r\n" +
				"	U.AGE,\r\n" +
				"	U.AUTHORITY_ID,\r\n" +
				"	(SELECT R.AUTHORITY_NAME\r\n" +
				"		FROM MST_ROLE R\r\n" +
				"		WHERE R.AUTHORITY_ID = U.AUTHORITY_ID) ROLE_NAME,\r\n" +
				"	U.ADMIN " +
				"FROM MST_USER U order by u.admin desc, u.user_id asc";

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
			u.setGenderName(rs.getString("gender_name"));
			u.setAge(rs.getInt("age"));
			u.setAuthorityId(rs.getInt("authority_id"));
			u.setRoleName(rs.getString("role_name"));
			u.setAdmin(rs.getInt("admin"));
			list.add(u);
		}
		return list;
	}

	public static void deleteUser(Connection conn, String userId) throws SQLException {
		String sql = "Delete From MST_USER WHERE USER_ID= ?";

		PreparedStatement pstm = conn.prepareStatement(sql);

		pstm.setString(1, userId);

		pstm.executeUpdate();
	}

	public static void createUser(Connection conn, User u) throws SQLException {
		String sql = "insert into mst_user(user_id, password, family_name, first_name, gender_id, age, authority_id, admin, create_user_id, update_user_id, create_date, update_date)\r\n"
				+
				"VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);
		int i = 1;
		pstm.setString(i++, u.getUserId());
		pstm.setString(i++, u.getPassword());
		pstm.setString(i++, u.getFamilyName());
		pstm.setString(i++, u.getFirstName());
		pstm.setInt(i++, u.getGenderId());
		pstm.setInt(i++, u.getAge());
		pstm.setInt(i++, u.getAuthorityId());
		pstm.setInt(i++, u.getAdmin());
		pstm.setString(i++, u.getCreateUserId());
		pstm.setString(i++, u.getUpdateUserId());
		pstm.setLong(i++, u.getCreateDate());
		pstm.setLong(i++, u.getUpdateDate());

		pstm.executeUpdate();
	}

	public static void updateUser(Connection conn, User u) throws SQLException {
		String sql = "update mst_user set password=?, family_name=?, first_name=?, gender_id=?, age=?, authority_id=?, admin=?, update_user_id=?, update_date=? where user_id=?\r\n";

		PreparedStatement pstm = conn.prepareStatement(sql);
		int i = 1;

		pstm.setString(i++, u.getPassword());
		pstm.setString(i++, u.getFamilyName());
		pstm.setString(i++, u.getFirstName());
		pstm.setInt(i++, u.getGenderId());
		pstm.setInt(i++, u.getAge());
		pstm.setInt(i++, u.getAuthorityId());
		pstm.setInt(i++, u.getAdmin());
		pstm.setString(i++, u.getUpdateUserId());
		pstm.setLong(i++, u.getUpdateDate());
		pstm.setString(i++, u.getUserId());

		pstm.executeUpdate();
	}
	
}
