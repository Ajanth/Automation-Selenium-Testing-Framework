package selenium.framework.exception;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import selenium.framework.core.Locator;


public class ByElementNotfoundException extends Exception {
	private Locator locator;
	private String prefix;
	private String Action;
	private WebDriver driver; 
    public ByElementNotfoundException(Locator locator,String prefix,String Action,WebDriver driver){
        this.locator = locator;
        this.prefix = prefix;
        this.Action = Action;
        this.driver = driver;
    }
 
    public String toString(){
        return "The requested element \"" + locator.getLegacyLocator() + "\" not found" ;
    }
    public Locator getLocator(){
    	return this.locator;
    }
    public String getPrefix(){
    	return prefix;
    }
    public WebDriver getDriver(){
    	return driver;
    }
    public String getAction(){
    	return Action;
    }
}
