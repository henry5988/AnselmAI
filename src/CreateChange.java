import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.ICreateEventInfo;
import com.agile.px.IEventInfo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CreateChange extends SuggestionPopup{

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req) {
    String source = "C:\\serverSource\\";
    String output_path = source + "createChange.txt";
    String output_html = source + "createChange.htm";
    String output_template = source + "createChange.html";
    init(session, req,output_path, output_html, output_template, false, false);
    super.doAction(session, node, req);
    return new EventActionResult(req, new ActionResult(ActionResult.STRING, "CreateChange"));
  }

  @Override
  protected String checksField() {
    return null;
  }

  @Override
  protected void writeToFile(List<List> infoList) throws IOException {
  }

  @Override
  protected List<List> convertObjectToInfo(List l) throws APIException {
    return null;
  }

  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    return null;
  }

  @Override
  protected IAgileObject getTargetItem(IEventInfo req) throws APIException {
    ICreateEventInfo info = (ICreateEventInfo) req;
    return info.getDataObject();
  }
}
