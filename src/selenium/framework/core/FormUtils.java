package selenium.framework.core;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.framework.datasource.LocatorReader;
import selenium.framework.exception.LegacyElementNotfoundException;
import selenium.framework.reports.HTMLReport;
import selenium.framework.reports.Logger;

public class FormUtils {
	
	
	//common variables
	private static WebElement UMFrame;
	public static LocatorReader locatorReader;
	public static WebElement myDynamicElement,tempElement;
	public static HTMLReport report;
	public static WebDriver driver;
	public static WebDriverBackedSelenium  selenium;
	private static String tempxHolder;
	private static WebDriverWait wait; 
	private static String tempStr;
	
	
	
	
	
	//common methods
	public static void initUtilVariables(WebDriver Driver,WebDriverBackedSelenium Selenium,HTMLReport Report,LocatorReader Reader){
		driver=Driver;
		selenium=Selenium;
		report=Report;
		locatorReader=Reader;
		Logger.sharedString = "in initUtilVariables in FormUtils";
		wait=new WebDriverWait(driver,30);
	}
	
	public static void typeInTextBoxById(String locator,String id,String value) throws LegacyElementNotfoundException, Exception{
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
		Library.legacyType(driver, selenium, locatorReader.getLocator(locator).overrideValue(value));
	}
	
	public static void typeInTextBoxByXpath(String locator,String xpath,String value) throws LegacyElementNotfoundException, Exception{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		Library.legacyType(driver, selenium, locatorReader.getLocator(locator).overrideValue(value));
	}
	
	public static void typeInTextBoxByElement(WebElement element,String value) throws LegacyElementNotfoundException, Exception{
		element.sendKeys(Keys.chord(Keys.CONTROL,"a"),value);
		element.sendKeys(Keys.TAB);
		wait_for_ajax_loading();
	}
	
	public static void typeInTextBoxByIdWithWait(String id,String value) throws LegacyElementNotfoundException, Exception{
		myDynamicElement=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
		myDynamicElement.sendKeys(Keys.chord(Keys.CONTROL,"a"),value);
		myDynamicElement.sendKeys(Keys.TAB);
		wait_for_ajax_loading();
	}
	
	public static void typeInTextBoxByXpathWithWait(String xpath,String value) throws LegacyElementNotfoundException, Exception{
		myDynamicElement=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		myDynamicElement.sendKeys(Keys.chord(Keys.CONTROL,"a"),value);
		myDynamicElement.sendKeys(Keys.TAB);
		wait_for_ajax_loading();
	}
	
	
	
	public static void checkForErrorMessageOne() throws LegacyElementNotfoundException, Exception{
		Logger.print("in checkForErrorMessageOne: Begin");
		wait_for_ajax_loading();
		tempxHolder="//img[@id='MainContent_imgIcon'][contains(@src,'error')]";
		if(Library.legacyIsElementPresent(driver, selenium, locatorReader.createDummyLocator("xpath="+tempxHolder,"Error Image"))){
			if(selenium.isVisible("xpath="+tempxHolder)){
				tempStr=Library.legacyGetText(driver, selenium,locatorReader.getLocator("Label_Msg"));
				report.createComment("Error msg Encountered in Accolade -"+tempStr);
				throw new Exception("Error msg Encountered in Accolade -"+tempStr);
			}
		}
		Logger.print("in checkForErrorMessageOne : End");
	}
	
	public static void checkForInfoMessageOne() throws LegacyElementNotfoundException, Exception{
		Logger.print("in checkForErrorMessageOne: Begin");
		wait_for_ajax_loading();
		tempxHolder="//img[@id='MainContent_imgIcon'][contains(@src,'info')]";
		if(Library.legacyIsElementPresent(driver, selenium, locatorReader.createDummyLocator("xpath="+tempxHolder,"Info Image"))){
			if(selenium.isVisible("xpath="+tempxHolder)){
				tempStr=Library.legacyGetText(driver, selenium,locatorReader.getLocator("Label_Msg"));
				report.createComment("Expected Info Message Found : "+tempStr);
			} else{
				report.createComment("Expected info message not found");
				throw new Exception("Expected info message not found");
			}
		}else{
			report.createComment("Expected info message not found");
			throw new Exception("Expected info message not found");
		}
		Logger.print("in checkForErrorMessageOne : End");
	}
	
	
	public static boolean searchAndSelectOne(String xpath,String locator,String searchCode) throws LegacyElementNotfoundException, Exception{

		Logger.logPrint("in searchAndSelectOne Begin");
		boolean retVal=false;
		if(!searchCode.equals("")){

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));//"//span[@id='MainContent_spanvendor']/a/img" 
			Library.legacyClick(driver, selenium, locatorReader.getLocator(locator));//"Vendor_Code_Search_Btn"
			selenium.waitForPageToLoad("50000");
			wait_for_ajax_loading();
			UMFrame=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("TB_iframeContent"))); 
			driver.switchTo().frame(UMFrame);//switch driver to the newly created frame
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_txtSearch")));
			wait_for_ajax_loading();
			Library.legacyType(driver, selenium, locatorReader.getLocator("Frame_Search_Field").overrideValue(searchCode));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lnkGo")));
			Library.legacyClick(driver, selenium, locatorReader.getLocator("GO_Frame_Button"));
			wait_for_ajax_loading();
			if(selenium.isTextPresent("No records to display.")){
				report.createComment("Searched Code Not Found  :" +searchCode);
				throw new Exception("Searched Code Not Found  :" +searchCode);
			}
			tempxHolder="//tr/td[1][normalize-space(text())='"+searchCode+"']/..";
			if(Library.legacyIsElementPresent(driver, selenium, locatorReader.createDummyLocator("xpath="+tempxHolder,"Seached Code"))){
				if(selenium.isVisible("xpath="+tempxHolder)){
					Library.legacyClick(driver, selenium, locatorReader.createDummyLocator("xpath="+tempxHolder, "Seached Code"));
					wait_for_ajax_loading(); 
				}

				else{
					report.createComment("Searched Code Not Found  :" +searchCode);
					throw new Exception("Searched Code Not Found  :" +searchCode);
				}

			}
			else{
				report.createComment("Searched Code Not Found  :" +searchCode);
				throw new Exception("Searched Code Not Found  :" +searchCode);
			}
		}

		Logger.logPrint("in searchAndSelectOne End : Retval :"+retVal);
		return retVal;
	}
	
	public static void wait_for_ajax_loading() throws Exception
	{
		try{
			int i=0;
			Thread.sleep(2000);
			if(selenium.isElementPresent("id=loadingPanel"))
				while(selenium.isElementPresent("id=loadingPanel")&&selenium.isVisible("id=loadingPanel"))//wait till the loading screen disappears
		{
					i+=2;
					Thread.sleep(2000);
					System.out.println("Loading....");
	
		}
			if(i!=0){
				report.createComment("Ajax Loading Took :"+i +" Seconds");
			}	
		}
		
		
		catch(Exception e){
			Thread.sleep(2000);
			return;
		}
		
	}
	
	
	

}
