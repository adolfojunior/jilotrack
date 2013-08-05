package br.com.cubekode.jilotrack.store.h2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.Server;

import br.com.cubekode.jilotrack.util.IOUtil;
import br.com.cubekode.jilotrack.util.IdentityUtil;

public class StoreServer {

	private static Server server = null;

	private static String user;

	private static String password;

	public static void main(String[] args) {

		System.out.println(IdentityUtil.apiSignature());

		try {

			loadConfig();

			initDriver();

			prepareDataBase();

			startServer();

			registerShutdow();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void loadConfig() {
		user = "SA";
		password = "";
	}

	private static void initDriver() {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
	}

	private static void prepareDataBase() throws SQLException {
		// open database within a VM
		Connection connection = DriverManager.getConnection("jdbc:h2:jilotrack", user, password);
		try {
			String ddl = IOUtil.getResourceAsString(StoreServer.class, "/ddl.sql");
			if (ddl != null) {
				System.out.print("DDL... ");
				connection.createStatement().execute(ddl);
				connection.commit();
			}
			System.out.println("created!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	private static void startServer() throws SQLException {
		// start a TCP server
		server = Server.createTcpServer("-tcpAllowOthers").start();
		// or use it from another process:
		System.out.println();
		System.out.println("Server started and connection is open.");
		System.out.println("URL: jdbc:h2:" + server.getURL() + "/jilotrack");
		System.out.println("Use H2 Web Console: \"h2.bat -web -browser\"");
	}

	private static void registerShutdow() {

		System.out.println();
		System.out.println("[CTRL+C] to shutdow");

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {

				System.out.println();
				System.out.println("shutdown...");

				if (server != null && server.isRunning(true)) {
					server.stop();
				}

				System.out.println("cya!");
			}
		}));
	}
}
