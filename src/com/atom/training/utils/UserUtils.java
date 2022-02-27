package com.atom.training.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.atom.training.entity.User;

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
			Object enabled = rs.getObject("enabled");
			user.setEnabled(enabled == null ? 1 : (int) enabled);
			return user;
		}
		return null;
	}

	public static User findByUserId(Connection conn, //
			String userId) throws SQLException {

		String sql = "SELECT MU.USER_ID," +
				"	MU.PASSWORD," +
				"	MU.FAMILY_NAME," +
				"	MU.FIRST_NAME," +
				"	MU.GENDER_ID," +
				"	MG.GENDER_NAME GENDER_NAME," +
				"	MU.AGE," +
				"	MU.AUTHORITY_ID," +
				"	MR.AUTHORITY_NAME ROLE_NAME," +
				"	MU.ENABLED," +
				"	MU.ADMIN " +
				"FROM MST_USER MU " +
				"LEFT JOIN MST_ROLE MR ON MU.AUTHORITY_ID = MR.AUTHORITY_ID " +
				"LEFT JOIN MST_GENDER MG ON MU.GENDER_ID = MG.GENDER_ID " +
				"WHERE MU.USER_ID = ?;";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userId);
		ResultSet rs = pstm.executeQuery();

		if (rs.next()) {
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setFirstName(rs.getString("first_name"));
			user.setFamilyName(rs.getString("family_name"));
			Object genderId = rs.getObject("gender_id");
			user.setGenderId(genderId == null ? null : (int) genderId);
			Object age = rs.getObject("age");
			user.setAge(age == null ? null : (int) age);
			Object authorityId = rs.getObject("authority_id");
			user.setAuthorityId(authorityId == null ? null : (int) authorityId);
			user.setAdmin(rs.getInt("admin"));
			Object enabled = rs.getObject("enabled");
			user.setEnabled(enabled == null ? 1 : (int) enabled);
			user.setGenderName(rs.getString("gender_name"));
			user.setRoleName(rs.getString("role_name"));

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
				"	AND U.FIRST_NAME like ?\r\n ";
		if (u.getAuthorityId() != null) {
			sql = sql + "	AND U.AUTHORITY_ID = ?";
		}
		sql = sql + " order by U.AUTHORITY_ID desc, u.admin desc";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, "%" + (u.getFamilyName() == null ? "" : u.getFamilyName()) + "%");
		pstm.setString(2, "%" + (u.getFirstName() == null ? "" : u.getFirstName()) + "%");
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
			/*user.setGenderId(rs.getInt("gender_id"));*/
			user.setGenderName(rs.getString("gender_name"));
			Object genderId = rs.getObject("gender_id");
			user.setGenderId(genderId == null ? null : (int) genderId);
			Object age = rs.getObject("age");
			user.setAge(age == null ? null : (int) age);
			Object authorityId = rs.getObject("authority_id");
			user.setAuthorityId(authorityId == null ? null : (int) authorityId);
			user.setRoleName(rs.getString("role_name"));
			user.setAdmin(rs.getInt("admin"));
			result.add(user);
		}
		System.out.println(result);
		return result;
	}

	public static List<User> searchOnAndroid(Connection conn, //
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
				"	U.ADMIN, U.ENABLED " +
				"FROM MST_USER U\r\n" +
				"WHERE (U.FAMILY_NAME like ?\r\n" +
				"	OR U.FIRST_NAME like ?\r\n) ";
		if (u.getAuthorityId() != null) {
			sql = sql + "	AND U.AUTHORITY_ID = ?";
		}
		sql = sql + " order by U.AUTHORITY_ID desc, u.admin desc";

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, "%" + (u.getFamilyName() == null ? "" : u.getFamilyName()) + "%");
		pstm.setString(2, "%" + (u.getFirstName() == null ? "" : u.getFirstName()) + "%");
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
			/*user.setGenderId(rs.getInt("gender_id"));*/
			user.setGenderName(rs.getString("gender_name"));
			Object genderId = rs.getObject("gender_id");
			user.setGenderId(genderId == null ? null : (int) genderId);
			Object age = rs.getObject("age");
			user.setAge(age == null ? null : (int) age);
			Object authorityId = rs.getObject("authority_id");
			user.setAuthorityId(authorityId == null ? null : (int) authorityId);
			user.setRoleName(rs.getString("role_name"));
			user.setAdmin(rs.getInt("admin"));
			Object enabled = rs.getObject("enabled");
			user.setEnabled(enabled == null ? 1 : (int) enabled);
			result.add(user);
		}
		System.out.println(result);
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
				"	U.ADMIN, U.ENABLED " +
				"FROM MST_USER U order by u.authority_id desc, u.admin desc";

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rs = pstm.executeQuery();
		List<User> list = new ArrayList<User>();
		while (rs.next()) {
			User u = new User();
			u.setUserId(rs.getString("user_id"));
			u.setPassword(rs.getString("password"));
			u.setFamilyName(rs.getString("family_name"));
			u.setFirstName(rs.getString("first_name"));
			Object genderId = rs.getObject("gender_id");
			u.setGenderId(genderId == null ? null : (int) genderId);
			Object age = rs.getObject("age");
			u.setAge(age == null ? null : (int) age);
			Object authorityId = rs.getObject("authority_id");
			u.setAuthorityId(authorityId == null ? null : (int) authorityId);
			u.setGenderName(rs.getString("gender_name"));
			u.setRoleName(rs.getString("role_name"));
			u.setAdmin(rs.getInt("admin"));
			Object enabled = rs.getObject("enabled");
			u.setEnabled(enabled == null ? 1 : (int) enabled);
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
		String sql = "insert into mst_user(user_id, password, family_name, first_name, gender_id, age, authority_id, admin, create_user_id, update_user_id, create_date, update_date, enabled)\r\n"
				+
				"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement pstm = conn.prepareStatement(sql);
		int i = 1;
		pstm.setString(i++, u.getUserId());
		pstm.setString(i++, u.getPassword());
		pstm.setString(i++, u.getFamilyName());
		pstm.setString(i++, u.getFirstName());
		pstm.setObject(i++, u.getGenderId());
		pstm.setObject(i++, u.getAge());
		pstm.setObject(i++, u.getAuthorityId());
		pstm.setInt(i++, u.getAdmin());
		pstm.setString(i++, u.getCreateUserId());
		pstm.setString(i++, u.getUpdateUserId());
		pstm.setLong(i++, u.getCreateDate());
		pstm.setLong(i++, u.getUpdateDate());
		pstm.setInt(i++, 1);

		pstm.executeUpdate();
	}

	public static void lockUser(Connection conn, String userId, Integer enabled) throws SQLException {
		String sql = "update mst_user set enabled= ? where userId = ?";

		PreparedStatement pstm = conn.prepareStatement(sql);
		int i = 1;
		pstm.setInt(i++, enabled);
		pstm.setString(i++, userId);

		pstm.executeUpdate();
	}

	public static void updateUser(Connection conn, User u) throws SQLException {
		String sql = "update mst_user set password=?, family_name=?, first_name=?, gender_id=?, age=?, authority_id=?, admin=?, update_user_id=?, update_date=?, enabled=? where user_id=?\r\n";

		PreparedStatement pstm = conn.prepareStatement(sql);
		int i = 1;
		System.out.println("enabled= " + u.getEnabled());
		pstm.setString(i++, u.getPassword());
		pstm.setString(i++, u.getFamilyName());
		pstm.setString(i++, u.getFirstName());
		pstm.setObject(i++, u.getGenderId());
		pstm.setObject(i++, u.getAge());
		pstm.setObject(i++, u.getAuthorityId());
		pstm.setInt(i++, u.getAdmin());
		pstm.setString(i++, u.getUpdateUserId());
		pstm.setLong(i++, u.getUpdateDate());
		pstm.setInt(i++, u.getEnabled());
		pstm.setString(i++, u.getUserId());

		pstm.executeUpdate();
	}

}
