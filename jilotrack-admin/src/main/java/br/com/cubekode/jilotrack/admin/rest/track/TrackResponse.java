package br.com.cubekode.jilotrack.admin.rest.track;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.cubekode.jilotrack.track.Track;

@XmlRootElement
public class TrackResponse {

	@XmlElement(name = "track")
	@XmlElementWrapper(name = "tracks")
	private List<Track> tracks;

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
}
