import com.HF;
import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IChange;
import com.agile.api.IDataObject;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.IUser;
import com.agile.api.UserConstants;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;

import static com.HF.executeSQL;
import static com.HF.getConnection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

public class GetFilePopup extends SuggestionPopup implements Constants{

	@Override
	public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req) {
		init(session, req, GETFILEPOPUP_OUTPUT_PATH,
				GETFILEPOPUP__HTML_TEMPLATE,
				 GETFILEPOPUP_HTML_OUTPUT, false, false);
		super.doAction(session, node, req);
		return new EventActionResult(req, new ActionResult(ActionResult.STRING, "Get File Popup"));
	}

	@Override
	protected String checksField() {
		// no field checking in this class
		return null;
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
	@Override
	protected void writeToFile(List<List> infoList) throws ClassNotFoundException {
	
		Connection conn_sql;
		
		
		try {
			conn_sql = getMySQLConnection2(Constants.MYSQLUSERNAME, Constants.MYSQLPASSWORD, Constants.MYSQLURL);
			String value ="";
			String column ="";
			String sql;
			List sqlResult= new LinkedList(); ;
			sqlResult.add(getTargetItem(getEventInfo()).getName());  
			for(int i=0; i<infoList.get(0).size(); i++){
			      for(int j = 0; j< infoList.size(); j++){
			    	  sqlResult.add(infoList.get(j).get(i));   	 
			      }
			    }
			for(int i=0; i<sqlResult.size();i++){	
				if (i==0) {
					column += "column"+String.valueOf(i+1);
					value += "'"+sqlResult.get(i)+"'";
				}
				else {
					column += ", column"+String.valueOf(i+1);
					value +=  ", '"+sqlResult.get(i)+"'";
				}
					
			}
			
			
			sql = "INSERT INTO "+GETFILEPOPUP_OUTPUT_PATH+" ("+column+") VALUES ("+value+")" ;
			System.out.println(sql);
//			Statement stat = conn_sql.createStatement();
//			stat.executeQuery(sql);
			conn_sql.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		// locate output file
		IEventInfo req = getEventInfo();
		IFileEventInfo info = (IFileEventInfo) req;
		String fileName = null;
		try {
			fileName = getDownloadedFileName(info);
		} catch (APIException e) {
			e.printStackTrace();
		}
		File f = new File(getOutput_path());
		File exist = new File(EXIST);
		if (!f.exists()) {
			Files.createDirectories(Paths.get(f.getPath()).getParent());
			f.createNewFile();
		}
		if (!exist.exists()) {
			Files.createDirectories(Paths.get(exist.getPath()).getParent());
			exist.createNewFile();
		}
		FileWriter existWriter = new FileWriter(exist);
		existWriter.write(isTest() ? getHtmlTemplate() : getHtmlOutput());
		existWriter.close();
		// open file streams
		FileOutputStream fos = new FileOutputStream(f);
		// print visited file name, then next line
		fos.write((fileName + "\n").getBytes());
		for (List l : infoList) {
			for (Object o : l) {
				// if printing last element, add new line character at the end
				if (l.indexOf(o) == l.size() - 1) {
					fos.write((o.toString() + "\r\n").getBytes());
				} else {
					// print image source
					// print file suggestion names
					// print number of visits for the names
					fos.write(o.toString().getBytes());
				}
			}
		}
		// close file streams
		fos.close();
		*/
	}

	@Override
	protected IAgileObject getTargetItem(IEventInfo req) {
		IFileEventInfo info = (IFileEventInfo) req;
		IAgileObject obj = null;
		try {
			obj = info.getDataObject();
		} catch (APIException e) {
			e.printStackTrace();
		}
		return obj;
	}

	private String getDownloadedFileName(IFileEventInfo info) throws APIException {
		IEventDirtyFile[] files = info.getFiles();
		return files[0].getFilename();
	}

	@Override
	protected List<List> convertObjectToInfo(List l) throws APIException {
		  List<List> list = new LinkedList<>();
		  list.add(l);
		  
	    return l;
	}

	@Override
	protected List getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
			throws SQLException, APIException, ClassNotFoundException {
		String sql;
		List sqlResult;
		
		List finalSuggestion = new LinkedList();
		Map<String,Integer> getFileDetail  = new HashMap();
		IUser user = session.getCurrentUser();
		IFileEventInfo info = (IFileEventInfo) req;
		IEventDirtyFile[] files = info.getFiles();
		IDataObject ob = info.getDataObject();
		IItem itm = (IItem)session.getObject(IItem.OBJECT_TYPE,  ob.getName());
		Connection conn = getConnection(USERNAME, PASSWORD, URL);
		//sql = "SELECT USER_NAME,to_char(to_date(TIMESTAMP,'DD-MON-YY'),'DD-MM-YY') as DATETIME  FROM ITEM_HISTORY   WHERE ACTION = 15 AND ITEM IN (SELECT ID FROM ITEM WHERE ITEM_NUMBER = '"+itm.getName()+"')AND DETAILS LIKE '%"+files[0].getFilename()+"%' AND USER_NAME != '"+user.toString()+"' GROUP BY USER_NAME,to_char(to_date(TIMESTAMP,'DD-MON-YY'),'DD-MM-YY')";	
		sql = "SELECT TO_CHAR(DETAILS) AS DETAILS,USER_NAME,to_char(to_date(TIMESTAMP,'DD-MON-YY'),'DD-MM-YY') AS DATETIME  FROM ITEM_HISTORY   WHERE ACTION = 15 AND ITEM IN (SELECT ID FROM ITEM WHERE ITEM_NUMBER = '"+itm.getName()+"')AND DETAILS LIKE '%"+files[0].getFilename()+"%' AND USER_NAME != '"+user.toString()+"' GROUP BY TO_CHAR(DETAILS),USER_NAME,to_char(to_date(TIMESTAMP,'DD-MON-YY'),'DD-MM-YY')";	
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		System.out.println("★"+sql);
		while (rs.next()) {
			String userName = rs.getString("USER_NAME").toString();
			String dateTime = rs.getString("DATETIME").toString();
			String detail = rs.getString("DETAILS").toString();
			sql = "SELECT TO_CHAR(DETAILS) AS DETAILS,USER_NAME  FROM ITEM_HISTORY   WHERE ACTION = 15 AND USER_NAME = '"+userName+"' AND DETAILS NOT LIKE '%"+detail+"%' AND TIMESTAMP >= TO_DATE('"+dateTime+"', 'DD-MM-YY')-7 AND TIMESTAMP <= TO_DATE('"+dateTime+"', 'DD-MM-YY')+7 GROUP BY TO_CHAR(DETAILS),USER_NAME ORDER BY USER_NAME";	
			System.out.println("★★"+sql);
			Statement stat2 = conn.createStatement();
			ResultSet rs2 = stat2.executeQuery(sql);
			String former_user  ="";
			String former_detail = "";
			
			while (rs2.next()) {
				
				detail = rs2.getString("DETAILS").toString();
				String userName2 = rs2.getString("USER_NAME").toString();
				if(!userName2.equals(former_user)|| !detail.equals(former_detail)) {
				  if(getFileDetail.containsKey(detail)){
				      Integer v = (Integer) getFileDetail.get(detail);
				      getFileDetail.remove(detail);
				      getFileDetail.put(detail, v+1);
				    }else{
				    	getFileDetail.put(detail, 1);
				    }
				}
				former_user =  rs2.getString("USER_NAME").toString();
				former_detail = rs2.getString("DETAILS").toString();
			}
			//System.out.println(userName+"   "+dateTime);
		}
		
		Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,Integer>>() {
	        @Override
	        public int compare(Entry<String, Integer> o1,Entry<String, Integer> o2) {
	            // TODO Auto-generated method stub
	            return o2.getValue()-o1.getValue();
	        }
	    };

	    // map轉換成list進行排序
	    List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(getFileDetail.entrySet());

	    // 排序
	    Collections.sort(list,valueComparator);

	    // 默認情况下，TreeMap對key進行降序排序
	    int max =1;
	    //System.out.println("------------map按照value降序排序--------------------");
	    for (Map.Entry<String, Integer> entry : list) {
	    	System.out.println("☆☆☆☆☆"+entry.getKey() + ":" + entry.getValue());
	    	List suggestion = new LinkedList();
	    	String detail = entry.getKey().toString();
	    	String[] fileInfo = detail.split(" ");
	    	sql = "SELECT ATTACHMENT.DESCRIPTION, FILES.FILENAME, ITEM.ITEM_NUMBER FROM ATTACHMENT "
	    			+ "INNER JOIN  ATTACHMENT_MAP ON ATTACHMENT_MAP.ATTACH_ID = ATTACHMENT.ID "
	    			+ "INNER JOIN FILES ON FILES.ID = ATTACHMENT_MAP.FILE_ID "
	    			+ "INNER JOIN ITEM ON ITEM.ID =  ATTACHMENT_MAP.PARENT_ID "
	    			+ "WHERE ATTACHMENT.ATTACHMENT_NUMBER = '"+fileInfo[0]+"'";
	    	Statement stat3 = conn.createStatement();
			ResultSet rs3 = stat3.executeQuery(sql);
			System.out.println(sql);
			String description = "n/a";
			rs3.next();
			//IDataObject ido= (IDataObject) session.getObject(IItem.OBJECT_TYPE, rs3.getString("ITEM_NUMBER").toString());
			try{
				itm = (IItem)session.getObject(IItem.OBJECT_TYPE,  rs3.getString("ITEM_NUMBER").toString());
				System.out.println(itm+"        "+ user.hasPrivilege(UserConstants.PRIV_READ, itm));
				if (!rs3.getString("DESCRIPTION").isEmpty())	
					description = rs3.getString("DESCRIPTION").toString();
				
				String filename = rs3.getString("FILENAME").toString();
				String[] filetype = filename.split("\\.");
				suggestion.add("aaa");
				suggestion.add(filename);	
				suggestion.add(description);
				suggestion.add(entry.getValue().toString());
				finalSuggestion.add(suggestion);	
				
				max++;
				if(max==3)
					break;
			}catch(APIException e){
				if(e.getErrorCode().toString().equals("407")){
					System.out.println("權限不足");
					continue;
				}
				e.printStackTrace();
				throw e;
			}
			

	    	
	    }
	   
	    
	    
		
		/*
		sqlResult = executeSQL(conn, sql);
		for(Object relatedFiletData : sqlResult){	
			sql = "SELECT DETAILS  FROM ITEM_HISTORY  WHERE ACTION = 15 AND ";
			System.out.println("☆☆"+folder[0]);
		}
		*/
		conn.close();
		return finalSuggestion;
	}

}
