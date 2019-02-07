package com.main;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.classes.OrderItem;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

public class OrderItemDialog extends Dialog {

	protected Object result;
	protected Shell shlOrderItemDialog;
	
	private Text txtItemName;
	private Text txtQty;
	private Text txtPrice;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public OrderItemDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlOrderItemDialog.open();
		shlOrderItemDialog.layout();
		Display display = getParent().getDisplay();
		while (!shlOrderItemDialog.isDisposed()) {
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
		shlOrderItemDialog = new Shell(getParent(), getStyle());
		shlOrderItemDialog.setSize(235, 285);
		shlOrderItemDialog.setText("Order Item Dialog");
		shlOrderItemDialog.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				// TODO Auto-generated method stub
				Application.isDialogOpen = false;
			}
		});

		Composite composite = new Composite(shlOrderItemDialog, SWT.NONE);
		composite.setBounds(10, 10, 210, 232);
		
		Label lblItemName = new Label(composite, SWT.NONE);
		lblItemName.setBounds(10, 10, 100, 15);
		lblItemName.setText("Item Name");
		
		txtItemName = new Text(composite, SWT.BORDER);
		txtItemName.setTextLimit(20);
		txtItemName.setBounds(10, 31, 190, 21);
		
		Label lblQuantity = new Label(composite, SWT.NONE);
		lblQuantity.setBounds(10, 58, 55, 15);
		lblQuantity.setText("Quantity");
		
		txtQty = new Text(composite, SWT.BORDER);
		txtQty.setBounds(10, 79, 190, 21);
		txtQty.setTextLimit(10);
		txtQty.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent event) {
			    // Assume we don't allow it
				event.doit = false;
			 
			    // Get the character typed
			    char myChar = event.character;
			    String str = ((Text) event.widget).getText();
			 
			    // Allow 0-9
			    if (Character.isDigit(myChar)) 
			    	event.doit = true;
			 
			    // Allow backspace
			    if (myChar == '\b') 
			    	event.doit = true;
			}
		});
		
		Label lblPrice = new Label(composite, SWT.NONE);
		lblPrice.setBounds(10, 106, 55, 15);
		lblPrice.setText("Unit Price");
		
		txtPrice = new Text(composite, SWT.BORDER);
		txtPrice.setBounds(10, 127, 190, 21);
		txtPrice.setTextLimit(15);
		txtPrice.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent arg) {
				// TODO Auto-generated method stub
				Text text = (Text) arg.getSource();

	            // get old text and create new text by using the VerifyEvent.text
	            final String oldS = text.getText();
	            String newS = oldS.substring(0, arg.start) + arg.text + oldS.substring(arg.end);

	            boolean isFloat = true;
	            try
	            {
	            	if (!newS.equalsIgnoreCase("")) {
	            		if (newS.contains("d")) throw new NumberFormatException();
		            	if (newS.contains("f")) throw new NumberFormatException();
		                Double.parseDouble(newS);
		                
		                if (newS.contains(".")) {
		                	int dot = newS.indexOf(".") + 1;
		                	if (newS.substring(dot).length() > 2)
		                		isFloat = false;
		                }	
	            	}
	            }
	            catch(NumberFormatException ex)
	            {
	                isFloat = false;
	            }

	            if(!isFloat)
	            	arg.doit = false;
			}
		});
		
		Button btnAddButton = new Button(composite, SWT.NONE);
		btnAddButton.setBounds(10, 163, 190, 25);
		btnAddButton.setText("Add");
		btnAddButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				OrderItem item = new OrderItem(txtItemName.getText(), Integer.parseInt(txtQty.getText()), Double.parseDouble(txtPrice.getText()));
				Application.AddOrderItem(item);
				Application.isDialogOpen = false;
				shlOrderItemDialog.close();
				
			}
		});
		
		Button btnCancelButton = new Button(composite, SWT.NONE);
		btnCancelButton.setBounds(10, 194, 190, 25);
		btnCancelButton.setText("Cancel");
		btnCancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Application.isDialogOpen = false;
				shlOrderItemDialog.close();
			}
		});

	}
}
