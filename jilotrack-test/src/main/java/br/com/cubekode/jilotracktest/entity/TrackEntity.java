package br.com.cubekode.jilotracktest.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.powerlogic.jcompany.commons.config.stereotypes.SPlcEntity;

// PLC
@SPlcEntity
// JPA
@Entity
@Access(AccessType.FIELD)
@Table(name = "TRACK")
@NamedQueries({ @NamedQuery(name = "TrackEntity.querySel", query = "from TrackEntity order by beginTime desc") })
public class TrackEntity extends AppBaseEntity {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TrackEntityKey id;

	@NotNull
	@Column(name = "type")
	private Integer type;

	@NotNull
	@Column(name = "value")
	private String value;

	@NotNull
	@Column(name = "begin_time")
	private Long beginTime;

	@NotNull
	@Column(name = "end_time")
	private Long endTime;

	@NotNull
	@Column(name = "parent")
	private Integer parent;

	public TrackEntityKey getId() {
		return id;
	}

	public void setId(TrackEntityKey id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "TrackEntity [id=" + id + ", type=" + type + ", value=" + value + ", beginTime=" + beginTime + ", endTime=" + endTime + ", parent=" + parent + "]";
	}
}
