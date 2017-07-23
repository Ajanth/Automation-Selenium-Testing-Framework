package selenium.framework.core;

import org.openqa.selenium.By;

import selenium.framework.reports.HTMLReport;


public class Locator {
	String LegacyLocator;
	By ByLocator;
	String ManualStep;
	HTMLReport report;
	String Value;
	String CustomerType;
	
	public Locator(){
		
	}
	public Locator(String LegacyLocator,String ManualStep,HTMLReport report,String Value){
		this.LegacyLocator = LegacyLocator;
		this.ManualStep = ManualStep;
		this.report = report;
		this.Value = Value;
	}
	public Locator(String LegacyLocator,String ManualStep,HTMLReport report){
		this.LegacyLocator = LegacyLocator;
		this.ManualStep = ManualStep;
		this.report = report;
		this.Value = "N/A";
	}
	public Locator(By ByLocator,String ManualStep,HTMLReport report,String Value){
		this.ByLocator = ByLocator;
		this.ManualStep = ManualStep;
		this.report = report;
		this.Value = Value;
	}
	public Locator(By ByLocator,String ManualStep,HTMLReport report){
		this.ByLocator = ByLocator;
		this.ManualStep = ManualStep;
		this.report = report;
		this.Value = "N/A";
	}
	public String getValue(){
		return Value;
	}
	public By getByLocator(){
		return this.ByLocator;
	}
	public String getLegacyLocator(){
		return this.LegacyLocator;
	}
	public String getManualStep(){
		return this.ManualStep;
	}
	public HTMLReport getHTMLReport(){
		return report;
	}
	
	public void setValue(String Value){
		this.Value = Value;
	}
	
	public Locator overrideValue(String Value){
		this.Value = Value;
		return this;
	}
	public void setCustomerType(String CustomerType){
		this.CustomerType = CustomerType;
	}
	public String getCustomerType(){
		return this.CustomerType;
	}
}
