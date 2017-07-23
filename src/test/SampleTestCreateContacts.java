/*--------------CREATE CONTACTS---------------

Test case description goes here.

EXCEL FILE(s) USED - MasterFile.xls //provide excel file name used as input for this test case

 */



package test;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.framework.core.DataReader;
import selenium.framework.core.EntryExit;
import selenium.framework.core.Library;
import selenium.framework.core.Locator;
import selenium.framework.core.TestParallel;
import selenium.framework.datasource.LocatorReader;
import selenium.framework.enums.AppType;
import selenium.framework.exception.AlertNotFoundException;
import selenium.framework.exception.ByElementNotfoundException;
import selenium.framework.exception.LegacyElementNotfoundException;
import selenium.framework.exception.LegacyElementTimeoutException;
import selenium.framework.exception.WindowNotFoundException;
import selenium.framework.reports.Logger;

public class SampleTestCreateContacts  extends TestParallel{
	private String baseUrl;
	private String TestcaseName;
	private StringBuffer verificationErrors = new StringBuffer();
	private LocatorReader locatorReader;
	private EntryExit entry;
	public WebElement myDynamicElement,tempElement;
	private String add_product_path="C:\\webdriverprop\\MasterFile.xls";//path for the input excel file
	private String url=Library.contactlisturl;
	int c;
	
	
	public SampleTestCreateContacts(String browserString)
	{
		super(browserString);
		browser = browserString;
	}
	
	@Before
	public void setUp() throws Exception {
		try{
			TestcaseName = "YOURTESTCASENAME"; // to be changed according to each test case
			Logger.sharedString = "in setup";
			createBrowserDriver();
			driver.manage().window().maximize();
			System.out.println(this.browser + " Launching...");
			baseUrl = browserURL;
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			selenium.setTimeout("50000");
			report.setTestcaseName(TestcaseName);
			Library.safeGet(driver, new Locator(baseUrl,"Accolade URL",report));
			entry = new EntryExit(driver,selenium,report,locale);
			//entry.setEntryCondition(true);
			//logging into the website
			locatorReader = entry.goToApplication(AppType.Accolade); // to be changed according to each testcase
			}
			catch(LegacyElementNotfoundException legacyelement){
				System.out.println("expected behavioour");
				Logger.failedReport(report, legacyelement);
				driver.quit();
				System.exit(0);
			}catch(ByElementNotfoundException byelement){
				System.out.println("expected behavioour in byelement");
				Logger.failedReport(report, byelement);
				driver.quit();
				System.exit(0);
			}
			catch(Exception e){
				Logger.logPrint("General Exception in setup: "+e);
				Logger.failedReport(report, e);
				driver.quit();
				System.exit(0);
				}
		}

	@Test
	public void testAERPCreateContacts() throws Exception {
		try{
		selenium.waitForPageToLoad("30000");
		selenium.open(url);
		selenium.waitForPageToLoad("30000");
		
		WebDriverWait wait=new WebDriverWait(driver,30); 
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblPageTitle")));
		
		if(!selenium.isElementPresent("id=MainContent_lblPageTitle"))//checking if page title is present
		{
			report.createComment("Page Title not found ");
   		 	throw new Exception("Page Title not found ");
			
		}
		
		if(selenium.getText("id=MainContent_lblPageTitle").equalsIgnoreCase("Contacts"))
		{
			System.out.print("\n Contacts Title Present ");
			
		}
		else
		{
			report.createComment("Contacts Page Title not found ");
   		 	throw new Exception("Contacts Page Title not found ");
		}		
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lnkNew")));
	 	
		//click on New button
		Library.legacyClick(driver, selenium, locatorReader.getLocator("New_User_Button"));
		 
		selenium.waitForPageToLoad("30000");
		
		//block for excel sheet processing
		
		
		int rowcount;
		rowcount=DataReader.GetRowColumnCount(add_product_path,"Contacts",true);//for gettin rowcount pass true as 2nd argmnt
		String contactCode="",Title="",firstName="",middleName="",Type="",compName="",dispName1="",email1="",CRM1="",busEmail1="";
		String tempXpath="";
		String tempStr="";
		for(int i=1;i<rowcount-2;i++)//loop through each row in the excel sheet
		{
			
			     //fetching Contact details from excel sheet for each row
				 Title=DataReader.GetData(add_product_path,"TITLE", "P"+i,"Contacts");
				 firstName=DataReader.GetData(add_product_path,"FIRSTNAME", "P"+i,"Contacts");	
				 middleName=DataReader.GetData(add_product_path,"MIDDLENAME", "P"+i,"Contacts");
				 Type=DataReader.GetData(add_product_path,"TYPE", "P"+i,"Contacts");
				 compName=DataReader.GetData(add_product_path,"COMPANYNAME", "P"+i,"Contacts");
				 busEmail1=DataReader.GetData(add_product_path,"BUSINESSEMAIL1", "P"+i,"Contacts");
			     //end of fetching from xl sheet
				 
				 
				 //verify all the data now
				 
				 if(firstName.equals("")){
					 report.createComment("Mandatory Fields cannot be Empty!! Please Fix the Excel file");
					 throw new Exception("Mandatory Fields cannot be Empty!! Please Fix the Excel file");
				 }
				 				 
				 //end of field verifications
				 
				 
				 //now fill the fetched details in the form
				 
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_ddlTitle"))); 
				 
				 tempElement=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_txtContactCode")));
	    	     contactCode=tempElement.getAttribute("value");
				 
				 tempXpath="//select[@id='MainContent_ddlTitle']/option[contains(text(),'"+Title+"')]";
				 Library.legacyClick(driver, selenium, locatorReader.createDummyLocator("xpath="+tempXpath, "Select Title For Contact"));
				 
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_txtFirst")));
				 
				 Library.legacyType(driver, selenium, locatorReader.getLocator("FirstName_Input").overrideValue(firstName));
				 
				 if(!middleName.equals("")){
					 
					 Library.legacyType(driver, selenium, locatorReader.getLocator("MiddleName_Input").overrideValue(middleName));
					 
				 }
				 
				 if(!Type.equals("")){
					 tempXpath="//select[@id='MainContent_ddlType']/option[contains(text(),'"+Type+"')]";
					 Library.legacyClick(driver, selenium, locatorReader.createDummyLocator("xpath="+tempXpath, "Select Type"));
					 wait_for_ajax_loading();
				
				 }
				 
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_txtCompanyName")));
				 
				 if(!compName.equals("")){
					 
					 Library.legacyType(driver, selenium, locatorReader.getLocator("CompanyName_Input").overrideValue(compName));
					 
				 }
				 
				 
				 if(!busEmail1.equals("")){
					 tempXpath="//select[@id='ctl00_MainContent_gridEmail_ctl00_ctl04_ddlEmailType']/option[contains(text(),'Business')]";
					 Library.legacyClick(driver, selenium, locatorReader.createDummyLocator("xpath="+tempXpath,"Select Business DDL"));
					 if(busEmail1.contains("|")){
						 int temp=1;
						 for(String str : busEmail1.split("\\|")){
							 
							 switch(temp){
							 	case 1:{
							 		Library.legacyType(driver, selenium, locatorReader.getLocator("DispName_Input").overrideValue(str));
							 		break;
							 	}
							 	case 2:{
							 		Library.legacyType(driver, selenium, locatorReader.getLocator("EmailID_Input").overrideValue(str));
							 		wait_for_ajax_loading();
							 	}
							 	case 3:{
							 		if(str.equalsIgnoreCase("Y"))
							 			Library.legacyClick(driver, selenium, locatorReader.getLocator("CRM_ChkBox"));
							 	}
							 	
							 }
							 temp++;
						 }
					 }
				 }

				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lnkSave")));
				 Library.legacyClick(driver, selenium, locatorReader.getLocator("Save_Button"));
				 wait_for_ajax_loading();
				 
				 if(Library.legacyIsElementPresent(driver, selenium, locatorReader.getLocator("Label_Msg"))){
					 
					 if(!selenium.isTextPresent("Record saved successfully.")){
						 if(selenium.isVisible("id=MainContent_lblMessge"))
						  {
							 tempStr=Library.legacyGetText(driver, selenium,locatorReader.getLocator("Label_Msg"));
							 report.createComment("Error msg Encountered in Accolade -"+tempStr);
							 throw new Exception("Error msg Encountered in Accolade -"+tempStr);
						  }
					 		}
					 	}
				 
				 
				 
				 selenium.waitForPageToLoad("30000");
				 selenium.open(url);
				 selenium.waitForPageToLoad("30000");
				 
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lblPageTitle"))); 
				 
				 if(!selenium.isElementPresent("id=MainContent_lblPageTitle"))//checking if page title is present
					{
					 report.createComment("Page Title not found ");
    	    		 throw new Exception("Page Title not found ");
						
					}
					
				 if(selenium.getText("id=MainContent_lblPageTitle").equalsIgnoreCase("Contacts"))
					{
						verify_Contact_creation(contactCode);
					}
				 
				 else
					{
						 report.createComment("Page Title not found ");
	    	    		 throw new Exception("Page Title not found ");
					}		
					
					
				 wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lnkNew")));
				 	
				 Library.legacyClick(driver, selenium, locatorReader.getLocator("New_User_Button"));
				 
				 selenium.waitForPageToLoad("30000");
			//looping till every product in the excel sheet is added 
			}
		}//end of try
			    
		catch(LegacyElementTimeoutException timeout){
			Logger.logPrint("in legacy timeout exception catch block");
			Logger.failedReport(report, timeout);
			driver.quit();
			System.exit(0);
		}catch(NullPointerException nullptr){
			Logger.logPrint("nullptr  exception catch block");
			Logger.failedReport(report, nullptr);
			driver.quit();
			System.exit(0);
		}catch(AlertNotFoundException AlertNotFount){
			Logger.logPrint("in legacy alert not found exception catch block");
			Logger.failedReport(report, AlertNotFount);
			driver.quit();
			System.exit(0);
		}catch(WindowNotFoundException WindowNotFount){
			Logger.logPrint("in window not found exception catch block");
			Logger.failedReport(report, WindowNotFount);
			driver.quit();
			System.exit(0);
		}
		catch(LegacyElementNotfoundException legacyelement){
			System.out.println("expected behavioour");
			Logger.failedReport(report, legacyelement);
			driver.quit();
			System.exit(0);
		}catch(ByElementNotfoundException byelement){
			System.out.println("expected behavioour in byelement");
			Logger.failedReport(report, byelement);
			driver.quit();
			System.exit(0);
		}
		catch(Exception e){
			Logger.logPrint("Exception in setup "+e);
			Logger.failedReport(report, e);
			driver.quit();
			System.exit(0);
		}
	}

	public void verify_Contact_creation(String contactCode) throws Exception
	{
		try{
			
			String tempXpath="//a[text()='"+contactCode+"']";
			WebDriverWait innerwait=new WebDriverWait(driver,30); 
			
			innerwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_txtSearch")));
			
			Library.legacyType(driver, selenium, locatorReader.getLocator("Frame_Search_Field").overrideValue(contactCode));
   	        innerwait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MainContent_lnkGo")));
   	        Library.legacyClick(driver, selenium, locatorReader.getLocator("GO_Frame_Button"));
   	        wait_for_ajax_loading();
			
   	        if(selenium.isTextPresent("No records to display.")){
				report.createComment("Created Contact not displayed in list!!");
				throw new Exception("Created Contact not displayed in list");
			}
   	        
			if(Library.legacyIsElementPresent(driver, selenium, locatorReader.createDummyLocator("xpath="+tempXpath, "Contact Code List"))){
				report.createComment("Contact Code verified in List");
				return;
			}
			
			report.createComment("Created Contact not displayed in list!!");
			throw new Exception("Created Contact not displayed in list!");
			
		}
		
		catch(Exception e){
			Logger.logPrint("Exception in verify_Contact_creation "+e);
			Logger.failedReport(report, e);
			driver.quit();
			System.exit(0);
		}
		
		
	}
	
	
	public void wait_for_ajax_loading() throws Exception
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
	//performing cleanup from here
		@After
		public void tearDown() throws Exception {
			try{
			entry.setExitCondition(true);
			report.getHTMLReport();
			driver.quit();
			String verificationErrorString = verificationErrors.toString();
			if (!"".equals(verificationErrorString)) {
				fail(verificationErrorString);
			}
			System.out.println("Completed Setup : " + Logger.sharedString);
		}catch(LegacyElementNotfoundException legacyelement){
			System.out.println("expected behavioour");
			Logger.failedReport(report, legacyelement);
			driver.quit();
			System.exit(0);
		}catch(ByElementNotfoundException byelement){
			System.out.println("expected behavioour in byelement");
			Logger.failedReport(report, byelement);
			driver.quit();
			System.exit(0);
		}
		catch(Exception e){
			Logger.logPrint("Exception in tearDown "+e);
			Logger.failedReport(report, e);
			driver.quit();
		}
		}
	}