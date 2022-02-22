package com.atom.training.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import com.atom.training.conn.ConnectionUtils;
import com.atom.training.entity.User;

public class MyUtils {
	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";

	private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

	public static Connection getStoredConnection(ServletRequest request) {
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static Connection getStoredConnection() {
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnection(Connection conn) {
		try {
			conn.commit();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeQuietly(conn);
		}
	}

	public static void storeLoginedUser(HttpSession session, User loginedUser) {
		session.setAttribute("loginedUser", loginedUser);
	}

	public static void removeLoginedUser(HttpSession session) {
		session.removeAttribute("loginedUser");
	}

	// Lấy thông tin người dùng lưu trữ trong Session.
	public static User getLoginedUser(HttpSession session) {
		User loginedUser = (User) session.getAttribute("loginedUser");
		return loginedUser;
	}



}
