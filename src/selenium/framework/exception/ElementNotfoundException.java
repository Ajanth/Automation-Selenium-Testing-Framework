package selenium.framework.exception;

import org.openqa.selenium.WebElement;

public class ElementNotfoundException extends Exception {
	private String Element;
	 
    public ElementNotfoundException(String Element){
        this.Element = Element;
    }
 
    public String toString(){
        return "The requested element \"" + Element + "\" not found" ;
    }
    
    public String getElementName(){
		return Element.toString().substring(Element.toString().indexOf('>')+2,Element.toString().lastIndexOf(']'));
	}
}
