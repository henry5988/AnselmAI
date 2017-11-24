import static com.HF.out;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.EventActionResult;
import com.agile.px.IEventAction;
import com.agile.px.IEventInfo;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;

public abstract class SuggestionPopup extends JFrame implements IEventAction {

  static final String USERNAME = "agile";
  static final String PASSWORD = "tartan";
  static final String URL = "jdbc:oracle:thin:@win-ooi3viu801v:1521:agile9";

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req) {
    try {
      LinkedList list = getItemAdvice(session, req);
      out("convert Object to String info...");
      LinkedList infoList = convertObjectToInfo(list);
      out("retrieving name array...");
      List names = (List) infoList.get(0);
      out("retrieving image array...");
      List images = (List) infoList.get(1);
      out("retrieving description array...");
      List descriptions = (List) infoList.get(2);
      out("retrieving related user count...");
      //List userCounts = (List) infoList.get(3);
      Popup.frame(names, images, descriptions);
    } catch (SQLException | APIException e) {
      out("Error occured", "err");
      e.getMessage();
      e.printStackTrace();
    }
    out("JFrame info printed");
    return null;
  }

  protected LinkedList<LinkedList<String>> convertObjectToInfo(LinkedList list)
      throws APIException{
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
      out("converting " + ((IAgileObject)list.peekLast()).getName());
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
        .orderLists(infoList, new String[]{"names", "images", "descriptions"});
    return infoList;
  }


  protected abstract LinkedList getItemAdvice(IAgileSession session, IEventInfo req)
      throws SQLException, APIException;
}
