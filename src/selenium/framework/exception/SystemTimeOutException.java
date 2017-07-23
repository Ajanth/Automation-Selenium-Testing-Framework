package selenium.framework.exception;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import selenium.framework.core.Locator;
import selenium.framework.reports.HTMLReport;


public class SystemTimeOutException extends Exception {
	HTMLReport report;
	public SystemTimeOutException(HTMLReport report) throws IOException{
		this.report = report;	
		GenerateHTMLReport();
    }
	public void GenerateHTMLReport() throws IOException{
		report.getHTMLReport();
	}
}
