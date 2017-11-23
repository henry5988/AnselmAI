import static com.HF.executeSQL;
import static com.HF.getConnection;
import static com.HF.getWordInParen;
import static com.HF.out;
import static com.HF.removeDup;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IAttachmentFile;
import com.agile.api.IItem;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;
import com.agile.px.IEventAction;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class GetFilePopup extends SuggestionPopup implements IEventAction {

  private final static String GETFILEEVENTNAME = "Get File";

  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IEventInfo req)
      throws SQLException, APIException {
    LinkedList lists = new LinkedList();
    String eventName = GETFILEEVENTNAME;
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
      lists.add(getAttachmentAdvice(conn, file[i], eventName, session));
    }
    if(lists.contains(null)){
      out("Culling null items...");
      removeNull(lists);
    }
    while(lists.size() < 3){
      //TODO work-around for if the list has fewer than 3 items, need to design a way to display nothing
      out("list fewer than 3 items...");
      lists.add((IItem)info.getDataObject());
    }

    return lists;
  }

  private LinkedList getAttachmentAdvice(Connection conn, IEventDirtyFile file, String eventName,
      IAgileSession session)
      throws APIException, SQLException {
    out("getAttachmentAdvice begin...");
    IEventDirtyFile downloaded = file; // file is the file that was downloaded
    String folderNum = downloaded.getFileFolder().getName();
    out("Folder name: " + folderNum);
    Integer[] folderVers = (Integer[]) downloaded.getFileFolder().getVersions();
    Integer folderVer = folderVers[Integer.parseInt(downloaded.getRowId().toString())];
    out("Folder Version: " + folderVer.toString());
    String DBDetails = folderNum + " V " + folderVer + " " + downloaded.getFilename();
    LinkedList advices = new LinkedList(); // declare and instantiate advices
    // select all users who have downloaded the same version of the file
    String sql = "SELECT USER_NAME FROM ITEM_HISTORY WHERE DETAILS = " + DBDetails + " ORDER BY TIMESTAMP DESC";
    LinkedList userSet = executeSQL(conn, sql);
    userSet = removeDup(userSet);
    LinkedList userNames = new LinkedList();
    for (Object user : userSet) {
      userNames.add(getWordInParen((String) user));
    }
    while(!userNames.isEmpty()){
      out("Scanning for user file download history...");
      sql = "SELECT * FROM(SELECT OBJECT_NUMBER FROM EVENT_HISTORY where OWNER =" + userNames.pop()
          + "AND EVENT_NAME = '" + eventName + "' Order by START_TIMESTAMP DESC) WHERE ROWNUM <= 10";
      LinkedList suggestedItems = executeSQL(conn, sql);
      suggestedItems = removeDup(suggestedItems);
      while(!suggestedItems.isEmpty()){
        item = (IItem) session.getObject(ItemConstants.CLASS_ITEM_BASE_CLASS, suggestedItems.pop());
        advices.add(item);
      }
    }
    return advices;
  }
}
