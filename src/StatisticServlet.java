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

import com.atom.training.entity.Statistic;
import com.atom.training.entity.User;
import com.atom.training.utils.CheckLoginUtils;
import com.atom.training.utils.MyUtils;
import com.atom.training.utils.Prop;
import com.atom.training.utils.ShowErrorUtils;
import com.atom.training.utils.StatisticUtils;

@WebServlet("/users/statistic")
public class StatisticServlet extends HttpServlet {
	private static final String jspPath = Prop.getPropValue("jspPath");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		Connection conn = MyUtils.getStoredConnection(request);
		try {
			// TODO: 以下はサンプルです。課題とは無関係の処理です。
			User loginedUser = CheckLoginUtils.checkLogin(request, response);
			if (loginedUser == null) {
				return;
			}
			List<Statistic> result = StatisticUtils.getStatistics(conn);
			request.setAttribute("result", result);
			dispatcher = this.getServletContext().getRequestDispatcher(jspPath + "statistic.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			ShowErrorUtils.showError(request, response, e.getMessage(), this.getServletContext());
		} catch(Exception e) {
			e.printStackTrace();
			ShowErrorUtils.showError(request, response, e.getMessage(), this.getServletContext());
		} finally {
			MyUtils.closeConnection(conn);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
