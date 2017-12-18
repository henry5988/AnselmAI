import static com.HF.executeSQL;
import static com.HF.extractTop;
import static com.HF.getConnection;
import static com.HF.getWordInParen;
import static com.HF.out;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.ItemConstants;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ItemSuggestion implements Constants {

  protected List getItemSuggestion(IAgileSession session, IItem item)
      throws SQLException, APIException, ClassNotFoundException {
    String itemNum;
    List placeholder = new LinkedList();
    List userNameSet = new LinkedList<String>();
    List items = new LinkedList();
    Map visitedItems = new HashMap<IItem, Integer>();
    List topItems = new LinkedList();
    Connection conn = getConnection(USERNAME, PASSWORD, URL);
    itemNum = item.getObjectId().toString();
    String sql =
        "SELECT USER_NAME FROM ITEM_HISTORY where ITEM = '" + itemNum + "'";
    List userSet = executeSQL(conn, sql, true);
    while (!userSet.isEmpty()) {
      userNameSet.add(getWordInParen((String) ((LinkedList) userSet).peek()));
      out("here");
      sql = "SELECT * FROM (SELECT ITEM FROM ITEM_HISTORY where USER_NAME = '"
          + ((LinkedList) userSet).pop()
          + "' ORDER BY TIMESTAMP DESC) WHERE ROWNUM <= 100";
      List recentlyVisited = executeSQL(conn, sql, true);
      while (!recentlyVisited.isEmpty()) {
        Integer id = Integer.parseInt((String) ((LinkedList) recentlyVisited).pop());
        item = (IItem) session.getObject(ItemConstants.CLASS_ITEM_BASE_CLASS, id);
        items.add(item);

        if (items.contains(null)) {
          removeNull((LinkedList) items);
        }
      }
    }
    for (Object o : items) {
      if (!visitedItems.containsKey((IItem) o)) {
        visitedItems.put(o, 1);
      } else {
        Integer oldValue = (Integer) visitedItems.get(o);
        Integer newValue = oldValue + 1;
        visitedItems.replace(o, oldValue, newValue);
      }
    }
    topItems = extractTop(visitedItems, 3);
    for (Object o : visitedItems.entrySet()) {
      while (placeholder.size() < 3) {
        placeholder.add(o);
      }
    }
    out("top item size: " + topItems.size());
    out("place holder size: " + placeholder.size());
    return topItems.size() == 3 ? topItems : placeholder;
  }

  protected List convertItemsToInfo(List l) throws APIException {
    ImagePath ip = new ImagePath(URLPATH);
    List infoList = new LinkedList<LinkedList>();
    List names = new LinkedList();
    List descriptions = new LinkedList();
    List images = new LinkedList();
    List viewerCounts = new LinkedList();

    for (Object e : l) {
      Entry entry = (Entry) e;
      IItem item = (IItem) entry.getKey();
      Integer viewerCount = (Integer) entry.getValue();
      viewerCounts.add(viewerCount);
      names.add(item.getName());
      descriptions.add(item.getValue(ItemConstants.ATT_TITLE_BLOCK_DESCRIPTION));
      images.add(ip.getImagePath(item));
    }

    infoList.add(IMAGEPRINTSQ, images);
    infoList.add(NAMEPRINTSQ, names);
    infoList.add(DESCRIPTIONPRINTSQ, descriptions);
    infoList.add(VIEWERPRINTSQ, viewerCounts);

    return infoList;
  }


}
