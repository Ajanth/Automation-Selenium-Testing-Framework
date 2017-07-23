package selenium.framework.core;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.internal.selenesedriver.IsElementEnabled;
import org.openqa.selenium.internal.seleniumemulation.GetCssCount;

import selenium.framework.datasource.ConfigReader;
import selenium.framework.datasource.InputSheetReader;
import selenium.framework.datasource.LocatorReader;
import selenium.framework.enums.CSQFieldType;
import selenium.framework.exception.AlertNotFoundException;
import selenium.framework.exception.ByElementNotfoundException;
import selenium.framework.exception.ByElementTimeoutException;
import selenium.framework.exception.LegacyElementNotfoundException;
import selenium.framework.exception.LegacyElementTimeoutException;
import selenium.framework.exception.WindowNotFoundException;
import selenium.framework.reports.Logger;


public class Library {
	
	public static int GlobalTimeout = 90;
	public static String urlprefix = ConfigReader.readConfig("AppName");
	public static String prodcreateurl=urlprefix + "/Masters/Purchase/ProductDetails.aspx?pageid=50&prevpageid=49";
	public static String prodlisturl=urlprefix + "/Masters/Purchase/ProductList.aspx?PageID=49";
	public static String orderentryurl=urlprefix + "/Transactions/Sales/OrderEntry.aspx?pageid=93";
	public static String adjusturl=urlprefix + "/Transactions/Purchase/ManualInventoryAdjustment.aspx?pageid=226";
	public static String shipconfirmurl=urlprefix + "/Transactions/Sales/ShipConfirmationList.aspx?pageid=164&Type=Shipping";
	public static String orderlisturl=urlprefix + "/Transactions/Sales/Orders.aspx?pageid=112";
	public static String createcustomerurl=urlprefix + "/masters/Sales/CustomerDetails.aspx?pageid=73";
	public static String customerlisturl=urlprefix + "/masters/Sales/CustomerList.aspx?pageid=87";
	public static String pocreateurl=urlprefix + "/Transactions/Purchase/PurchaseOrderEntry.aspx?pageid=119";
	public static String poreceiveurl=urlprefix + "/Transactions/Purchase/PurchaseOrderList.aspx?pageid=638&IsRecvPO=true";
	public static String proddelurl=urlprefix + "/Transactions/Purchase/ProductDelete.aspx?pageid=390";
	public static String vendcreateurl=urlprefix + "/masters/Purchase/VendorDetails.aspx?pageid=52";
	public static String vendlisturl=urlprefix + "/masters/Purchase/VendorList.aspx?pageid=51";
	public static String userlisturl=urlprefix + "/masters/dynamiclist.aspx?pageid=372";
	public static String currencylisturl=urlprefix + "/masters/dynamiclist.aspx?pageid=2052";
	public static String countrylisturl=urlprefix + "/masters/dynamiclist.aspx?pageid=2053";
	public static String statelisturl=urlprefix + "/masters/dynamiclist.aspx?pageid=54";
	public static String batchlisturl=urlprefix + "/Transactions/Finance/BatchList.aspx?pageid=100";
	public static String cashbatchlisturl=urlprefix + "/Transactions/Finance/CashReceiptsBatchList.aspx?pageid=136";
	public static String apbatchentryurl=urlprefix + "/Transactions/Finance/BatchList.aspx?pageid=100";
	public static String contactlisturl=urlprefix + "/masters/dynamiclist.aspx?pageid=560";
	public static String spersonlisturl=urlprefix +"/masters/Sales/SalesPersonList.aspx?pageid=236";
	public static String authordersurl=urlprefix+"/Transactions/Sales/Creditholdmenu.aspx?pageid=597";
	public static String actioncodeurl=urlprefix + "/masters/dynamiclist.aspx?pageid=43";
	public static String deletecodeurl=urlprefix + "/masters/dynamiclist.aspx?pageid=821";
	public static String shipviaurl=urlprefix + "/masters/Sales/ShipViaListing.aspx?pageid=84";
	public static String deltransactionurl=urlprefix+"/General/DeleteTransactions.aspx?pageid=10036";
	
	public static void safeGet(WebDriver driver,Locator locator)
	{
		String prefix = "Open";
		String Action = "Open";
		driver.get(locator.getLegacyLocator());
		Logger.print(prefix,Action,locator);
	}
	
	public static void legacyType(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator) throws LegacyElementNotfoundException{
		String prefix = "Type on";
		String Action = "Type";
		// equivalent to legacy type selenium.type()
		if(legacyIsElementPresentInternal(driver,selenium,locator)){
			Logger.print(prefix,Action,locator);
			selenium.type(locator.getLegacyLocator(), locator.getValue());
		}
		else{
			throw new LegacyElementNotfoundException(locator,prefix,Action);
		}	
	}
	
	public static String legacyGetText(WebDriver driver,WebDriverBackedSelenium selenium, Locator locator) throws LegacyElementNotfoundException{
		String prefix = "Getting Text of";
		String Action = "GetText";
		// equivalent to legacy type selenium.getText()
		if(legacyIsElementPresentInternal(driver,selenium,locator)){
			Logger.print(prefix,Action,locator);
			return selenium.getText(locator.getLegacyLocator());
		}
		else{
			
			throw new LegacyElementNotfoundException(locator,prefix,Action);
		}
		
	}
	
	public static String legacyGetValue(WebDriver driver,WebDriverBackedSelenium selenium, Locator locator) throws LegacyElementNotfoundException{
		String prefix = "Getting value of";
		String Action = "GetText";
		// equivalent to legacy type selenium.getText()
		if(legacyIsElementPresentInternal(driver,selenium,locator)){
			Logger.print(prefix,Action,locator);
			return selenium.getValue(locator.getLegacyLocator());
		}
		else{
			
			throw new LegacyElementNotfoundException(locator,prefix,Action);
		}
		
	}
	
	public static void waitForLegacyElementPresent(){
		
	}
	
	public static void legacyVerifyTextPresent(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator) throws LegacyElementNotfoundException{
		String prefix = "Verify Text";
		String Action = "Verify Text";
		// equivalent to legacy selenium.verifyTextPresent()
		Logger.logPrint("Verifying the text "+locator.getLegacyLocator());
		if(legacyIsTextPresentInternal(driver,selenium,locator)){
			Logger.logPrint("inside legacyVerifyElementPresent "+locator.getLegacyLocator());
			Logger.print(prefix,Action,locator);
		}
		else{
			throw new LegacyElementNotfoundException(locator,prefix,Action);
		}	
	}
	
	public static void legacyVerifyElementPresent(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator) throws LegacyElementNotfoundException{
		String prefix = "Verify Element";
		String Action = "Verify Element";
		// equivalent to legacy selenium.verifyElementPresent()
		if(legacyIsElementPresentInternal(driver,selenium,locator)){
			Logger.logPrint("inside legacyVerifyElementPresent");
			Logger.print(prefix,Action,locator);
		}
		else{
			throw new LegacyElementNotfoundException(locator,prefix,Action);
		}	
	}
	
	public static void legacyVerifyElementNotPresent(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator) throws LegacyElementNotfoundException{
		String prefix = "Verify Element not Present";
		String Action = "Verify Element nor Present";
		// equivalent to legacy selenium.verifyElementPresent()
		if(!legacyIsElementPresentInternal(driver,selenium,locator)){
			Logger.logPrint("inside legacyVerifyElementPresent");
			Logger.print(prefix,Action,locator);
		}
		else{
			throw new LegacyElementNotfoundException(locator,prefix,Action);
		}	
	}
	
	
	public static boolean legacyIsElementPresent(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator) {
		String prefix = "Is Element Present";
		String Action = "Is Element present";
		String LocatorString = locator.getValue();
		Logger.logPrint("Inside LegacyElementPresent");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Logger.logPrint("Changing timeout to 2 sec");
		// equivalent to legacy selenium.verifyElementPresent()
		if(legacyIsElementPresentInternal(driver,selenium,locator)){
			driver.manage().timeouts().implicitlyWait(GlobalTimeout, TimeUnit.SECONDS);
			Logger.logPrint("inside if(legacyIsElementPresentInternal=true) block");
			locator.setValue("Returned True");
			Logger.print(prefix,Action,locator);
			locator.setValue(LocatorString);
			return true;
		}
		else{
			driver.manage().timeouts().implicitlyWait(GlobalTimeout, TimeUnit.SECONDS);
			Logger.logPrint("inside legacyIsElementPresent false block");
			locator.setValue("Returned False");
			Logger.print(prefix,Action,locator);
			locator.setValue(LocatorString);
			return false;
		}	
	}	
	
	private static boolean legacyIsElementPresentInternal(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator) {
		Logger.logPrint("in internal "+locator.getLegacyLocator());
		//String LocatorString = locator.getValue();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		//ADS.setTimeout("2000");
		// equivalent to legacy selenium.verifyElementPresent()
		if(selenium.isElementPresent(locator.getLegacyLocator())){
			driver.manage().timeouts().implicitlyWait(GlobalTimeout, TimeUnit.SECONDS);
			//ADS.setTimeout(""+GlobalTimeout);
			Logger.logPrint("inside legacyIsElementPresentInternalfunction true block");
			//locator.setValue("Returned True");
			//Logger.print(prefix,Action,locator);
			//locator.setValue(LocatorString);
			return true;
		}
		else{
			driver.manage().timeouts().implicitlyWait(GlobalTimeout, TimeUnit.SECONDS);
			//ADS.setTimeout(""+GlobalTimeout);
			Logger.logPrint("inside legacyIsElementPresent false block");
			//locator.setValue("Returned False");
			//Logger.print(prefix,Action,locator);
			//locator.setValue(LocatorString);
			return false;
		}	
	}	
	
	public static boolean legacyIsTextPresent(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator) {
		String prefix = "Is Text Present";
		String Action = "Is Text present";
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		// equivalent to legacy selenium.verifyTextPresent()
		if(selenium.isTextPresent(locator.getLegacyLocator())){
			driver.manage().timeouts().implicitlyWait(GlobalTimeout, TimeUnit.SECONDS);
			Logger.logPrint("inside legacyIsTextPresent true block");
			locator.setValue("Returned True");
			Logger.print(prefix,Action,locator);
			return true;
		}
		else{
			driver.manage().timeouts().implicitlyWait(GlobalTimeout, TimeUnit.SECONDS);
			Logger.logPrint("inside legacyIsTextPresent false block");
			locator.setValue("Returned False");
			Logger.print(prefix,Action,locator);
			return false;
		}	
	}
	
	public static boolean legacyIsTextPresentInternal(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator) {
		//String prefix = "Is Text Present";
		//String Action = "Is Text present";
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		// equivalent to legacy selenium.verifyTextPresent()
		if(selenium.isTextPresent(locator.getLegacyLocator())){
			driver.manage().timeouts().implicitlyWait(GlobalTimeout, TimeUnit.SECONDS);
			//Logger.logPrint("inside legacyIsTextPresent true block");
			//locator.setValue("Returned True");
			//Logger.print(prefix,Action,locator);
			return true;
		}
		else{
			driver.manage().timeouts().implicitlyWait(GlobalTimeout, TimeUnit.SECONDS);
			//Logger.logPrint("inside legacyIsTextPresent false block");
			//locator.setValue("Returned False");
			//Logger.print(prefix,Action,locator);
			return false;
		}	
	}
	
	
	public static void legacySelect(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator) throws LegacyElementNotfoundException{
		String prefix = "Select";
		String Action = "Select";
		// equivalent to legacy type selenium.type()
		if(legacyIsElementPresentInternal(driver,selenium,locator)){
			Logger.print(prefix,Action,locator);
			selenium.select(locator.getLegacyLocator(), locator.getValue());
		}
		else{
			throw new LegacyElementNotfoundException(locator,prefix,Action);
		}	
	}
	
	public static void legacyClick(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator) throws LegacyElementNotfoundException{
		String prefix = "Click On";
		String Action = "Click";
		// equivalent to legacy type selenium.type()
		if(legacyIsElementPresentInternal(driver,selenium,locator)){
			Logger.print(prefix,Action,locator);
			selenium.click(locator.getLegacyLocator());
		}
		else{
			throw new LegacyElementNotfoundException(locator,prefix,Action);
		}	
	}
	
		
	public static void legacyWaitForElementPresent(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator) throws LegacyElementTimeoutException,InterruptedException{
		String prefix = "Wait for ";
		String Action = "Wait for Element";
		// equivalent to legacy selenium.verifyElementPresent()
		for (int second = 0;; second++) {
			if (second >= GlobalTimeout){
				throw new LegacyElementTimeoutException(locator, prefix, Action);
			}else{
				Logger.logPrint("Waiting for legacy element : " + locator.getLegacyLocator() + "Seconds = "+second);
			}
				if (legacyIsElementPresentInternal(driver,selenium,locator)) {
					Logger.print(prefix,Action,locator);
					break;
				}
			Thread.sleep(1000);
		}	
	}
	
	public static boolean legacyWaitForConditionalElementPresent(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator,int customTimeout) throws LegacyElementTimeoutException,InterruptedException{
		String prefix = "Wait for ";
		String Action = "Wait for Element";
		// equivalent to legacy selenium.verifyElementPresent()
		for (int second = 0;; second++) {
			if (second >= customTimeout){
				return false;
			}else{
				Logger.logPrint("Waiting for legacy element : " + locator.getLegacyLocator() + "Seconds = "+second);
			}
				if (legacyIsElementPresentInternal(driver,selenium,locator)) {
					Logger.print(prefix,Action,locator);
					return true;
				}
			Thread.sleep(1000);
		}	
	}
	
	public static void legacyWaitForTextPresent(WebDriver driver,WebDriverBackedSelenium selenium,Locator locator) throws LegacyElementTimeoutException,InterruptedException{
		String prefix = "Wait for ";
		String Action = "Wait for Text";
		// equivalent to legacy selenium.verifyElementPresent()
		for (int second = 0;; second++) {
			if (second >= GlobalTimeout){
				throw new LegacyElementTimeoutException(locator, prefix, Action);
			}else{
				Logger.logPrint("Waiting for legacy element : " + locator.getLegacyLocator() + "Seconds = "+second);
			}
				if (legacyIsTextPresentInternal(driver,selenium,locator)) {
					Logger.print(prefix,Action,locator);
					break;
				}
			Thread.sleep(1000);
		}	
	}
	
	public static void safeVerifyElementPresent(WebDriver driver,Locator locator) throws ByElementNotfoundException{
		String prefix = "Verify Element";
		String Action = "Verify Element";
		if(isByElementPresent(driver, locator.getByLocator())){
			Logger.print(driver,prefix,Action,locator);
		}
		else{
			throw new ByElementNotfoundException(locator, prefix, Action,driver);
		}
	}
	
	public static void safeWaitForElementPresent(WebDriver driver,Locator locator) throws ByElementTimeoutException,InterruptedException{
		String prefix = "Wait for ";
		String Action = "Wait for Element";
		
		for (int second = 0;; second++) {
			if (second >= GlobalTimeout){
				throw new ByElementTimeoutException(locator, prefix, Action,driver);
			}else{
				Logger.logPrint("Waiting for driver element : " + locator.getByLocator() + "Seconds = "+second);
			}
				if (isByElementPresent(driver,locator.getByLocator())) {
					Logger.print(driver,prefix,Action,locator);
					break;
				}
			Thread.sleep(1000);
		}
	}
	
	public static void safeSendKeys(WebDriver driver,Locator locator) throws ByElementNotfoundException{
		String prefix = "Type on";
		String Action = "Type";
		if(isByElementPresent(driver, locator.getByLocator())){
			WebElement element = driver.findElement(locator.getByLocator());
			Logger.print(driver,prefix,Action,locator);
			//Logger.print("Typing on the element - "+getElementName(element) + "*");
			
			element.sendKeys(locator.getValue());
			element.sendKeys(Keys.valueOf("TAB"));
		}
		else{
			Logger.logPrint("in sendkeys exception block");
			throw new ByElementNotfoundException(locator, prefix, Action,driver);
		}
	}
	public static void safeClick(WebDriver driver,Locator locator) throws ByElementNotfoundException{
		String prefix = "Click on";
		String Action = "Click";
		// click() method with reporting.
		if(isByElementPresent(driver, locator.getByLocator())){
		WebElement element = driver.findElement(locator.getByLocator());
		Logger.print(driver,prefix,Action,locator);
		element.click();
		}
		else{
			throw new ByElementNotfoundException(locator, prefix, Action,driver);
		}
	}
	


	public static void acceptAlert(WebDriver driver,LocatorReader locatorReader)throws AlertNotFoundException{
		String prefix = "Verify Alert Present";
		String Action = "Verify Alert Present";
		Locator locator = locatorReader.createDummyLocator("Expected Alert box Not available", "");
		if(isAlertPresent(driver)){
			Alert alert = driver.switchTo().alert();
			alert.accept();
			Logger.print(prefix,Action,locatorReader.createDummyLocator(alert.getText(), ""));
		}
		else{
			throw new AlertNotFoundException(locator, prefix, Action);
		}
	}
	
	public static void switchWindows(WebDriver driver,LocatorReader locatorReader,String URLstring) throws InterruptedException,WindowNotFoundException {
		String prefix = "Switch to window";
		String Action = "Switch to window";
		Locator locator = locatorReader.createDummyLocator("Expected window Not available", "");
        Set<String> windows = driver.getWindowHandles();

        for (String window : windows) {
        	//Logger.logPrint("IN for loop");
            driver.switchTo().window(window);
            Thread.sleep(2000);
            //Logger.logPrint(driver.getTitle());
            if (driver.getCurrentUrl().contains(URLstring)) {
            	Logger.logPrint("IN If condition");
            	Logger.logPrint(driver.getTitle() + "   "+driver.getCurrentUrl());
            	Logger.print(prefix,Action,locatorReader.createDummyLocator(driver.getCurrentUrl(), ""));
                return;
            }
        }
        throw new WindowNotFoundException(locator, prefix, Action);
    }
	
	public static boolean isAlertPresent(WebDriver driver){
		try
        {
            driver.switchTo().alert();
            return true;
        }   // try
        catch (NoAlertPresentException Ex)
        {
            return false;
        } 
	}

	
	private static boolean isByElementPresent(WebDriver driver,By by) {
		// private method to verifyElementPresent, webdriver way
	    try {
	        driver.findElement(by);
	        return true;
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	}

	

}
