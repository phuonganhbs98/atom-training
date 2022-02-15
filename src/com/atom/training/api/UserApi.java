package com.atom.training.api;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.atom.training.beans.JsonResponse;
import com.atom.training.beans.Statistic;
import com.atom.training.beans.User;
import com.atom.training.utils.MyUtils;
import com.atom.training.utils.StatisticUtils;
import com.atom.training.utils.UserUtils;

@Path("/users")
public class UserApi {

	/**
	 * search
	 */
	@POST
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> search(User u) {
		Connection conn = MyUtils.getStoredConnection();
		List<User> users = null;
		try {
			users = UserUtils.searchOnAndroid(conn, u);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			MyUtils.closeConnection(conn);
		}

		return users == null ? new ArrayList<>() : users;
	}

	/**
	 * get detail infor
	 */
	@GET
	@Path("/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public User findByUserId(@PathParam("userId") String userId) {
		Connection conn = MyUtils.getStoredConnection();
		User u = null;
		try {
			u = UserUtils.findByUserId(conn, userId);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			MyUtils.closeConnection(conn);
		}

		return u;
	}

	/**
	 * sign up
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public JsonResponse<String> createUser(User u) {

		System.out.println("-------------");
		System.out.println(u.toString());

		String result = null;
		if (u.getUserId() == null || u.getUserId() == "") {
			result = "ユーザIDが未入力です。";
		} else if (u.getPassword() == null || u.getPassword() == "") {
			result = "パスワードが未入力です。";
		} else if (u.getFamilyName() == null || u.getFamilyName() == "") {
			result = "姓が未入力です。";
		} else if (u.getFirstName() == null || u.getFirstName() == "") {
			result = "名が未入力です。";
		}

		if (result == null) {
			Connection conn = MyUtils.getStoredConnection();
			try {
				User checkUser = UserUtils.findByUserId(conn, u.getUserId());
				if (checkUser != null) {
					System.out.println("HERE...");
					result = "ユーザIDが重複しています。";
				} else {
					//					user.setCreateUserId(loginedUser.getUserId());
					//					user.setUpdateUserId(loginedUser.getUserId());
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
					String date = format.format(new Date());
					u.setCreateDate(Long.valueOf(date));
					u.setUpdateDate(Long.valueOf(date));
					UserUtils.createUser(conn, u);
				}
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				result = e.getMessage();
			} finally {
				MyUtils.closeConnection(conn);
			}
		}

		JsonResponse<String> response = new JsonResponse<>();
		if (result != null) {
			response.setMessage(result);
		} else {
			response.setValue("Success");
		}

		return response;
	}

	/**
	 * update
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	public JsonResponse<String> updateUser(User u) {

		System.out.println("-------------");
		System.out.println(u.toString());

		String result = null;
		if (u.getUserId() == null || u.getUserId() == "") {
			result = "ユーザIDが未入力です。";
		} else if (u.getPassword() == null || u.getPassword() == "") {
			result = "パスワードが未入力です。";
		} else if (u.getFamilyName() == null || u.getFamilyName() == "") {
			result = "姓が未入力です。";
		} else if (u.getFirstName() == null || u.getFirstName() == "") {
			result = "名が未入力です。";
		}

		if (result == null) {
			Connection conn = MyUtils.getStoredConnection();
			try {
				//					user.setCreateUserId(loginedUser.getUserId());
				//					user.setUpdateUserId(loginedUser.getUserId());
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String date = format.format(new Date());
				u.setUpdateDate(Long.valueOf(date));
				UserUtils.updateUser(conn, u);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				result = e.getMessage();
			} finally {
				MyUtils.closeConnection(conn);
			}
		}

		JsonResponse<String> response = new JsonResponse<>();
		if(result!=null) {
			response.setMessage(result);
		}else {
			response.setValue("Success");
		}

		return response;
	}

	/**
	 * delete
	 */

	@DELETE
	@Path("/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public JsonResponse<String> deleteUser(@PathParam("userId") String userId) {
		String result = null;
		Connection conn = MyUtils.getStoredConnection();
		try {
			User checkUser = UserUtils.findByUserId(conn, userId);
			if (checkUser == null) {
				result = "ユーザーは存在しません。";
			} else {
				UserUtils.deleteUser(conn, userId);
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			result = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}

		JsonResponse<String> response = new JsonResponse<>();
		if(result!=null) {
			response.setMessage(result);
		}else {
			response.setValue("Success");
		}
		return response;
	}

	/**
	 * login
	 */
	@POST
	@Path("/login")
	@Produces({ MediaType.APPLICATION_JSON })
	public JsonResponse<String> login(User u) {
		String errorString = null;
		if (u.getUserId() == null || u.getPassword() == null || u.getUserId().length() == 0
				|| u.getPassword().length() == 0) {
			errorString = "ログインに失敗しました。";
		} else {
			Connection conn = MyUtils.getStoredConnection();
			try {
				// Tìm user trong DB.
				User user = UserUtils.findUser(conn, u.getUserId(), u.getPassword());
				if (user == null) {
					errorString = "ログインに失敗しました。";
				} else {
					errorString = null;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			} catch (Exception e) {
				e.printStackTrace();
				errorString = e.getMessage();
			} finally {
				MyUtils.closeConnection(conn);
			}
		}

		JsonResponse<String> response = new JsonResponse<>();
		response.setMessage(errorString);
		response.setValue(null);
		return response;
	}

	/**
	 * statistic
	 */
	@POST
	@Path("/statistics")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Statistic> statisticByRole() {
		List<Statistic> result = new ArrayList<>();
		Connection conn = MyUtils.getStoredConnection();
		try {
			result = StatisticUtils.getStatistics(conn);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			MyUtils.closeConnection(conn);
		}
		return result;
	}
}
