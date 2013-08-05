package br.com.cubekode.jilotrack.store;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import br.com.cubekode.jilotrack.store.data.DataStoreAppender;
import br.com.cubekode.jilotrack.track.Tag;
import br.com.cubekode.jilotrack.track.Track;
import br.com.cubekode.jilotrack.track.Trail;
import br.com.cubekode.jilotrack.util.IdentityUtil;
import br.com.cubekode.jilotrack.util.LogUtil;

/**
 * @author adolfojunior
 */
public class Store {

	private static final long JOB_INTERVAL = TimeUnit.SECONDS.toMillis(4);

	private static final Store INSTANCE = new Store();

	public static Store instance() {
		return INSTANCE;
	}

	private String storeIdentity;

	private long trailCount;

	private StoreJob storeJob;

	private StoreAppender storeAppender;

	private StoreMap<Track> storeTracks;

	private StoreMap<Tag> storeTags;

	public Store() {
		this.trailCount = 0;
		this.storeTags = new StoreMap<Tag>();
		this.storeTracks = new StoreMap<Track>();
		this.storeIdentity = IdentityUtil.uid(this);
		this.storeAppender = new DataStoreAppender();
		this.storeJob = new StoreJob(JOB_INTERVAL);
	}

	public Trail createTrail(int type, String entry) {
		return new Trail(nextTrailId(), type, entry);
	}

	public Track createTrack(Track parent, Trail trail, int index, int type, String value) {
		Track track = new Track(parent, trail.getId(), index, type, value);
		updateTrack(track);
		return track;
	}

	public Tag createTag(Track track, int index, String key, String value) {
		Tag tag = new Tag(track, index, key, value);
		updateTag(tag);
		return tag;
	}

	/**
	 * All track updates.
	 */
	public void updateTrack(Track track) {
		storeTracks.put(track.getTrail(), track);
	}

	/**
	 * All tags.
	 */
	public void updateTag(Tag tag) {
		storeTags.put(tag.getTrail(), tag);
	}

	protected String nextTrailId() {
		return storeIdentity + "-" + Long.toString(trailCount++);
	}

	public StoreJob getStoreJob() {
		return storeJob;
	}

	class StoreJob implements Runnable {

		private long sleepTime;

		private StoreJob(long sleepTime) {
			this.sleepTime = sleepTime;
			new Thread(this, "Jilotrack-StoreJob").start();
		}

		@Override
		public void run() {

			while (true) {
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// back to work
				}
				try {
					store();
				} catch (Exception e) {
					LogUtil.error("TrackStoreJob error: ", e);
				}
			}
		}

		private void store() throws IOException {

			Map<String, Set<Track>> consumedTracks = storeTracks.consume();

			if (consumedTracks != null && !consumedTracks.isEmpty()) {
				for (Entry<String, Set<Track>> e : consumedTracks.entrySet()) {
					storeAppender.appendTracks(e.getKey(), e.getValue());
				}
			}

			Map<String, Set<Tag>> consumedTags = storeTags.consume();

			if (consumedTags != null && !consumedTags.isEmpty()) {
				for (Entry<String, Set<Tag>> e : consumedTags.entrySet()) {
					storeAppender.appendTags(e.getKey(), e.getValue());
				}
			}
		}
	}
}
