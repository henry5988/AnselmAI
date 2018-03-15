import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IDataObject;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import com.agile.px.IUpdateEventInfo;
import com.agile.px.IUpdateTableEventInfo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BOMPopup extends SuggestionPopup {

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    System.out.println("BOMPopup()...");
    setFieldCheck(true);
    output_path = "C:\\serverSource\\bomPopup.txt";
    return super.doAction(session, node, req);

  }

  @Override
  protected String checksField() {
    return "All Normal";
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
    return info;
  }

  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IItem obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    List itemList = new LinkedList();
    return (LinkedList) itemList;
  }

  @Override
  protected IItem getTargetItem(IEventInfo req) throws APIException {
    // get the target item
    IUpdateTableEventInfo info = (IUpdateTableEventInfo) req;
    IDataObject obj = info.getDataObject();
    IItem item = (IItem) obj;
    return item;
  }
}