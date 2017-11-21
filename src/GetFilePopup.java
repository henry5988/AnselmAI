import static com.HF.getConnection;
import static com.HF.out;

import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IDataObject;
import com.agile.px.IEventAction;
import com.agile.px.IEventInfo;
import com.agile.px.IObjectEventInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;

public class GetFilePopup extends SuggestionPopup implements IEventAction {

  @Override
  protected LinkedList<LinkedList<String>> convertObjectToInfo(LinkedList<IAgileObject> list)
      throws APIException {
    return null;
  }

  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IEventInfo req)
      throws SQLException, APIException {
    out("Getting attachment file advice...");
    Connection conn= getConnection(USERNAME, PASSWORD);
    IObjectEventInfo info = (IObjectEventInfo) req;
    IDataObject obj = info.getDataObject();
    out(obj.toString());
    return null;
  }
}
