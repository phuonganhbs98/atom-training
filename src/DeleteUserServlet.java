import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atom.training.beans.Role;
import com.atom.training.beans.User;
import com.atom.training.utils.MyUtils;
import com.atom.training.utils.Prop;
import com.atom.training.utils.RoleUtils;
import com.atom.training.utils.UserUtils;

@WebServlet("/users/delete")
public class DeleteUserServlet  extends HttpServlet{
	private static final String jspPath = Prop.getPropValue("jspPath");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = MyUtils.getStoredConnection(request);
		try {
			String userId = request.getParameter("userId");
			UserUtils.deleteUser(conn, userId);
			List<User> users = UserUtils.findAllUsers(conn);
			List<Role> roles = RoleUtils.findAllRoles(conn);
			MyUtils.closeConnection(conn);
			response.sendRedirect(request.getContextPath() + "/users");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
