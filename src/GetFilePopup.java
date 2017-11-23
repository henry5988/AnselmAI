import static com.HF.executeSQL;
import static com.HF.getConnection;
import static com.HF.out;
import static com.HF.removeDup;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.IAgileObject;
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
  protected LinkedList<LinkedList<String>> convertObjectToInfo(LinkedList<IAgileObject> list)
      throws APIException {
    return UpdateTitleBlockPopup.convertObjectToInfo(list);
  }

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
      out("Getting related file from " + files[i].getFilename());
      IAttachmentFile file = (IAttachmentFile) files[i];
      lists.add(getAttachmentAdvice(conn, (IItem) info.getDataObject(), eventName, session));
    }
    if(lists.contains(null)){
      out("Culling null items...");
      removeNull(lists);
    }
    while(lists.size() < 3){
      //TODO work-around for if the list has fewer than 3 items, need to design a way to display nothing
      lists.add((IItem)info.getDataObject());
    }

    return lists;
  }

  private LinkedList getAttachmentAdvice(Connection conn, IItem item, String eventName,
      IAgileSession session)
      throws APIException, SQLException {
    LinkedList advices = new LinkedList();
    String sql = "SELECT OWNER FROM EVENT_HISTORY where EVENT_NAME = '" + eventName + "' AND OBJECT_NUMBER = " + item.getObjectId();
    LinkedList userNameSet = executeSQL(conn, sql);
    userNameSet = removeDup(userNameSet);
    while(!userNameSet.isEmpty()){
      out("Scanning for user file download history...");
      sql = "SELECT * FROM(SELECT OBJECT_NUMBER FROM EVENT_HISTORY where OWNER =" + userNameSet.pop()
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
