import static com.HF.*;

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

  protected static final String USERNAME = "agile";
  protected static final String PASSWORD = "tartan";

  public static String getUSERNAME() {
    return USERNAME;
  }

  public static String getPASSWORD() {
    return PASSWORD;
  }

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req) {
    try {
      LinkedList<IAgileObject> list = getItemAdvice(session, req);
      out("convert Object to String info...");
      LinkedList infoList = convertObjectToInfo(list);
      out("retrieving name array...");
      List names = (List) infoList.get(0);
      out("retrieving image array...");
      List images = (List) infoList.get(1);
      out("retrieving description array...");
      List descriptions = (List) infoList.get(2);
      out("retrieving related user count...");
      List userCounts = (List) infoList.get(3);
      Popup.frame(names, images, descriptions);
    } catch (SQLException | APIException e) {
      out("Error occured", "err");
      e.getMessage();
      e.printStackTrace();
    }
    out("JFrame info printed");
    return null;
  }

  protected abstract LinkedList<LinkedList<String>> convertObjectToInfo(
      LinkedList<IAgileObject> list)
      throws APIException;

  protected abstract LinkedList getItemAdvice(IAgileSession session, IEventInfo req)
      throws SQLException, APIException;
}
