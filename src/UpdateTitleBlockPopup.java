import static com.HF.executeSQL;
import static com.HF.getConnection;
import static com.HF.getWordInParen;
import static com.HF.out;
import static com.HF.removeDup;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IDataObject;
import com.agile.api.IItem;
import com.agile.api.IUser;
import com.agile.api.ItemConstants;
import com.agile.api.UserConstants;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;
import com.agile.px.IUpdateEventInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;


public class UpdateTitleBlockPopup extends SuggestionPopup {


  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IItem obj, IFileEventInfo info)
      throws SQLException, APIException {
    // Tells what documents people have looked at this document
    // get connection
    Connection conn = null;
    try {
      conn = getConnection(USERNAME, PASSWORD, URL);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    out("Executing SQL statement...");
    //String sql = "SELECT ITEM.ITEM_NUMBER from ITEM where ITEM.CLASS=9000";
    IItem item = obj;
    String itemNum = item.getObjectId().toString();
    LinkedList<IItem> items = new LinkedList();
    // select who have viewed the doc from database recently
    out("item ID: " + itemNum);
    String sql =
        "SELECT USER_NAME FROM ITEM_HISTORY where ITEM = '" + itemNum + "' ORDER　BY TIMESTAMP DESC";
    LinkedList<String> userNameSet = executeSQL(conn, sql);
    LinkedList<String> uniqueUserSet = removeDup(userNameSet);
    LinkedList<String> userSet = new LinkedList<String>();
    while (uniqueUserSet.isEmpty() == false) {
      out("scanning user item history for " + uniqueUserSet.peek());
      userSet.add(getWordInParen(uniqueUserSet.peek()));
      sql = "SELECT * FROM (SELECT ITEM FROM ITEM_HISTORY where USER_NAME = '" + uniqueUserSet.pop()
          + "' ORDER BY TIMESTAMP DESC) WHERE ROWNUM <= 10";
      LinkedList<String> recentlyVisited = executeSQL(conn, sql);
      recentlyVisited = removeDup(recentlyVisited);
      out("Recently visited items id: " + recentlyVisited);
      while (recentlyVisited.isEmpty() == false) {
        Integer id = Integer.parseInt(recentlyVisited.pop());
        item = (IItem) session.getObject(ItemConstants.CLASS_ITEM_BASE_CLASS, id);
        items.add(item);
        if (items.contains(null)) {
          out("Culling null items");
          removeNull(items);
        }
        out("Got object: " + items.peekLast().getName());
      }
      while (items.size() < 3) {
        items.add(item);
      }
    }
    out("Got recently visited items!");
    return items;
  }
}