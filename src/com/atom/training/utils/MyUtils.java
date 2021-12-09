package com.atom.training.utils;

import java.sql.Connection;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atom.training.beans.User;
import com.atom.training.conn.ConnectionUtils;

public class MyUtils {
	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";

	private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";

	public static void storeConnection(ServletRequest request, Connection conn) {
		request.setAttribute(ATT_NAME_CONNECTION, conn);
	}

	public static Connection getStoredConnection(ServletRequest request) {
		//		Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
		Connection conn = null;
		try {
			conn = ConnectionUtils.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
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

	public static void storeUserCookie(HttpServletResponse response, User user) {
		System.out.println("Store user cookie");
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserId());
		cookieUserName.setMaxAge(24 * 60 * 60);
		response.addCookie(cookieUserName);
	}

	public static String getUserNameInCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static void deleteUserCookie(HttpServletResponse response) {
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
		cookieUserName.setMaxAge(0);
		response.addCookie(cookieUserName);
	}

}
