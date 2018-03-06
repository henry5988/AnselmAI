import static com.HF.countOccurance;
import static com.HF.executeSQL;
import static com.HF.extractTop;
import static com.HF.getConnection;
import static com.HF.getWordInParen;
import static com.HF.out;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.FolderConstants;
import com.agile.api.IAgileSession;
import com.agile.api.IAttachmentFile;
import com.agile.api.IFileFolder;
import com.agile.api.IFolder;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.ITable;
import com.agile.api.IUser;
import com.agile.api.ItemConstants;
import com.agile.api.UserConstants;
import com.agile.px.EventActionResult;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class FileSuggestionPopup extends SuggestionPopup {

  static String fileEventName;
  static String targetFile;

  public static String getTargetFile() {
    return targetFile;
  }

  public static void setTargetFile(String targetFile) {
    FileSuggestionPopup.targetFile = targetFile;
  }

  public static String getFileEventName() {
    return fileEventName;
  }

  public static void setFileEventName(String fileEventName) {
    FileSuggestionPopup.fileEventName = fileEventName;
  }

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req) {
    try {
      //session = connect();
      Connection conn = null;
      conn = getConnection(USERNAME, PASSWORD, URL);
      IFileEventInfo info = (IFileEventInfo) req;
      IItem obj = (IItem) info.getDataObject();
      conn.close();
      LinkedList list = getItemAdvice(session, obj, info);
      out("List size: " + list.size());
      out("List: " + list.toString());
      List<List<String>> infoList = convertObjectToInfo(list);
      if (list.size() < 3) {
        out("list has fewer than 3 items, does nothing");
        while(((List) infoList.get(1)).size() < 3){
          infoList.get(1).add("n/a");
          infoList.get(2).add(NOPATH);
          infoList.get(3).add("n/a");
          infoList.get(4).add("n/a");
        }
      }
      out("convert Object to String info...");
      String fileName = getDownloadedFileName(info);
      //p.frame(session, infoList, fileName);
    } catch (SQLException | APIException | ClassNotFoundException e) {
      out("Error occured", "err");
      e.getMessage();
      e.printStackTrace();
    }
    out("JFrame info printed");
    return null;
  }

  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IItem obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {

    IFileEventInfo info = (IFileEventInfo) req;
    LinkedList lists = new LinkedList();
    String eventName = "Get File";
    setFileEventName(eventName);
    out("Getting attachment file advice...");


	    Connection conn = getConnection(USERNAME, PASSWORD, URL);
		Connection conn2 = getConnection(USERNAME2, PASSWORD2, URL2);
	  //out("Getting " + obj.toString() + "'s attachment table");
	  //out("Getting files that was downloaded...");
	    IEventDirtyFile[] files = info.getFiles();
	    for (int i = 0; i < files.length; i++) {
   //   out("New dirty file");
   //   out("Getting related file from " + files[i].getFilename());
	    	setTargetFile(files[i].getFilename());
	    	lists.addAll(getAttachmentAdvice(conn,conn2, files[i], session,obj)); // gets file list that contains filename and viewer count
	    }
	    if (lists.contains(null)) {
	    	removeNull(lists);
	    }
	    return lists;
  }


  protected List getAttachmentAdvice(Connection conn, Connection conn2, IEventDirtyFile file,
  IAgileSession session, IItem item) throws APIException, SQLException{
	  Map viewerCounts = new HashMap();
	  StringParser sp = new StringParser();
	  //  out("getAttachmentAdvice begin...");
	  //  out("action code: " + getActionCode());
	  IEventDirtyFile downloaded = file; // file is the file that was downloaded
	  String folderNum = downloaded.getFileFolder().getName();
	  //  out("Folder name: " + folderNum);
	  Integer[] folderVers = (Integer[]) downloaded.getFileFolder().getVersions();
	  //  out("File row: " + folderVers[folderVers.length - 1].toString());
	  Integer folderVer = folderVers[folderVers.length - 1];
	  //  out("Folder Version: " + folderVer.toString());
	  LinkedList advices = new LinkedList(); // declare and instantiate advices
	  // select all users who have downloaded the same version of the file
	  String sql_findItem =
            "SELECT ITEM_HISTORY.ITEM FROM ITEM_HISTORY,ITEM "+
            "WHERE ITEM_HISTORY.DETAILS LIKE '%" + downloaded.getFilename()+ "' "+
	        "AND ITEM.ITEM_NUMBER ='"+item.toString()+"' "+
            "AND ITEM_HISTORY.ITEM = ITEM.ID";
      LinkedList ItemNum = executeSQL(conn, sql_findItem, true);
 
      String sql =
    		 "SELECT ITEM_HISTORY.USER_NAME, ITEM_HISTORY.ITEM FROM ITEM_HISTORY , ITEM "+
    		 "WHERE ITEM_HISTORY.DETAILS LIKE '%" + downloaded.getFilename()+"' "+
    		 "AND ITEM.SUBCLASS =(SELECT ITEM.SUBCLASS FROM ITEM WHERE ITEM.ITEM_NUMBER ='"+item.toString()+"') "+
             "ORDER BY TIMESTAMP DESC";

      LinkedList userSet = executeSQL(conn, sql, true);
      
      sql = "SELECT SUBCLASS FROM ITEM WHERE ITEM_NUMBER='"+item.toString()+"'";
      List subclass = executeSQL(conn, sql, true);
      
      sql = "SELECT FILENAME FROM AI "+
        		"WHERE ORIGINALFILE = '" + folderNum+ "' ";
       
      List clickedFile = executeSQL(conn2, sql, true);
	
      sql = "SELECT POINTS FROM AI "+
	  		"WHERE ORIGINALFILE = '" + folderNum+ "' ";
	 
      List points = executeSQL(conn2, sql, true);

	      
      LinkedList userNames = new LinkedList();
      
      for (Object user : userSet) {
    	  userNames.add(getWordInParen((String) user));
    	  if(userNames.getLast().equals(session.getCurrentUser().getName())){
    		  userNames.removeLast();
    		  continue;
    	  }
    	
		  sql = "SELECT DETAILS FROM ITEM_HISTORY "+
		    	"WHERE USER_NAME = '" + user+ "' "+
		    	"AND ACTION = " + getActionCode() + 
		    	" AND ITEM IN (SELECT ITEM.ID FROM ITEM WHERE ITEM.SUBCLASS = '"+subclass.get(0).toString()+"') "+
		    	" ORDER BY TIMESTAMP DESC";
	
		  List relevantFiles = executeSQL(conn, sql, true);
	      
	      relevantFiles = filterRelevantFiles(relevantFiles, session, conn);
	      advices.addAll(relevantFiles);
	
	    //  out("advices: " + advices.toString());
      }
      List attAdvices;
      for (Object detail : advices) {
   //   out("Getting file name from action details...");
    	  if(!viewerCounts.containsKey(detail)){
    		  viewerCounts.put(detail, Collections.frequency(advices, detail));
    	  }
    //  out("file name and frequency added");
      }
   
      for(int i=0; i<clickedFile.size();i++) {
    	  
    	int clicktime =   (int) viewerCounts.get(clickedFile.get(i));
    	clicktime +=  Integer.valueOf((String) points.get(i));
    	
    	viewerCounts.put(clickedFile.get(i), clicktime);
      }
     out("******"+viewerCounts);
      List topViewerCounts = extractTop(viewerCounts, 3);
      attAdvices = topViewerCounts;
 //   out("getAttachmentAdvice ends...");
      conn.close();
      return attAdvices;
  }

  @Override
  protected List<List<String>> convertObjectToInfo(List l)
      throws APIException {
    // convert attachment file object to printed info
    // need file name, description, image, and viewer count
    // list could contain map entries, which would need to be access for keys to get the file names
    // both image and viewer count need their own private algorithm to get
    // image needs to grab the suffix of the file name and find the corresponding file image
    // viewer count needs a aggregate function to add up all relevant views
    Connection conn;
    StringParser sp = new StringParser();
    List info = new LinkedList();
    List images = new LinkedList();
    List descriptions = new LinkedList();
    List names = new LinkedList();
    List folders = new LinkedList();
    List viewerCounts = new LinkedList();
    List wholename = new LinkedList();
    List list = new LinkedList();
  //  out("GetFilePopup.convertObjectToInfo()...");
    for (Object entry : l) {
      Map.Entry e = (Entry) entry;
      viewerCounts.add(e.getValue().toString());
      list.add(e.getKey());
    }

 //   out("List to convert: " + list.toString());

    try {
      conn = getConnection(USERNAME, PASSWORD, URL);
   //   out("FileSuggestionPopup.convertObjectToInfo()...");
      for (Object f : list) {
        String file = sp.getDetailsFileName((String) f);
        String folder = sp.getDetailsFolderName((String) f);
    //    out("File: " + file);
        String sql =
            "SELECT Attachment.DESCRIPTION FROM ATTACHMENT where ATTACHMENT_NUMBER ='"
                + folder + "'";
        String description = (String) executeSQL(conn, sql).pop();
        String imageSrc = getImageSrc(file);
        folders.add(folder);
        names.add(file);
        descriptions.add(description);
        images.add(imageSrc);
        
     //   out("converted file: " + file);
      }
      wholename = list;
      // the order in which the lists are added does matter as they are being taken out, in the parent class, according to their indices
      info.add(folders);
      info.add(names);
      info.add(images);
      info.add(descriptions);
      info.add(viewerCounts);
      info.add(wholename);
   //   out("GetFilePopup.convertObjectToInfo() ends...");
      conn.close();
    } catch (SQLException e) {
   //   out("Error when establishing database connection", "err");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
   // out("FileSuggestionPopup.convertObjectToInfo() ends...");
    return (List<List<String>>) info;
  }

  protected String getImageSrc(String file) {
    String imageType = file.substring(file.indexOf('.') + 1, file.length());
    ImagePath ip = new ImagePath(LOCALPATH);
    return ip.getImagePath(imageType);
  }

  protected Integer getViewerCount(List fileList, String file){
    return countOccurance(fileList, file, 0);
  }

  protected boolean checkEventType(IEventInfo req, int eventType, String actionCode){
    try {
   //   out("Event type: " + req.getEventType());
      setActionCode(String.valueOf(actionCode));
      if(req.getEventType() == eventType)
        return true;
    } catch (APIException e) {
      e.printStackTrace();
    }
    return false;
  }

  protected boolean checkFilePrivilege(IAgileSession session, Object item) throws APIException {
    IUser user = session.getCurrentUser();
    IItem thisItem = (IItem) item;
  //  out("current user is " + user.getName());

   // out("Privilege read");
    return user.hasPrivilege(UserConstants.PRIV_READ, item, true);
  }

  private List filterRelevantFiles(List relevantFiles, IAgileSession session, Connection conn)
      throws SQLException, APIException {
    List filteredFiles = new LinkedList();
    int index = 0;
    for (Object f : relevantFiles) {
   //   out("iterating through files...");
      StringParser sp = new StringParser();
      if(sp.getDetailsFileName(f.toString()).equals(getTargetFile())) continue;
   //   out("file being processed: " + sp.getDetailsFolderName(f.toString()));
      String sql =
          "SELECT ATTACHMENT_MAP.ID FROM ATTACHMENT_MAP JOIN ATTACHMENT ON ATTACHMENT_MAP.ATTACH_ID=ATTACHMENT.ID WHERE ATTACHMENT.ATTACHMENT_NUMBER = '"
              + sp.getDetailsFolderName(f.toString()) + "'";
      List parentIDs = executeSQL(conn, sql, false);
    //  out("ParentID size: " + parentIDs.size());
      if (parentIDs.size() == 0 || parentIDs.get(0).toString().equals("704")){
        continue;
      }
    //  out("scanning proceeds...");
      Integer parentIDNum = Integer.parseInt((String) parentIDs.get(0));
      IItem item = (IItem) session.getObject(ItemConstants.CLASS_ITEM_BASE_CLASS, parentIDNum);
      if (checkFilePrivilege(session, item)) {
    //    out(session.getCurrentUser().getName() +" has privilege to " + item.getName());
        index++;
        filteredFiles.add(f);
      } else {
     //   out(session.getCurrentUser().getName() + " has no privilege to " + item.getName());
        parentIDs.remove(0);
     //   out("relevantFiles length after removal of non-privileged item: " + parentIDs.size());
      }
    }
    return filteredFiles;
  }

  protected class StringParser {

    String buildDetails(String folderNum, String folderVer, String fileName) {
      return folderNum + " V " + folderVer + " " + fileName;
    }

    String getDetailsFileName(String details) {
      return details.replaceAll("^(\\S*\\s){3}", "");
    }

    String getDetailsFolderName(String details){
      return details.substring(0, details.indexOf(' '));
    }

  }

  private String getDownloadedFileName(IFileEventInfo info) throws APIException {
    IEventDirtyFile[] files = info.getFiles();
    return files[0].getFilename();
  }

}
