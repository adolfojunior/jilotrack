package br.com.cubekode.jilotrack.store.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

public class ResultMapTransform implements ResultTransform<Map<String, Object>> {

	private static final ResultMapTransform INSTANCE = new ResultMapTransform();

	public static ResultMapTransform instance() {
		return INSTANCE;
	}

	@Override
	public Map<String, Object> transform(ResultSetMetaData metaData, ResultSet rs) throws SQLException {
		ResultMap map = new ResultMap();
		for (int c = 1, columns = metaData.getColumnCount(); c <= columns; c++) {
			map.put(metaData.getColumnName(c), rs.getObject(c));
		}
		return map;
	}
}
