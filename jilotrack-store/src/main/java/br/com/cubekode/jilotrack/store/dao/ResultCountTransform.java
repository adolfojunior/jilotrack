package br.com.cubekode.jilotrack.store.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultCountTransform implements ResultTransform<Number> {

	private static final ResultCountTransform INSTANCE = new ResultCountTransform();

	public static ResultCountTransform instance() {
		return INSTANCE;
	}

	@Override
	public Number transform(ResultSetMetaData metaData, ResultSet rs) throws SQLException {
		return Number.class.cast(rs.getObject(1));
	}
}
