package br.com.cubekode.jilotrack.store.dao.track;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import br.com.cubekode.jilotrack.store.dao.AbstractTransformer;
import br.com.cubekode.jilotrack.store.dao.ResultMapTransform;
import br.com.cubekode.jilotrack.store.dao.ResultTransform;
import br.com.cubekode.jilotrack.track.Tag;

public class TagResultTransform extends AbstractTransformer implements ResultTransform<Tag> {

	private static final TagResultTransform INSTANCE = new TagResultTransform();

	public static TagResultTransform instance() {
		return INSTANCE;
	}

	@Override
	public Tag transform(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
		return tag(ResultMapTransform.instance().transform(metaData, resultSet));
	}

	protected Tag tag(Map<String, Object> map) {
		Tag tag = new Tag();
		tag.setTrail(toString(map.get("trail")));
		tag.setIndex(toInt(map.get("index")));
		tag.setTrack(toInt(map.get("track_index")));
		tag.setTimestamp(toLong(map.get("timestamp")));
		tag.setKey(toString(map.get("key")));
		tag.setValue(toString(map.get("value")));
		return tag;
	}
}
