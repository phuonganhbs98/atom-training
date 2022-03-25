import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atom.training.entity.Role;
import com.atom.training.entity.User;
import com.atom.training.utils.MyUtils;
import com.atom.training.utils.Prop;
import com.atom.training.utils.RoleUtils;
import com.atom.training.utils.UserUtils;

import jp.co.nobworks.openfunxion4.core.BlockLayout;
import jp.co.nobworks.openfunxion4.core.OpenFunXion;
import jp.co.nobworks.openfunxion4.core.Text;

@WebServlet("/users/print2")
public class PrintServlet3 extends HttpServlet {
	private static final String jspPath = Prop.getPropValue("jspPath");
	private final static String XML_FILE = Prop.getPropValue("templateXml");
	private final static String PDF_FILE = Prop.getPropValue("templatePdf");

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long startTime = System.currentTimeMillis();
		OpenFunXion ofx = null;

		try {
			ofx = new OpenFunXion("/Users/phuon/Desktop/atom-training/template1.xml");
			ofx.open(response);
			makePdf(ofx, request);

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Processed in " + (endTime - startTime) + "ms");
	}

	private void makePdf(OpenFunXion ofx, HttpServletRequest request) throws SQLException {
		String template1 = "/Users/phuon/Desktop/atom-training/template1.xml";
		String template2 = "/Users/phuon/Desktop/atom-training/template2.xml";
		Connection conn = MyUtils.getStoredConnection(request);
		List<User> dataAll = getData(request);
		List<Role> roles = getRoles(conn, request);
		int countPage = getTotalPage(dataAll);
		boolean isFirst = true;
		Integer pageNo = 0;
		long start1 = System.currentTimeMillis();
		for (Role r : roles) {
			if (r.getAuthorityId() == 0 || r.getAuthorityId() == 1) {
				if (!isFirst) {
					ofx.newPage();
				} else {
					isFirst = false;
				}
				ofx.changePage(template1);
			} else {
				if (!isFirst) {
					ofx.newPage();
				} else {
					isFirst = false;
				}
				ofx.changePage(template2);
			}

			pageNo++;

			List<User> data = getUsersByRoleId(conn, request, r.getAuthorityId());
			printOutline(ofx);
			int moveY = 12 * 3;

			Text page = ofx.getText("page");
			page.setMessage(pageNo.toString() + "/" + countPage);
			page.print();

			int count = 0;

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
			role.setMessage(r.getAuthorityName() == null ? "未登録" : r.getAuthorityName());
			role.print();
			for (User u : data) {
				if (count > 0 && count % 20 == 0) {
					ofx.newPage();
					dataBlock.resetPosition();
					printOutline(ofx);
					pageNo++;
					page.setMessage(pageNo.toString() + "/" + countPage);
					page.print();
					date.setMessage(dateFormat);
					date.print();
					role.setMessage(r.getAuthorityName());
					role.print();

				}

				userId.setMessage(u.getUserId());
				userId.print();
				userId.moveY(moveY);

				Integer no = count + 1;
				number.setMessage(no.toString());
				number.print();
				number.moveY(moveY);

				name.setMessage(u.getFamilyName() + " " + u.getFirstName());
				name.print();
				name.moveY(moveY);

				gender.setMessage(u.getGenderName() == null ? "" : u.getGenderName());
				gender.print();
				gender.moveY(moveY);

				age.setMessage(u.getAge() == null ? "" : u.getAge().toString());
				age.print();
				age.moveY(moveY);

				count++;
			}

		}
		long end1 = System.currentTimeMillis();
		System.out.println("Processed in " + (end1 - start1) + "ms");
		ofx.out();
	}

	public void printOutline(OpenFunXion ofx) {
		ofx.print("body_block");
	}

	public List<Role> getRoles(Connection conn, HttpServletRequest request) throws SQLException {
		List<Role> roles = null;

		try {
			roles = RoleUtils.findAllRoles(conn);
		} catch (SQLException e) {
			throw e;
		} finally {

		}
		return roles;
	}

	public List<User> getUsersByRoleId(Connection conn, HttpServletRequest request, Integer roleId)
			throws SQLException {
		List<User> users = null;
		try {
			User search = new User();
			String firstName = request.getParameter("firstName");
			String familyName = request.getParameter("familyName");
			String role = request.getParameter("role");
			search.setFirstName(firstName == null ? "" : firstName);
			search.setFamilyName(familyName == null ? "" : familyName);
			search.setAuthorityId(roleId);
			users = UserUtils.search(conn, search);
		} catch (SQLException e) {
			throw e;
		} finally {
		}
		return users;
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
			throw e;
		} finally {
			MyUtils.closeConnection(conn);
		}
		return users;
	}

	public Integer getTotalPage(List<User> users) {
		users = users.stream().filter(x-> x.getAuthorityId()!=null).collect(Collectors.toList());
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
