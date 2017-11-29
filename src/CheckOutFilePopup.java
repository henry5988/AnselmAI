import static com.HF.out;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CheckOutFilePopup extends FileSuggestionPopup{
  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    try {
      out("Action code: " + req.getEventType());
      setActionCode(String.valueOf(req.getEventType()));
    } catch (APIException e) {
      e.printStackTrace();
    }

    if(Objects.equals(getActionCode(), "22"))
      return super.doAction(session, node, req);
    else
      return new EventActionResult(req, new ActionResult(ActionResult.STRING, "Not applicable event"));
  }

  @Override
  protected List getAttachmentAdvice(Connection conn, IEventDirtyFile file,
      IAgileSession session) throws APIException, SQLException {
    return null;
  }
}
