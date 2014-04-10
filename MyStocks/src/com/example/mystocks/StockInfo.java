package com.example.mystocks;

public class StockInfo {

	private String name  = "";
	private String yearLow = "";
	private String yearHigh = "";
	private String daysLow = "";
	private String daysHigh = "";
	private String lastPrice = "";
	private String change = "";
	private String dailyPriceRange = "";

	public StockInfo(String name, String yearLow, String yearHigh,
			String daysLow, String daysHigh, String lastPrice, String change,
			String dailyPriceRange) {
		super();
		this.name = name;
		this.yearLow = yearLow;
		this.yearHigh = yearHigh;
		this.daysLow = daysLow;
		this.daysHigh = daysHigh;
		this.lastPrice = lastPrice;
		this.change = change;
		this.dailyPriceRange = dailyPriceRange;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getYearLow() {
		return yearLow;
	}
	public void setYearLow(String yearLow) {
		this.yearLow = yearLow;
	}
	public String getYearHigh() {
		return yearHigh;
	}
	public void setYearHigh(String yearHigh) {
		this.yearHigh = yearHigh;
	}
	public String getDaysLow() {
		return daysLow;
	}
	public void setDaysLow(String daysLow) {
		this.daysLow = daysLow;
	}
	public String getDaysHigh() {
		return daysHigh;
	}
	public void setDaysHigh(String daysHigh) {
		this.daysHigh = daysHigh;
	}
	public String getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	public String getDailyPriceRange() {
		return dailyPriceRange;
	}
	public void setDailyPriceRange(String dailyPriceRange) {
		this.dailyPriceRange = dailyPriceRange;
	}
	
}
