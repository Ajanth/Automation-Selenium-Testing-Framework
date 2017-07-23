package selenium.framework.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import selenium.framework.datasource.ConfigReader;
import selenium.framework.reports.HTMLReport;
import selenium.framework.reports.Logger;

import com.google.common.collect.ImmutableMap;
import com.opera.core.systems.OperaDriver;




@RunWith(Parallelized.class)
public class TestParallel {
	protected HTMLReport report;
	protected WebDriver driver;
	protected String browser;
	public static String ConfigLocation = "C://webdriverprop//AccoladeSelenium.config";
	public static String EnvironmentPropertyLocation = ConfigReader.readConfig("EnvironmentPropertyLocation");
	public static Properties environmentProps = new Properties();
	//static String ReportPath = "Reports";
	public static String ReportPath = ConfigReader.readConfig("ReportRoot");
	String ChromeDriverLocation = ConfigReader.readConfig("ChromeDriverLocation");
	String IEDriverLocation = ConfigReader.readConfig("InternetExplorerDriverLocation");
	protected String browserURL;
	public static String InputSheetLocation = ConfigReader.readConfig("InputSheetLocation");
	public static String PropertiesFileLocation = ConfigReader.readConfig("PropertiesSheetLocation");
	public static String MozillaFirefoxLocation = ConfigReader.readConfig("MozillaFirefoxLocation");
	public static String ChromeLocation = ConfigReader.readConfig("ChromeLocation");
	public static int DefaultSystemTimeOut = 86400; 
	String browserName;
	public String locale;
	protected WebDriverBackedSelenium  selenium;
	public TestParallel(String browser) {
		this.browser = browser;
	}
	protected void createBrowserDriver() throws ParseException{
		int SystemTimeOut;
		boolean ExecJScript = false; 
		try{
			SystemTimeOut = Integer.parseInt(ConfigReader.readConfig("SystemTimeOut"));
		}catch(NumberFormatException nf){
			SystemTimeOut = DefaultSystemTimeOut;
		}
		
		
		Logger.logPrint("n th browser" + browser + "\n");
		String[] browserParams = browser.split(";");
		Logger.logPrint("browser[0] string is " + browserParams[0] + "\n");
		browserURL = browserParams[1];
		//this.locale = "";
		try{
		if((browserParams[2] != null) || browserParams[2].equalsIgnoreCase("")){
			this.locale = browserParams[2].toUpperCase();
			Logger.logPrint("Locale is set to "+locale);
		}
		}catch(ArrayIndexOutOfBoundsException aiob ){
			Logger.logPrint("Locale information is not given in Environment properties file, Defaults to \'EN\' locale");
			this.locale = "EN";
		}
		
		if(browserParams[0].equalsIgnoreCase("*firefox")){
			Logger.logPrint("in firefox");
			browserName = "firefox";
			FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("network.proxy.type", 5);
			FirefoxBinary binary = new FirefoxBinary(new File(MozillaFirefoxLocation));
			driver = new FirefoxDriver(binary,profile);
		}
		
		//test code delete afterwards
		
		
		//end of test code
		else if(browserParams[0].equalsIgnoreCase("*chrome")){
			Logger.logPrint("in chrome");
			browserName = "chrome";
			System.setProperty("webdriver.chrome.driver", ChromeDriverLocation);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.binary", ChromeLocation);
			ChromeDriverService chromeDriverService = new ChromeDriverService.Builder()
		    .usingDriverExecutable(new File(ChromeDriverLocation))
		    .usingAnyFreePort()
		    .withEnvironment(ImmutableMap.of("DISPLAY",":1"))
		    .build();
			try {
				chromeDriverService.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//driver = new ChromeDriver(capabilities);
			driver = new RemoteWebDriver(chromeDriverService.getUrl(),capabilities);
		}else if(browserParams[0].equalsIgnoreCase("*opera")){
			Logger.logPrint("in opera");
			browserName = "opera";
			//System.setProperty("webdriver.chrome.driver", ChromeDriverLocation);
			DesiredCapabilities capabilities = DesiredCapabilities.opera();
			capabilities.setCapability("opera.binary", "C:\\Documents and Settings\\pb35194\\Local Settings\\Application Data\\Programs\\Opera\\opera.exe");
			capabilities.setCapability("opera.port", -1);
			capabilities.setCapability("opera.profile", "");
			capabilities.setCapability("opera.log.level", "CONFIG");
			driver = new OperaDriver(capabilities);
		}
		else{
			System.setProperty("webdriver.ie.driver", IEDriverLocation);
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(capabilities);
			driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		}
		selenium=new WebDriverBackedSelenium(driver,browserParams[1]);
		String BrowserVersion = "UNDEFINED";
		if(ExecJScript){
		BrowserVersion = (String) ((JavascriptExecutor) this.driver).executeScript("return navigator.userAgent;");
		Logger.logPrint(BrowserVersion);
		}
		String BrowserName = browserParams[0].substring(1);
		Logger.logPrint("Browser Name is "+BrowserName);
		report = new HTMLReport(BrowserVersion,BrowserName);
		Thread t;
		t = new Thread(new ProcessKillerAndRescue(selenium, driver,report,SystemTimeOut));   // Process killer , used when testcase gets hangs.
		t.start();
		
	}
	@SuppressWarnings("unchecked")
	@Parameters
	public static LinkedList browsersStrings() throws Exception {
		LinkedList browsers = new LinkedList();
		environmentProps
				.load(new FileInputStream(EnvironmentPropertyLocation));
		String[] rawBrowserStrings = environmentProps.getProperty("browsers")
				.split(",");
		for (String rawBrowserString : rawBrowserStrings) {
			browsers.add(new String[] { rawBrowserString });
		}
		return browsers;
	}
}