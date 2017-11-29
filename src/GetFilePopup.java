import static com.HF.executeSQL;
import static com.HF.extractTop;
import static com.HF.getConnection;
import static com.HF.getWordInParen;
import static com.HF.out;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class GetFilePopup extends FileSuggestionPopup {

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    try {
      out("Action code: " + req.getEventType());
      setActionCode(String.valueOf(req.getEventType()));
    } catch (APIException e) {
      e.printStackTrace();
    }
    if(Objects.equals(getActionCode(), "15"))
      return super.doAction(session, node, req);
    else
      return new EventActionResult(req, new ActionResult(ActionResult.STRING, "Not applicable event"));
  }

  @Override
  protected List<List<String>> convertObjectToInfo(List list)
      throws APIException {
    List info;
    List viewerCounts = new LinkedList();
    List objects = new LinkedList();
    out("GetFilePopup.convertObjectToInfo()...");
    for (Object entry : list) {
      Map.Entry e = (Entry) entry;
      viewerCounts.add(e.getValue().toString());
      objects.add(e.getKey());
    }
    out("List to convert: " + objects.toString());
    info = super.convertObjectToInfo(objects);
    info.add(viewerCounts);
    out("GetFilePopup.convertObjectToInfo() ends...");
    return info;
  }

  @Override
  protected List getAttachmentAdvice(Connection conn, IEventDirtyFile file,
      IAgileSession session)
      throws APIException, SQLException {
    setActionCode("15");
    return super.getAttachmentAdvice(conn, file, session);
  }

}
