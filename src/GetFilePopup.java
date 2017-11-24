import static com.HF.countOccurance;
import static com.HF.executeSQL;
import static com.HF.extractTop;
import static com.HF.getConnection;
import static com.HF.getWordInParen;
import static com.HF.out;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GetFilePopup extends FileSuggestionPopup {

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
      lists.addAll(getAttachmentAdvice(conn, files[i], eventName, session)); // gets file list that contains filename and viewer count
    }
    if (lists.contains(null)) {
      out("Culling null items...");
      removeNull(lists);
    }
    while (lists.size() < 3) {
      //TODO work-around for if the list has fewer than 3 items, need to design a way to display nothing
      out("list size: " + lists.size() + "; list fewer than 3 items...");
      lists.add((IItem) info.getDataObject());
    }

    return lists;
  }

  private List getAttachmentAdvice(Connection conn, IEventDirtyFile file, String eventName,
      IAgileSession session)
      throws APIException, SQLException {
    Map viewerCounts = new HashMap();
    List visitCount = new LinkedList();
    StringParser sp = new StringParser();
    out("getAttachmentAdvice begin...");
    IEventDirtyFile downloaded = file; // file is the file that was downloaded
    String folderNum = downloaded.getFileFolder().getName();
    out("Folder name: " + folderNum);
    Integer[] folderVers = (Integer[]) downloaded.getFileFolder().getVersions();
    out("File row: " + folderVers[folderVers.length - 1].toString());
    Integer folderVer = folderVers[folderVers.length - 1];
    out("Folder Version: " + folderVer.toString());
    String DBDetails = sp.buildDetails(folderNum, folderVer.toString(), downloaded.getFilename());
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
          + "' AND ACTION = 15 ORDER BY TIMESTAMP DESC";
      List relevantFiles = executeSQL(conn, sql, true);
      advices.addAll(relevantFiles);
      out("advices: " + advices.toString());
    }
    List attAdvices = new LinkedList();
    for (Object detail : advices) {
      detail = sp.getDetailsFileName((String) detail);
      attAdvices.add(detail);
    }
    for ( Object fileName : attAdvices) {
      if(!viewerCounts.containsKey(fileName)){
        viewerCounts.put(file, countOccurance(attAdvices, fileName, 0));
      }
    }
    List topViewerCounts = extractTop(viewerCounts, 3);
    attAdvices = topViewerCounts;
    return attAdvices;
  }

}
