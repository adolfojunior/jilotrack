package br.com.cubekode.jilotrack.store.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import br.com.cubekode.jilotrack.store.StoreAppender;
import br.com.cubekode.jilotrack.track.Tag;
import br.com.cubekode.jilotrack.track.Track;
import br.com.cubekode.jilotrack.util.LogUtil;

public class DataStoreAppender extends StoreAppender {

	private static final String SQL_TRACK_MERGE = "MERGE INTO TRACK(TRAIL,INDEX,TYPE,PARENT,BEGIN_TIME,END_TIME,VALUE) KEY(TRAIL,INDEX) VALUES(?,?,?,?,?,?,?)";
	
	private static final String SQL_TRACK_DELETE = "DELETE FROM TRACK WHERE TRAIL=? AND INDEX=?";
	
	private static final String SQL_TAG_MERGE = "MERGE INTO TAG(TRAIL,INDEX,TRACK_INDEX,TIMESTAMP,KEY,VALUE) KEY(TRAIL,INDEX) VALUES(?,?,?,?,?,?)";


	@Override
	public void appendTracks(String id, Collection<Track> tracks) throws IOException {
		if (isActive()) {
			Connection connection = null;
			try {
				connection = DataStoreConnection.instance().getConnection();
				connection.setAutoCommit(true);
				updateTracks(connection, tracks);
			} catch (SQLException e) {
				LogUtil.error("DataBaseH2Appender error", e);
			} finally {
				DataStoreConnection.instance().close(connection);
			}
		}
	}
	
	@Override
	public void appendTags(String id, Collection<Tag> tags) throws IOException {
		if (isActive()) {
			Connection connection = null;
			try {
				connection = DataStoreConnection.instance().getConnection();
				connection.setAutoCommit(true);
				updateTags(connection, tags);
			} catch (SQLException e) {
				LogUtil.error("DataBaseH2Appender error", e);
			} finally {
				DataStoreConnection.instance().close(connection);
			}
		}
	}

	private void updateTracks(Connection connection, Collection<Track> tracks) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(SQL_TRACK_MERGE);

		try {
			for (Track track : tracks) {
				// Tracks with small values must be discarted.
				if (discart(track)) {
					delete(connection, track);
				} else {
					merge(ps, track);
				}
			}
		} finally {
			DataStoreConnection.instance().close(ps);
		}
	}
	
	private void updateTags(Connection connection, Collection<Tag> tags) throws SQLException {
		
		PreparedStatement ps = connection.prepareStatement(SQL_TAG_MERGE);
		
		try {
			for (Tag tag : tags) {
				// Tracks with small values must be discarted.
				merge(ps, tag);
			}
		} finally {
			DataStoreConnection.instance().close(ps);
		}
	}

	private boolean discart(Track track) {
		if (track.isType(Track.METHOD)) {
			if (track.isFinished() && track.getTime() < 2) {
				return true;
			}
		}
		return false;
	}

	private void delete(Connection connection, Track track) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(SQL_TRACK_DELETE);
		try {
			ps.setString(1, track.getTrail());
			ps.setInt(2, track.getIndex());
			ps.executeUpdate();
		} finally {
			DataStoreConnection.instance().close(ps);
		}
	}

	private void merge(PreparedStatement ps, Track track) throws SQLException {
		ps.clearParameters();
		ps.setString(1, track.getTrail());
		ps.setInt(2, track.getIndex());
		ps.setInt(3, track.getType());
		ps.setObject(4, track.getParent());
		ps.setLong(5, track.getBeginTime());
		ps.setLong(6, track.getEndTime());
		ps.setString(7, track.getValue());
		ps.executeUpdate();
	}
	
	private void merge(PreparedStatement ps, Tag tag) throws SQLException {
		ps.clearParameters();
		ps.setString(1, tag.getTrail());
		ps.setInt(2, tag.getIndex());
		ps.setInt(3, tag.getTrack());
		ps.setLong(4, tag.getTimestamp());
		ps.setString(5, tag.getKey());
		ps.setString(6, tag.getValue());
		ps.executeUpdate();
	}
	
	public static void main(String[] args) throws IOException {
		
		DataStoreAppender dataStoreAppender = new DataStoreAppender();
		
		LinkedList<Track> list = new LinkedList<Track>();
		
		for (int i = 0; i < 1000000; i++) {
			Track track = new Track();
			track.setTrail("XXXX");
			track.setIndex(i);
			track.setType(1);
			track.setBeginTime(1);
			track.setValue("XXXX");
			
			list.add(track);
			dataStoreAppender.appendTracks("XXXX", list);
			list.clear();
		}
	}
}
