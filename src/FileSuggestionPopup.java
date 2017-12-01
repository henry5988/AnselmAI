import static com.HF.countOccurance;
import static com.HF.executeSQL;
import static com.HF.extractTop;
import static com.HF.getConnection;
import static com.HF.getWordInParen;
import static com.HF.out;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IFileFolder;
import com.agile.api.IItem;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;
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

  public static String getFileEventName() {
    return fileEventName;
  }

  public static void setFileEventName(String fileEventName) {
    FileSuggestionPopup.fileEventName = fileEventName;
  }


  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IEventInfo req)
      throws SQLException, APIException{
    LinkedList lists = new LinkedList();
    String eventName = "Get File";
    setFileEventName(eventName);
    out("Getting attachment file advice...");
    Connection conn = getConnection(USERNAME, PASSWORD, URL);
    IFileEventInfo info = (IFileEventInfo) req;
    IItem obj = (IItem) info.getDataObject();
    out("Getting " + obj.toString() + "'s attachment table");
    ITable attachments = obj.getTable(ItemConstants.TABLE_ATTACHMENTS);
    out("Getting files that was downloaded...");
    IEventDirtyFile[] files = info.getFiles();
    for (int i = 0; i < files.length; i++) {
      out("New dirty file");
      out("Getting related file from " + files[i].getFilename());
      lists.addAll(getAttachmentAdvice(conn, files[i], session)); // gets file list that contains filename and viewer count
    }
    if (lists.contains(null)) {
      out("Culling null items...");
      removeNull(lists);
    }
    return lists;
  }

  protected List getAttachmentAdvice(Connection conn, IEventDirtyFile file,
       IAgileSession session) throws APIException, SQLException{
    Map viewerCounts = new HashMap();
    StringParser sp = new StringParser();
    out("getAttachmentAdvice begin...");
    out("action code: " + getActionCode());
    IEventDirtyFile downloaded = file; // file is the file that was downloaded
    String folderNum = downloaded.getFileFolder().getName();
    out("Folder name: " + folderNum);
    Integer[] folderVers = (Integer[]) downloaded.getFileFolder().getVersions();
    out("File row: " + folderVers[folderVers.length - 1].toString());
    Integer folderVer = folderVers[folderVers.length - 1];
    out("Folder Version: " + folderVer.toString());
    LinkedList advices = new LinkedList(); // declare and instantiate advices
    // select all users who have downloaded the same version of the file
    String sql =
        "SELECT USER_NAME FROM ITEM_HISTORY WHERE DETAILS LIKE '%" + downloaded.getFilename()
            + "' ORDER BY TIMESTAMP DESC";
    LinkedList userSet = executeSQL(conn, sql, true);
    LinkedList userNames = new LinkedList();
    for (Object user : userSet) {
      userNames.add(getWordInParen((String) user));
      sql = "SELECT DETAILS FROM ITEM_HISTORY WHERE USER_NAME = '" + user
          + "' AND ACTION = " + getActionCode() + " ORDER BY TIMESTAMP DESC";
      List relevantFiles = executeSQL(conn, sql, true);
      advices.addAll(relevantFiles);
      out("advices: " + advices.toString());
    }
    List attAdvices;
    for (Object detail : advices) {
      out("Getting file name from action details...");
      if(!viewerCounts.containsKey(detail)){
        viewerCounts.put(detail, Collections.frequency(advices, detail));
      }
      out("file name and frequency added");
    }
    List topViewerCounts = extractTop(viewerCounts, 3);
    attAdvices = topViewerCounts;
    out("getAttachmentAdvice ends...");
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
    List list = new LinkedList();
    out("GetFilePopup.convertObjectToInfo()...");
    for (Object entry : l) {
      Map.Entry e = (Entry) entry;
      viewerCounts.add(e.getValue().toString());
      list.add(e.getKey());
    }
    out("List to convert: " + list.toString());

    try {
      conn = getConnection(USERNAME, PASSWORD, URL);
      out("FileSuggestionPopup.convertObjectToInfo()...");
      for (Object f : list) {
        String file = sp.getDetailsFileName((String) f);
        String folder = sp.getDetailsFolderName((String) f);
        out("File: " + file);
        String sql =
            "SELECT Attachment.DESCRIPTION FROM ATTACHMENT join bo_attach_versions_history on attachment.id = bo_attach_versions_history.attach_id join files on bo_attach_versions_history.file_id = files.id and files.FILENAME = '"
                + file + "'";
        String description = (String) executeSQL(conn, sql).pop();
        String imageSrc = getImageSrc(file);
        folders.add(folder);
        names.add(file);
        descriptions.add(description);
        images.add(imageSrc);
        out("converted file: " + file);
      }
      // the order in which the lists are added does matter as they are being taken out, in the parent class, according to their indices
      info.add(folders);
      info.add(names);
      info.add(images);
      info.add(descriptions);
      info.add(viewerCounts);
      out("GetFilePopup.convertObjectToInfo() ends...");
      conn.close();
    } catch (SQLException e) {
      out("Error when establishing database connection", "err");
    }
    out("FileSuggestionPopup.convertObjectToInfo() ends...");
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
      out("Event type: " + req.getEventType());
      setActionCode(String.valueOf(actionCode));
      if(req.getEventType() == eventType)
        return true;
    } catch (APIException e) {
      e.printStackTrace();
    }
    return false;
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
}
