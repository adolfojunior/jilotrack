package br.com.cubekode.jilotrack.store.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public interface ResultTransform<T> {

	T transform(ResultSetMetaData metaData, ResultSet rs) throws SQLException;
}
