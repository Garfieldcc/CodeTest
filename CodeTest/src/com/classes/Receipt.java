package com.classes;

import java.text.DecimalFormat;
import java.util.List;

import com.common.Constants;

public abstract class Receipt {

	private Order order;
	
	public Receipt(Order order) {
		this.order = order;
	}
	
	public Order getOrder() { 
		return order;	
	}
	
	protected double roundUp(double inVal) {
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
	
	public String generateReceipt() {
		String s = String.format(Constants.RECEIPT_HEADER_FORMAT, "item", "price", "qty");
		s += "\n";
		s += "\n";
		s += generateOrderItems(order.getOrderItems(), 0);
		s += String.format(Constants.RECEIPT_FOOTER_FORMAT, "subtotal:", "$" + (new DecimalFormat("##0.00").format(order.calculateSubTotal()))) + "\n";
		s += String.format(Constants.RECEIPT_FOOTER_FORMAT, "tax:", "$" + (new DecimalFormat("##0.00").format(order.calculateTaxAmount()))) + "\n";
		s += String.format(Constants.RECEIPT_FOOTER_FORMAT, "total:", "$" + (new DecimalFormat("##0.00").format(order.calculateTotalAmount()))) + "\n";
		return s;
	}
	
	private String generateOrderItems(List<OrderItem> items, int index) {
		if (items.size() == index) 
			return ""; 
		else {
			OrderItem item = items.get(index);
			String str = String.format(Constants.RECEIPT_HEADER_FORMAT, item.getName(), "$" + item.getPrice(), "" + item.getQuantity()) + "\n";
			return str + generateOrderItems(items, ++index);
		}
	}
	
	public abstract void printReceipt();

}
