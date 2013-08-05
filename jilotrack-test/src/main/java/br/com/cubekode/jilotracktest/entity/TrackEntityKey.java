package br.com.cubekode.jilotracktest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class TrackEntityKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 64)
	@Column(name = "trail")
	private String trail;

	@NotNull
	@Column(name = "index")
	private Integer index;

	public String getTrail() {
		return trail;
	}

	public void setTrail(String trail) {
		this.trail = trail;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "TrackEntityKey [trail=" + trail + ", index=" + index + "]";
	}
}
