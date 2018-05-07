import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UploadFilePopup extends SuggestionPopup {
  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    String source = "C:\\serverSource\\";
    String output_path = source + "uploadFilePopup.txt";
    String output_html = source + "uploadFilePopup.htm";
    String output_template = source + "uploadFilePopup.html";
    init(session, req, output_path, output_html, output_template, true, true);
    return new EventActionResult(req, new ActionResult(ActionResult.STRING, "UploadFilePopup()"));
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
    // no use in this class
    return null;
  }

  @Override
  protected List getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    // no use in this class
    return null;
  }


}
