import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atom.training.beans.User;
import com.atom.training.utils.MyUtils;
import com.atom.training.utils.Prop;
import com.atom.training.utils.UserUtils;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
	private static final String jspPath = Prop.getPropValue("jspPath");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		try {
			List<User> users = UserUtils.findAllUsers(conn);
			request.setAttribute("users", users);
			RequestDispatcher dispatcher //
					= this.getServletContext().getRequestDispatcher(jspPath + "userInfor.jsp");

			dispatcher.forward(request, response);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
