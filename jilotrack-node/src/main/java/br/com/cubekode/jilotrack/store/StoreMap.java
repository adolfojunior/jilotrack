package br.com.cubekode.jilotrack.store;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StoreMap<T> implements Iterable<String> {

	private int size;

	private Map<String, Set<T>> map = new HashMap<String, Set<T>>();

	public synchronized void put(String id, T value) {
		Set<T> set = map.get(id);
		if (set == null) {
			map.put(id, set = new HashSet<T>());
		}
		set.add(value);
		size++;
	}

	public synchronized Set<String> keySet() {
		return new HashSet<String>(map.keySet());
	}

	public synchronized boolean containsKey(String id) {
		return map.containsKey(id);
	}

	public synchronized Set<T> values(String id) {
		Set<T> set = map.get(id);
		if (set == null) {
			return Collections.emptySet();
		}
		return new HashSet<T>(set);
	}

	public synchronized Set<T> remove(String id) {
		Set<T> set = map.remove(id);
		if (set == null) {
			return Collections.emptySet();
		}
		size -= set.size();
		return new HashSet<T>(set);
	}

	public boolean remove(String id, T value) {
		Set<T> set = map.get(id);
		if (set != null) {
			if (set.remove(value)) {
				size--;
				return true;
			}
		}
		return false;
	}

	public synchronized Map<String, Set<T>> consume() {
		Map<String, Set<T>> comsumed = map;
		size = 0;
		map = new HashMap<String, Set<T>>();
		return comsumed;
	}

	@Override
	public Iterator<String> iterator() {
		return keySet().iterator();
	}

	public synchronized int size() {
		return size;
	}
}