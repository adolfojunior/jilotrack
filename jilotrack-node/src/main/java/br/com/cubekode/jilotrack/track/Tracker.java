package br.com.cubekode.jilotrack.track;

import java.util.Deque;
import java.util.LinkedList;

import br.com.cubekode.jilotrack.store.Store;
import br.com.cubekode.jilotrack.util.IdentityUtil;

/**
 * Provide track control by thread. Control timeline of trail with tracks and
 * add contextual data information.
 * 
 * @see Trail
 * @see Track
 * @see Store
 * 
 * @author adolfojunior
 */
public class Tracker {

	private static final Tracker INSTANCE = new Tracker();

	public static Tracker instance() {
		return INSTANCE;
	}

	private Trail main;

	private TrailStack threadStack = new TrailStack();

	public Tracker() {
		mainTrail();
	}

	public Trail get() {
		return threadStack.get().peek();
	}

	public Trail trail() {
		Trail trail = threadStack.get().peek();
		if (trail == null) {
			trail = beginTrail(Track.THREAD_TRAIL, "THREAD-TRAIL");
		}
		return trail;
	}

	public Trail beginTrail(int type, String entry) {

		Deque<Trail> stack = threadStack.get();

		Trail trail = Store.instance().createTrail(type, entry);

		trail.tag("THREAD_NAME", IdentityUtil.threadName());

		if (!stack.isEmpty()) {
			stack.peek().beginTrack(Track.CHILD_TRAIL, trail.getId());
		} else {
			mainTrail().beginTrack(Track.CHILD_TRAIL, trail.getId());
		}

		stack.push(trail);

		return trail;
	}

	protected Trail mainTrail() {
		if (main == null) {
			synchronized (this) {
				if (main == null) {
					main = Store.instance().createTrail(Track.MAIN_TRAIL, "MAIN-TRAIL");
					main.tag("HOST_NAME", IdentityUtil.localHostName());
					main.tag("HOST_ADDRESS", IdentityUtil.localHostAddress());
					main.tag("JAVA_VENDOR", System.getProperty("java.vendor"));
					main.tag("JAVA_VERSION", System.getProperty("java.version"));
					main.tag("OS_ARCH", System.getProperty("os.arch"));
					main.tag("OS_NAME", System.getProperty("os.name"));
					main.tag("OS_VERSION", System.getProperty("os.version"));
					main.tag("THREAD_NAME", IdentityUtil.threadName());
				}
			}
		}
		return main;
	}

	public Trail endTrail() {

		Deque<Trail> stack = threadStack.get();

		if (stack.isEmpty()) {
			return null;
		}

		Trail trail = stack.pop();

		if (!stack.isEmpty()) {
			stack.peek().endTrack(trail);
		}

		return trail;
	}

	public void tag(String key, Object value) {
		if (value != null) {
			trail().tag(key, String.valueOf(value));
		}
	}

	public void error(Throwable e) {
		trail().error(e.toString());
	}

	public void beginTrack(int type, String entry) {
		trail().beginTrack(type, entry);
	}

	public void endTrack(String entry) {

		Trail trail = trail();

		trail.endTrack(entry);

		if (trail.isFinished()) {
			endTrail();
		}
	}

	public void endTrack(String entry, Throwable e) {
		if (e != null) {
			error(e);
		}
		endTrack(entry);
	}

	/**
	 * Nested trails in thread.
	 */
	private static class TrailStack extends ThreadLocal<Deque<Trail>> {
		protected java.util.Deque<Trail> initialValue() {
			return new LinkedList<Trail>();
		}
	}
	
	public static void main(String[] args) {
		Tracker.instance().mainTrail();
	}
}
