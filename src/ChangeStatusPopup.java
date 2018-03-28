import com.agile.api.APIException;
import com.agile.api.ChangeConstants;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IChange;
import com.agile.api.IDataObject;
import com.agile.api.INode;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import com.agile.px.IWFChangeStatusEventInfo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ChangeStatusPopup extends SuggestionPopup{

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req) {
    setSession(session);
    setEventInfo(req);
    setTest(true);
    setFieldCheck(true);
    setOutput_path("C:\\serverSource\\ChangeStatus.txt");
    return super.doAction(session, node, req);
  }

  @Override
  protected String checksField() {
    // checks for the common reason behind a rejected review
    IDataObject obj;
    IChange change;
    String description = null;
    IWFChangeStatusEventInfo info = (IWFChangeStatusEventInfo) getEventInfo();
    try {
      
      obj = info.getDataObject();
      
      change = (IChange) obj;
      
      description = (String) change.getValue(ChangeConstants.ATT_COVER_PAGE_DESCRIPTION);
    } catch (APIException e) {
      System.out.println(e.getMessage());
    }
    return description;
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
    existWriter.write("ChangeStatusPopup\n" + System.currentTimeMillis());
    FileWriter fw = new FileWriter(f);
    fw.write("ChangeStatusPopup test string"); //TODO ChangeStatusPopup data function logic
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
  protected LinkedList getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    List itemList = new LinkedList();
    itemList.add(null);
    return (LinkedList) itemList;
  }

  @Override
  protected IAgileObject getTargetItem(IEventInfo req) throws APIException {
    IWFChangeStatusEventInfo info = (IWFChangeStatusEventInfo) req;
    IDataObject obj = info.getDataObject();
    return (IChange) obj;
  }
}
