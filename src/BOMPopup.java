import static com.HF.executeSQL;
import static com.HF.extractTop;
import static com.HF.getConnection;

import com.HF;
import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IDataObject;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;
import com.agile.px.EventActionResult;
import com.agile.px.IEventDirtyRowUpdate;
import com.agile.px.IEventDirtyTable;
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
import org.apache.commons.dbutils.DbUtils;

public class BOMPopup extends SuggestionPopup {

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    System.out.println("BOMPopup()...");
    init(session, req, BOMPOPUP_OUTPUT_PATH, BOMPOPUP_HTML_OUTPUT, BOMPOPUP_HTML_TEMPLATE, true, false);
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
      int cost = (int) row.getValue(ItemConstants.ATT_PAGE_TWO_TEXT02); // base number for cost column in BOM table; can be any other text field
      if(cost <= 0) resultString += "cost of " + row.getReferent().getName() + " is not valid." + String.format("%n");

    } catch (APIException e) {
      System.err.println(e.getMessage());
    }
    if(resultString.equals("Result: ")) return "Column fields are correct.";
    else return resultString;
  }

  @Override
  protected void writeToFile(List<List> infoList) throws IOException{
    System.out.println("Matrix Transpose...");
    infoList = HF.transposeMatrix(infoList);
    System.out.println("Transpose Complete!");
    File f = new File(getOutput_path());
    String existFile = null;
    try {
      existFile = replaceServerSource(getSession().getCurrentUser().getName(), EXIST);
    } catch (APIException e) {
      System.err.println("Error in BOMPopup.writeToFile(): " + e.getMessage());
    }
    File exist = new File(existFile);
    if(!exist.exists()){
      Files.createDirectories(Paths.get(exist.getPath()).getParent());
      exist.createNewFile();
    }
    if(!f.exists()){
      Files.createDirectories(Paths.get(f.getPath()).getParent());
      f.createNewFile();
    }

    FileWriter existWriter = new FileWriter(exist);
    existWriter.write(getHtmlOutput());
    existWriter.close();
    FileWriter fw = new FileWriter(f);
    StringBuilder line = new StringBuilder();
    System.out.println(getFieldCheckResponse()+String.format("%n"));
    line.append(getFieldCheckResponse());
    line.append(String.format("%n"));
    try {
      System.out.println(getTargetItem(getEventInfo()).getName()+String.format("%n"));
      line.append(getTargetItem(getEventInfo()).getName());
      line.append(String.format("%n"));
    }catch(APIException e){
      System.err.println(e.getMessage());
    }
    for(int i=0; i<infoList.size(); i++){
      for(int j = 0; j< infoList.get(i).size(); j++){
        line.append(infoList.get(i).get(j) + String.format("%n"));
      }
    }
    fw.write(line.toString()); //TODO BOM data function logic
    fw.close();
  }

  @Override
  protected List<List> convertObjectToInfo(List l) throws APIException {

    List<List> info = new LinkedList();
    for(int i=0; i<l.size(); i++){
      List itemInfo = new LinkedList();
      Map.Entry entry = (Entry) l.get(i);
      IItem item = (IItem) entry.getKey();
      itemInfo.add(item.getName());
      itemInfo.add(item.getValue(ItemConstants.ATT_TITLE_BLOCK_DESCRIPTION));
      String itemQty = "n/a";
      IItem target = (IItem) getTargetItem(getEventInfo());
      ITable bom = target.getTable(ItemConstants.TABLE_BOM);
      Iterator it = bom.getTableIterator();
      while(it.hasNext()){
        IRow row = (IRow) it.next();
        if(row.getReferent().getName().equals(item.getName())){
          itemQty = (String) row.getValue(ItemConstants.ATT_BOM_QTY);
        }
      }
      itemInfo.add(itemQty);
      info.add(itemInfo);
    }
    return info;
  }

  @Override
  //TODO getItemAdvice需要回饋機制
  protected List getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
      throws APIException, ClassNotFoundException {
    // get the item
    HashMap altItemOccurance = new HashMap();
    LinkedList altItems = null;
    List itemList = null;
    Connection conn = null;
    try {
      conn = getConnection(USERNAME, PASSWORD, URL);

      IUpdateTableEventInfo updateBOMEvent = (IUpdateTableEventInfo) getEventInfo();
      IEventDirtyTable table = updateBOMEvent.getTable();
      Iterator it = table.iterator();
      IEventDirtyRowUpdate rowUpdate = (IEventDirtyRowUpdate) it.next();
      IItem item = (IItem) rowUpdate.getReferent();
      String itemName = item.getName();
      System.out.println("Item name: " + itemName);
      String sql = "select FIND_NUMBER from BOM where ITEM_NUMBER = '" + itemName + "'";
      LinkedList findNumberList = executeSQL(conn, sql, true);
      System.out.println(
          "Find Number: " + (findNumberList.get(0) == null ? "null" : findNumberList.get(0)));
      String findNumber = (String) findNumberList.get(0);
      if (findNumber.equals("0"))
        return null;
      sql = "select ITEM_NUMBER from BOM where FIND_NUMBER = '" + findNumber + "'";
      altItems = executeSQL(conn, sql, false);
    }catch(SQLException e){
      System.err.println("SQLException in BOMPopup.getItemAdvice(): " + e.getMessage());
    }finally {
      DbUtils.closeQuietly(conn);
    }
    System.out.println("altItems: " + altItems.toString());
    for(int i = 0; i<altItems.size(); i++) {
      if(!altItemOccurance.containsKey(altItems.get(i)))
        altItemOccurance.put(getSession().getObject(ItemConstants.CLASS_ITEM_BASE_CLASS, altItems.get(i)), Collections.frequency(altItems, altItems.get(i)));
    }
    itemList = extractTop(altItemOccurance, 3);
    System.out.println("returned: " + itemList.toString());
    return itemList;
  }

  @Override
  protected IAgileObject getTargetItem(IEventInfo req) throws APIException {
    // get the target item
    IUpdateTableEventInfo info = (IUpdateTableEventInfo) req;
    IEventDirtyTable table = info.getTable();
    IDataObject obj = info.getDataObject();
    return obj;
  }
}