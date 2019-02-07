package com.main;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.classes.Order;
import com.classes.OrderItem;
import com.classes.Shop;
import com.classes.command.Command;
import com.classes.command.ReceiptCommand;
import com.common.Constants;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;

public class Application {
	
	public static Properties AppConfig;

	public static Display display;
	public static Shell shlShoppingReceipt;
	public static Composite composite;
	public static Table table;
	public static Combo combo;
	public static OrderItemDialog itemDialog;
	public static OrderReceiptDialog receiptDialog;
	public static boolean isDialogOpen = false;
	
	public static Map<String, String> taxExceptCategories;
	
	private static Command cmd;
	private static Label lblShopLocation;
	private static Label lblOrderItems;
	private static Button btnAdd;
	private static Button btnGenerate;
	private static Button btnCancel;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		// Initial Application
		InitApp();
		// Initial Windows
		InitWindows();
		// Initial Captions
		InitCaptions();
		// Initial Tables
		InitTbls();
		// Initial Windows Controls
		InitControls();
		// Open Windows
		shlShoppingReceipt.open();
		shlShoppingReceipt.layout();
		while (!shlShoppingReceipt.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public static void InitApp() {
		readConfig();
	}
	
	private static void InitWindows() {
		display = Display.getDefault();
		shlShoppingReceipt = new Shell();
		shlShoppingReceipt.setSize(450, 300);
		shlShoppingReceipt.setText("Shopping Receipt");
		
		composite = new Composite(shlShoppingReceipt, SWT.NONE);
		composite.setBounds(10, 10, 414, 241);
	}
	
	private static void InitCaptions() {
		lblShopLocation = new Label(composite, SWT.NONE);
		lblShopLocation.setBounds(10, 10, 76, 15);
		lblShopLocation.setText("Shop Location");
		
		lblOrderItems = new Label(composite, SWT.NONE);
		lblOrderItems.setBounds(10, 41, 76, 15);
		lblOrderItems.setText("Order Items");
	}
	
	private static void InitTbls() {
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 71, 394, 160);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnItemName = new TableColumn(table, SWT.NONE);
		tblclmnItemName.setWidth(190);
		tblclmnItemName.setText("Item Name");
		
		TableColumn tblclmnQuantity = new TableColumn(table, SWT.NONE);
		tblclmnQuantity.setWidth(100);
		tblclmnQuantity.setText("Quantity");
		
		TableColumn tblclmnPrice = new TableColumn(table, SWT.NONE);
		tblclmnPrice.setWidth(100);
		tblclmnPrice.setText("Price");
	}
	
	private static void InitControls() {
		combo = new Combo(composite, SWT.NONE);
		combo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!combo.getText().equalsIgnoreCase("")) {
					btnAdd.setEnabled(true);
					btnGenerate.setEnabled(true);
				} else {
					btnAdd.setEnabled(false);
					btnGenerate.setEnabled(false);
				}
			}
		});
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!combo.getText().equalsIgnoreCase("")) {
					btnAdd.setEnabled(true);
					btnGenerate.setEnabled(true);
				}
			}
		});
		combo.setBounds(92, 7, 76, 23);
		for(String shopLoc : AppConfig.getProperty("Shop").split(","))
			combo.add(shopLoc);

		btnAdd = new Button(composite, SWT.NONE);
		btnAdd.setEnabled(false);
		btnAdd.setBounds(92, 36, 75, 25);
		btnAdd.setText("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (!isDialogOpen) {
					isDialogOpen = true;
					itemDialog = new OrderItemDialog(shlShoppingReceipt, SWT.DIALOG_TRIM);
					itemDialog.open();	
				}
			}
		});
		
		btnGenerate = new Button(composite, SWT.NONE);
		btnGenerate.setEnabled(false);
		btnGenerate.setBounds(329, 5, 75, 25);
		btnGenerate.setText("Generate");
		
		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				isDialogOpen = false;
				combo.setText("");
				table.clearAll();
				btnAdd.setEnabled(false);
				btnGenerate.setEnabled(false);
			}
		});
		btnCancel.setBounds(329, 36, 75, 25);
		btnCancel.setText("Cancel");
		btnGenerate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				cmd = new ReceiptCommand();
				cmd.execute();
			}
		});
	}
	
	private static void readConfig() {
		final String propFileName = Constants.RESOURCES_FOLDER + Constants.CONFIGURATION_FILE;
		
		Properties config = new Properties();

		try {
			InputStream input = new FileInputStream(propFileName);
			config.load(input);		
			
			
		}
		catch (IOException ex) {
			ex.printStackTrace();
		} 
		finally {
			AppConfig = config;	
		}
	}
	
	public static Order prepareOrder() {
		Order order = new Order(new Shop(combo.getText()));
		for(TableItem item : table.getItems()) {
			String itemName = item.getText(Constants.COLITMNAM);
			int quantity = Integer.parseInt(item.getText(Constants.COLQTY));
			double price = Double.parseDouble(item.getText(Constants.COLPRC));
			order.addOrderItem(new OrderItem(itemName, quantity, price));
		}
		return order;
	}
	
	public static void AddOrderItem (OrderItem item) {
		TableItem row = new TableItem(table, SWT.None);
		row.setText(Constants.COLITMNAM, item.getName());
		row.setText(Constants.COLQTY, "" + item.getQuantity());
		row.setText(Constants.COLPRC, "" + item.getPrice());
	}
}
