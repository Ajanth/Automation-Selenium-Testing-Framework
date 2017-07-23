package selenium.framework.reports;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import selenium.framework.core.TestParallel;
import selenium.framework.enums.AppType;


public class HTMLReport {
	String BrowserVersion;
	String FileLocation;
	String Row;
	String Header;
	String BodyHeader;
	Table table;
	String HTMLReport;
	String Body;
	String TestcaseName;
	boolean reportPassed;
	String ReportBasePath;
	String RelatedCssFolderName = "../../../../css_files/";
	String locale;
	AppType apptype;
	String BrowserName;
	public HTMLReport(){
		this.Row = "";
	}
	public HTMLReport(String BrowserVersion,String BrowserName){
		this.ReportBasePath = TestParallel.ReportPath;
		this.BrowserVersion = BrowserVersion;
		this.BrowserName = BrowserName;
		this.Row = "";
		this.TestcaseName = "&lt;Please set testcase name in testcase java file&gt;";
		table = new Table();
	}
	public void setAppType(AppType apptype){
		this.apptype = apptype;
	}
	public void setLocale(String locale){
		this.locale = locale;
	}
	
	public void setTestcaseName(String TestcaseName){
		this.TestcaseName = TestcaseName;
	}
	
	public void initializeHeader(){
		Header = String.format("<head>\n"+
	    		 "<title>Accolade Selenium Test Results</title>\n"+
	    		 "<link rel=\"stylesheet\" href=\"%s\" type=\"text/css\" media=\"screen\">\n"+
	    		 "<link rel=\"stylesheet\" href=\"%s\" type=\"text/css\" media=\"screen\">\n"+
	    		 "</head>\n",RelatedCssFolderName+"style.css",RelatedCssFolderName+"header.css");
	}
	
	public void initializeBodyHeader(){
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date(); 
		BodyHeader = String.format("<h1 class=\"test-results-header\">ACCOLADE Selenium Test Results (%s)</h1>\n"+
							"<h2>%s</h2>\n"+
							"<h2><div class=\"test-cast-status-box-ok\">&nbsp;</div> Test Case: %s</h2>\n",df.format(date),BrowserVersion,this.TestcaseName);		
	}
	
	public void initializeFailedBodyHeader(){
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date(); 
		BodyHeader = String.format("<h1 class=\"test-results-header\">ACCOLADE Selenium Test Results (%s)</h1>\n"+
							"<h2>%s</h2>\n"+
							"<h2><div class=\"test-cast-status-box-fail\">&nbsp;</div> Test Case: %s</h2>\n",df.format(date),BrowserVersion,this.TestcaseName);		
	}
	
	public void createComment(String Comment){
		table.createCommentRow(Comment);
	}
	
	public void createRow(String ManualStep,String Locator,String Value,String Action){
		this.reportPassed = true;
		table.createRow(ManualStep, Locator, Value, Action);
	}
	
	public void createFailedRow(String ManualStep,String Locator,String Value,String Action){
		this.reportPassed = false;
		table.createFailedRow(ManualStep, Locator, Value, Action);
	}
	
	public String getBody(){
		Body = "<body>"+BodyHeader+table.getTable()+"</body>\n";
		return Body;
	}
	public String getHTMLReport() throws IOException{
		initializeHeader();
		if(reportPassed){
		initializeBodyHeader();
		HTMLReport = "<html>\n"+Header+getBody()+"</html>";
		FileWrite writer = new FileWrite(ReportBasePath,TestcaseName,locale,apptype,BrowserName);
		writer.write(HTMLReport,true);
		return HTMLReport;}
		else{
			initializeFailedBodyHeader();
			HTMLReport = "<html>\n"+Header+getBody()+"</html>";
			FileWrite writer = new FileWrite(ReportBasePath,TestcaseName,locale,apptype,BrowserName);
			writer.write(HTMLReport,false);
			return HTMLReport;}
		}
	}

