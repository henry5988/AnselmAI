import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;
import java.sql.SQLException;
import java.util.LinkedList;

public class CreateChangeOrderSuggestionPopup extends SuggestionPopup{

  CreateChangeOrderSuggestionPopup(){
    p = new ProjectPopup();
  }

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){

    return null;
  }

  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IItem obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {

    return null;
  }
}
