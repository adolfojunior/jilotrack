package br.com.cubekode.jilotrack.servlet;

import java.security.Principal;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.cubekode.jilotrack.track.Tracker;
import br.com.cubekode.jilotrack.track.Trail;

public class ServletInfo {

	public static final String TRACKER_REQUEST_ATTRIBUTE = ServletInfo.class.getName();

	public static void trackServlet(ServletRequest req) {

		Trail trail = Tracker.instance().trail();

		if (isTrackRequest(req)) {
			return;
		}

		req.setAttribute(TRACKER_REQUEST_ATTRIBUTE, Boolean.TRUE);

		trail.tag("REQUEST_CONTENTTYPE", req.getContentType());
		trail.tag("REQUEST_LOCALADDR", req.getLocalAddr());
		trail.tag("REQUEST_LOCALNAME", req.getLocalName());
		trail.tag("REQUEST_PROTOCOL", req.getProtocol());
		trail.tag("REQUEST_REMOTEADDR", req.getRemoteAddr());
		trail.tag("REQUEST_REMOTEHOST", req.getRemoteHost());
		trail.tag("REQUEST_REMOTEPORT", Integer.toString(req.getRemotePort()));
		trail.tag("REQUEST_SERVERPORT", Integer.toString(req.getServerPort()));
		trail.tag("REQUEST_LOCALE", (req.getLocale() != null ? req.getLocale().toString() : ""));

		if (req instanceof HttpServletRequest) {

			HttpServletRequest httpReq = (HttpServletRequest) req;

			trackHttpHeader(httpReq, trail);
			trackHttpRequest(httpReq, trail);
			trackHttpPrincipal(httpReq, trail);
			trackHttpSession(httpReq, trail);
		}

		trackRequestParams(req, trail);
	}

	private static void trackHttpHeader(HttpServletRequest httpReq, Trail trail) {

		Enumeration<String> names = httpReq.getHeaderNames();

		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String header = httpReq.getHeader(name);
			trail.tag("REQUEST_HEADER_" + name, (header == null ? "" : header));
		}
	}

	private static void trackRequestParams(ServletRequest req, Trail trail) {

		Map<String, String[]> parameterMap = req.getParameterMap();

		if (parameterMap != null) {
			for (Entry<String, String[]> p : parameterMap.entrySet()) {
				trail.tag("REQUEST_PARAM_" + p.getKey(), (p.getValue() == null ? "[]" : Arrays.toString(p.getValue())));
			}
		}
	}

	static void trackHttpRequest(HttpServletRequest req, Trail trail) {

		trail.tag("REQUEST_CONTEXTPATH", req.getContextPath());
		trail.tag("REQUEST_METHOD", req.getMethod());
		trail.tag("REQUEST_PATHINFO", req.getPathInfo());
		trail.tag("REQUEST_PATHTRANSLATED", req.getPathTranslated());
		trail.tag("REQUEST_REMOTEUSER", req.getRemoteUser());
		trail.tag("REQUEST_URI", req.getRequestURI());
		trail.tag("REQUEST_URL", req.getRequestURL().toString());
		trail.tag("REQUEST_QUERYSTRING", req.getQueryString());
		trail.tag("REQUEST_SERVLETPATH", req.getServletPath());
	}

	private static void trackHttpSession(HttpServletRequest req, Trail trail) {

		HttpSession session = req.getSession(false);

		if (session != null) {
			trail.tag("REQUEST_SESSION_ID", session.getId());
			trail.tag("REQUEST_SESSION_CREATIONTIME", Long.toString(session.getCreationTime()));
			trail.tag("REQUEST_SESSION_LASTACCESSEDTIME", Long.toString(session.getLastAccessedTime()));
			trail.tag("REQUEST_SESSION_MAXINACTIVEINTERVAL", Integer.toString(session.getMaxInactiveInterval()));
		} else {
			trail.tag("REQUEST_SESSION_ID", "");
		}
	}

	private static void trackHttpPrincipal(HttpServletRequest req, Trail trail) {

		Principal principal = req.getUserPrincipal();

		if (principal != null) {
			trail.tag("REQUEST_PRINCIPAL", principal.getName());
		} else {
			trail.tag("REQUEST_PRINCIPAL", "");
		}
	}

	public static boolean isTrackRequest(ServletRequest req) {
		return req.getAttribute(TRACKER_REQUEST_ATTRIBUTE) != null;
	}
}