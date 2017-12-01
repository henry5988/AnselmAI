import static com.HF.executeSQL;
import static com.HF.getConnection;
import static com.HF.getWordInParen;
import static com.HF.removeDup;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.ItemConstants;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ItemSuggestion implements Constants{

  protected List getItemSuggestion(IAgileSession session, IItem item) throws SQLException, APIException {
    String itemNum;
    List userNameSet = new LinkedList<String>();
    List items = new LinkedList();
    Connection conn = getConnection(USERNAME, PASSWORD, URL);
    itemNum = item.getName();
    String sql =
        "SELECT USER_NAME FROM ITEM_HISTORY where ITEM = '" + itemNum + "' ORDER　BY TIMESTAMP DESC";
    List userSet = executeSQL(conn, sql);
    userSet = removeDup((LinkedList) userSet);
    while(!userSet.isEmpty()){
      userNameSet.add(getWordInParen((String) ((LinkedList) userSet).peek()));
      sql = "SELECT * FROM (SELECT ITEM FROM ITEM_HISTORY where USER_NAME = '" + ((LinkedList) userSet).pop()
          + "' ORDER BY TIMESTAMP DESC) WHERE ROWNUM <= 10";
      List recentlyVisited = executeSQL(conn, sql);
      recentlyVisited = removeDup(recentlyVisited);
      while(!recentlyVisited.isEmpty()){
        Integer id = Integer.parseInt((String) ((LinkedList) recentlyVisited).pop());
        item = (IItem) session.getObject(ItemConstants.CLASS_ITEM_BASE_CLASS, id);
        items.add(item);
        if(items.contains(null)){
          removeNull(items);
        }
      }
      while(items.size() < 3) items.add(item);
    }
    return items;
  }

}
