package br.com.cubekode.jilotrack.store.dao;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.cubekode.jilotrack.store.data.DataStoreConnection;

public class StoreDAO implements Closeable {

	private static final Pattern PARAM_NAME = Pattern.compile(":(\\w+)");

	private Connection connection;

	public Connection getConnection() throws SQLException {
		if (connection == null) {
			connection = DataStoreConnection.instance().getConnection();
		}
		return connection;
	}

	protected List<String> parseParameters(CharSequence query) {

		Matcher matcher = PARAM_NAME.matcher(query);

		List<String> names = new LinkedList<String>();

		while (matcher.find()) {
			names.add(matcher.group(1));
		}

		return names;
	}

	protected String parseQuery(CharSequence query) {

		return PARAM_NAME.matcher(query).replaceAll("?");
	}

	protected PreparedStatement createQuery(CharSequence query, QueryParameters parameters) throws SQLException {

		PreparedStatement ps = getConnection().prepareStatement(parseQuery(query));

		if (parameters != null && !parameters.isEmpty()) {

			int parameterIndex = 1;

			for (String name : parseParameters(query)) {
				ps.setObject(parameterIndex++, parameters.get(name));
			}
		}
		return ps;
	}

	protected <T> List<T> queryList(CharSequence query, QueryParameters parameters, ResultTransform<T> transformer) throws SQLException {

		PreparedStatement statement = createQuery(query, parameters);

		try {
			if (statement.execute()) {
				return resultList(statement.getResultSet(), transformer);
			}
			return Collections.emptyList();
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	protected <T> T querySingle(CharSequence query, QueryParameters parameters, ResultTransform<T> transformer) throws SQLException {

		PreparedStatement statement = createQuery(query, parameters);

		try {
			if (statement.execute()) {
				return resultSingle(statement.getResultSet(), transformer);
			}
			return null;
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
	}

	protected long queryCount(StringBuilder query, QueryParameters parameters) throws SQLException {
		Number count = querySingle(query, parameters, ResultCountTransform.instance());
		if (count != null) {
			return count.longValue();
		}
		return 0L;
	}

	protected <T> List<T> resultList(ResultSet rs, ResultTransform<T> transformer) throws SQLException {

		List<T> list = new ArrayList<T>();

		ResultSetMetaData metaData = rs.getMetaData();

		while (rs.next()) {
			list.add(transformer.transform(metaData, rs));
		}
		return list;
	}

	protected <T> T resultSingle(ResultSet rs, ResultTransform<T> transformer) throws SQLException {

		ResultSetMetaData metaData = rs.getMetaData();

		if (rs.next()) {
			return transformer.transform(metaData, rs);
		}
		return null;
	}

	@Override
	public void close() {
		DataStoreConnection.instance().close(connection);
		connection = null;
	}
}
