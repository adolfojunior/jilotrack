package br.com.cubekode.jilotrack.track;

import java.io.Serializable;

/**
 * Track unit represent execution with begin and end time.
 * 
 * @author adolfojunior
 */
public class Track implements Serializable {

	public static final int MAIN_TRAIL = 1;
	public static final int THREAD_TRAIL = 2;
	public static final int CHILD_TRAIL = 3;
	public static final int METHOD = 4;
	public static final int REQUEST = 5;
	public static final int JSF = 0;

	private static final long serialVersionUID = 1L;

	private Integer parent;

	private String trail;

	private int index;

	private int type;

	private long beginTime;

	private long endTime;

	private String value;

	public Track() {
	}

	public Track(Track parent, String trail, int index, int type, String value) {
		if (parent != null) {
			this.parent = parent.index;
		}
		this.trail = trail;
		this.index = index;
		this.type = type;
		this.value = value;
		this.beginTime = System.currentTimeMillis();
		this.endTime = -1;
	}

	public boolean isType(int type) {
		return this.type == type;
	}

	public boolean isValue(String value) {
		return value != null ? value.equals(this.value) : this.value == null;
	}

	public boolean isRunning() {
		return endTime == -1;
	}

	public boolean isFinished() {
		return endTime != -1;
	}

	public void end() {
		if (this.endTime == -1) {
			this.endTime = System.currentTimeMillis();
		}
	}

	public long getTime() {
		if (endTime > 0) {
			return endTime - beginTime;
		}
		return -1;
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
		Track other = (Track) obj;
		if (type != other.type) {
			return false;
		}
		if (index != other.index) {
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
		return "Track [trail=" + trail + ", index=" + index + ", type=" + type + ", entry=" + value + ", beginTime=" + beginTime + "]";
	}

	public String getTrail() {
		return trail;
	}

	public void setTrail(String trail) {
		this.trail = trail;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
