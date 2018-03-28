import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.api.IProject;
import com.agile.api.ProjectConstants;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import com.agile.px.ISaveAsEventInfo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ProjectPopup extends SuggestionPopup{
  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    setTest(true);
    setFieldCheck(false);
    setOutput_path("C:\\serverSource\\createProject.txt");
    return super.doAction(session, node, req);
  }

  @Override
  protected String checksField() {
    // no use in this module
    return null;
  }

  @Override
  protected void writeToFile(List<List<String>> infoList) throws IOException {
    File f = new File(getOutput_path());
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
    existWriter.write("createProject\n" + System.currentTimeMillis());
    FileWriter fw = new FileWriter(f);
    fw.write("createProject test string"); //TODO createProject logic
    existWriter.close();
    fw.close();
  }

  @Override
  protected List<List<String>> convertObjectToInfo(List l) throws APIException {
    return new LinkedList<>();
  }

  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    return null;
  }

  @Override
  protected IAgileObject getTargetItem(IEventInfo req) throws APIException {
    ISaveAsEventInfo info = (ISaveAsEventInfo) req;
    //ICreateEventInfo info = (ICreateEventInfo) req;
    String projectName = info.getNewNumber();
    IAgileSession session = getSession();
    IProject project = (IProject) session.getObject(ProjectConstants.CLASS_PROJECT_BASE_CLASS, projectName);
    return project;
  }
}
