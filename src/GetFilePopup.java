import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IChange;
import com.agile.api.IDataObject;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.IUser;
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
import java.util.Map.Entry;

public class GetFilePopup extends SuggestionPopup {

	@Override
	public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req) {
		init(session, req, "C:\\anselmAIWeb\\serverSource\\documentPopup.txt",
				"C:\\anselmAIWeb\\serverSource\\createDocumentPopup.htm",
				"C:\\anselmAIWeb\\serverSource\\createDocumentPopup.html", false, true);
		super.doAction(session, node, req);
		return new EventActionResult(req, new ActionResult(ActionResult.STRING, "Get File Popup"));
	}

	@Override
	protected String checksField() {
		// no field checking in this class
		return null;
	}

	@Override
	protected void writeToFile(List<List> infoList) throws IOException {
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
					fos.write((o.toString() + "\n").getBytes());
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
		return null;
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
		sql = "SELECT USER_NAME,to_char(to_date(TIMESTAMP,'DD-MON-YY'),'DD-MM-YY') AS DATETIME  FROM ITEM_HISTORY   WHERE ACTION = 15 AND ITEM IN (SELECT ID FROM ITEM WHERE ITEM_NUMBER = '"+itm.getName()+"')AND DETAILS LIKE '%"+files[0].getFilename()+"%' AND USER_NAME != '"+user.toString()+"' GROUP BY USER_NAME,to_char(to_date(TIMESTAMP,'DD-MON-YY'),'DD-MM-YY')";	
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		
		while (rs.next()) {
			String userName = rs.getString("USER_NAME").toString();
			String dateTime = rs.getString("DATETIME").toString();
			sql = "SELECT TO_CHAR(DETAILS) AS DETAILS,USER_NAME  FROM ITEM_HISTORY   WHERE ACTION = 15 AND USER_NAME = '"+userName+"' AND DETAILS NOT LIKE '%"+files[0].getFilename()+"%' AND TIMESTAMP >= TO_DATE('"+dateTime+"', 'DD-MM-YY')-7 AND TIMESTAMP <= TO_DATE('"+dateTime+"', 'DD-MM-YY')+7 GROUP BY TO_CHAR(DETAILS),USER_NAME ORDER BY USER_NAME";	
			Statement stat2 = conn.createStatement();
			ResultSet rs2 = stat2.executeQuery(sql);
			String former_user  ="";
			String former_detail = "";
			
			while (rs2.next()) {
				
				String detail = rs2.getString("DETAILS").toString();
				String userName2 = rs2.getString("USER_NAME").toString();
				if(!userName2.equals(former_user)&& !detail.equals(former_detail)) {
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

	    // map转换成list进行排序
	    List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(getFileDetail.entrySet());

	    // 排序
	    Collections.sort(list,valueComparator);

	    // 默认情况下，TreeMap对key进行降序排序
	    System.out.println("------------map按照value降序排序--------------------");
	    for (Map.Entry<String, Integer> entry : list) {
	    	String detail = entry.getKey().toString();
	    	String[] fileInfo = detail.split(" ");
	    	sql = "SELECT ATTACHMENT.DESCRIPTION, FILES.FILENAME FROM ATTACHMENT INNER JOIN  ATTACHMENT_MAP ON ATTACHMENT_MAP.ATTACH_ID = ATTACHMENT.ID INNER JOIN FILES ON FILES.ID = ATTACHMENT_MAP.FILE_ID WHERE ATTACHMENT.ATTACHMENT_NUMBER = '"+fileInfo[0]+"'";
	    	Statement stat3 = conn.createStatement();
			ResultSet rs3 = stat3.executeQuery(sql);
			while (rs3.next()) {
				String description = rs3.getString("DESCRIPTION").toString();
				String filename = rs3.getString("FILENAME").toString();
				String[] filetype = filename.split(".");
				if(filetype[0].equals("doc")) {
					
				}
				else if (filetype[0].equals("xlms")){
					
				}				
				finalSuggestion.add(filename);
				finalSuggestion.add(description);
				finalSuggestion.add(entry.getValue().toString());
			}
	    
	    	//System.out.println(entry.getKey() + ":" + entry.getValue());
	    }
	  
		
		/*
		sqlResult = executeSQL(conn, sql);
		for(Object relatedFiletData : sqlResult){	
			sql = "SELECT DETAILS  FROM ITEM_HISTORY  WHERE ACTION = 15 AND ";
			System.out.println("☆☆"+folder[0]);
		}
		*/
		return finalSuggestion;
	}

}
