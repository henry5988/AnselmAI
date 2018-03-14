import static com.HF.out;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.ISubscription;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CheckOutFilePopup extends FileSuggestionPopup{
  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){

    if(checkEventType(req, CHECKOUTFILEEVENTTYPE, CHECKOUTFILEACTIONCODE))
      return super.doAction(session, node, req);
    return new EventActionResult(req, new ActionResult(ActionResult.STRING, "Not applicable event"));
  }

  @Override
  protected String checksField() {
    return null;
  }

  @Override
  protected void writeToFile(List<List<String>> infoList) throws IOException {

  }

  @Override
  protected IItem getTargetItem(IEventInfo req) throws APIException {
    return null;
  }

  protected void writeToFile(List<List<String>> infoList, String fileName)
      throws IOException {

  }

}
