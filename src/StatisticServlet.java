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
import javax.servlet.http.HttpSession;

import com.atom.training.beans.Statistic;
import com.atom.training.beans.User;
import com.atom.training.utils.MyUtils;
import com.atom.training.utils.Prop;
import com.atom.training.utils.StatisticUtils;

@WebServlet("/users/statistic")
public class StatisticServlet extends HttpServlet {
	private static final String jspPath = Prop.getPropValue("jspPath");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// TODO: 以下はサンプルです。課題とは無関係の処理です。
			HttpSession session = request.getSession();
			User loginedUser = MyUtils.getLoginedUser(session);
			Connection conn = MyUtils.getStoredConnection(request);
			List<Statistic> result = StatisticUtils.getStatistics(conn);
			MyUtils.closeConnection(conn);
			request.setAttribute("result", result);
			RequestDispatcher dispatcher //
					= this.getServletContext().getRequestDispatcher(jspPath + "statistic.jsp");

			dispatcher.forward(request, response);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
