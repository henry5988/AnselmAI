import static com.HF.countOccurance;
import static com.HF.executeSQL;
import static com.HF.extractTop;
import static com.HF.getConnection;

import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IDataObject;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;
import com.agile.api.TableTypeConstants;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import com.agile.px.IUpdateTableEventInfo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BOMPopup extends SuggestionPopup {

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    System.out.println("BOMPopup()...");
    setTest(true);
    setFieldCheck(true);
    setSession(session);
    setEventInfo(req);
    setOutput_path("C:\\serverSource\\bomPopup.txt");
    init(getSession(), getEventInfo(), getOutput_path(), isFieldCheck(), isTest());
    return super.doAction(session, node, req);

  }

  @Override
  protected String checksField() {
    String resultString = "Result: ";
    IEventInfo event = getEventInfo();
    IUpdateTableEventInfo info = (IUpdateTableEventInfo) event;
    try {
      IAgileSession session = getSession();
      IItem item = (IItem) session.getObject(ItemConstants.CLASS_PARTS_CLASS, info.getDataObject());
      ITable table = item.getTable(ItemConstants.TABLE_BOM);
      Iterator it = table.iterator();
      IRow row = (IRow) it.next();
      int cost = (int) row.getValue(2000019494); // base number for cost column in BOM table
      if(cost <= 0) resultString += "cost of " + row.getReferent().getName() + " is not valid.";
      if(resultString.equals("Result: ")) return "Column fields are correct.";
    } catch (APIException e) {
      System.err.println(e.getMessage());
    }
    return resultString;
  }

  @Override
  protected void writeToFile(List<List<String>> infoList) throws IOException {
    File f = new File(getOutput_path());
    File exist = new File(EXIST);
    if(!exist.exists()){
      Files.createDirectories(Paths.get(exist.getPath()).getParent());
      exist.createNewFile();
    }
    if(!f.exists()){
      Files.createDirectories(Paths.get(f.getPath()).getParent());
      f.createNewFile();
    }

    FileWriter existWriter = new FileWriter(exist);
    existWriter.write("bomPopup\n" + System.currentTimeMillis());
    FileWriter fw = new FileWriter(f);
    StringBuilder line = new StringBuilder();
    for(int i=0; i<infoList.size(); i++){
      for(int j = 0; j< infoList.get(i).size(); j++){
        line.append(infoList.get(i).get(j));
      }
    }
    fw.write(line.toString()); //TODO BOM data function logic
    existWriter.close();
    fw.close();
  }

  @Override
  protected List<List<String>> convertObjectToInfo(List l) throws APIException {
    List<List<String>> info = new LinkedList();
    for(int i=0; i<l.size(); i++){
      List itemInfo = new LinkedList();
      Map.Entry entry = (Entry) l.get(i);
      itemInfo.add((String) entry.getKey());
      info.add(itemInfo);
    }
    return info;
  }

  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    // get the item
    HashMap altItemOccurance = new HashMap();
    Connection conn = getConnection(USERNAME, PASSWORD, URL);
    List itemList = new LinkedList();
    IItem item = (IItem) obj;
    String itemName = item.getName();
    System.out.println("Item name: " + itemName);
    String sql = "select FIND_NUMBER from BOM where ITEM_NUMBER = '" + itemName + "'";
    LinkedList findNumberList = executeSQL(conn, sql, true);

    String findNumber = (String) findNumberList.get(0);
    sql = "select ITEM_NUMBER from BOM where FIND_NUMBER = '" + findNumber + "'";
    LinkedList altItems = executeSQL(conn, sql, false);
    for(int i = 0; i<altItems.size(); i++) {
      if(!altItemOccurance.containsKey(altItems.get(i)))
        altItemOccurance.put(altItems.get(i), Collections.frequency(altItems, altItems.get(i)));
    }
    itemList = extractTop(altItemOccurance, 3);
    return (LinkedList) itemList;
  }

  @Override
  protected IAgileObject getTargetItem(IEventInfo req) throws APIException {
    // get the target item
    IUpdateTableEventInfo info = (IUpdateTableEventInfo) req;
    IDataObject obj = info.getDataObject();
    return obj;
  }
}