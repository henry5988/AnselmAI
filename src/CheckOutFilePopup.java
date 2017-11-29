import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.EventActionResult;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CheckOutFilePopup extends FileSuggestionPopup{
  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    setActionCode("22");
    return super.doAction(session, node, req);
  }

  @Override
  protected List getAttachmentAdvice(Connection conn, IEventDirtyFile file,
      IAgileSession session) throws APIException, SQLException {
    return null;
  }
}
