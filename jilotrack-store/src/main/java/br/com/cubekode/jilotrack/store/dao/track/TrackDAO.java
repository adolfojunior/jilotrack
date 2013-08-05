package br.com.cubekode.jilotrack.store.dao.track;

import java.sql.SQLException;
import java.util.List;

import br.com.cubekode.jilotrack.store.dao.QueryParameters;
import br.com.cubekode.jilotrack.store.dao.StoreDAO;
import br.com.cubekode.jilotrack.track.Track;

public class TrackDAO extends StoreDAO {

	private static final String SQL_COUNT_TRACK = "SELECT COUNT(1) FROM TRACK ";

	private static final String SQL_SELECT_TRACK = "SELECT TRAIL,INDEX,TYPE,PARENT,BEGIN_TIME,END_TIME,ENTRY,VALUE FROM TRACK ";

	public long count(Integer type, Long beginTime, Long endTime) throws SQLException {

		StringBuilder query = new StringBuilder();

		query.append(SQL_COUNT_TRACK).append(" WHERE TYPE = :type AND BEGIN_TIME < :endTime AND (END_TIME = -1 OR END_TIME > :beginTime)");

		QueryParameters parameters = new QueryParameters();
		parameters.put("type", type);
		parameters.put("beginTime", beginTime);
		parameters.put("endTime", endTime);

		return queryCount(query, parameters);
	}

	public List<Track> find(int type, long beginTime, long endTime) throws SQLException {

		StringBuilder query = new StringBuilder();

		query.append(SQL_SELECT_TRACK).append(" WHERE TYPE = :type AND BEGIN_TIME < :endTime AND (END_TIME = -1 OR END_TIME > :beginTime)");

		QueryParameters parameters = new QueryParameters();
		parameters.put("type", type);
		parameters.put("beginTime", beginTime);
		parameters.put("endTime", endTime);

		return queryList(query, parameters, TrackResultTransform.instance());
	}

	public static void main(String[] args) {

		TrackDAO trackDAO = new TrackDAO();

		try {
			System.out.println(trackDAO.count(1, 0L, System.currentTimeMillis()));
			System.out.println(trackDAO.count(2, 0L, System.currentTimeMillis()));
			System.out.println(trackDAO.count(3, 0L, System.currentTimeMillis()));
			System.out.println(trackDAO.count(4, 0L, System.currentTimeMillis()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			trackDAO.close();
		}

	}
}
