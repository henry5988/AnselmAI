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
      LinkedList list = getItemAdvice(session, req);
      out("List size: " + list.size());
      out("List: " + list.toString());
      if (list.size() >= 3) {
        out("convert Object to String info...");
        List infoList = convertObjectToInfo(list);

        Popup.frame(session, infoList);
      }else{
        out("list has fewer than 3 items, does nothing");
      }
    } catch (SQLException | APIException e) {
      out("Error occured", "err");
      e.getMessage();
      e.printStackTrace();
    }
    out("JFrame info printed");
    return null;
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


  protected abstract LinkedList getItemAdvice(IAgileSession session, IEventInfo req)
      throws SQLException, APIException;
}
