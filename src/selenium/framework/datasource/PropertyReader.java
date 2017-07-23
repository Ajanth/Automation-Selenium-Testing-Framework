package selenium.framework.datasource;

import java.io.File;

import selenium.framework.core.Locator;
import selenium.framework.core.TestParallel;
import selenium.framework.reports.Logger;


public class PropertyReader {
	String PropertiesFileLocation;
	String PropertySheet;
	String PropertyName;
	Locator locator;
	InputSheetReader InputSheet;
	jxl.Workbook workbook;
	jxl.Sheet sheet;
	
	public PropertyReader(String PropertySheet){
		this.PropertiesFileLocation = TestParallel.PropertiesFileLocation;
		this.PropertySheet = PropertySheet;
		Logger.logPrint("In PropertyReader constructor "+this.PropertySheet);
	}
	public PropertyReader(){
		
	}
	
	
		
	public String GetData (String ColName, String LocatorID)throws Exception
	{    
		workbook = null;
		sheet = null;
        try
        {
        	workbook = jxl.Workbook.getWorkbook(new File(PropertiesFileLocation));
        	sheet = workbook.getSheet(PropertySheet);
        }catch(Exception E){
			Logger.logPrint("could not open the file");
		}
       		// get column index
        int columnIndex=0;
        int cfound = 0;
        int columnCount = sheet.getColumns();
        for (int i = 0; i < columnCount; i++)
        {
			if(sheet.getCell(i, 0).getContents().trim().equals(ColName))
			{		
				columnIndex=i;
				cfound =1;
			}
					
		}
		//get row index
        int rowIndex=0;
        int rowsCount = 0;
        int apptypecol = 0;
        int rfound = 0;
        rowsCount = sheet.getRows();
         for (int i = 0; i < rowsCount; i++)
        {
        	 if(sheet.getCell(apptypecol, i).getContents().trim().equals(LocatorID))
        	 {
        		 rowIndex = i;
        		 rfound=1;
        	 }
        }
		if (rfound==0||cfound==0)
		{
			Logger.logPrint("\n row or column not found check params passed");
			
		}
		String data;	
		//Cell userval = sheet.getCell(6,1);
		//String user = userval.getContents();
		data = sheet.getCell(columnIndex, rowIndex).getContents().trim();
		return data;
	} 
	public static void main(String args[]) throws Exception{
		PropertyReader preader = new PropertyReader("SignOnProperties");
		System.out.println(preader.GetData("By Type", "Username_Input"));
	}
}
