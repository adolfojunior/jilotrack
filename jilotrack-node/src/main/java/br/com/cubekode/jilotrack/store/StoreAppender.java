package br.com.cubekode.jilotrack.store;

import java.io.IOException;
import java.util.Collection;

import br.com.cubekode.jilotrack.track.Tag;
import br.com.cubekode.jilotrack.track.Track;

public abstract class StoreAppender {

	private boolean active = true;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public abstract void appendTracks(String id, Collection<Track> tracks) throws IOException;

	public abstract void appendTags(String id, Collection<Tag> tracks) throws IOException;
}
