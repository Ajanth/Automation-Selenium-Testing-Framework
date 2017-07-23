package selenium.framework.exception;

import org.openqa.selenium.WebElement;

import selenium.framework.core.Locator;


public class LegacyElementNotfoundException extends Exception {
	private Locator locator;
	private String prefix;
	private String Action;
	 
    public LegacyElementNotfoundException(Locator locator,String prefix,String Action){
        this.locator = locator;
        this.prefix = prefix;
        this.Action = Action;
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
    public String getAction(){
    	return Action;
    }
}
