package br.com.cubekode.jilotrack.track;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

import br.com.cubekode.jilotrack.store.Store;

/**
 * Control the trail execution, with nested tracks in stack. Create error and
 * tag informations in current track context.
 * 
 * @author adolfojunior
 */
public class Trail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Track rootTrack;

	private String trail;

	private int trackCount;

	private int tagCount;

	private Deque<Track> trackStack;

	public Trail(String trail, int type, String entry) {
		this.trail = trail;
		this.tagCount = 0;
		this.trackCount = 0;
		this.trackStack = new LinkedList<Track>();
		this.rootTrack = createTrack(type, entry);
	}

	public boolean isRunning() {
		return rootTrack.isRunning();
	}

	public boolean isFinished() {
		return rootTrack.isFinished();
	}

	protected Track createTrack(int type, String value) {
		return Store.instance().createTrack(getParent(), this, trackCount++, type, value);
	}

	public Tag tag(String key, String value) {
		return Store.instance().createTag(getParent(), tagCount++, key, value);
	}

	public Tag error(String errorString) {
		return tag("ERROR", errorString);
	}

	protected Track beginTrack(int type, String entry) {
		if (rootTrack.isFinished()) {
			throw new IllegalStateException("Trail is finished");
		}
		Track track = createTrack(type, entry);
		trackStack.push(track);
		return track;
	}

	protected void endTrack(Trail trail) {
		endTrack(trail.getId());
	}

	protected void endTrack(String value) {
		endTrack(findTrack(value));
	}

	protected void endTrack(Track track) {
		if (track != null) {
			while (!trackStack.isEmpty()) {
				Track poped = trackStack.pop();
				finishTrack(poped);
				if (poped == track) {
					break;
				}
			}
			if (track == rootTrack) {
				finishTrack(track);
			}
		}
	}

	protected void finishTrack(Track track) {
		track.end();
		Store.instance().updateTrack(track);
	}

	protected Track findTrack(String entry) {
		if (rootTrack.isValue(entry)) {
			return rootTrack;
		} else {
			for (Track t : trackStack) {
				if (t.isValue(entry)) {
					return t;
				}
			}
		}
		return null;
	}

	public String getId() {
		return trail;
	}

	public Track getRoot() {
		return rootTrack;
	}

	public Track getCurrent() {
		return trackStack.peek();
	}
	
	public Track getParent() {
		Track peek = trackStack.peek();
		if (peek == null) {
			peek = rootTrack;
		}
		return peek;
	}
}
