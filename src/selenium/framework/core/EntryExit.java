package selenium.framework.core;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import selenium.framework.datasource.LocatorReader;
import selenium.framework.enums.AppType;
import selenium.framework.exception.ByElementNotfoundException;
import selenium.framework.exception.ByElementTimeoutException;
import selenium.framework.exception.ElementNotfoundException;
import selenium.framework.exception.LegacyElementTimeoutException;
import selenium.framework.reports.HTMLReport;
import selenium.framework.reports.Logger;


public class EntryExit{
	WebDriver driver;
	WebDriverBackedSelenium selenium;
	String customerType;
	HTMLReport report;
	String locale;
	WebElement myElement;

	//If Locale is not mentioned in environment properties file
	public EntryExit(WebDriver driver,WebDriverBackedSelenium selenium,HTMLReport report,String locale){
		this.driver = driver;
		this.selenium = selenium;
		this.report = report;
		this.customerType = "General";
		report.setLocale(locale);
		this.locale = locale+"_";
	}	
	

	public LocatorReader goToApplication(AppType apptype) throws ElementNotfoundException, Exception{
		//if(appName.equalsIgnoreCase("transfers")){
		
		String Property = null;
		LocatorReader locatorReader = null; 
		switch(apptype){
			
		case Accolade:
			report.setAppType(apptype);
			Property = locale+"AccoladeProperties";
			Logger.logPrint("loading property "+Property);
			locatorReader = new LocatorReader(report).setProperty(Property,this.customerType);
			goToAccoladeApp(locatorReader);
			
			return locatorReader;
			
		default:
			throw new Exception("Proper application name is not given");
			
		}
	}
	private void goToAccoladeApp(LocatorReader locatorReader) throws ElementNotfoundException, ByElementNotfoundException, Exception {
		// TODO Auto-generated method stub
		Logger.logPrint("In goto Accoladeapp");
		String Property = locale+"EntryExitProperties";
		locatorReader = new LocatorReader(report).setProperty(Property,this.customerType);
		signOnAccolade(locatorReader);
	}


	private void signOnAccolade(LocatorReader locatorReader) throws Exception {
		// TODO Auto-generated method stub
		Logger.logPrint("in signon");
		String userIDElement;
		String userIDValue;
		By passwordElement;
		String passwordValue;
		
		
		 Logger.logPrint("Legacy locator:" + locatorReader.getLocator("Username_Input").getValue());
		 Library.legacyType(driver,selenium,locatorReader.getLocator("Username_Input"));
		//selenium.fireEvent(locatorReader.getLocator("Username_Input").getLegacyLocator(), "blur");
		 Library.legacyType(driver,selenium,locatorReader.getLocator("Password_Input"));
		//selenium.fireEvent(locatorReader.getLocator("Password_Input").getLegacyLocator(), "blur");
		//Library.safeSendKeys(driver,locatorReader.getLocator("Password_Input"));		
		 
		 
		 Library.legacyClick(driver,selenium, locatorReader.getLocator("Signon_Button"));		
	}


	
	public void setEntryCondition(boolean SignOn) throws ElementNotfoundException,ByElementNotfoundException, Exception{
			String Property = locale+"EntryExitProperties";
			Logger.logPrint(Property);
			LocatorReader locatorReader = new LocatorReader(report).setProperty(Property,this.customerType);
			
			if(SignOn){
				if(locale.contains("ES")){
					Library.safeClick(driver, locatorReader.getLocator("Hispanic_Link"));
					signOnAccolade(new LocatorReader(report).setProperty(Property,this.customerType));
				}
				else{
					signOnAccolade(locatorReader);
				}
			}
	}
	public void setExitCondition(boolean SignOff) throws ElementNotfoundException,ByElementNotfoundException, Exception{
		String Property = locale+"EntryExitProperties";
		Logger.logPrint(Property);
		LocatorReader locatorReader = new LocatorReader(report).setProperty(Property,this.customerType);
		if(SignOff){
			if(locale.contains("ES")){
				signOff(new LocatorReader(report).setProperty(Property,this.customerType));
			}
			else{
				signOff(locatorReader);
			}
		}
}
	
	
	public void setCustomerType(String customerType){
		this.customerType = customerType;
	}
	public void signOff(LocatorReader locatorReader)throws ElementNotfoundException,ByElementNotfoundException, Exception
	{
		Logger.logPrint("in signoff");
		Thread.sleep(1000);
		//Library.legacyClick(driver, selenium, locatorReader.getLocator("Signoff_Button"));
		Thread.sleep(1000);
	}
}
