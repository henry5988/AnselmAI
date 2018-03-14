import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import com.agile.px.IUpdateEventInfo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BOMPopup extends SuggestionPopup {

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    output_path = "C:\\serverSource\\bomPopup.txt";
    List infoList = new LinkedList();
    List stringList = new LinkedList();
    String resultString = checkBOMIntegrity();
    stringList.add(resultString);
    infoList.add(stringList);
    try {
      writeToFile(infoList);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new EventActionResult(req, new ActionResult(ActionResult.STRING, "BOM Update info updated!"));

  }

  @Override
  protected String checksField() {
    return null;
  }


  private String checkBOMIntegrity() {
  // method that checks for quantity column of the BOM table
    String result = "";
    return result;
  }

  @Override
  protected void writeToFile(List<List<String>> infoList, String fileName) throws IOException {
    File f = new File(output_path);
    File exist = new File(EXIST);
    if(!exist.exists()){
      Files.createDirectories(Paths.get(exist.getPath()).getParent());
      exist.createNewFile();
    }
    if(!f.exists()){
      Files.createDirectories(Paths.get(f.getPath()).getParent());
      f.createNewFile();
    }

    FileWriter existWriter = new FileWriter(exist);
    existWriter.write("bomPopup\n" + System.currentTimeMillis());
    FileWriter fw = new FileWriter(f);
    fw.write("bomPopup test string"); //TODO BOM data function logic
    existWriter.close();
    fw.close();
  }

  @Override
  protected void writeToFile(List<List<String>> infoList) throws IOException {

  }

  @Override
  protected List<List<String>> convertObjectToInfo(List l) throws APIException {
    List<List<String>> info = new LinkedList();
    return info;
  }

  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IItem obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    List itemList = new LinkedList();
    return (LinkedList) itemList;
  }

  @Override
  protected IItem getTargetItem() {
    return null;
  }
}