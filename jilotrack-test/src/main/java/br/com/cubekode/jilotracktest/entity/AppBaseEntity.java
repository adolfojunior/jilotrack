package br.com.cubekode.jilotracktest.entity;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AppBaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
}
