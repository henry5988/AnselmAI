import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class UpdateHTML {

  private String dbTableDirectory;
  private String templateDirectory;
  private String outputDirectory;
  // TODO �ݭn�N�o��Parameter�令�q��Ʈw���o���
  UpdateHTML(String dbTable, String template, String output) {
    setTxtDirectory(dbTable);
    setTemplateDirectory(template);
    setOutputDirectory(output);
  }

  public void update() {
	  Connection conn_sql;
	  String data_path = getTxtDirectory();
	  System.out.println(data_path);
	  
	  ArrayList<String> sqlResult = new ArrayList<String>();
	
	  try {
		conn_sql = getMySQLConnection2(Constants.MYSQLUSERNAME, Constants.MYSQLPASSWORD, Constants.MYSQLURL);
		String sql = "SELECT * FROM "+data_path+" ORDER BY TIME DESC";
		System.out.println(sql);
		Statement stat = conn_sql.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		
		rs.next();
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		System.out.println(columnsNumber);
		for (int i=1;i<=columnsNumber-2;i++) {
			//System.out.println(rs.getString(i));
			System.out.println(rs.getString("column"+String.valueOf(i)).toString());
			sqlResult.add(rs.getString("column"+String.valueOf(i)).toString());
		}
		String line;
		String html = "";
		int i = 0;
		String html_path = getTemplateDirectory();
		FileReader fr_html = new FileReader(html_path);
	    BufferedReader br_html = new BufferedReader(fr_html);
	    BufferedWriter fw = new BufferedWriter(
		          new OutputStreamWriter(new FileOutputStream(getOutputDirectory()), "big5"));
	    
	    while ((line = br_html.readLine()) != null) {
	   
	    	if (line.contains("{(anselmai)}")) { 
	    	 	String value = sqlResult.get(i).toString();
	    		if (value.equals("{(anselmai)}")) {
	    			value = "";
		            html += line.replace("{(anselmai)}", value) + String.format("%n");
		        } else {
		            html += line.replace("{(anselmai)}", value) + String.format("%n");
		        }
		          i++;
		    } else {
		          html += line + String.format("%n");
		    }
		}
	    fw.write(html);
	    fw.close();
	    fr_html.close();
	    br_html.close();
		conn_sql.close();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//	    String line;
//	    String html = "";
//	    String[] tempArray = new String[20];
//	    int br_data_line = 0;
//	    int i = 0;
//	
////	    String data_path = getTxtDirectory();//��Ƥ����|
//	    FileReader fr_data = null;
//	    try {
//	      fr_data = new FileReader(data_path);
//	
//	      BufferedReader br_data = new BufferedReader(fr_data);
//	
//	      String html_path = getTemplateDirectory();//���������|
//	      FileReader fr_html = new FileReader(html_path);
//	      BufferedReader br_html = new BufferedReader(fr_html);
//	      StringBuffer sb = new StringBuffer();
//	
//	      //��X�����W�ٻP�s�X�]�w
//	      BufferedWriter fw = new BufferedWriter(
//	          new OutputStreamWriter(new FileOutputStream(getOutputDirectory()), "big5"));
//	
//	      while ((line = br_data.readLine())!=null) { //�v��Ū��txt�ɡA�Ӧ檺���e�s�iline
//	        tempArray[br_data_line] = line;
//	        System.out.println("token: " + tempArray[br_data_line]);
//	        br_data_line++;
//	      }
//	      System.out.println("Token number:" + String.valueOf(br_data_line-1));
//	      while ((line = br_html.readLine()) != null) {//�v��Ū��html�A�Ӧ檺���e�s�iline
//	        if (line.contains("{(anselmai)}")) { //�P�_�Ӧ�O�_�t������r
//	          if (tempArray[i].equals("{(anselmai)}")) {
//	            tempArray[i] = "";
//	            html += line.replace("{(anselmai)}", tempArray[i]) + String.format("%n");
//	          } else {
//	            html += line.replace("{(anselmai)}", tempArray[i]) + String.format("%n");
//	          }
//	          i++;
//	        } else {
//	          html += line + String.format("%n");
//	        }
//	      }
//	      fw.write(html);//�N�Ȧs���e�g�i�ɮפ�
//	      fw.close();
//	      fr_data.close();
//	      br_data.close();
//	      fr_html.close();
//	      br_html.close();
//	    } catch (FileNotFoundException e) {
//	      System.err.println("FileNotFoundException in UpdateHTML()");
//	      System.err.println(e.getMessage());
//	    } catch (UnsupportedEncodingException e) {
//	      System.err
//	          .println("UnsupportedEncodingException in UpdateHTML(); Current Encoding set to: big5");
//	      System.err.println(e.getMessage());
//	    } catch (IOException e) {
//	      System.err.println("IOException in UpdateHTML()");
//	      System.err.println(e.getMessage());
//	    }
  }

  /*Getters and Setters*/

  public String getTxtDirectory() {
    return dbTableDirectory;
  }

  public void setTxtDirectory(String dbTableDirectory) {
    this.dbTableDirectory = dbTableDirectory;
  }

  public String getTemplateDirectory() {
    return templateDirectory;
  }

  public void setTemplateDirectory(String templateDirectory) {
    this.templateDirectory = templateDirectory;
  }

  public String getOutputDirectory() {
    return outputDirectory;
  }

  public void setOutputDirectory(String outputDirectory) {
    this.outputDirectory = outputDirectory;
  }
  public static Connection getMySQLConnection2(String username, String password, String url)
	      throws SQLException, ClassNotFoundException,Exception {
	    Connection conn;
	    Class.forName("org.mariadb.jdbc.Driver");
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", username);
	    connectionProps.put("password", password);
	    conn = DriverManager.getConnection(url, connectionProps);
	    System.out.println("Connection established");
	    return conn;
	  }
}

