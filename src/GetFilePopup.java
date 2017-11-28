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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GetFilePopup extends FileSuggestionPopup {


  @Override
  protected List<List<String>> convertObjectToInfo(List list)
      throws APIException {
    List info;
    List viewerCounts = new LinkedList();
    List objects = new LinkedList();
    out("GetFilePopup.convertObjectToInfo()...");
    for (Object entry : list) {
      Map.Entry e = (Entry) entry;
      viewerCounts.add(e.getValue().toString());
      objects.add(e.getKey());
    }
    out("List to convert: " + objects.toString());
    info = super.convertObjectToInfo(objects);
    info.add(viewerCounts);
    out("GetFilePopup.convertObjectToInfo() ends...");
    return info;
  }


  protected List getAttachmentAdvice(Connection conn, IEventDirtyFile file, String eventName,
      IAgileSession session)
      throws APIException, SQLException {
    Map viewerCounts = new HashMap();
    StringParser sp = new StringParser();
    out("getAttachmentAdvice begin...");
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
          + "' AND ACTION = 15 ORDER BY TIMESTAMP DESC";
      List relevantFiles = executeSQL(conn, sql, true);
      advices.addAll(relevantFiles);
      out("advices: " + advices.toString());
    }
    List attAdvices = new LinkedList();
    for (Object detail : advices) {
      out("Getting file name from action details...");
      String fileName = sp.getDetailsFileName((String) detail);
      if(!viewerCounts.containsKey(fileName)){
        viewerCounts.put(fileName, Collections.frequency(advices, detail));
      }
      out("file name and frequency added");
    }
    List topViewerCounts = extractTop(viewerCounts, 3);
    attAdvices = topViewerCounts;
    out("getAttachmentAdvice ends...");
    return attAdvices;
  }

}
