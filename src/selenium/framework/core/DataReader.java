package selenium.framework.core;
import java.io.File;
import java.io.IOException;

import jxl.read.biff.BiffException;


public class DataReader {
	public static String DataPath = "C:\\webdriverprop\\dataread.xls";
	
	static jxl.Workbook workbook;
	static jxl.Sheet sheet;
	
	
	
	public static String GetRowData (String Path, String ColName, int rowindex)throws Exception
	{
		workbook = null;
		sheet =  null;
    try
    {
    	workbook = jxl.Workbook.getWorkbook(new File(Path));
    	sheet = workbook.getSheet(0);
    }catch(Exception E){
		System.out.println("could not open the file");
	}
   		// get column index
    int columnIndex=0;
    int cfound = 0;
    int columnCount = sheet.getColumns();
    System.out.print("\n COL CT :"+columnCount);
    
    
    for (int i = 0; i < columnCount; i++)
    {
		if(sheet.getCell(i, 0).getContents().equalsIgnoreCase(ColName))
		{		
			System.out.print("\n CELL["+ i+",0]"+sheet.getCell(i, 0).getContents());
			columnIndex=i;
			cfound =1;
		}
				
	}
	//get row index
    
	if (cfound==0)
	{
		System.out.println("\n column not found check params passed");
	}
	String data;	
	//Cell userval = sheet.getCell(6,1);
	//String user = userval.getContents();
	data = sheet.getCell(columnIndex, rowindex).getContents();
	return data;

}

	public static int GetRowColumnCount(String xlpath,String sheetname, boolean flag)throws Exception
	{
	jxl.Workbook workbook = null;
	jxl.Sheet sheet = null;
	try
    {
    	workbook = jxl.Workbook.getWorkbook(new File(xlpath));
    	sheet = workbook.getSheet(sheetname);
    }catch(Exception e){
		System.out.println("GetRowColumnCount():could not open the xl file"+e);
	}
	return flag ?sheet.getRows():sheet.getColumns();//return based on boolean flag i.e rowcount or columncount
	}
	
	public static String GetData (String Path, String ColName, String Apptype,String sheetname)throws Exception
	{
    jxl.Workbook workbook = null;
    jxl.Sheet sheet = null;
    try
    { 
    	workbook = jxl.Workbook.getWorkbook(new File(Path));
    	sheet = workbook.getSheet(sheetname);
    }catch(Exception E){
		System.out.println("could not open the file");
	}
   		// get column index
    int columnIndex=0;
    int cfound = 0;
    int columnCount = sheet.getColumns();
    System.out.print("\n COL CT :"+columnCount);
    for (int i = 0; i < columnCount; i++)
    {
		if(sheet.getCell(i, 0).getContents().equalsIgnoreCase(ColName))
		{		
			System.out.print("\n"+ i+",0:"+sheet.getCell(i, 0).getContents());
			columnIndex=i;
			cfound =1;
		}
				
	}
	//get row index
    int rowIndex=0;
    int rowsCount = 0;
   
    int rfound = 0;
    rowsCount = sheet.getRows();
    System.out.print("\n ROW CT :"+rowsCount);
     for (int i = 0; i < rowsCount; i++)
    {
    	 if(sheet.getCell(0, i).getContents().equalsIgnoreCase(Apptype))
    	 {
    		 System.out.print("\n [0]"+ ",[i:]"+i+":::"+sheet.getCell(0, i).getContents());
    		 rowIndex = i;
    		 rfound=1;
    	 }
    }
	if (rfound==0||cfound==0)
	{
		System.out.println("\n row or column not found check params passed");
	}
	String data;	
	//Cell userval = sheet.getCell(6,1);
	//String user = userval.getContents();
	data = sheet.getCell(columnIndex, rowIndex).getContents();
	return data;


}
	
	public static String GetData (String Path, String ColName, String Apptype)throws Exception
	{
    jxl.Workbook workbook = null;
    jxl.Sheet sheet = null;
    try
    {
    	workbook = jxl.Workbook.getWorkbook(new File(Path));
    	sheet = workbook.getSheet(0);
    }catch(Exception E){
		System.out.println("could not open the file");
	}
   		// get column index
    int columnIndex=0;
    int cfound = 0;
    int columnCount = sheet.getColumns();
    System.out.print("\n COL CT :"+columnCount);
    for (int i = 0; i < columnCount; i++)
    {
		if(sheet.getCell(i, 0).getContents().equalsIgnoreCase(ColName))
		{		
			System.out.print("\n"+ i+",0:"+sheet.getCell(i, 0).getContents());
			columnIndex=i;
			cfound =1;
		}
				
	}
	//get row index
    int rowIndex=0;
    int rowsCount = 0;
   
    int rfound = 0;
    rowsCount = sheet.getRows();
    System.out.print("\n ROW CT :"+rowsCount);
     for (int i = 0; i < rowsCount; i++)
    {
    	 if(sheet.getCell(0, i).getContents().equalsIgnoreCase(Apptype))
    	 {
    		 System.out.print("\n [0]"+ ",[i:]"+i+":::"+sheet.getCell(0, i).getContents());
    		 rowIndex = i;
    		 rfound=1;
    	 }
    }
	if (rfound==0||cfound==0)
	{
		System.out.println("\n row or column not found check params passed");
	}
	String data;	
	//Cell userval = sheet.getCell(6,1);
	//String user = userval.getContents();
	data = sheet.getCell(columnIndex, rowIndex).getContents();
	return data;


}
	
	public static int getcolumnLength(String Path){
		try {
			workbook = jxl.Workbook.getWorkbook(new File(Path));
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	sheet = workbook.getSheet(0);
		int columnCount = sheet.getColumns();
		return columnCount;
	}
	public static int getrowLength(String Path){
		try {
			workbook = jxl.Workbook.getWorkbook(new File(Path));
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	sheet = workbook.getSheet(0);
		int rowCount = sheet.getRows();
		return rowCount;
	}
}
