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

class TestOrderCase3 {

	private Shop shop;
	private Order order;
	
	
	@BeforeEach
	void setUp() throws Exception {
		Application.InitApp();		
		shop = new Shop("NY");
		order = new Order(shop);
		order.addOrderItem(new OrderItem("pencil", 2, 2.99));
		order.addOrderItem(new OrderItem("shirt", 1, 29.99));
	}

	@Test
	public void testSubTotalCalculationCase1() {
		String testVal = (new DecimalFormat("0.00")).format(order.calculateSubTotal());
		String expected = (new DecimalFormat("0.00")).format(35.97);
		assertEquals(expected, testVal);
	}

	@Test
	public void testTaxCalculationCase1() {
		String testVal = (new DecimalFormat("0.00")).format(order.calculateTaxAmount());
		String expected = (new DecimalFormat("0.00")).format(0.55);
		assertEquals(expected, testVal);
	}

	@Test
	public void testTotalCase1() {
		String testVal = (new DecimalFormat("0.00")).format(order.calculateTotalAmount());
		String expected = (new DecimalFormat("0.00")).format(36.52);
		assertEquals(expected, testVal);
	}
}
