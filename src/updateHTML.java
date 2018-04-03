import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class updateHTML {
		 public static void main(final String[] args) throws Exception {
		
			 String line;
			 String html = "";
			 String[] tempArray= new String[10];
			 int br_data_line = 0;
			 int i = 0;
			 
			 String data_path = "C:\\Users\\user\\Desktop\\documentsPopup_data.txt";//資料文件路徑
			 FileReader fr_data= new FileReader(data_path);
			 BufferedReader br_data = new BufferedReader(fr_data);
			
			 String html_path = "C:\\Users\\user\\Desktop\\documentsPopup.txt";//網頁文件路徑
			 FileReader fr_html= new FileReader(html_path);
			 BufferedReader br_html = new BufferedReader(fr_html);
			 StringBuffer sb = new StringBuffer();
			 
			 //輸出網頁名稱與編碼設定
			 BufferedWriter fw  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\user\\Desktop\\documentsPopup.html"), "UTF8"));
	

			 while ((line = br_data.readLine()) != null) { //逐行讀取txt檔，該行的內容存進line
				 tempArray[br_data_line] = line;  			 
				 br_data_line++;	          
			 }
			
		
			 while ((line = br_html.readLine()) != null) {//逐行讀取html，該行的內容存進line
				 if( line.indexOf("{(anselmai)}")!=-1) { //判斷該行是否含有關鍵字
					 if (tempArray[i].equals("{(anselmai)}")) {
						 tempArray[i] = "";
						 html += line.replace("{(anselmai)}",tempArray[i])+"\n";	
					 }
					 else {
						 html += line.replace("{(anselmai)}",tempArray[i])+"\n";
					 }
					 i++;
				 }
				 else {
					 html += line+"\n"; 
				 }	 
			 }
			 fw.write(html);//將暫存內容寫進檔案中
			 fw.close();
			 fr_data.close();
			 br_data.close();
			 fr_html.close();
			 br_html.close();
			 
			  }
		 }

