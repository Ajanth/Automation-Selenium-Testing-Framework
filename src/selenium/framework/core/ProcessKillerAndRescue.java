package selenium.framework.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import selenium.framework.exception.SystemTimeOutException;
import selenium.framework.reports.HTMLReport;
import selenium.framework.reports.Logger;


public class ProcessKillerAndRescue implements Runnable {
	
	/*
	 * This class runs parallel with CBOL Selenium Framework code
	 * Objective:
	 * 		Waits till SystemTimeOut seconds and quits the driver object, thus progressing with next
	 * testcase execution. If a testcase gets stuck, this class takes care of quitting the testcase and proceed
	 * with next.
	 * 
	 * Note: the default SystemtimeOut is 420 seconds (7 - minutes) and can be confugured through CBOLSelenium.config file.
	 * Default system timeout can be changed from TestParallel class file (DefaultSystemTimeOut)
	 */
	
	
	WebDriverBackedSelenium selenium;
	WebDriver driver;
	HTMLReport report;
	int SystemTimeOut;
	public ProcessKillerAndRescue(WebDriverBackedSelenium selenium, WebDriver driver,HTMLReport report,int SystemTimeOut) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		this.selenium = selenium;
		this.report = report;
		this.SystemTimeOut = SystemTimeOut;
		Logger.logPrint("Setting system timeout as "+SystemTimeOut+" Seconds");
	}
	public void run(){
		try{
			Thread.sleep(5000);
			for(int i=0;i<SystemTimeOut;i++){
				Logger.logPrint("Process killer daemon timeout : "+i);
				Thread.sleep(1000);
			}
			driver.quit();
			selenium.close();
			throw new SystemTimeOutException(report);
			}catch(Exception e){
				Logger.logPrint(" "+e);
			}
	}
}
