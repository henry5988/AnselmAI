import com.agile.api.APIException;
import com.agile.api.APINameConstants;
import com.agile.api.CommonConstants;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IDataObject;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ITwoWayIterator;
import com.agile.api.ItemConstants;
import com.agile.api.TableTypeConstants;
import com.agile.px.EventActionResult;
import com.agile.px.IEventDirtyRow;
import com.agile.px.IEventDirtyRowUpdate;
import com.agile.px.IEventDirtyTable;
import com.agile.px.IEventInfo;
import com.agile.px.IUpdateEventInfo;
import com.agile.px.IUpdateTableEventInfo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BOMPopup extends SuggestionPopup {

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    System.out.println("BOMPopup()...");
    setTest(true);
    setFieldCheck(true);
    setSession(session);
    setEventInfo(req);
    output_path = "C:\\serverSource\\bomPopup.txt";
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
    File f = new File(output_path);
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
    fw.write("bomPopup test string"); //TODO BOM data function logic
    existWriter.close();
    fw.close();
  }

  @Override
  protected List<List<String>> convertObjectToInfo(List l) throws APIException {
    List<List<String>> info = new LinkedList();
    info.add(null);
    return info;
  }

  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IItem obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    List itemList = new LinkedList();
    itemList.add(null);
    return (LinkedList) itemList;
  }

  @Override
  protected IAgileObject getTargetItem(IEventInfo req) throws APIException {
    // get the target item
    IUpdateTableEventInfo info = (IUpdateTableEventInfo) req;
    IDataObject obj = info.getDataObject();
    return (IItem) obj;
  }
}