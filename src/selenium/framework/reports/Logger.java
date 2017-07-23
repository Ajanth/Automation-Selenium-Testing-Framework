package selenium.framework.reports;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import selenium.framework.core.Locator;
import selenium.framework.exception.AlertNotFoundException;
import selenium.framework.exception.ByElementNotfoundException;
import selenium.framework.exception.ByElementTimeoutException;
import selenium.framework.exception.LegacyElementNotfoundException;
import selenium.framework.exception.LegacyElementTimeoutException;
import selenium.framework.exception.WindowNotFoundException;


public class Logger {
	public static String sharedString;
	public static boolean logFirstTime = true;
	public static java.util.logging.Logger logger;
	public static FileHandler handler;
	public static void print(WebDriver driver,String prefix,String Action,Locator locator){
		/*Logger.logPrint("Manual Step : "+prefix+" "+locator.getManualStep());
		Logger.logPrint("Locator : "+getElementName(driver.findElement(locator.getByLocator())));
		Logger.logPrint("Action : "+Action);
		Logger.logPrint("Value : "+locator.getValue());*/
		String LocatorString = getElementName(driver.findElement(locator.getByLocator()));
		locator.getHTMLReport().createRow(prefix+" "+locator.getManualStep(),LocatorString , locator.getValue(), Action);
	}
	public static void print(String prefix,String Action,Locator locator){
		/*Logger.logPrint("Manual Step : "+prefix+" "+locator.getManualStep());
		Logger.logPrint("Locator : "+locator.getLegacyLocator());
		Logger.logPrint("Action : "+Action);
		Logger.logPrint("Value : "+locator.getValue());*/
		locator.getHTMLReport().createRow(prefix+" "+locator.getManualStep(), locator.getLegacyLocator(), locator.getValue(), Action);
	}
	public static void failedReport(HTMLReport report,Exception generalException) throws IOException{
		report.createFailedRow("Exception Occured", generalException.getMessage(), "", "");
		Logger.logPrint(report.getHTMLReport());
}
	public static void failedReport(HTMLReport report,LegacyElementNotfoundException legacyexception) throws IOException{
			Locator locator = legacyexception.getLocator();
			String prefix = legacyexception.getPrefix();
			String Action = legacyexception.getAction();
			locator.getHTMLReport().createFailedRow(prefix+" "+locator.getManualStep(), locator.getLegacyLocator(), locator.getValue(), Action);
			Logger.logPrint(locator.getHTMLReport().getHTMLReport());
	}
	
	public static void failedReport(HTMLReport report,AlertNotFoundException AlertNotFound) throws IOException{
		Locator locator = AlertNotFound.getLocator();
		String prefix = AlertNotFound.getPrefix();
		String Action = AlertNotFound.getAction();
		locator.getHTMLReport().createFailedRow(prefix+" "+locator.getManualStep(), locator.getLegacyLocator(), locator.getValue(), Action);
		Logger.logPrint(locator.getHTMLReport().getHTMLReport());
}
	public static void failedReport(HTMLReport report,WindowNotFoundException WindowNotFound) throws IOException{
		Locator locator = WindowNotFound.getLocator();
		String prefix = WindowNotFound.getPrefix();
		String Action = WindowNotFound.getAction();
		locator.getHTMLReport().createFailedRow(prefix+" "+locator.getManualStep(), locator.getLegacyLocator(), locator.getValue(), Action);
		Logger.logPrint(locator.getHTMLReport().getHTMLReport());
}
	public static void failedReport(HTMLReport report,ByElementTimeoutException byexception) throws IOException{
		Locator locator = byexception.getLocator();
		String prefix = byexception.getPrefix();
		String Action = byexception.getAction();
		locator.getHTMLReport().createFailedRow(prefix+" "+locator.getManualStep(), locator.getLegacyLocator(), locator.getValue(), Action);
		Logger.logPrint(locator.getHTMLReport().getHTMLReport());
}
	
	public static void failedReport(HTMLReport report,LegacyElementTimeoutException timeoutexception) throws IOException{
		Locator locator = timeoutexception.getLocator();
		String prefix = timeoutexception.getPrefix();
		String Action = timeoutexception.getAction();
		locator.getHTMLReport().createFailedRow(prefix+" "+locator.getManualStep(), locator.getLegacyLocator(), locator.getValue(), Action);
		Logger.logPrint(locator.getHTMLReport().getHTMLReport());
}
	public static void failedReport(HTMLReport report,ByElementNotfoundException byexception) throws IOException{
		Logger.logPrint("in failed report");
		Locator locator = byexception.getLocator();
		String prefix = byexception.getPrefix();
		String Action = byexception.getAction();
		Logger.logPrint("before element name");
		String LocatorString = locator.getByLocator().toString();
		Logger.logPrint("after element name");
		locator.getHTMLReport().createFailedRow(prefix+" "+locator.getManualStep(), LocatorString, locator.getValue(), Action);
		Logger.logPrint(locator.getHTMLReport().getHTMLReport());
}
	public static void print(String ExceptionMessage){
		Logger.logPrint("!!!! -- Error, execution stopped due to missing object \""+ExceptionMessage+"\" --- !!!");
	}
	public static void logPrint(String ExceptionMessage){
		System.out.println("Log print : "+ExceptionMessage+"\" --- !!!");
		if(logFirstTime){
		logger = java.util.logging.Logger.getLogger("CBOL_Selenium_Logfile");
		try{
		handler = new FileHandler("./CBOL_Selenium_Log.log",true);
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
        logger.setUseParentHandlers(false);
		}catch(IOException ioe){
			System.out.println("Problem with writing log file");
		}
		logFirstTime = false;
		}
		logger.info(ExceptionMessage);
	}
	private static String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }

	    return stringBuilder.toString();
	}
	private static String getElementName(WebElement element){
		return element.toString().substring(element.toString().indexOf('>')+2,element.toString().lastIndexOf(']'));
	}
	
	
}
