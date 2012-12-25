package com.mycollection.app;

public interface ResizeableArray<T> {
	void add(T element);

	T get(int index);

	T remove(int index);

	boolean contains(T element);

	int size();

	boolean isEmpty();
}
