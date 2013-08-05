package br.com.cubekode.jilotrack.store.dao;

public class AbstractTransformer {

	protected String toString(Object object) {
		return object == null ? null : object.toString();
	}

	protected int toInt(Object object) {
		return object == null ? 0 : Integer.parseInt(object.toString());
	}

	protected Integer toIntObj(Object object) {
		return object == null ? null : Integer.valueOf(object.toString());
	}

	protected long toLong(Object object) {
		return object == null ? 0L : Long.parseLong(object.toString());
	}

	protected Long toLongObj(Object object) {
		return object == null ? 0L : Long.valueOf(object.toString());
	}
}
