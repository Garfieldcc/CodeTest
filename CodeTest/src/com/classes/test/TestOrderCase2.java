package com.classes.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.text.DecimalFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.classes.Order;
import com.classes.OrderItem;
import com.classes.Shop;
import com.main.Application;

class TestOrderCase2 {

	private Shop shop;
	private Order order;
	
	
	@BeforeEach
	void setUp() throws Exception {
		Application.InitApp();		
		shop = new Shop("NY");
		order = new Order(shop);
		order.addOrderItem(new OrderItem("book", 1, 17.99));
		order.addOrderItem(new OrderItem("pencil", 3, 2.99));
	}

	@Test
	public void testSubTotalCalculationCase2() {
		String testVal = (new DecimalFormat("0.00")).format(order.calculateSubTotal());
		String expected = (new DecimalFormat("0.00")).format(26.96);
		assertEquals(expected, testVal);
	}

	@Test
	public void testTaxCalculationCase2() {
		String testVal = (new DecimalFormat("0.00")).format(order.calculateTaxAmount());
		String expected = (new DecimalFormat("0.00")).format(2.40);
		assertEquals(expected, testVal);
	}

	@Test
	public void testTotalCase2() {
		String testVal = (new DecimalFormat("0.00")).format(order.calculateTotalAmount());
		String expected = (new DecimalFormat("0.00")).format(29.36);
		assertEquals(expected, testVal);
	}
}
