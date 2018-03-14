import static com.HF.getConnection;
import static com.HF.out;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.px.EventActionResult;
import com.agile.px.IEventAction;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;

public abstract class SuggestionPopup extends JFrame implements IEventAction, Constants {
  String output_path = "";
  private String actionCode;
  private boolean fieldCheck; // this boolean has to be set at the beginning of doAction
  String fieldCheckResponse = "";
  String getActionCode() {
    return actionCode;
  }

  void setActionCode(String actionCode) {
    this.actionCode = actionCode;
  }

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req) {
    try {
      //session = connect();
      String username = session.getCurrentUser().getName();

      Connection conn = null;
      conn = getConnection(USERNAME, PASSWORD, URL);
      // field checks
      if(isFieldCheck()){
        fieldCheckResponse = checksField();
      }

      // get suggestions
      IItem obj = getTargetItem(req);
      LinkedList list = getItemAdvice(session, obj, req);
     // out("List: " + list.toString());
      List<List<String>> infoList = convertObjectToInfo(list);
      if (list.size() < 3) {
     //   out("list has fewer than 3 items, does nothing");
        while(((List) infoList.get(1)).size() < 3){
          infoList = addEmptyInfoToList(infoList);
        }
      }
    //  out("convert Object to String info...");
      writeToFile(infoList);
    } catch (SQLException | APIException | ClassNotFoundException e) {
    //  out("Error occured", "err");
      e.getMessage();
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // out("JFrame info printed");
    return null;
  }

  protected List addEmptyInfoToList(List<List<String>> infoList) {
    for(int i=0; i<infoList.size(); i++){
      infoList.get(i).add("n/a");
    }
    return infoList;
  }

  protected abstract String checksField();

  protected boolean checkEventType(IEventInfo req, int eventType, String actionCode){
    try {
      //   out("Event type: " + req.getEventType());
      setActionCode(String.valueOf(actionCode));
      if(req.getEventType() == eventType)
        return true;
    } catch (APIException e) {
      e.printStackTrace();
    }
    return false;
  }

  protected abstract void writeToFile(List<List<String>> infoList, String fileName) throws IOException;
  protected abstract void writeToFile(List<List<String>> infoList) throws IOException;

  private String getDownloadedFileName(IFileEventInfo info) throws APIException {
    IEventDirtyFile[] files = info.getFiles(); 
    return files[0].getFilename();
  }
  private String getDownloadedFolderName(IFileEventInfo info) throws APIException {
	    IEventDirtyFile[] files = info.getFiles();
	    return files[0].getFileFolder().toString();
	  }


  protected abstract List<List<String>> convertObjectToInfo(List l)
      throws APIException;


  protected abstract LinkedList getItemAdvice(IAgileSession session, IItem obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException;

  public boolean isFieldCheck() {
    return fieldCheck;
  }

  public void setFieldCheck(boolean fieldcheck) {
    this.fieldCheck = fieldcheck;
  }

  protected abstract IItem getTargetItem(IEventInfo req) throws APIException;
}


