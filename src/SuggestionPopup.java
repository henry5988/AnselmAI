import static com.HF.getConnection;
import static com.HF.out;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.ITable;
import com.agile.px.EventActionResult;
import com.agile.px.IEventAction;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;

public abstract class SuggestionPopup extends JFrame implements IEventAction, Constants {

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
      Popup p = new AttachmentPopup();
      IFileEventInfo info = (IFileEventInfo) req;
      IItem obj = (IItem) info.getDataObject();
      conn.close();
      LinkedList list = getItemAdvice(session, obj, info);
      out("List size: " + list.size());
      out("List: " + list.toString());
      List<List<String>> infoList = convertObjectToInfo(list);
      if (list.size() < 3) {
        out("list has fewer than 3 items, does nothing");
        while(((List) infoList.get(1)).size() < 3){
          infoList.get(1).add("n/a");
          infoList.get(2).add(NOPATH);
          infoList.get(3).add("n/a");
          infoList.get(4).add("n/a");
        }
      }
      out("convert Object to String info...");
      String fileName = getDownloadedFileName(info);
      p.frame(session, infoList, fileName);
    } catch (SQLException | APIException | ClassNotFoundException e) {
      out("Error occured", "err");
      e.getMessage();
      e.printStackTrace();
    }
    out("JFrame info printed");
    return null;
  }

  private String getDownloadedFileName(IFileEventInfo info) throws APIException {
    IEventDirtyFile[] files = info.getFiles();
    return files[0].getFilename();
  }

  protected List<List<String>> convertObjectToInfo(List l)
      throws APIException {
    LinkedList list = (LinkedList) l;
    list = removeNull(list);
    LinkedList infoList = new LinkedList();
    ItemInfoConverter converter = new ItemInfoConverter();
    LinkedList names = new LinkedList();
    LinkedList descriptions = new LinkedList();
    out("List of objects to convert: " + list);
    out("Setting name type array...");
    names.add("names");
    descriptions.add("descriptions");
    LinkedList images = new LinkedList();
    images.push("images3");
    images.push("images2");
    images.push("images1");
    images.push("images");
    out("converter variables defined");
    while (!list.isEmpty()) {
      out("converting " + ((IAgileObject) list.peekLast()).getName());
      converter.setConverterAtt("name");
      out("extracting name...");
      names.add(converter.convert((IAgileObject) list.peekLast()));
      out("extracting description...");
      converter.setConverterAtt("description");
      descriptions.add(converter.convert((IAgileObject) list.peekLast()));
      out("extracting images...");
      converter.setConverterAtt("image");
      list.removeLast();
    }
    out("adding names...");
    infoList.add(names);
    out("adding descriptions...");
    infoList.add(descriptions);
    out("adding images...");
    infoList.add(images);
    out("info list: " + infoList.toString());
    infoList = (LinkedList) converter
        .orderLists(infoList, new String[]{"names", "images", "descriptions", "viewerCounts"});
    return infoList;
  }


  protected abstract LinkedList getItemAdvice(IAgileSession session, IItem obj, IFileEventInfo req)
      throws SQLException, APIException, ClassNotFoundException;
}


