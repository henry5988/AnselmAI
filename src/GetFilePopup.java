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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		Map<String, Integer> getFileDetail  = new HashMap();
		IUser user = session.getCurrentUser();
		IFileEventInfo info = (IFileEventInfo) req;
		IEventDirtyFile[] files = info.getFiles();
		IDataObject ob = info.getDataObject();
		IItem itm = (IItem)session.getObject(IItem.OBJECT_TYPE,  ob.getName());
		Connection conn = getConnection(USERNAME, PASSWORD, URL);
		sql = "SELECT USER_NAME,to_char(to_date(TIMESTAMP,'DD-MON-YY'),'DD-MM-YY') as DATETIME  FROM ITEM_HISTORY   WHERE ACTION = 15 AND ITEM IN (SELECT ID FROM ITEM WHERE ITEM_NUMBER = '"+itm.getName()+"')AND DETAILS LIKE '%"+files[0].getFilename()+"%' AND USER_NAME != '"+user.toString()+"' GROUP BY USER_NAME,to_char(to_date(TIMESTAMP,'DD-MON-YY'),'DD-MM-YY')";	

		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		
		while (rs.next()) {
			String userName = rs.getString("USER_NAME").toString();
			String dateTime = rs.getString("DATETIME").toString();
			sql = "SELECT DETAILS  FROM ITEM_HISTORY   WHERE ACTION = 15 AND USER_NAME = '"+userName+"' AND DETAILS NOT LIKE '%"+files[0].getFilename()+"%' AND TIMESTAMP >= TO_DATE('"+dateTime+"', 'DD-MM-YY')-7 AND TIMESTAMP <= TO_DATE('"+dateTime+"', 'DD-MM-YY')+7";
			
			Statement stat2 = conn.createStatement();
			ResultSet rs2 = stat2.executeQuery(sql);
			System.out.println("☆☆☆☆☆"+sql);
			while (rs2.next()) {
				String detail = rs2.getString("DETAILS").toString();
				  if(getFileDetail.containsKey(detail)){
				      Integer v = (Integer) getFileDetail.get(detail);
				      getFileDetail.remove(detail);
				      getFileDetail.put(detail, v+1);
				    }else{
				    	getFileDetail.put(detail, 1);
				    }
			}
			//System.out.println(userName+"   "+dateTime);
		}
		   for (Map.Entry entry : getFileDetail.entrySet()) {
	            System.out.println("Key-value : " + entry.getKey() + "- "
	                    + entry.getValue());
	        }
		
		/*
		sqlResult = executeSQL(conn, sql);
		for(Object relatedFiletData : sqlResult){	
			sql = "SELECT DETAILS  FROM ITEM_HISTORY  WHERE ACTION = 15 AND ";
			System.out.println("☆☆"+folder[0]);
		}
		*/
		return null;
	}

}
