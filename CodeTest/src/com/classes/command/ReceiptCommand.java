package com.classes.command;

import org.eclipse.swt.SWT;

import com.classes.Order;
import com.classes.Receipt;
import com.classes.ReceiptConsole;
import com.classes.ReceiptTextFile;
import com.classes.ReceiptUIDialog;
import com.main.Application;
import com.main.OrderReceiptDialog;

public class ReceiptCommand implements Command {
	
	@Override
	public void execute() {
		Receipt receipt;
		Order order = Application.prepareOrder();
		
		char cmd = Application.AppConfig.getProperty("Receipt").charAt(0);
		switch (cmd) {
			case 'C':
				receipt = new ReceiptConsole(order);
				receipt.printReceipt();
				break;
			case 'F':
				receipt = new ReceiptTextFile(order);
				receipt.printReceipt();
				break;
			case 'U':
				receipt = new ReceiptUIDialog(order);
				Application.receiptDialog = new OrderReceiptDialog(Application.shlShoppingReceipt, SWT.DIALOG_TRIM, receipt);
				Application.isDialogOpen = true;
				Application.receiptDialog.open();
				break;
		}
	}

}
