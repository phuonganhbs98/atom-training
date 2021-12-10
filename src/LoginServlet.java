
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atom.training.beans.User;
import com.atom.training.utils.MyUtils;
import com.atom.training.utils.Prop;
import com.atom.training.utils.UserUtils;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String jspPath = Prop.getPropValue("jspPath");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO: 以下はサンプルです。課題とは無関係の処理です。
		HttpSession session = request.getSession();
		User loginedUser = MyUtils.getLoginedUser(session);
		if (loginedUser != null) {
			response.sendRedirect(request.getContextPath() + "/users");
		} else {
			RequestDispatcher dispatcher //
					= this.getServletContext().getRequestDispatcher(jspPath + "login.jsp");

			dispatcher.forward(request, response);
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("userID");
		String password = request.getParameter("password");

		User user = null;
		boolean hasError = false;
		String errorString = null;

		if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
			hasError = true;
			errorString = "ログインに失敗しました。";
		} else {
			Connection conn = MyUtils.getStoredConnection(request);
			try {
				// Tìm user trong DB.
				user = UserUtils.findUser(conn, userName, password);
				MyUtils.closeConnection(conn);
				if (user == null) {
					hasError = true;
					errorString = "ログインに失敗しました。";
				} else {
					errorString = null;
				}

			} catch (SQLException e) {
				e.printStackTrace();
				hasError = true;
				errorString = e.getMessage();
			}
		}

		if (hasError) {
			user = new User();
			user.setUserId(userName);
			user.setPassword(password);

			request.setAttribute("errorString", errorString);
			request.setAttribute("user", user);

			RequestDispatcher dispatcher //
					= this.getServletContext().getRequestDispatcher(jspPath + "login.jsp");

			dispatcher.forward(request, response);
		} else {
			HttpSession session = request.getSession();
			MyUtils.storeLoginedUser(session, user);
			response.sendRedirect(request.getContextPath() + "/users");
		}

	}

}
