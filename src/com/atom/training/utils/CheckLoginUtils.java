package com.atom.training.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atom.training.beans.User;

public class CheckLoginUtils {
	public static User checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User loginedUser = MyUtils.getLoginedUser(session);
		if (loginedUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return null;
		}

		return loginedUser;
	}

}
