import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atom.training.beans.User;
import com.atom.training.utils.CheckLoginUtils;
import com.atom.training.utils.MyUtils;
import com.atom.training.utils.Prop;
import com.atom.training.utils.ShowErrorUtils;
import com.atom.training.utils.UserUtils;

import jp.co.nobworks.openfunxion4.core.BlockLayout;
import jp.co.nobworks.openfunxion4.core.OpenFunXion;
import jp.co.nobworks.openfunxion4.core.OpenFunXionException;
import jp.co.nobworks.openfunxion4.core.Text;

@WebServlet("/users/print")
public class PrintServlet extends HttpServlet {
	private static final String jspPath = Prop.getPropValue("jspPath");
	private final static String XML_FILE = Prop.getPropValue("templateXml");
	private final static String PDF_FILE = Prop.getPropValue("templatePdf");

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO: 以下はサンプルです。課題とは無関係の処理です。
		User loginedUser = CheckLoginUtils.checkLogin(request, response);
		if (loginedUser == null) {
			return;
		}

		OpenFunXion ofx;
		try {
			ofx = new OpenFunXion(XML_FILE);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", XML_FILE + "指定されたファイルが見つかりません");
			RequestDispatcher dispatcher //
					= this.getServletContext().getRequestDispatcher(jspPath + "error.jsp");
			dispatcher.forward(request, response);
			return;
		}

		try {
			ofx.open(response);
		} catch (OpenFunXionException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			ShowErrorUtils.showError(request, response, e.getMessage(), this.getServletContext());
		}
		response.setContentType("application/pdf");
		try {
			makePdf(ofx, request);
		} catch (Exception e) {
			e.printStackTrace();
			ShowErrorUtils.showError(request, response, e.getMessage(), this.getServletContext());
		}
	}

	private void makePdf(OpenFunXion ofx, HttpServletRequest request) throws SQLException {
		List<User> dataList = getData(request);
		int totalPage = getTotalPage(dataList);
		printOutline(ofx);

		int moveY = 12 * 3;

		Integer pageNo = 1;
		Text page = ofx.getText("page");
		page.setMessage(pageNo.toString() + "/" + totalPage);
		page.print();

		int count = 0;

		//		Line rowLine = ofx.getLine("row_line");
		Text userId = ofx.getText("userId");
		Text name = ofx.getText("name");
		Text number = ofx.getText("number");
		Text gender = ofx.getText("gender");
		Text age = ofx.getText("age");
		Text role = ofx.getText("role");
		Text date = ofx.getText("date");

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		String dateFormat = format.format(new Date());
		date.setMessage(dateFormat);
		date.print();

		BlockLayout dataBlock = ofx.getBlockLayout("data_block");
		Integer preRole = dataList.get(0).getAuthorityId();
		role.setMessage(dataList.get(0).getRoleName() == null ? "未登録" : dataList.get(0).getRoleName());
		role.print();
		for (Iterator<User> it = dataList.iterator(); it.hasNext();) {
			User model = (User) it.next();

			if (count > 0 && (count % 20 == 0 || model.getAuthorityId() != preRole)) {
				ofx.newPage();
				dataBlock.resetPosition();

				printOutline(ofx);
				pageNo++;
				page.setMessage(pageNo.toString() + "/" + totalPage);
				page.print();
				date.setMessage(dateFormat);
				date.print();
				role.setMessage(model.getRoleName());
				role.print();
				if (model.getAuthorityId() != preRole) {
					count = 0;
				}
			}
			preRole = model.getAuthorityId();

			userId.setMessage(model.getUserId());
			userId.print();
			userId.moveY(moveY);

			Integer no = count + 1;
			number.setMessage(no.toString());
			number.print();
			number.moveY(moveY);

			name.setMessage(model.getFamilyName() + " " + model.getFirstName());
			name.print();
			name.moveY(moveY);

			gender.setMessage(model.getGenderName() == null ? "" : model.getGenderName());
			gender.print();
			gender.moveY(moveY);

			age.setMessage(model.getAge() == null ? "" : model.getAge().toString());
			age.print();
			age.moveY(moveY);

			count++;
		}
		ofx.out();
	}

	public void printOutline(OpenFunXion ofx) {
		ofx.print("body_block");
	}

	public List<User> getData(HttpServletRequest request) throws SQLException {
		List<User> users = null;
		Connection conn = MyUtils.getStoredConnection(request);
		try {
			User search = new User();
			String firstName = request.getParameter("firstName");
			String familyName = request.getParameter("familyName");
			String role = request.getParameter("role");
			search.setFirstName(firstName == null ? "" : firstName);
			search.setFamilyName(familyName == null ? "" : familyName);
			search.setAuthorityId((role == "" || role == null) ? null : Integer.parseInt(role));
			users = UserUtils.search(conn, search);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			throw e;
		} finally {
			MyUtils.closeConnection(conn);
		}
		return users;
	}

	public Integer getTotalPage(List<User> users) {
		int totalPage = 0;
		Integer authorityId = users.get(0).getAuthorityId();
		final Integer a = authorityId;
		int countUser = (int) users.stream().filter(x -> x.getAuthorityId() == a).count();

		totalPage = totalPage + (countUser / 20) + (countUser % 20 == 0 ? 0 : 1);
		for (User u : users) {
			if (u.getAuthorityId() != authorityId) {
				authorityId = u.getAuthorityId();
				final Integer check = authorityId;
				countUser = (int) users.stream().filter(x -> x.getAuthorityId() == check).count();
				totalPage = totalPage + (countUser / 20) + (countUser % 20 == 0 ? 0 : 1);
			}
		}
		return totalPage;
	}
}
