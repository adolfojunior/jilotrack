package br.com.cubekode.jilotrack.store.dao.track;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import br.com.cubekode.jilotrack.store.dao.AbstractTransformer;
import br.com.cubekode.jilotrack.store.dao.ResultMapTransform;
import br.com.cubekode.jilotrack.store.dao.ResultTransform;
import br.com.cubekode.jilotrack.track.Track;

public class TrackResultTransform extends AbstractTransformer implements ResultTransform<Track> {

	private static final TrackResultTransform INSTANCE = new TrackResultTransform();

	public static TrackResultTransform instance() {
		return INSTANCE;
	}

	@Override
	public Track transform(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
		return track(ResultMapTransform.instance().transform(metaData, resultSet));
	}

	protected Track track(Map<String, Object> map) {
		Track track = new Track();
		track.setTrail(toString(map.get("trail")));
		track.setIndex(toInt(map.get("index")));
		track.setBeginTime(toLong(map.get("begin_time")));
		track.setEndTime(toLong(map.get("end_time")));
		track.setType(toInt(map.get("type")));
		track.setValue(toString(map.get("value")));
		track.setParent(toIntObj(map.get("parent")));
		return track;
	}
}
