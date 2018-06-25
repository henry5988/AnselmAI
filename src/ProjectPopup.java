import static com.HF.executeSQL;
import static com.HF.extractTop;
import static com.HF.getConnection;

import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IDataObject;
import com.agile.api.INode;
import com.agile.api.IProgram;
import com.agile.api.IProject;
import com.agile.api.IQuery;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.IUser;
import com.agile.api.ProgramConstants;
import com.agile.api.ProjectConstants;
import com.agile.api.QueryConstants;
import com.agile.api.TableTypeConstants;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventInfo;
import com.agile.px.ISaveAsEventInfo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ProjectPopup extends SuggestionPopup{
  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
	  System.out.println("-----Project Pop up Start-----");
//    setTest(true);
    setFieldCheck(false);
    String userDir = null;
    userDir = "C:\\anselmAIWeb\\serverSource\\";
    String output_path = userDir + "creatProjectPopup(HR).txt";
    String output_html = userDir + "creatProjectPopup(HR).htm";
    String output_template = userDir + "creatProjectPopup(HR).html";
    init(session, req,output_path, output_html, output_template, false, false);
    super.doAction(session, node, req);
    System.out.println("-----Project Pop up End-----");
    return new EventActionResult(req, new ActionResult(ActionResult.STRING, "Project Pop up"));
  }

  @Override
  protected String checksField() {
    // no use in this module
    return null;
  }

  @Override
  protected void writeToFile(List<List> infoList) throws IOException {
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
  protected List<List> convertObjectToInfo(List l) throws APIException {
    return new LinkedList<>();
  }

  @Override
  protected List getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    List suggestion = new LinkedList();
    List finalSuggestion = new LinkedList();
//    finalSuggestion.add("");finalSuggestion.add("");finalSuggestion.add("");
    String sql;
    List sqlResult;
    Map<IAgileObject, Integer> teamMembers  = new HashMap();
    // A. get project
    IProgram program = (IProgram) obj;

    // B. get created from template value
    String template = (String) program.getValue(2000008049);

    // C. get list of project from the same template value
    // 1. get template ID from ACTIVITY table
    Connection conn = getConnection(USERNAME, PASSWORD, URL);
    sql = "SELECT ID FROM ACTIVITY WHERE NAME='" + template + "'";
    sqlResult = executeSQL(conn, sql);
    String templateID = (String) sqlResult.get(0);
    System.out.println("Template ID: " + templateID);
    // 2. get project names created from the same template ID
    sql = "SELECT NAME FROM ACTIVITY WHERE CREATED_FROM_TEMPLATE='"+ templateID +"' AND SUBCLASS='"+18027+"'";
    sqlResult = executeSQL(conn, sql);
   
    // D. get the team table for each project
    for(Object relatedProjectName : sqlResult){
      System.out.println("Related: " + relatedProjectName);
      // 1. get the project object from their names
      sql = "SELECT ACTIVITY_NUMBER FROM ACTIVITY WHERE NAME='" + relatedProjectName + "'";
      sqlResult = executeSQL(conn, sql);
      IProgram relatedProject = (IProgram) session.getObject(IProgram.OBJECT_TYPE, sqlResult.get(0).toString());
      System.out.println("program name: " + relatedProject);
      finalSuggestion.add(relatedProjectName);
      
      // 2. Get actual duration of project
      String actualDuration = "";
      String estimatedDuration = "";
      int error = 0;
      String errorDuration = "";
      if("Complete".equals(relatedProject.getValue(ProgramConstants.ATT_GENERAL_INFO_STATUS).toString())) {
    	  actualDuration = relatedProject.getValue(ProgramConstants.ATT_GENERAL_INFO_ACTUAL_DURATION).toString();
    	  estimatedDuration = relatedProject.getValue(ProgramConstants.ATT_GENERAL_INFO_ESTIMATED_DURATION).toString();
    	  double estimatedDrationD = Double.valueOf(estimatedDuration);
    	  double actualDurationD = Double.valueOf(actualDuration);
    	  error = (int)(estimatedDrationD)-(int)(actualDurationD);
    	  if(error>0)errorDuration = "提早"+error+"天";
    	  else errorDuration = "延遲"+String.valueOf(error).substring(1,String.valueOf(error).length())+"天";
    	  System.out.println("Error duration: "+errorDuration);
      }
      finalSuggestion.add(estimatedDuration);
      finalSuggestion.add(errorDuration);
      ITable teamTable = relatedProject.getTable(ProgramConstants.TABLE_TEAM);
      // 3. all the members from each table in a hashmap with number of appearances
      
      for(Object r: teamTable){
    	  
        IRow row = (IRow) r;
        System.out.println(addMemberRecommendation(teamMembers, row.getReferent()));
      }
    }
    // E. check top 3 member availability
//    suggestion = extractTop(teamMembers, 3);
    // lower priority if a member is loaded
//    for(Object memberEntryObj: suggestion){
//      // query for programs that the member is in and is in progress
//      Entry memberEntry = (Entry) memberEntryObj;
//      IUser member = (IUser) memberEntry.getKey();
//      IQuery q = (IQuery) getSession().createObject(IQuery.OBJECT_TYPE, ProgramConstants.CLASS_PROGRAM_BASE_CLASS);
//      q.setCaseSensitive(false);
//      q.setCriteria("[Team.Name] contains '" + member.toString()+"'");
//      ITable queryResult = q.execute();
//      // if query result > limit of finalSuggestion, move suggestion down one spot and move others up
//      finalSuggestion.add(2,memberEntryObj);
//      if(queryResult.size() > 2){
//        finalSuggestion.add(0,memberEntryObj);
//      }else{
//        finalSuggestion.add(memberEntryObj);
//      }
//    }
    // return project final suggestions
    conn.close();
    return finalSuggestion;
  }
  @Override
  protected IAgileObject getTargetItem(IEventInfo req) throws APIException {
    ISaveAsEventInfo info = (ISaveAsEventInfo) req;
    //ICreateEventInfo info = (ICreateEventInfo) req;
    String projectName = info.getNewNumber();
    System.out.println(projectName);
    IAgileSession session = getSession();
    IProgram program = (IProgram) session.getObject(IProgram.OBJECT_TYPE, projectName);
    return program;
  }

  private String addMemberRecommendation(Map teamMembers, IDataObject referent)
      throws APIException {
    if(teamMembers.containsKey(referent)){
      Integer v = (Integer) teamMembers.get(referent);
      teamMembers.remove(referent);
      teamMembers.put(referent, v+1);
    }else{
      teamMembers.put(referent, 1);
    }
    return "User " + referent.getName() + " added to teamMember for " + teamMembers.get(referent) + " time" + (teamMembers.get(referent).equals(1)?"":"s");
  }
}
