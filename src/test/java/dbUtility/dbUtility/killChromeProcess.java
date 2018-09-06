package dbUtility.dbUtility;

import java.io.IOException;

public class killChromeProcess {

	public void kill() throws Exception {

		try{
			Runtime.getRuntime().exec("TASKKILL /F /IM chrome.exe");
			Runtime.getRuntime().exec("TASKKILL /F /IM chromedriver.exe");
		}
		catch(IOException io){
			System.out.println(io.getMessage());
		}	

	}
	public static void main (String[] args) { 
		killChromeProcess obj= new killChromeProcess();
		try {
			obj.kill();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}



