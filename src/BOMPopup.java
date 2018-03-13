import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BOMPopup extends SuggestionPopup {

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    output_path = "C:\\serverSource\\bomPopup.txt";

    if(checkEventType(req, BOMEVENTTYPE, BOMACTIONCODE))
      return super.doAction(session, node, req);
    return new EventActionResult(req, new ActionResult(ActionResult.STRING, "Not applicable event"));
  }

  @Override
  protected void writeToFile(List<List<String>> infoList, String fileName) throws IOException {

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
}