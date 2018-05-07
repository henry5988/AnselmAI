import static com.HF.executeSQL;
import static com.HF.getConnection;
import static com.HF.getWordInParen;
import static com.HF.out;
import static com.HF.removeDup;
import static com.HF.removeNull;

import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.ItemConstants;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class UpdateTitleBlockPopup extends SuggestionPopup {

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    String source = "C:\\serverSource\\";
    String output_path = source + "updateTitleBlockPopup.txt";
    String output_html = source + "updateTitleBlockPopup.htm";
    String output_template = source + "updateTitleBlockPopup.html";
    init(session, req, output_path, output_html, output_template, true, true);
    super.doAction(session, node, req);
    return new EventActionResult(req, new ActionResult(ActionResult.STRING, "UpdateTitleBlockPopup"));
  }

  @Override
  protected IAgileObject getTargetItem(IEventInfo req) throws APIException {
    return null;
  }

  @Override
  protected String checksField() {
    return null;
  }

  @Override
  protected void writeToFile(List<List> infoList) throws IOException {
    // write getFieldCheckResponse() to file
  }

  @Override
  protected List<List> convertObjectToInfo(List l) throws APIException {
    // no use
    return null;
  }

  @Override
  protected List getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    // no item suggestion in this class
    return null;
  }

}