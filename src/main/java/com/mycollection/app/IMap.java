package com.mycollection.app;

 interface IMap<K, V> {
	 boolean containsKey(K key);

	 boolean containsValue(V value);

	 V get(K key);

	 boolean isEmpty();

	//  Set<K> keySet();

	 V put(K key, V value);

	 V remove(K key);

	 int size();
}
