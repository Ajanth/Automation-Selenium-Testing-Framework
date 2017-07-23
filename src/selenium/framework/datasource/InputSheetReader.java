package selenium.framework.datasource;

import java.io.File;

import selenium.framework.core.TestParallel;
import selenium.framework.reports.Logger;


public class InputSheetReader {
	String InputSheetLocation;
	jxl.Workbook workbook;
	jxl.Sheet sheet;
	public InputSheetReader(){
		this.InputSheetLocation = TestParallel.InputSheetLocation;
	}
	public String GetData (String ColName, String LocatorID)throws Exception
	{   
		
		workbook = null;
		sheet = null;
        try
        {
        	workbook = jxl.Workbook.getWorkbook(new File(InputSheetLocation));
        	sheet = workbook.getSheet(0);
        }catch(Exception E){
			Logger.logPrint("couldnot open the file");
		}
       		// get column index
        int columnIndex=0;
        int cfound = 0;
        int columnCount = sheet.getColumns();
        for (int i = 0; i < columnCount; i++)
        {
			if(sheet.getCell(i, 0).getContents().equals(ColName))
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
        	 if(sheet.getCell(apptypecol, i).getContents().equals(LocatorID))
        	 {
        		 rowIndex = i;
        		 rfound=1;
        	 }
        }
		if (rfound==0||cfound==0)
		{
			Logger.logPrint("row or column not found check params passed "+LocatorID);
			
		}
		String data;	
		//Cell userval = sheet.getCell(6,1);
		//String user = userval.getContents();
		data = sheet.getCell(columnIndex, rowIndex).getContents();
		return data;

	} 
	/*public static void main(String args[]) throws Exception{
		InputSheetReader Ireader = new InputSheetReader();
		Logger.logPrint(Ireader.GetData("Password_Input", "General"));
	}*/
}
