package net.daw.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Servlet implementation class json
 */
public class json extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public json() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String strJson = "";
		String strOp = request.getParameter("op");

		if (strOp != null) {
			if (!strOp.equalsIgnoreCase("")) {

				if (strOp.equalsIgnoreCase("connect")) {
					// conexion a la base de datos

					try {
						Class.forName("com.mysql.jdbc.Driver");

					} catch (Exception ex) {
						strJson = "{\"status\":500,\"msg\":\"jdbc driver not found\"}";
					}

					HikariConfig config = new HikariConfig();
					config.setJdbcUrl("jdbc:mysql://localhost:3306/trolleyes");
					config.setUsername("root");
					config.setPassword("bitnami");

					config.addDataSourceProperty("cachePrepStmts", "true");
					config.addDataSourceProperty("prepStmtCacheSize", "250");
					config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

					config.setMaximumPoolSize(10);
					config.setMinimumIdle(5);
					config.setLeakDetectionThreshold(15000);
					config.setConnectionTestQuery("SELECT 1");
					config.setConnectionTimeout(2000);

					try {
						HikariDataSource oConnectionPool = new HikariDataSource(config);
						Connection oConnection = (Connection) oConnectionPool.getConnection();
						strJson = "{\"status\":200,\"msg\":\"Hikari Connection OK\"}";
					} catch (SQLException ex) {
						strJson = "{\"status\":500,\"msg\":\"Bad Hikari Connection\"}";
					}

				}

				// http://localhost:8081/authentication/json?op=login&user=nombre&pass=password
				// http://localhost:8081/authentication/json?op=check
				// http://localhost:8081/authentication/json?op=logout
				response.setContentType("application/json;charset=UTF-8");
				HttpSession oSession = request.getSession();

				if (strOp.equalsIgnoreCase("login")) {
					String strUser = request.getParameter("user");
					String strPass = request.getParameter("pass");
					if (strUser.equals("rafa") && strPass.equals("thebest")) {
						oSession.setAttribute("daw", strUser);
						strJson = "{\"status\":200,\"msg\":\"" + strUser + "\"}";
					} else {
						strJson = "{\"status\":401,\"msg\":\"Authentication error\"}";
					}
				}
				if (strOp.equalsIgnoreCase("logout")) {
					oSession.invalidate();
					strJson = "{\"status\":200,\"msg\":\"Session is closed\"}";
				}
				if (strOp.equalsIgnoreCase("check")) {
					String strUserName = (String) oSession.getAttribute("daw");
					if (strUserName != null) {
						strJson = "{\"status\":200,\"msg\":\"" + strUserName + "\"}";
					} else {
						strJson = "{\"status\":401,\"msg\":\"Authentication error\"}";
					}
				}
				if (strOp.equalsIgnoreCase("getsecret")) {
					String strUserName = (String) oSession.getAttribute("daw");
					if (strUserName != null) {
						strJson = "{\"status\":200,\"msg\":\"985739847598\"}";
					} else {
						strJson = "{\"status\":401,\"msg\":\"Authentication error\"}";
					}
				}

			} else {
				strJson = "{\"status\":200,\"msg\":\"operation empty\"}";
			}
		} else {
			strJson = "{\"status\":200,\"msg\":\"operation can't be null\"}";
		}
		response.getWriter().append(strJson).close();
	}

}
