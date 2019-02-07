package com.classes;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptTextFile extends Receipt {

	public ReceiptTextFile(Order order) {
		super(order);
	}

	@Override
	public void printReceipt() {
		PrintWriter writer;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
			Date date = new Date();
			writer = new PrintWriter("receipts/" + dateFormat.format(date) + ".txt", "UTF-8");
			writer.println(generateReceipt());
			writer.close();	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
