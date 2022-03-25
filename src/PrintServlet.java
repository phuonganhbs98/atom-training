import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

@WebServlet("/users/print")
public class PrintServlet extends HttpServlet {
	private static final String jspPath = Prop.getPropValue("jspPath");
	private final static String XML_FILE = Prop.getPropValue("templateXml");
	private final static String PDF_FILE = Prop.getPropValue("templatePdf");

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO: 以下はサンプルです。課題とは無関係の処理です。
		//		User loginedUser = CheckLoginUtils.checkLogin(request, response);
		//		if (loginedUser == null) {
		//			return;
		//		}

		try {
			try {
				long start = System.currentTimeMillis();
				//								printByJasperReport(request);
				manyJrxmlFileExample(request);
				long end = System.currentTimeMillis();
				System.out.println("Processed in " + (end - start) + "ms");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	public List<User> getData(Connection conn, HttpServletRequest request, Integer roleId) throws SQLException {
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

	public List<Role> getRoles(HttpServletRequest request) throws SQLException {
		List<Role> roles = null;
		Connection conn = MyUtils.getStoredConnection(request);
		try {
			roles = RoleUtils.findAllRoles(conn);
		} catch (SQLException e) {
			throw e;
		} finally {
			MyUtils.closeConnection(conn);
		}
		return roles;
	}

	public ResultSet getResultSetUsers(HttpServletRequest request) throws SQLException {
		ResultSet rs = null;
		Connection conn = MyUtils.getStoredConnection(request);
		try {
			User search = new User();
			String firstName = request.getParameter("firstName");
			String familyName = request.getParameter("familyName");
			String role = request.getParameter("role");
			search.setFirstName(firstName == null ? "" : firstName);
			search.setFamilyName(familyName == null ? "" : familyName);
			search.setAuthorityId((role == "" || role == null) ? null : Integer.parseInt(role));
			rs = UserUtils.searchJasper(conn, search);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			throw e;
		} finally {
			MyUtils.closeConnection(conn);
		}
		return rs;
	}

	public ResultSet getResultSetUsers(Connection conn, HttpServletRequest request, Integer roleId)
			throws SQLException {
		ResultSet rs = null;
		try {
			User search = new User();
			String firstName = request.getParameter("firstName");
			String familyName = request.getParameter("familyName");
			String role = request.getParameter("role");
			search.setFirstName(firstName == null ? "" : firstName);
			search.setFamilyName(familyName == null ? "" : familyName);
			search.setAuthorityId(roleId);
			rs = UserUtils.searchJasper(conn, search);
		} catch (SQLException e) {
			throw e;
		} finally {

		}
		return rs;
	}

	private Integer getCountOfSearch(Connection conn, HttpServletRequest request, Integer roleId) throws SQLException {
		Integer count = 0;
		try {
			User search = new User();
			String firstName = request.getParameter("firstName");
			String familyName = request.getParameter("familyName");
			String role = request.getParameter("role");
			search.setFirstName(firstName == null ? "" : firstName);
			search.setFamilyName(familyName == null ? "" : familyName);
			search.setAuthorityId(roleId);
			count = UserUtils.getCountOfSearch(conn, search);
		} catch (SQLException e) {
			throw e;
		} finally {

		}
		return count;
	}

	//Jasper Report で帳票出力
	public void printByJasperReport(HttpServletRequest request)
			throws JRException, ClassNotFoundException, SQLException, FileNotFoundException {

		ResultSet rs = getResultSetUsers(request);

		Map<String, Object> parameters = new HashMap<String, Object>();
		String reportSrcFile = "C:\\Users\\phuon\\JaspersoftWorkspace\\AtomTraining\\users-report.jrxml";
		long start1 = System.currentTimeMillis();
		JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);
		long end1 = System.currentTimeMillis();
		JasperPrint print = JasperFillManager.fillReport(jasperReport,
				parameters, new JRResultSetDataSource(rs));
		long end2 = System.currentTimeMillis();
		System.out.println("compileaaa: " + (end1 - start1) + "ms");
		System.out.println("print: " + (end2 - end1) + "ms");
		// PDF Exportor.
		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setExporterInput(new SimpleExporterInput(print));
		// ExporterOutput
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				"C:\\Users\\phuon\\Desktop\\FirstJasperReport.pdf");

		// Output
		exporter.setExporterOutput(exporterOutput);

		//
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();
		try {
			Desktop.getDesktop().open(new File("C:\\Users\\phuon\\Desktop\\FirstJasperReport.pdf"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}

	//	複数のjrxmlファイル使いの例 - Resultset
	public void manyJrxmlFileExample(HttpServletRequest request)
			throws JRException, ClassNotFoundException, SQLException, FileNotFoundException {

		Connection conn = MyUtils.getStoredConnection(request);
		String reportSrcFile = "C:\\Users\\phuon\\JaspersoftWorkspace\\AtomTraining\\";
		List<JasperPrint> jprintlist = new ArrayList<>();
		List<Role> roles = getRoles(request);
		int page = 0;
		int totalPage = 0;
		boolean firstPage = true;
		long start1 = System.currentTimeMillis();
		JasperReport jasperReport1 = JasperCompileManager.compileReport(reportSrcFile + "users-report_type3.jrxml");
		JasperReport jasperReport2 = JasperCompileManager.compileReport(reportSrcFile + "users-report_type4.jrxml");
		long end1 = System.currentTimeMillis();
		System.out.println("compile: " + (end1 - start1) + "ms");
		for (Role r : roles) {
			JasperReport typeReport = null;
			if (r.getAuthorityId() == 0 || r.getAuthorityId() == 1) {
				typeReport = jasperReport1;
			} else {
				typeReport = jasperReport2;
			}
			ResultSet rs = getResultSetUsers(conn, request, r.getAuthorityId());
			Map<String, Object> parameters = new HashMap<String, Object>();
			Integer count = getCountOfSearch(conn, request, r.getAuthorityId());

			page = totalPage + 1;
			parameters.put("Page", page);
			JasperPrint print = JasperFillManager.fillReport(typeReport,
					parameters, new JRResultSetDataSource(rs));
			jprintlist.add(print);
			totalPage += getPageCount(count);
		}

		MyUtils.closeConnection(conn);
		System.out.println("total pages: " + totalPage);

		File outDir = new File("C:\\Users\\phuon\\Desktop");
		outDir.mkdirs();

		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setExporterInput(SimpleExporterInput.getInstance(jprintlist));

		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
				"C:\\Users\\phuon\\Desktop\\FirstJasperReport.pdf");

		exporter.setExporterOutput(exporterOutput);

		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		exporter.setConfiguration(configuration);
		exporter.exportReport();
		try {
			Desktop.getDesktop().open(new File("C:\\Users\\phuon\\Desktop\\FirstJasperReport.pdf"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}

	//	複数のjrxmlファイル使いの例 - CollectionBean
	//	public void manyJrxmlFileExample(HttpServletRequest request)
	//			throws JRException, ClassNotFoundException, SQLException, FileNotFoundException {
	//
	//		Connection conn = MyUtils.getStoredConnection(request);
	//		String reportSrcFile = "C:\\Users\\phuon\\JaspersoftWorkspace\\AtomTraining\\";
	//		List<JasperPrint> jprintlist = new ArrayList<>();
	//		List<Role> roles = getRoles(request);
	//		int page = 0;
	//		int totalPage = 0;
	//		boolean firstPage = true;
	//		for (Role r : roles) {
	//			String typeReport = null;
	//			if (r.getAuthorityId() == 0 || r.getAuthorityId() == 1) {
	//				typeReport = "users-report_type1.jrxml";
	//			} else {
	//				typeReport = "users-report_type2.jrxml";
	//			}
	////			ResultSet rs = getResultSetUsers(conn, request, r.getAuthorityId());
	//			List<User> rs = getData(conn, request, r.getAuthorityId());
	//			Map<String, Object> parameters = new HashMap<String, Object>();
	////			Integer count = getCountOfSearch(conn, request, r.getAuthorityId());
	//			Integer count = rs.size();
	//
	//			page = totalPage + 1;
	//			parameters.put("Page", page);
	//			JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile + typeReport);
	//			JasperPrint print = JasperFillManager.fillReport(jasperReport,
	//					parameters, new JRBeanCollectionDataSource(rs));
	//			jprintlist.add(print);
	//			totalPage += getPageCount(count);
	//		}
	//		MyUtils.closeConnection(conn);
	//		System.out.println("total pages: " + totalPage);
	//
	//		File outDir = new File("C:\\Users\\phuon\\Desktop");
	//		outDir.mkdirs();
	//
	//		JRPdfExporter exporter = new JRPdfExporter();
	//
	//		exporter.setExporterInput(SimpleExporterInput.getInstance(jprintlist));
	//
	//		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
	//				"C:\\Users\\phuon\\Desktop\\FirstJasperReport.pdf");
	//
	//		exporter.setExporterOutput(exporterOutput);
	//
	//		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
	//		exporter.setConfiguration(configuration);
	//		exporter.exportReport();
	//		try {
	//			Desktop.getDesktop().open(new File("C:\\Users\\phuon\\Desktop\\FirstJasperReport.pdf"));
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//
	//		System.out.println("Done");
	//	}

	//	サブレポートの例
	public void subReportExample(HttpServletRequest request)
			throws JRException, ClassNotFoundException, SQLException, FileNotFoundException {

		ResultSet users = getResultSetUsers(request);
		List<Role> roles = getRoles(request);
		ResultSet rs = getResultSetUsers(request);

		Map<String, Object> parameters = new HashMap<String, Object>();
		try {
			JasperReport jrSubStudent = JasperCompileManager
					.compileReport("C:\\Users\\phuon\\JaspersoftWorkspace\\AtomTraining\\users-report2.jrxml");
			parameters.put("UserSubReport", jrSubStudent);
			parameters.put("UserData", new JRResultSetDataSource(users));

			JasperReport jrSubClass = JasperCompileManager
					.compileReport("C:\\Users\\phuon\\JaspersoftWorkspace\\AtomTraining\\role-report.jrxml");
			String reportSrcFile3 = "C:\\Users\\phuon\\JaspersoftWorkspace\\AtomTraining\\users-report3.jrxml";
			JasperReport jasperReport3 = JasperCompileManager.compileReport(reportSrcFile3);
			parameters.put("RoleSubReport", jrSubClass);
			parameters.put("RoleData", new JRBeanCollectionDataSource(roles));
			parameters.put("TestReport", jasperReport3);

			JasperReport jasperReport = JasperCompileManager
					.compileReport("C:\\Users\\phuon\\JaspersoftWorkspace\\AtomTraining\\sub-report-test.jrxml");

			JasperPrint print = JasperFillManager.fillReport(jasperReport,
					parameters, new JRResultSetDataSource(users));

			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setExporterInput(new SimpleExporterInput(print));

			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
					"C:\\Users\\phuon\\Desktop\\FirstJasperReport.pdf");

			exporter.setExporterOutput(exporterOutput);

			SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
			exporter.setConfiguration(configuration);
			exporter.exportReport();
			try {
				Desktop.getDesktop().open(new File("C:\\Users\\phuon\\Desktop\\FirstJasperReport.pdf"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Done");
	}

	private Integer getPageCount(Integer size) {
		return size / 34 + (size % 34 == 0 ? 0 : 1);
	}

	private static JasperReport loadJasperReport(String reportName) {
		try {
			File f = new File(reportName + ".jasper");
			if (!f.exists()) {
				JasperCompileManager.compileReportToFile(reportName + ".jrxml", reportName + ".jasper");
				f = new File(reportName + ".jasper");
			}
			JasperReport jr = (JasperReport) JRLoader.loadObject(f);
			return jr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
