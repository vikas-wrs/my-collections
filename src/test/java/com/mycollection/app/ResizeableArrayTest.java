package com.mycollection.app;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ResizeableArrayTest {
	private static final String str = "test";

	ResizeableArrayImpl<String> ra;

	@BeforeMethod
	public void setup() {
		ra = new ResizeableArrayImpl<String>();
	}

	@Test
	public void testAdd() {
		Assert.assertTrue(ra.isEmpty());
		ra.add(str);
		Assert.assertFalse(ra.isEmpty());
		Assert.assertEquals(str, ra.get(0));
		Assert.assertTrue(ra.contains(str));
	}

	@Test
	public void testAddNull() {
		Assert.assertTrue(ra.isEmpty());
		ra.add(null);
		Assert.assertFalse(ra.isEmpty());
		Assert.assertNull(ra.get(0));
		Assert.assertTrue(ra.contains(null));
	}

	@Test
	public void testGet() {
		Assert.assertNull(ra.get(0));
		Assert.assertNull(ra.get(Integer.MAX_VALUE));
	}

	@Test
	public void testGetNegativeIndex() {
		Assert.assertNull(ra.get(-1));
	}

	@Test
	public void TestRemove() {
		ra.add(str);
		Assert.assertNull(ra.remove(-1));
		Assert.assertNull(ra.remove(Integer.MAX_VALUE));
		Assert.assertEquals(ra.remove(0), str);
	}
}
