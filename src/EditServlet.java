import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atom.training.beans.Gender;
import com.atom.training.beans.Role;
import com.atom.training.beans.User;
import com.atom.training.utils.CheckLoginUtils;
import com.atom.training.utils.GenderUtils;
import com.atom.training.utils.MyUtils;
import com.atom.training.utils.Prop;
import com.atom.training.utils.RoleUtils;
import com.atom.training.utils.UserUtils;

@WebServlet("/users/edit")
public class EditServlet extends HttpServlet {
	private static final String jspPath = Prop.getPropValue("jspPath");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User loginedUser = CheckLoginUtils.checkLogin(request, response);
		if (loginedUser == null) {
			return;
		}

		Connection conn = MyUtils.getStoredConnection(request);

		String userId = request.getParameter("userId");
		if (userId == null || userId == "") {
			response.sendRedirect(request.getContextPath() + "/users");
			return;
		}

		try {
			User user = UserUtils.findByUserId(conn, userId);
			List<Gender> genders = GenderUtils.findAllGenders(conn);
			List<Role> roles = RoleUtils.findAllRoles(conn);
			MyUtils.closeConnection(conn);
			request.setAttribute("genders", genders);
			request.setAttribute("roles", roles);
			request.setAttribute("user", user);
			request.setAttribute("edit", true);
			RequestDispatcher dispatcher //
					= this.getServletContext().getRequestDispatcher(jspPath + "signUp.jsp");

			dispatcher.forward(request, response);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		HttpSession session = request.getSession();
		User loginedUser = MyUtils.getLoginedUser(session);
		if (loginedUser == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		try {
			User user = new User();
			String userId = request.getParameter("userId");
			String password = request.getParameter("password");
			String firstName = request.getParameter("firstName");
			String familyName = request.getParameter("familyName");
			String admin = request.getParameter("admin");
			String gender = request.getParameter("gender");
			String age = request.getParameter("age");
			String role = request.getParameter("role");
			String errorString = null;
			if (userId == null || userId == "") {
				errorString = "ユーザIDが未入力です。";
			} else if (password == null || password == "") {
				errorString = "パスワードが未入力です。";
			} else if (familyName == null || familyName == "") {
				errorString = "姓が未入力です。";
			} else if (firstName == null || firstName == "") {
				errorString = "名が未入力です。";
			}

			user.setFirstName(firstName);
			user.setFamilyName(familyName);
			user.setUserId(userId);
			user.setPassword(password);
			user.setAdmin((admin == null || admin == "") ? 0 : Integer.parseInt(admin));
			user.setGenderId((gender == null || gender == "") ? null : Integer.parseInt(gender));
			user.setAge((age == null || age == "") ? null : Integer.parseInt(age));
			user.setAuthorityId((role == "" || role == null) ? null : Integer.parseInt(role));
			List<Gender> genders = GenderUtils.findAllGenders(conn);
			List<Role> roles = RoleUtils.findAllRoles(conn);

			RequestDispatcher dispatcher;
			if (errorString != null) {
				request.setAttribute("genders", genders);
				request.setAttribute("roles", roles);
				request.setAttribute("errorString", errorString);
				request.setAttribute("user", user);
				request.setAttribute("edit", true);
				dispatcher = this.getServletContext().getRequestDispatcher(jspPath + "signUp.jsp");

			} else {

				user.setUpdateUserId(loginedUser.getUserId());
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String date = format.format(new Date());
				user.setUpdateDate(Long.valueOf(date));

				UserUtils.updateUser(conn, user);
				request.setAttribute("title", "更新");
				dispatcher = this.getServletContext().getRequestDispatcher(jspPath + "finish.jsp");
			}
			MyUtils.closeConnection(conn);
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			MyUtils.closeConnection(conn);
			e.printStackTrace();
		}
	}

}
