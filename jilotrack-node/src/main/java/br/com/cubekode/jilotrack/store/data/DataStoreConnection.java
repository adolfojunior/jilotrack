package br.com.cubekode.jilotrack.store.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.jdbcx.JdbcConnectionPool;

public class DataStoreConnection {

	private static final DataStoreConnection INSTANCE = new DataStoreConnection();

	public static DataStoreConnection instance() {
		return INSTANCE;
	}

	private String url = "jdbc:h2:tcp://127.0.0.1:9092/jilotrack";

	private String user = "SA";

	private String password = "";

	private JdbcConnectionPool pool;

	protected DataStoreConnection() {
		pool = JdbcConnectionPool.create(url, user, password);
	}

	public Connection getConnection() throws SQLException {
		return pool.getConnection();
	}

	public void close(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			// shhh
		}
	}

	public void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// shhh
			}
		}
	}
}
