package com.example.mystocks;

import java.util.Arrays;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText; 
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	SharedPreferences stockSymbolsEntered;
	EditText enterSymbolData;
	TableLayout stocksScrollView;
	Button enterSymbolButton;
	Button clearDataButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		stockSymbolsEntered = getSharedPreferences("stocks", MODE_PRIVATE);
		
		enterSymbolData = (EditText) findViewById(R.id.enterSymbolData);
		
		stocksScrollView = (TableLayout) findViewById(R.id.stocksScrollView);
		
		enterSymbolButton = (Button) findViewById(R.id.enterSymbolButton);
		clearDataButton = (Button) findViewById(R.id.clearDataButton);
		 
		enterSymbolButton.setOnClickListener(enterSymbolButtonListener);
		clearDataButton.setOnClickListener(clearDataButtonListener);
		
		updateSavedStockList(null);
	}
	
	public OnClickListener clearDataButtonListener = new OnClickListener() {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			
			SharedPreferences.Editor prefEditor = stockSymbolsEntered.edit();
			prefEditor.clear();
			prefEditor.apply();
			
			stocksScrollView.removeAllViews();
		}
	};
	
	public OnClickListener enterSymbolButtonListener = new OnClickListener() {
		
		public void onClick(View arg0) {
			
			if (enterSymbolData.getText().length() > 0) {
				
				updateSavedStockList(enterSymbolData.getText().toString());
				//enterSymbolData.setText("");
				
			}else {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle(R.string.something_wrong);
				builder.setPositiveButton(R.string.ok, null);
				builder.setMessage(R.string.no_stock_entered);
				
				AlertDialog dia = builder.create();
				dia.show();
			}
		}
	};

	private void updateSavedStockList(String newStock) {
		
		String[] stocks = stockSymbolsEntered.getAll().keySet().toArray(new String[0]); 
		
		Arrays.sort(stocks, String.CASE_INSENSITIVE_ORDER);
		
		if (newStock != null) {
			
			insertStockInScrollView(newStock, Arrays.binarySearch(stocks, newStock));
			
		}else {
			
		}
		
	}
	
	private void insertStockInScrollView(String newStock, int arrayIndex) {
		
		LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View newStockRow = inflator.inflate(R.layout.stock_quote_row, null);
		
		TextView stockTextView = (TextView) newStockRow.findViewById(R.id.retrievedStockTextView);
		
		stockTextView.setText(newStock);
		
		Button getQuoteButton = (Button) newStockRow.findViewById(R.id.getQuoteButton);
		Button viewWebButton = (Button) newStockRow.findViewById(R.id.viewWebButton);
		
		stocksScrollView.addView(newStockRow, arrayIndex);
	}

	@SuppressLint("NewApi")
	private void saveStockSymbol(String newStock){
		
		String isTheStockNew = stockSymbolsEntered.getString(newStock, null);
		
		if (isTheStockNew == null) {
			updateSavedStockList(newStock);
		}else{
			
			SharedPreferences.Editor prefEditor = stockSymbolsEntered.edit();
			prefEditor.putString(newStock, newStock);
			prefEditor.apply();
			stockSymbolsEntered.toString();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
