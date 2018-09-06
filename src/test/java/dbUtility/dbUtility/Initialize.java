package dbUtility.dbUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Collator;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;





public class Initialize{
	
	public Process p;
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;      
	String filename;
	String SheetName;
	public  String path;
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row   =null;
	private XSSFCell cell = null;
	private static Date date;
	private String DestPath;
	


	@SuppressWarnings("static-access")
	
//	/String Store,String date
	
	public static double roundTwoDecimals(double d) {
	    DecimalFormat twoDForm = new DecimalFormat("#.##");
	    return Double.valueOf(twoDForm.format(d));
	}
	
	public static boolean strCompareBinaray(String str1, String str2)
	{
		 Collator coll = Collator.getInstance(Locale.US);
		 coll.setStrength(Collator.PRIMARY); 
		 if ((coll.compare(str1,str2))==0) 
		 {
			 return true;
		 }
		 else
			 return false;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void excelOutputforBI(Hashtable< String, HashMap> table,String filepath) throws FileNotFoundException, IOException {
		Enumeration names;
		String key;
		workbook = new XSSFWorkbook();
			Sheet QueryResults = workbook.createSheet("QueryResults");
		
		 HashMap<String,String> dmap  =table.get("0");
			HashMap<String,String> excelMap = new HashMap<String,String>();
		 Row row = QueryResults.createRow(0);
		 int cellIndex = 0;
		 //excel Columns header
		 for(Map.Entry entry: dmap.entrySet()){
			 row.createCell(cellIndex++).setCellValue(entry.getKey().toString());
		 }
			names = table.keys();
			int rownum=1;
			cellIndex =0;
			while(names.hasMoreElements()) {
				key = (String) names.nextElement();
				excelMap = table.get(key);
				row = QueryResults.createRow(rownum++);
				for(Map.Entry entry: excelMap.entrySet()){
					 row.createCell(cellIndex).setCellValue(excelMap.get(entry.getKey().toString()));
					 cellIndex++;
				}
				cellIndex =0;
			
       	}
			try {
				
				FileOutputStream fos = new FileOutputStream(filepath);
			
			workbook.write(fos);
			fos.close();
//			workbook.close();			
			 System.out.println(filepath + " is successfully written");
			}catch (FileNotFoundException e) {
				e.printStackTrace();

			}catch (IOException e) {
				e.printStackTrace();

			}
			
	}


	
	@SuppressWarnings("unused")
	private int FetchExcelHeaders(String path, Sheet xlsxSheet,int rowNum,String val) {
		int index=0;
		int row = xlsxSheet.getRow(rowNum).getLastCellNum();
		for(int i=0;i<row;i++)
		{
			
			if(strCompareBinaray(xlsxSheet.getRow(rowNum).getCell(i).getStringCellValue(), val))
			{
				index = i;
				break;
			}
		}
		return index;
	}
	public Initialize()
	{}
	public Initialize(String path) {

		this.path=path;
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
		
			e.printStackTrace();
		} 

	}
	public int getColumnCount(String sheetName){
		// check if sheet exists
		if(!isSheetExist(sheetName))
			return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if(row==null)
			return -1;

		return row.getLastCellNum();



	}
	public boolean isSheetExist(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1){
			index=workbook.getSheetIndex(sheetName.toUpperCase());
			if(index==-1)
				return false;
			else
				return true;
		}
		else
			return true;
	}
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
			sheet = workbook.getSheetAt(index);
			int number=sheet.getLastRowNum()+1;
			return number;
		}

	}
	public String getCellData(String sheetName,int colNum,int rowNum){
		try{
			if(rowNum <=0)
				return "";

			int index = workbook.getSheetIndex(sheetName);

			if(index==-1)
				return "";


			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(colNum);
			if(cell==null)
				return "";

			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){

				String cellText  = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal =Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText =
							(String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH)+1 + "/" +
							cal.get(Calendar.DAY_OF_MONTH) + "/" +
							cellText;

					// System.out.println(cellText);

				}



				return cellText;
			}else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
				return "";
			else 
				return String.valueOf(cell.getBooleanCellValue());
		}
		catch(Exception e){

			e.printStackTrace();
			return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
		}
	}
	
	public String getPropertyValue(String propertyName,String propertyFile) throws IOException{
		
		Properties prop = new Properties();
		InputStream input = null;
		File file = new File(propertyFile);
		input = new FileInputStream(file.getAbsolutePath());

		// load a properties file
		prop.load(input);
		// get the property value and return
		return  prop.getProperty(propertyName);
			
	}
	
	public  boolean CheckDoubleValie (String String )
	{
		try {
				Double.parseDouble(String);
			 return  true;
		}catch (NumberFormatException Ex) {
			
		return false;
		}

	}
	
	public static Object[][] dataProvider(String fileName, String SheetName ) throws Exception {
		Initialize excelObj = new Initialize(fileName);
		int colCount=0,rowCount =0;
		excelObj.sheet = excelObj.workbook.getSheetAt(0);
		String AxtualSheetName  = excelObj.sheet.getSheetName();
		if (AxtualSheetName.equals(SheetName))
		{
			 colCount = excelObj.getColumnCount(SheetName);
			 rowCount = excelObj.getRowCount(SheetName);
		}
		else
		{
			 excelObj.row = excelObj.sheet.getRow(0);
			 colCount = excelObj.row.getLastCellNum();
			 rowCount = excelObj.sheet.getLastRowNum()+1;
			 SheetName = AxtualSheetName;
		}
	
		Object[][] data = new Object[rowCount-1][1];
		List<String> colNames= new ArrayList<String>();
		

		for(int col=0;col<colCount;col++) 
		{

			colNames.add(col, excelObj.getCellData(SheetName, col, 1));

		}
		
		int actualRowCount=0;
		
		for(int row=2;row<=rowCount;row++)
		{

			

				Map<String, String> table = new Hashtable<String,String>();
				
				for(int col=0;col<colCount;col++)
				{
					table.put(colNames.get(col), excelObj.getCellData(SheetName, col, row));

				}

				data[actualRowCount][0]= table;
				actualRowCount++;
			}

			

		


		return data;

		
	}
	
}
