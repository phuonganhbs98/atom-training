import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.atom.training.beans.User;
import com.atom.training.utils.CheckLoginUtils;
import com.atom.training.utils.MyUtils;

@WebServlet("/logout")
public class LogOutServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loginedUser = CheckLoginUtils.checkLogin(request, response);
		if (loginedUser == null) {
			return;
		}
		MyUtils.removeLoginedUser(session);
		response.sendRedirect(request.getContextPath() + "/login");
	}
}
