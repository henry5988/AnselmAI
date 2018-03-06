import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GetFilePopup extends FileSuggestionPopup {



  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    if(checkEventType(req, GETFILEEVENTTYPE, GETFILEACTIONCODE))
      return super.doAction(session, node, req);
    return new EventActionResult(req, new ActionResult(ActionResult.STRING, "Not applicable event"));
  }

  @Override
  protected void writeToFile(List<List<String>> infoList, String fileName, String folderName)
      throws IOException {
    File f = new File(GETFILEFILE);
    if(!f.exists())
      f.createNewFile();

  }

}
