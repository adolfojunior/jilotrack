package br.com.cubekode.jilotrack.store.dao;

import java.util.TreeMap;

public class ResultMap extends TreeMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public ResultMap() {
		super(String.CASE_INSENSITIVE_ORDER);
	}
}
