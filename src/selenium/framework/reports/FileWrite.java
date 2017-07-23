package selenium.framework.reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.bcel.generic.FDIV;

import selenium.framework.enums.AppType;


public class FileWrite {
	String TestcaseName;
	String BaseFileLocation;
	String FileLocationToWrite;
	String locale;
	AppType apptype;
	DateFormat dateFormat,timeFormat;
	String curTime;
	Date date;
	String BrowserName;
	public FileWrite(String BaseFileLocation,String TestcaseName,String locale,AppType apptype,String BrowserName){
		this.BaseFileLocation = BaseFileLocation;
		this.TestcaseName = TestcaseName;
		this.locale = locale;
		this.apptype = apptype;
		this.BrowserName = BrowserName;
		dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		timeFormat = new SimpleDateFormat("ddhhmmss");
		date = new Date();
		this.FileLocationToWrite = this.BaseFileLocation+"Results "+dateFormat.format(date)+"/"+this.BrowserName+"/"+this.locale+"/"+this.apptype+"/";
	}
	
	public void write(String Content,boolean passFlag) throws IOException{
		if(this.locale==null || this.locale.equals("null"))
			this.locale = "SystemLevel";
		if(this.apptype==null || this.apptype.equals("null"))
			this.apptype = AppType.SystemLevelErrors;
		this.FileLocationToWrite = this.BaseFileLocation+"Results "+dateFormat.format(date)+"/"+this.BrowserName+"/"+this.locale+"/"+this.apptype+"/";
		FileWriter fstream;
		new File(this.FileLocationToWrite).mkdirs();
		//Thread.sleep(2000);
		Logger.logPrint("Writing report to the location -> "+this.FileLocationToWrite);
		Date dnow =new Date();	
		curTime = new SimpleDateFormat("ddMMHHmmss").format(dnow);
		if(passFlag)
			fstream = new FileWriter(this.FileLocationToWrite+"Passed_"+TestcaseName+"_"+curTime+".html");
		else
			fstream = new FileWriter(this.FileLocationToWrite+"Failed_"+TestcaseName+"_"+curTime+".html");
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(Content);
		out.close();
		fstream.close();
	}
	
}
