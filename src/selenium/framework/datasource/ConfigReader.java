package selenium.framework.datasource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import selenium.framework.core.TestParallel;
import selenium.framework.reports.Logger;


public class ConfigReader {
	public ConfigReader(){
		
	}
	
	public static String readConfig(String ConfigurationKey) {
		try{
		Properties ConfigProperties = new Properties();
		ConfigProperties.load(new FileInputStream(TestParallel.ConfigLocation));
		return ConfigProperties.getProperty(ConfigurationKey);
		}catch(Exception e){
			Logger.logPrint("Problem with configuration reader: \n"+e);
			System.exit(0);
		}
		return "";
	}
}
