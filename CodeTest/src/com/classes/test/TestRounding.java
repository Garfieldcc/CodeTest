package com.classes.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.classes.Order;
import com.classes.OrderItem;

public class TestRounding {

	private Order order;
	
	@Before
	public void setUp() throws Exception {
		order = new Order();
	}

	@Test
	public void testRoundingCase1() {
		double testVal = order.getRoundupVal(1.13);
		double expected = 1.15;
		assertEquals(expected, testVal, 0.0);
	}

	@Test
	public void testRoundingCase2() {
		double testVal = order.getRoundupVal(1.16);
		double expected = 1.20;
		assertEquals(expected, testVal, 0.0);
	}
	
	@Test
	public void testRoundingCase3() {
		double testVal = order.getRoundupVal(1.151);
		double expected = 1.20;
		assertEquals(expected, testVal, 0.0);
	}

}
