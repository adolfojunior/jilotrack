package br.com.cubekode.jilotracktest.model;

import br.com.cubekode.jilotracktest.entity.TrackEntity;

import com.powerlogic.jcompany.commons.annotation.PlcAggregationIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcRepository;
import com.powerlogic.jcompany.model.PlcBaseRepository;

@SPlcRepository
@PlcAggregationIoC(clazz = TrackEntity.class)
public class TrackRepository extends PlcBaseRepository {

}
