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
import com.agile.api.IUserGroup;
import com.agile.api.ProgramConstants;
import com.agile.api.ProjectConstants;
import com.agile.api.QueryConstants;
import com.agile.api.TableTypeConstants;
import com.agile.api.UserConstants;
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
import java.util.Iterator;
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
    if(!infoList.isEmpty()) {
    	for(List list:infoList) {
    		if(!list.isEmpty()) {
    			for(Object o:list) {
    				fw.write(o.toString()+"\r\n");
    				fw.flush();
    			}
    		}
    	}
    }else {
    	fw.write("empty");
    }
    existWriter.close();
    fw.close();
  }

  @Override
  protected List<List> convertObjectToInfo(List l) throws APIException {
	  
	  
    return l;
  }

  @Override
  protected List getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
	List projectSuggestion = new LinkedList();
    List memberSuggestion = new LinkedList();
    List<List> finalSuggestion = new LinkedList();
    String objname = ((IProgram)obj).getValue(ProgramConstants.ATT_GENERAL_INFO_NAME) + "";
    projectSuggestion.add(objname);
    finalSuggestion.add(new LinkedList(projectSuggestion));
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
    int countProject = 0;
    int countTeamMember = 0;
    // D. get the team table for each project
    for(Object relatedProjectName : sqlResult){
    	if(objname.equals(relatedProjectName.toString()))continue;
      projectSuggestion = new LinkedList();
      System.out.println("Related: " + relatedProjectName);
      // 1. get the project object from their names
      sql = "SELECT ACTIVITY_NUMBER FROM ACTIVITY WHERE NAME='" + relatedProjectName + "'";
      sqlResult = executeSQL(conn, sql);
      IProgram relatedProject = (IProgram) session.getObject(IProgram.OBJECT_TYPE, sqlResult.get(0).toString());
//      System.out.println("program name: " + relatedProject);
      if(countProject<3)projectSuggestion.add(relatedProjectName);
      
      // 2. Get actual duration of project
      String actualDuration = "";
      String estimatedDuration = "";
      int error = 0;
      String errorDuration = "";
      if(relatedProject.getValue(ProgramConstants.ATT_GENERAL_INFO_ESTIMATED_DURATION)!=null)
    	  estimatedDuration = relatedProject.getValue(ProgramConstants.ATT_GENERAL_INFO_ESTIMATED_DURATION).toString();
      double estimatedDrationD = Double.valueOf(estimatedDuration);
      int estimatedDrationI = (int)(estimatedDrationD);
      if("Complete".equals(relatedProject.getValue(ProgramConstants.ATT_GENERAL_INFO_STATUS).toString())) {
    	  actualDuration = relatedProject.getValue(ProgramConstants.ATT_GENERAL_INFO_ACTUAL_DURATION).toString();
    	  double actualDurationD = Double.valueOf(actualDuration);
    	  error = estimatedDrationI-(int)(actualDurationD);
    	  if(error>0)errorDuration = "提早"+error+"天";
    	  else errorDuration = "延遲"+String.valueOf(error).substring(1,String.valueOf(error).length())+"天";
    	  System.out.println("Error duration: "+errorDuration);
      }
      if(!"".equals(estimatedDuration))
    	  estimatedDuration = estimatedDrationI + "天";
      if(countProject<3) projectSuggestion.add(estimatedDuration);
      if(countProject<3) projectSuggestion.add(errorDuration);
      
      ITable teamTable = relatedProject.getTable(ProgramConstants.TABLE_TEAM);
      // 3. all the members from each table in a hashmap with number of appearances
      
      for(Object r: teamTable){
    	if(countTeamMember==3)break;
        IRow row = (IRow) r;
        IUser teamMember = (IUser) row.getReferent();
        // Check the quantity of the projects this user have
        IQuery query = (IQuery) session.createObject(IQuery.OBJECT_TYPE, ProgramConstants.CLASS_PROGRAM);
        // Project = Active and  Status = In Process and team have above user
        String queryCritria = "[General Info.Project State] == 'Active'"
				+ " and [General Info.Status] in ( 'Default Activities.In Process' ) " + " and ["
				+ ProgramConstants.ATT_GENERAL_INFO_ACTIVITIES_TYPE + "] == 'Program'"
			    + " and ["+ProgramConstants.ATT_TEAM_NAME+"] == '"+teamMember.toString()+"'";
		query.setCriteria(queryCritria);
		ITable queryResult = query.execute();
		memberSuggestion.add(queryResult.size());
		
		// Check the division of the user
		String userGroupList = "";
		ITable userGroupTable = teamMember.getTable(UserConstants.TABLE_USERGROUP);
		Iterator ugIt = userGroupTable.iterator();
		while(ugIt.hasNext()) {
			IRow ugRow = (IRow) ugIt.next();
			IUserGroup userGroup = (IUserGroup) ugRow.getReferent();
			if(!"".equals(userGroupList)) {
				userGroupList += "<br>"+userGroup.getName();
			}else {
				userGroupList += userGroup.getName();
			}
		}
		memberSuggestion.add(userGroupList);
		memberSuggestion.add(teamMember.getValue(UserConstants.ATT_GENERAL_INFO_FIRST_NAME)
				+" "+teamMember.getValue(UserConstants.ATT_GENERAL_INFO_LAST_NAME));
        countTeamMember++;
      }
      if(countProject<3)finalSuggestion.add(new LinkedList(projectSuggestion));
      countProject++;
    }
    addEmptyInfoToList(finalSuggestion);
    finalSuggestion.add(memberSuggestion);
    
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
  @Override
  protected List addEmptyInfoToList(List<List> infoList) {
		while (infoList.size() < 4) {
			infoList.add(new LinkedList<>());
			for (int i = 0; i < infoList.get(1).size(); i++) {
				infoList.get(infoList.size() - 1).add("n/a");
			}
		}
		return infoList;
	}

}
