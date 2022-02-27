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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.atom.training.entity.CurrentUser;
import com.atom.training.entity.Statistic;
import com.atom.training.entity.User;
import com.atom.training.response.ResultResponse;
import com.atom.training.security.JWTokenHelper;
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
	public Response search(User u) {
		String err = null;
		Connection conn = MyUtils.getStoredConnection();
		List<User> users = new ArrayList<>();
		try {
			users = UserUtils.searchOnAndroid(conn, u);
			if (users != null && users.size() == 1) {
				users.add(new User());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			err = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}

		if (err != null) {
			return ResultResponse.responseError(err, 500);
		}

		return ResultResponse.responseOk(new GenericEntity<List<User>>(users) {
		});
	}

	/**
	 * get detail infor
	 */
	@GET
	@Path("/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findByUserId(@PathParam("userId") String userId) {
		String err = null;
		Connection conn = MyUtils.getStoredConnection();
		User u = null;
		try {
			u = UserUtils.findByUserId(conn, userId);
		} catch (SQLException e) {
			e.printStackTrace();
			err = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}
		if (err == null) {
			return ResultResponse.responseOk(u);
		}
		return ResultResponse.responseError(err, 400);
	}

	/**
	 * sign up
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createUser(User u) {

		String result = null;
		if (u.getUserId() == null || u.getUserId() == "") {
			result = "ユーザIDが未入力です。";
		} else if (u.getPassword() == null || u.getPassword() == "") {
			result = "パスワードが未入力です。";
		} else if (u.getFamilyName() == null || u.getFamilyName() == "") {
			result = "姓が未入力です。";
		} else if (u.getFirstName() == null || u.getFirstName() == "") {
			result = "名が未入力です。";
		} else if (u.getUserId().length() > 8) {
			result = "ユーザーIDは8文字までしか入力できません。";
		} else if (u.getPassword().length() > 8) {
			result = "パスワードは8文字までしか入力できません。";
		} else if (u.getFamilyName().length() > 10) {
			result = "姓は10文字までしか入力できません。";
		} else if (u.getFirstName().length() > 10) {
			result = "名は10文字までしか入力できません。";
		}

		if (result != null) {
			return ResultResponse.responseError(result, 400);
		}

		Connection conn = MyUtils.getStoredConnection();
		try {
			User checkUser = UserUtils.findByUserId(conn, u.getUserId());
			if (checkUser != null) {
				result = "ユーザIDが重複しています。";
				return ResultResponse.responseError(result, 400);
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = format.format(new Date());
			u.setCreateDate(Long.valueOf(date));
			u.setUpdateDate(Long.valueOf(date));
			User loginedUser = CurrentUser.getInstance().getUser();
			u.setUpdateUserId(loginedUser.getUserId());
			u.setCreateUserId(loginedUser.getUserId());
			UserUtils.createUser(conn, u);
		} catch (SQLException e) {
			e.printStackTrace();
			result = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}

		if (result != null) {
			return ResultResponse.responseError(result, 500);
		}

		return ResultResponse.responseOk(new User());
	}

	/**
	 * update
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateUser(User u) {
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

		if (result != null) {
			return ResultResponse.responseError(result, 400);
		}

		Connection conn = MyUtils.getStoredConnection();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = format.format(new Date());
			u.setUpdateDate(Long.valueOf(date));
			User loginedUser = CurrentUser.getInstance().getUser();
			u.setUpdateUserId(loginedUser.getUserId());
			UserUtils.updateUser(conn, u);
		} catch (SQLException e) {
			e.printStackTrace();
			result = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}

		if (result != null) {
			return ResultResponse.responseError(result, 500);
		}
		return ResultResponse.responseOk(new User());
	}

	/**
	 * delete
	 */

	@DELETE
	@Path("/{userId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteUser(@PathParam("userId") String userId) {
		String result = null;
		Connection conn = MyUtils.getStoredConnection();
		User checkUser = null;
		try {
			checkUser = UserUtils.findByUserId(conn, userId);
			if (checkUser == null) {
				result = "ユーザーは存在しません。";
				return ResultResponse.responseError(result, 400);
			}
			UserUtils.deleteUser(conn, userId);

		} catch (SQLException e) {
			e.printStackTrace();
			result = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}

		if (result != null) {
			return ResultResponse.responseError(result, 500);
		}
		return ResultResponse.responseOk(new User());
	}

	/**
	 * login
	 */
	@POST
	@Path("/login")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response login(User u) {
		System.out.println(u.toString());
		String errorString = null;
		User user = null;
		if (u.getUserId() == null || u.getPassword() == null || u.getUserId().length() == 0
				|| u.getPassword().length() == 0) {
			errorString = "ログインに失敗しました。";
		} else {
			Connection conn = MyUtils.getStoredConnection();
			try {
				user = UserUtils.findUser(conn, u.getUserId(), u.getPassword());
				if (user == null) {
					errorString = "ログインに失敗しました。";
				} else if (user.getEnabled() == 0) {
					System.out.println(".......abcabc");
					errorString = "アカウントがロックされました";
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

		//トークン作成
		if (errorString == null) {
			String token = JWTokenHelper.createJWT(user);
			return ResultResponse.responseOk(token);
		}

		return ResultResponse.responseError(errorString, 401);
	}

	/**
	 * statistic
	 */
	@POST
	@Path("/statistics")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response statisticByRole() {
		List<Statistic> result = new ArrayList<>();
		String err = null;
		Connection conn = MyUtils.getStoredConnection();
		try {
			result = StatisticUtils.getStatistics(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			err = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}
		if (err != null) {
			return ResultResponse.responseError(err, 500);
		}
		return ResultResponse.responseOk(new GenericEntity<List<Statistic>>(result) {
		});
	}

	/**
	 * get profile
	 */
	@GET
	@Path("/profile")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getProfile() {
		String err = null;
		Connection conn = MyUtils.getStoredConnection();
		User u = null;
		try {
			User current = CurrentUser.getInstance().getUser();
			u = UserUtils.findByUserId(conn, current.getUserId());
		} catch (SQLException e) {
			e.printStackTrace();
			err = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}
		if (err == null) {
			return ResultResponse.responseOk(u);
		}
		return ResultResponse.responseError(err, 400);
	}

	/**
	 * lock
	 */
	@POST
	@Path("/{userId}/lock")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response lockUser(@PathParam("userId") String userId) {
		String err = null;
		Connection conn = MyUtils.getStoredConnection();
		User u = null;
		try {
			u = UserUtils.findByUserId(conn, userId);
			if (u == null) {
				err = "ユーザが存在しません";
			} else {
				UserUtils.lockUser(conn, userId, u.getEnabled() == 1 ? 0 : 1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			err = e.getMessage();
		} finally {
			MyUtils.closeConnection(conn);
		}
		if (err == null) {
			return ResultResponse.responseOk(new User());
		}
		return ResultResponse.responseError(err, 400);
	}
}
