package com.mycollection.app;

public class ResizeableArrayImpl<T> implements ResizeableArray<T> {
	private static final int DEFAULT_SIZE = 16;
	private static final int RESIZE_FACTOR = 2;

	private T[] arr;
	private int index;

	ResizeableArrayImpl() {
		this(DEFAULT_SIZE);
	}

	ResizeableArrayImpl(int size) {
		arr = (T[]) new Object[size];
		index = -1;
	}

	private boolean isFull() {
		return arr.length == index + 1;
	}

	private void resizeAndCopy(int newSize) {
		T[] temp = (T[]) new Object[newSize];
		System.arraycopy(arr, 0, temp, 0, arr.length);
		arr = temp;
	}

	@Override
	public void add(T element) {
		if (isFull()) {
			int newSize = arr.length << 1;// Double the size
			resizeAndCopy(newSize);
		}
		index++;
		arr[index] = element;
	}

	@Override
	public boolean contains(T element) {
		for (T e : arr) {
			if (e == element || e.equals(element)) {
				return true;
			}
		}
		return false;
	}

	private boolean isArrayIndexOutOfBounds(int i) {
		return i < 0 || i > index;
	}

	@Override
	public T get(int i) {
		return isArrayIndexOutOfBounds(i) ? null : arr[i];
	}

	@Override
	public boolean isEmpty() {
		return index == -1;
	}

	/**
	 * Removes the element at the specified index. and copies the last element
	 * in to it and decrements the index Takes O(1)
	 */
	@Override
	public T remove(int i) {
		if (isArrayIndexOutOfBounds(i)) {
			return null;
		}
		T e = arr[i];
		arr[i] = arr[index];
		index--;
		if (isSizeCompressable()) {
			resizeAndCopy(arr.length >> 1); // Half the size
		}
		return e;
	}

	// check if array occupancy is less than or equal to quarter the size of array
	private boolean isSizeCompressable() {
		return arr.length > DEFAULT_SIZE
				&& index <= (arr.length >> RESIZE_FACTOR);
	}

	@Override
	public int size() {
		return index + 1;
	}

}
