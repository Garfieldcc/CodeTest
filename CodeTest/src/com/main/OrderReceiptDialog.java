package com.main;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

import com.classes.Order;
import com.classes.Receipt;
import com.classes.ReceiptUIDialog;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class OrderReceiptDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	protected Label lblReceiptContent;
	private Receipt receipt;
	private Text text;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public OrderReceiptDialog(Shell parent, int style, Receipt receipt) {
		super(parent, style);
		this.receipt = receipt;
		setText("Receipt");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(450, 300);
		shell.setText(getText());
		shell.setLayout(new FillLayout());
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				// TODO Auto-generated method stub
				Application.isDialogOpen = false;
			}
		});
		
		text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.WRAP);
		text.setFont(SWTResourceManager.getFont("Courier", 12, SWT.NORMAL));
		text.setEditable(false);
		text.setBounds(10, 10, 424, 251);
		text.setText(receipt.generateReceipt());
	}
	
	public Label getLblReceiptContent() {
		return lblReceiptContent;
	}

}
