package br.com.cubekode.jilotrack.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import br.com.cubekode.jilotrack.track.Track;
import br.com.cubekode.jilotrack.track.Tracker;

@Aspect
public abstract class TrackAspect {

	@Pointcut("execution(* *(..)) && !within(br.com.cubekode.jilotrack..*)")
	public void trackPointcut() {
	}

	@Pointcut("within(javax.sql.DataSource+)")
	public void dataSourcePointcut() {
	}

	@Pointcut("within(javax.sql.Statement+)")
	public void statementPointcut() {
	}

	@Pointcut("within(javax.servlet.ServletContextListener+)")
	public void servletListenerPointcut() {
	}

	@Pointcut("within(javax.servlet.http.HttpServlet+)")
	public void servletPointcut() {
	}

	@Pointcut("within(javax.faces.event.PhaseListener+)")
	public void facesPhaseListenerPointcut() {
	}

	@Pointcut("dataSourcePointcut() || statementPointcut() || servletListenerPointcut() || servletPointcut() || facesPhaseListenerPointcut()")
	public void apiPointcut() {
	}

	protected void beforeTrack(JoinPoint joinPoint) {
		Tracker.instance().beginTrack(Track.METHOD, joinPoint.getSignature().toString());
	}

	protected void afterTrackReturning(JoinPoint joinPoint) {
		Tracker.instance().endTrack(joinPoint.getSignature().toString());
	}

	protected void afterTrackThrowing(JoinPoint joinPoint, Throwable e) {
		Tracker.instance().endTrack(joinPoint.getSignature().toString(), e);
	}
}
