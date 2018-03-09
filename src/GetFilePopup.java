import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GetFilePopup extends FileSuggestionPopup {


  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    try {
      String username = session.getCurrentUser().getName();
      output_path = "C:\\serverSource\\" + username + "\\getFilePopup.txt";
    } catch (APIException e) {
      System.err.println(e.getMessage());
    }
    if(checkEventType(req, GETFILEEVENTTYPE, GETFILEACTIONCODE))
      return super.doAction(session, node, req);
    return new EventActionResult(req, new ActionResult(ActionResult.STRING, "Not applicable event"));
  }

  @Override
  protected void writeToFile(List<List<String>> infoList, String fileName)
      throws IOException {
    // locate output file
    File f = new File(output_path);
    if(!f.exists()) {
      Files.createDirectories(Paths.get(f.getPath()).getParent());
      f.createNewFile();
    }
    // open file streams
    FileOutputStream fos = new FileOutputStream(f);
    // print visited file name, then next line
    fos.write((fileName + "\n").getBytes());
    for(List l : infoList) {
      for(Object o : l) {
        // if printing last element, add new line character at the end
        if(l.indexOf(o) == l.size()-1){
          fos.write((o.toString() + "\n").getBytes());
        }else{
        // print image source
        // print file suggestion names
        // print number of visits for the names
          fos.write(o.toString().getBytes());
        }
      }
    }
    // close file streams
    fos.close();
  }

}
