package selenium.framework.datasource;

import org.openqa.selenium.By;

import selenium.framework.core.Locator;
import selenium.framework.reports.HTMLReport;
import selenium.framework.reports.Logger;


public class LocatorReader {
	String LocatorID;
	String PropertySheet;
	PropertyReader PReader;
	HTMLReport report;
	String CustomerType;
	InputSheetReader Ireader;
	
	String LegacyLocator;
	String ManualStep;
	String Value;
	By ByLocator;
	
	
	public LocatorReader(){
		
	}
	public LocatorReader(HTMLReport report){
		this.report = report;
		Ireader = new InputSheetReader();
	}
	public LocatorReader getLocatorReader(){
		return this;
	}
	public Locator createDummyLocator(String LegacyLocator, String ManualStep){
		return new Locator(LegacyLocator, ManualStep, this.report);
	}
	public LocatorReader setProperty(String PropertySheet){
		this.PropertySheet = PropertySheet;
		PReader = new PropertyReader(PropertySheet);
		return this;
	}
	public LocatorReader setProperty(String PropertySheet,String CustomerType){
		this.PropertySheet = PropertySheet;
		this.CustomerType = CustomerType;
		Logger.logPrint("In locator reade "+PropertySheet);
		PReader = new PropertyReader(PropertySheet);
		return this;
	}
	public String getManualStep() throws Exception{
		return PReader.GetData("Manual Step", LocatorID); 
	}
	public String getByType() throws Exception{
		return PReader.GetData("By Type", LocatorID);
	}
	public String getLocatorText() throws Exception{
		return PReader.GetData("Locator Text", LocatorID);
	}
	public String getValue() throws Exception{
		String Value = "N/A";
		try{
			Value = PReader.GetData("Value", LocatorID);
			if(Value.equalsIgnoreCase(""))
				Value = "N/A";
		}catch(NullPointerException e){
		
		}
		return Value;
	}
	public void setLocatorID(String LocatorID){
		this.LocatorID = LocatorID;
	}
	
	public Locator createLegacyLocator(String Locator,String ManualStep,String Value){
		Logger.logPrint("Generated Locator : "+Locator);
		return new Locator(Locator,ManualStep,this.report,Value);
	}
	
	public String getCustomerType(){
		return this.CustomerType;
	}
	
	public Locator getLocator(String LocatorID) throws Exception{
		/* initialize */
		this.LocatorID="";
		String PropertySheet;
		PropertyReader PReader;
		HTMLReport report;
		InputSheetReader Ireader;
		
		this.LegacyLocator = "";
		this.ManualStep = "";
		this.Value= "" ;
		this.ByLocator = null;
		
		
		this.LocatorID = LocatorID;
		try{
		String ByType = getByType();
		Locator locator = getConditionalLocator(ByType);
		locator.setCustomerType(this.CustomerType);
		return locator;
		}catch(Exception e){
			Logger.logPrint(e.toString());
			return new Locator();
		}
		
		
	}
	private Locator getConditionalLocator(String ByType) throws Exception{
		Locator locator = null;
		if(ByType.equalsIgnoreCase("Legacy")){
			Logger.logPrint("in legacy");
			locator = getLegacyLocator();
		}
		else{
			locator = getByLocator(ByType);
		}
		return locator;
	}
	
	private Locator getByLocator(String ByType) throws Exception{
		Locator locator = null;
		if(ByType.equalsIgnoreCase("ID")){
			locator = getByIDLocator();
		}else if(ByType.equalsIgnoreCase("LinkText")){
			locator = getByLinkTextLocator();
		}else if(ByType.equalsIgnoreCase("CssSelector")){
			locator = getByCssSelectorLocator();
		}else if(ByType.equalsIgnoreCase("ClassName")){
			locator = getByClassNameLocator();
		}else if(ByType.equalsIgnoreCase("Name")){
			locator = getByNameLocator();
		}else if(ByType.equalsIgnoreCase("PartialLinkText")){
			locator = getByPartialLinkTextLocator();
		}else if(ByType.equalsIgnoreCase("TagName")){
			locator = getByTagNameLocator();
		}else if(ByType.equalsIgnoreCase("XPath")){
			locator = getByXPathLocator();
		}
		return locator;
	}
	
	private Locator getByXPathLocator() throws Exception{
		this.ManualStep = getManualStep();
		this.Value = getValue();
		this.ByLocator = By.xpath(getLocatorText());
		if(Value.equalsIgnoreCase("%inputsheet%"))
			this.Value = Ireader.GetData(LocatorID, CustomerType);
		return new Locator(this.ByLocator,this.ManualStep,this.report,this.Value);
	}
	
	private Locator getByTagNameLocator() throws Exception{
		this.ManualStep = getManualStep();
		this.Value = getValue();
		this.ByLocator = By.tagName(getLocatorText());
		if(Value.equalsIgnoreCase("%inputsheet%"))
			this.Value = Ireader.GetData(LocatorID, CustomerType);
		return new Locator(this.ByLocator,this.ManualStep,this.report,this.Value);
	}
	
	private Locator getByPartialLinkTextLocator() throws Exception{
		this.ManualStep = getManualStep();
		this.Value = getValue();
		this.ByLocator = By.partialLinkText(getLocatorText());
		if(Value.equalsIgnoreCase("%inputsheet%"))
			this.Value = Ireader.GetData(LocatorID, CustomerType);
		return new Locator(this.ByLocator,this.ManualStep,this.report,this.Value);
	}
	
	private Locator getByNameLocator() throws Exception{
		this.ManualStep = getManualStep();
		this.Value = getValue();
		this.ByLocator = By.name(getLocatorText());
		if(Value.equalsIgnoreCase("%inputsheet%"))
			this.Value = Ireader.GetData(LocatorID, CustomerType);
		return new Locator(this.ByLocator,this.ManualStep,this.report,this.Value);
	}
	
	private Locator getByClassNameLocator() throws Exception{
		this.ManualStep = getManualStep();
		this.Value = getValue();
		this.ByLocator = By.className(getLocatorText());
		if(Value.equalsIgnoreCase("%inputsheet%"))
			this.Value = Ireader.GetData(LocatorID, CustomerType);
		return new Locator(this.ByLocator,this.ManualStep,this.report,this.Value);
	}
	
	private Locator getByCssSelectorLocator() throws Exception{
		this.ManualStep = getManualStep();
		this.Value = getValue();
		this.ByLocator = By.cssSelector(getLocatorText());
		if(Value.equalsIgnoreCase("%inputsheet%"))
			this.Value = Ireader.GetData(LocatorID, CustomerType);
		return new Locator(this.ByLocator,this.ManualStep,this.report,this.Value);
	}
	
	private Locator getByLinkTextLocator() throws Exception{
		this.ManualStep = getManualStep();
		this.Value = getValue();
		this.ByLocator = By.linkText(getLocatorText());
		if(Value.equalsIgnoreCase("%inputsheet%"))
			this.Value = Ireader.GetData(LocatorID, CustomerType);
		return new Locator(this.ByLocator,this.ManualStep,this.report,this.Value);
	}
	
	private Locator getByIDLocator() throws Exception{
		this.ManualStep = getManualStep();
		this.Value = getValue();
		this.ByLocator = By.id(getLocatorText());
		if(Value.equalsIgnoreCase("%inputsheet%"))
			this.Value = Ireader.GetData(LocatorID, CustomerType);
		return new Locator(this.ByLocator,this.ManualStep,this.report,this.Value);
	}
	
	private Locator getLegacyLocator() throws Exception{
		this.ManualStep = getManualStep();
		this.Value = getValue();
		this.LegacyLocator = getLocatorText();
		if(Value.equalsIgnoreCase("%inputsheet%"))
			this.Value = Ireader.GetData(LocatorID, CustomerType);
		return new Locator(this.LegacyLocator,this.ManualStep,this.report,this.Value);
	}
	
	public static void main(String args[]) throws Exception{
		LocatorReader locatorReader = new LocatorReader(new HTMLReport()).setProperty("SignOnProperties","General");
//		Locator locator = locatorReader.getLocator("Signon_Button");
//		System.out.println(locator.getValue());
		
		Locator locator = locatorReader.getLocator("Username_Input");
		Logger.logPrint(locator.getValue());
		/*locatorReader.setLocatorID("Signon_Button");
		System.out.println(locatorReader.getByType());*/
	}
}
