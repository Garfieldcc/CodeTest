package com.classes;

public class ReceiptConsole extends Receipt {

	public ReceiptConsole(Order order) {
		// TODO Auto-generated constructor stub
		super(order);
	}

	@Override
	public void printReceipt() {
		System.out.println(generateReceipt());
	}

}
