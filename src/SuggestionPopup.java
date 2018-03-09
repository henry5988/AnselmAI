import static com.HF.getConnection;
import static com.HF.out;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IFileFolder;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.ITable;
import com.agile.px.EventActionResult;
import com.agile.px.IEventAction;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;

public abstract class SuggestionPopup extends JFrame implements IEventAction, Constants {
  String output_path = "";
  private String actionCode;

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
      Connection conn = null;
      conn = getConnection(USERNAME, PASSWORD, URL);

      IFileEventInfo info = (IFileEventInfo) req;
      IItem obj = (IItem) info.getDataObject();
      conn.close();
      LinkedList list = getItemAdvice(session, obj, info);
    
     // out("List: " + list.toString());
      List<List<String>> infoList = convertObjectToInfo(list);
      if (list.size() < 3) {
     //   out("list has fewer than 3 items, does nothing");
        while(((List) infoList.get(1)).size() < 3){
          infoList.get(1).add("n/a");
          infoList.get(2).add(NOPATH);
          infoList.get(3).add("n/a");
          infoList.get(4).add("n/a");
          infoList.get(5).add("n/a");
        }
      }
    //  out("convert Object to String info...");
      String fileName = getDownloadedFileName(info);
      String folderName = getDownloadedFolderName(info);
      writeToFile(infoList, fileName);
      //Popup.frame(session, infoList, fileName,folderName);
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

  protected abstract void writeToFile(List<List<String>> infoList, String fileName) throws IOException;


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
}


