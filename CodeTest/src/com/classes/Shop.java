package com.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.main.Application;

public class Shop {
	private String locationCode;
	private double taxRates;
	private List<String> categoryExcludeTax;
	private Map<String, String> productCategory;
	
	public Shop(String locationCode) {
		this.locationCode = locationCode;
		String s = Application.AppConfig.getProperty(locationCode + ".taxRate", "0");
		this.taxRates = Double.parseDouble(s);
		
		s = Application.AppConfig.getProperty(locationCode + ".taxExclCat", "");
		this.categoryExcludeTax = new ArrayList<String>(Arrays.asList(s.split(",")));

		try {
			productCategory = new HashMap<String, String>();
			s = Application.AppConfig.getProperty("ProductCategory");
			JSONObject json = new JSONObject(s.trim());
			Iterator<String> keys = json.keys();
			
			while(keys.hasNext()) {
			    String key = keys.next();
			    productCategory.put(key, json.getString(key));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Map<String, String> getProductCategory() {
		return productCategory;
	}
	
	public List<String> getCategoryExcludeTax() {
		return categoryExcludeTax;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public double getTaxRates() {
		return taxRates;
	}
}
