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
			 
			 String data_path = "C:\\Users\\user\\Desktop\\documentsPopup_data.txt";//��Ƥ����|
			 FileReader fr_data= new FileReader(data_path);
			 BufferedReader br_data = new BufferedReader(fr_data);
			
			 String html_path = "C:\\Users\\user\\Desktop\\documentsPopup.txt";//���������|
			 FileReader fr_html= new FileReader(html_path);
			 BufferedReader br_html = new BufferedReader(fr_html);
			 StringBuffer sb = new StringBuffer();
			 
			 //��X�����W�ٻP�s�X�]�w
			 BufferedWriter fw  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Users\\user\\Desktop\\documentsPopup.html"), "UTF8"));
	

			 while ((line = br_data.readLine()) != null) { //�v��Ū��txt�ɡA�Ӧ檺���e�s�iline
				 tempArray[br_data_line] = line;  			 
				 br_data_line++;	          
			 }
			
		
			 while ((line = br_html.readLine()) != null) {//�v��Ū��html�A�Ӧ檺���e�s�iline
				 if( line.indexOf("{(anselmai)}")!=-1) { //�P�_�Ӧ�O�_�t������r
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
			 fw.write(html);//�N�Ȧs���e�g�i�ɮפ�
			 fw.close();
			 fr_data.close();
			 br_data.close();
			 fr_html.close();
			 br_html.close();
			 
			  }
		 }

