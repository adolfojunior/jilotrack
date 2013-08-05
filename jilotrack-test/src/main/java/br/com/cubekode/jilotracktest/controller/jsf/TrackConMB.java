package br.com.cubekode.jilotracktest.controller.jsf;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import br.com.cubekode.jilotracktest.entity.TrackEntity;

import com.powerlogic.jcompany.commons.annotation.PlcUriIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcMB;
import com.powerlogic.jcompany.config.aggregation.PlcConfigAggregation;
import com.powerlogic.jcompany.config.collaboration.FormPattern;
import com.powerlogic.jcompany.config.collaboration.PlcConfigForm;
import com.powerlogic.jcompany.config.collaboration.PlcConfigFormLayout;
import com.powerlogic.jcompany.controller.jsf.annotations.PlcHandleException;

// CONFIG
@PlcConfigAggregation(entity = br.com.cubekode.jilotracktest.entity.TrackEntity.class)
@PlcConfigForm(formPattern = FormPattern.Con, formLayout = @PlcConfigFormLayout(dirBase = "/WEB-INF/fcls/track"))
// MB
@SPlcMB
@PlcUriIoC("trackcon")
@PlcHandleException
public class TrackConMB extends AppMB {

	private static final long serialVersionUID = 1L;

	@Produces
	@Named("trackconArg")
	public TrackEntity createEntityPlc() {
		if (this.entityPlc == null) {
			this.entityPlc = new TrackEntity();
			this.newEntity();
		}
		return (TrackEntity) this.entityPlc;
	}
}
