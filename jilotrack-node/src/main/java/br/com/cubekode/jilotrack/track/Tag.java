package br.com.cubekode.jilotrack.track;

import java.io.Serializable;

/**
 * Tag with information of execution context.
 * 
 * @author adolfojunior
 */
public class Tag implements Serializable {

	private static final long serialVersionUID = 1L;

	private String trail;

	private int index;

	private int track;

	private long timestamp;

	private String key;

	private String value;

	public Tag() {
	}

	public Tag(Track track, int index, String key, String value) {
		this.trail = track.getTrail();
		this.index = index;
		this.track = track.getIndex();
		this.key = key;
		this.value = value;
		this.timestamp = System.currentTimeMillis();
	}

	@Override
	public int hashCode() {
		return index;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Tag other = (Tag) obj;
		if (index != other.index) {
			return false;
		}
		if (track != other.track) {
			return false;
		}
		if (trail == null) {
			if (other.trail != null) {
				return false;
			}
		} else if (!trail.equals(other.trail)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Tag [trail=" + trail + ", index=" + index + ", track=" + track + ", timestamp=" + timestamp + ", key=" + key + ", value=" + value + "]";
	}

	public String getTrail() {
		return trail;
	}

	public void setTrail(String trail) {
		this.trail = trail;
	}

	public int getTrack() {
		return track;
	}

	public void setTrack(int track) {
		this.track = track;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
