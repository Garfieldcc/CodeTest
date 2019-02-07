package com.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {
	private Shop shop;
	private List<OrderItem> orderItems;

	public Order() {}
	
	public Order(Shop shop) {
		this.shop = shop;
		this.orderItems = new ArrayList<OrderItem>();
	}
	
	public void addOrderItem(OrderItem item) {
		orderItems.add(item);
	}
	
	public List<OrderItem> getOrderItems() {
		return this.orderItems;
	}
	
	public Shop getShop() {
		return this.shop;
	}
	
	public double getRoundupVal(double inVal) {
		double num = inVal * 20;
		int decimal = (int)num; 
		double fractional  = num - decimal;
		
		double result;
		
		if (fractional > 0 && fractional < 0.5)
			result = (Math.round(num) + 1) / 20.0;
		else
			result = Math.round(num) / 20.0;
		
		return result;
	}
	
	private List<String> getExcludeList() {
		List<String> exclTax = new ArrayList<String>();
		List<String> listCatExcl = shop.getCategoryExcludeTax();
		HashMap<String, String> exclMap = (HashMap<String, String>) shop.getProductCategory();
		
		for (String key : exclMap.keySet()) {
			String val = exclMap.get(key);
			if (listCatExcl.stream().anyMatch(p -> p.equalsIgnoreCase(val)))
				exclTax.add(key);
		}
		return exclTax;
	}
	
	public double calculateSubTotal() {
		return orderItems.stream().mapToDouble(s -> s.getPrice() * s.getQuantity()).sum();
	}
	
	public double calculateTaxAmount() {
		double tax = 0.00;
		List<String> exclTax = getExcludeList();
		if (shop.getTaxRates() > 0) {
			for(OrderItem item : orderItems) {
				if (!exclTax.stream().anyMatch(p -> p.equalsIgnoreCase(item.getName()))) {
					tax += getRoundupVal(item.getPrice() * item.getQuantity() * (shop.getTaxRates()/100));
				}
			}	
		}
		return tax;
	}

	public double calculateTotalAmount() {
		return calculateSubTotal() + calculateTaxAmount();
	}
}
