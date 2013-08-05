package br.com.cubekode.jilotrack.servlet;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.cubekode.jilotrack.track.Track;
import br.com.cubekode.jilotrack.track.Tracker;

/**
 * Explicit Tracker for JSF.
 * 
 * @author adolfojunior
 */
public class TrackerFacesPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	public void beforePhase(PhaseEvent event) {
		Tracker.instance().beginTrack(Track.JSF, "JSF-" + event.getPhaseId().toString());
	}

	public void afterPhase(PhaseEvent event) {
		Tracker.instance().endTrack("JSF-" + event.getPhaseId().toString());
	}
}