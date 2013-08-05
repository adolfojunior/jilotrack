package br.com.cubekode.jilotracktest.persistence.jpa;

import java.util.List;

import br.com.cubekode.jilotracktest.entity.TrackEntity;

import com.powerlogic.jcompany.commons.PlcBaseContextVO;
import com.powerlogic.jcompany.commons.annotation.PlcAggregationDAOIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcDataAccessObject;
import com.powerlogic.jcompany.persistence.jpa.PlcQuery;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryFirstLine;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryLineAmount;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryOrderBy;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryParameter;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryService;

@PlcQueryService
@SPlcDataAccessObject
@PlcAggregationDAOIoC(TrackEntity.class)
public class TrackDAO extends AppJpaDAO  {

	@PlcQuery("querySel")
	public native List<TrackEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   
			@PlcQueryParameter(name="id_trail", expression="id.trail = :id_trail") String trail,
			@PlcQueryParameter(name="id_index", expression="id.index = :id_index") Integer index,
			@PlcQueryParameter(name="type", expression="type = :type") Integer type,
			@PlcQueryParameter(name="value", expression="value like '%' || :value || '%' ") String value,
			@PlcQueryParameter(name="beginTime", expression="beginTime >= :beginTime  ") Long beginTime,
			@PlcQueryParameter(name="endTime", expression="endTime <= :endTime ") Long endTime,
			@PlcQueryParameter(name="parent", expression="parent = :parent") Integer parent
	);

	@PlcQuery("querySel")
	public native Long findCount(
			PlcBaseContextVO context,
			@PlcQueryParameter(name="id_trail", expression="id.trail = :id_trail") String trail,
			@PlcQueryParameter(name="id_index", expression="id.index = :id_index") Integer index,
			@PlcQueryParameter(name="type", expression="type = :type") Integer type,
			@PlcQueryParameter(name="value", expression="value like '%' || :value || '%' ") String value,
			@PlcQueryParameter(name="beginTime", expression="beginTime >= :beginTime  ") Long beginTime,
			@PlcQueryParameter(name="endTime", expression="endTime <= :endTime ") Long endTime,
			@PlcQueryParameter(name="parent", expression="parent = :parent") Integer parent
	);
	
}
