package com.mycollection.app;

public class MyMap<K, V> implements IMap<K, V> {
	private static final double LOAD_FACTOR = 0.8;
	private static final int DEFAULT_SIZE = 16;

	private MapNode<K, V>[] table;
	private int size;

	public MyMap() {
		this(DEFAULT_SIZE);
	}

	public MyMap(int capacity) {
		table = new MapNode[capacity];
	}

	private static final class MapNode<K, V> {
		final K key;
		final V value;
		MapNode<K, V> prev;
		MapNode<K, V> next;

		public MapNode(K k, V v) {
			key = k;
			value = v;
		}
	}

	private int slot(K key) {
		int hash = key.hashCode();
		return hash & table.length;
	}

	private MapNode<K, V> matchingKeyNode(K key) {
		int index = slot(key);
		MapNode<K, V> node = table[index];
		while (node != null && !key.equals(node.key)) {
			node = node.next;
		}
		return node;
	}

	@Override
	public boolean containsKey(K key) {
		MapNode<K, V> match = matchingKeyNode(key);
		return match != null;
	}

	private MapNode<K, V> matchingValueNode(MapNode<K, V> node, V val) {
		if (node == null || val.equals(node.value)) {
			return node;
		}
		return matchingValueNode(node.next, val);
	}

	@Override
	public boolean containsValue(V value) {
		MapNode<K, V> match = null;
		int i = 0;
		while (match == null && i < table.length) {
			match = matchingValueNode(table[i], value);
			i++;
		}
		return match != null;
	}

	@Override
	public V get(K key) {
		MapNode<K, V> match = matchingKeyNode(key);
		return match == null ? null : match.value;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public V put(K key, V value) {
		if (size >= table.length * LOAD_FACTOR) {
			resize();
		}
		MapNode<K, V> node = new MapNode<K, V>(key, value);
		store(node);
		size++;
		return node.value;
	}

	private void store(MapNode<K, V> node) {
		int index = slot(node.key);
		node.next = table[index];
		if (table[index] != null) {
			table[index].prev = node;
		}
		table[index] = node;
	}

	private void resize() {
		int newSize = 2 * table.length;
		MapNode<K, V>[] temp = table;
		table = new MapNode[newSize];
		for (MapNode<K, V> node : temp) {
			while (node != null) {
				store(new MapNode<K, V>(node.key, node.value));
				node = node.next;
			}
		}
	}

	@Override
	public V remove(K key) {
		MapNode<K, V> node = matchingKeyNode(key);
		if (node == null) {
			return null;
		}
		MapNode<K, V> prev = node.prev;
		if (prev == null) {
			int index = slot(key);
			table[index] = node.next;
		} else {
			prev.next = node.next;
		}
		node.prev = null;
		node.next = null;
		
		return node.value;
	}

	@Override
	public int size() {
		return size;
	}
}
