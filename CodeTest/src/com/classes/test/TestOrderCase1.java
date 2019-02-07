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

class TestOrderCase1 {

	private Shop shop;
	private Order order;
	
	
	@BeforeEach
	void setUp() throws Exception {
		Application.InitApp();		
		shop = new Shop("CA");
		order = new Order(shop);
		order.addOrderItem(new OrderItem("book", 1, 17.99));
		order.addOrderItem(new OrderItem("potato chips", 1, 3.99));
	}

	@Test
	public void testSubTotalCalculationCase1() {
		String testVal = (new DecimalFormat("0.00")).format(order.calculateSubTotal());
		String expected = (new DecimalFormat("0.00")).format(21.98);
		assertEquals(expected, testVal);
	}

	@Test
	public void testTaxCalculationCase1() {
		String testVal = (new DecimalFormat("0.00")).format(order.calculateTaxAmount());
		String expected = (new DecimalFormat("0.00")).format(1.80);
		assertEquals(expected, testVal);
	}

	@Test
	public void testTotalCase1() {
		String testVal = (new DecimalFormat("0.00")).format(order.calculateTotalAmount());
		String expected = (new DecimalFormat("0.00")).format(23.78);
		assertEquals(expected, testVal);
	}
}
