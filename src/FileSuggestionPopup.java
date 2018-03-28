import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class FileSuggestionPopup extends SuggestionPopup {

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req) {
    String returnString = "no event";
    setSession(session);
    setEventInfo(req);
    setOutput_path("C:\\serverSource\\documentPopup.txt");
    setFieldCheck(false);
    setTest(true);
    init(getSession(), getEventInfo(), getOutput_path(), isFieldCheck(), isTest());
    super.doAction(getSession(), node, getEventInfo());
    try {
      returnString = req.getEventHandlerName() + "!";
    } catch (APIException e) {
      e.printStackTrace();
    }
    return new EventActionResult(req, new ActionResult(ActionResult.STRING, returnString));
  }

  @Override
  protected String checksField() {
    // no use in this event
    return null;
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
    existWriter.write(String.format("documentPopup%n" + System.currentTimeMillis()));
    FileWriter fw = new FileWriter(f);
    fw.write("GetFile test string"); //TODO file suggestion logic
    existWriter.close();
    fw.close();
  }

  @Override
  protected List<List<String>> convertObjectToInfo(List l) throws APIException {
    return new LinkedList<>();
  }

  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    return null;
  }

  @Override
  protected IAgileObject getTargetItem(IEventInfo req) throws APIException {
    IItem item;
    IFileEventInfo info = (IFileEventInfo) req;
    item = (IItem) info.getDataObject();
    return item;
  }
}
