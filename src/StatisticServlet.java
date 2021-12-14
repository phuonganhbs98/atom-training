import java.io.IOException;

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

@WebServlet("/users/statistic")
public class StatisticServlet extends HttpServlet {
	private static final String jspPath = Prop.getPropValue("jspPath");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO: 以下はサンプルです。課題とは無関係の処理です。
		HttpSession session = request.getSession();
		User loginedUser = MyUtils.getLoginedUser(session);
		/*Connection conn = MyUtils.getStoredConnection(request);*/
		RequestDispatcher dispatcher //
				= this.getServletContext().getRequestDispatcher(jspPath + "statistic.jsp");

		dispatcher.forward(request, response);
	}
}
