package br.com.cubekode.jilotrack.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import br.com.cubekode.jilotrack.track.Track;
import br.com.cubekode.jilotrack.track.Tracker;

/**
 * Explicit Tracker for Servlet Request.
 * 
 * @author adolfo.junior
 */
public class TrackerServletListener implements ServletContextListener, HttpSessionListener, ServletRequestListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
	}

	@Override
	public void requestInitialized(ServletRequestEvent event) {
		String url = "Request";
		if (event.getServletRequest() instanceof HttpServletRequest) {
			url = ((HttpServletRequest) event.getServletRequest()).getRequestURL().toString();
		}
		Tracker.instance().beginTrail(Track.REQUEST, url);
		// add request info.
		ServletInfo.trackServlet(event.getServletRequest());
	}

	@Override
	public void requestDestroyed(ServletRequestEvent event) {
		Tracker.instance().endTrail();
	}
}
