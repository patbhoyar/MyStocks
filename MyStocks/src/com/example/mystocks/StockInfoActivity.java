package com.example.mystocks;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class StockInfoActivity extends Activity{

	TextView companyNameText;
	TextView yearLowText;
	TextView yearHighText;
	TextView daysLowText;
	TextView daysHighText;
	TextView lastPriceText;
	TextView changeText;
	TextView dailyPriceRangeText;
	
	static final String KEY_NAME = "Name";
	static final String KEY_YEAR_LOW = "YearLow";
	static final String KEY_YEAR_HIGH = "YearHigh";
	static final String KEY_DAYS_LOW = "DaysLow";
	static final String KEY_DAYS_HIGH = "DaysHigh";
	static final String KEY_LAST_PRICE = "LastTradePriceOnly";
	static final String KEY_CHANGE = "Change";
	static final String KEY_DAYS_RANGE = "DaysRange";
	
	private String name = "";
	private String yearLow = "";
	private String yearHigh = "";
	private String daysLow = "";
	private String daysHigh = "";
	private String lastPrice = "";
	private String change = "";
	private String daysRange = "";
	
	private String yahooURLfirst = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22";
	private String yahooURLSecond = "%22)&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stock_info);
		
		Intent intent = getIntent();
		String stockSymbol = intent.getStringExtra(KEY_NAME);
		
		companyNameText = (TextView) findViewById(R.id.companyNameText);
		yearLowText = (TextView) findViewById(R.id.yearLowText);
		yearHighText = (TextView) findViewById(R.id.yearHighText);
		daysLowText = (TextView) findViewById(R.id.daysLowText);
		daysHighText = (TextView) findViewById(R.id.daysHighText);
		lastPriceText = (TextView) findViewById(R.id.lastPriceText);
		changeText = (TextView) findViewById(R.id.changeText);
		dailyPriceRangeText = (TextView) findViewById(R.id.dailyPriceRangeText);
		
		final String yqlURL = yahooURLfirst + stockSymbol + yahooURLSecond;
		
		new MyAsyncTask().execute(yqlURL);
	}
	
	private class MyAsyncTask extends AsyncTask<String, String, String>{

		protected String doInBackground(String... args) {
			
			try {
				
				URL url = new URL(args[0]);
				
				URLConnection conn = url.openConnection();
				
				HttpURLConnection httpconn = (HttpURLConnection) conn;
				int responseCode = httpconn.getResponseCode();
				
				if (responseCode == HttpURLConnection.HTTP_OK) {
					 InputStream in = httpconn.getInputStream();
					 
					 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					 DocumentBuilder db = dbf.newDocumentBuilder();
					 Document dom = db.parse(in);
					 Element ele = dom.getDocumentElement();
					 NodeList nl = ele.getElementsByTagName("quote");
					 
					 if (nl != null && nl.getLength() > 0) {
						StockInfo theStock = getStockInfo(ele);
						
						name = theStock.getName();
						yearLow = theStock.getYearLow();
						yearHigh = theStock.getYearHigh();
						daysLow = theStock.getDaysLow();
						daysHigh = theStock.getDaysHigh();
						lastPrice = theStock.getLastPrice();
						change = theStock.getChange();
						daysRange = theStock.getDailyPriceRange();
					}
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}

			return null;
		}

		protected void onPostExecute(String result){
			companyNameText.setText(name);
			yearLowText.setText(yearLow);
			yearHighText.setText(yearHigh);
			daysLowText.setText(daysLow);
			daysHighText.setText(daysHigh);
			lastPriceText.setText(lastPrice);
			changeText.setText(change);
			dailyPriceRangeText.setText(daysRange);
		}
		
	}
	
	private StockInfo getStockInfo(Element entry){
		
		String stockName = getTextValue(entry, "Name");
		String stockYearLow = getTextValue(entry, "YearLow");
		String stockYearHigh = getTextValue(entry, "YearHigh");
		String stockDaysLow = getTextValue(entry, "DaysLow");
		String stockDaysHigh = getTextValue(entry, "DaysHigh");
		String stockLastPrice = getTextValue(entry, "LastTradePriceOnly");
		String stockChange = getTextValue(entry, "Change");
		String stockDaysRange = getTextValue(entry, "DaysRange");
		
		StockInfo stockData = new StockInfo(stockName, stockYearLow, stockYearHigh, stockDaysLow, stockDaysHigh, stockLastPrice, stockChange, stockDaysRange);
		return stockData;
	}
	
	private String getTextValue(Element entry, String TagName){
		 
		String TagValueToReturn = null;
		NodeList nl = entry.getElementsByTagName(TagName);
		
		if (nl != null && nl.getLength() > 0) {
			Element ele = (Element) nl.item(0);
			TagValueToReturn = ele.getFirstChild().getNodeValue();
		}
		return TagValueToReturn;
	}
}
