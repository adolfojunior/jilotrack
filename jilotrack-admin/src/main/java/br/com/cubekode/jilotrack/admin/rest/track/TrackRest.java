package br.com.cubekode.jilotrack.admin.rest.track;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.cubekode.jilotrack.admin.rest.AbstractRest;
import br.com.cubekode.jilotrack.store.dao.track.TrackDAO;
import br.com.cubekode.jilotrack.util.IdentityUtil;

@Path("/track")
public class TrackRest extends AbstractRest {

	@GET
	@Path("/")
	public String index() {
		return "<pre>" + IdentityUtil.apiSignature() + "</pre>";
	}

	@GET
	@Path("/tracks")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public TrackResponse tracks(@QueryParam("type") Integer type, @QueryParam("time") Long time, @QueryParam("range") @DefaultValue("1") Long range) {

		time = time != null ? time : System.currentTimeMillis();
		range = TimeUnit.MINUTES.toMillis(Math.max(1, Math.min(range, 5)));

		long end = time - (time % range); // clear minute.
		long begin = end - range; // 1 minute in pass

		TrackDAO dao = new TrackDAO();
		TrackResponse response = new TrackResponse();

		try {
			response.setTracks(dao.find(type, begin, end));
		} catch (SQLException e) {
			throw new RuntimeException(e.toString());
		} finally {
			dao.close();
		}

		return response;
	}
}
