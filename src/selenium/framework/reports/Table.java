package selenium.framework.reports;

public class Table {
	
	String TableHeader;
	String TableFooter;
	String StepRows;
	String Table;
	
	public Table(){
		initializeTableHeader();
		initializeTableFooter();
		Table = "";
		StepRows = "";
	}
	public void initializeTableHeader(){
		TableHeader =   "<thead>\n"+
        			 	"<tr>\n"+
        			 	"<th scope=\"col\" abbr=\"Medium\">Manual Step</th>\n"+
        			 	"<th scope=\"col\" abbr=\"Starter\">Selenium Locator</th>\n"+
        			 	"<th scope=\"col\" abbr=\"Medium\">Value</th>\n"+
        			 	"<th scope=\"col\" abbr=\"Business\">Action</th>\n"+
        			 	"<th scope=\"col\" abbr=\"Deluxe\">Result</th>\n"+
        			 	//"<th scope=\"col\" abbr=\"Deluxe\">ScreenShot</th>\n"+
        			 	"</tr>\n"+
        			 	"</thead>\n";
	}
	public void initializeTableFooter(){
		TableFooter = "<tfoot>\n"+
        				"<tr>\n"+
        				"<td colspan=\"5\">This testcase is passed on execution</td>\n"+
        				"</tr>\n"+
        				"</tfoot>\n";
	}
	
	public void passedOnExecution(){
		TableFooter = "<tfoot>\n"+
		"<tr>\n"+
		"<td colspan=\"5\">This testcase is passed on execution</td>\n"+
		"</tr>\n"+
		"</tfoot>\n";
	}
	
	public void failedOnExecution(String Reason){
		TableFooter = "<tfoot>\n"+
		"<tr>\n"+
		"<td colspan=\"5\">This testcase <span class=\"highlighted\">Failed</span> on execution<br>Exception : "+Reason+"</td>\n"+
		"</tr>\n"+
		"</tfoot>\n";
	}
	public void createCommentRow(String Comment){
		StepRows = StepRows + new Row(Comment).generateCommentRow();
	}
	public void createRow(String ManualStep,String Locator,String Value,String Action){
		StepRows = StepRows + new Row(ManualStep,Locator,Value,Action).generateRow();
	}
	public void createFailedRow(String ManualStep,String Locator,String Value,String Action){
		StepRows = StepRows + new Row(ManualStep,Locator,Value,Action).generateFailedRow();
		failedOnExecution(Locator);
	}
	public String getStepsReport(){
		return "<tbody>\n"+StepRows+"</tbody>\n";
	}
	public String getTable(){
		Table = "<table class=\"table2\">\n"+TableHeader+TableFooter+getStepsReport()+"</table>\n";
		return Table;
	}
}
