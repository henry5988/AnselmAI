import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GetFilePopup extends FileSuggestionPopup {


  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    setSession(session);
    setEventInfo(req);
    setFieldCheck(false);
    setTest(true);
    setOutput_path("C:\\serverSource\\documentPopup.txt");
    if(checkEventType(req, GETFILEEVENTTYPE, GETFILEACTIONCODE))
      return super.doAction(session, node, req);
    return new EventActionResult(req, new ActionResult(ActionResult.STRING, "Not applicable event"));
  }

  @Override
  protected String checksField() {
    return null;
  }

  @Override
  protected void writeToFile(List<List> infoList)
      throws IOException {
    // locate output file
    IEventInfo req = getEventInfo();
    IFileEventInfo info = (IFileEventInfo) req;
    String fileName = null;
    try {
      fileName = getDownloadedFileName(info);
    } catch (APIException e) {
      e.printStackTrace();
    }
    File f = new File(getOutput_path());
    File exist = new File(EXIST);
    if(!f.exists()) {
      Files.createDirectories(Paths.get(f.getPath()).getParent());
      f.createNewFile();
    }
    if(!exist.exists()){
      Files.createDirectories(Paths.get(exist.getPath()).getParent());
      exist.createNewFile();
    }
    FileWriter existWriter = new FileWriter(exist);
    existWriter.write("createDocumentPopup\n" + System.currentTimeMillis());
    existWriter.close();
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

  @Override
  protected IItem getTargetItem(IEventInfo req) {
    return null;
  }
  private String getDownloadedFileName(IFileEventInfo info) throws APIException {
    IEventDirtyFile[] files = info.getFiles();
    return files[0].getFilename();
  }


}
