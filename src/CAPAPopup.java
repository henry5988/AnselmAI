import static com.HF.executeSQL;
import static com.HF.extractTop;
import static com.HF.getConnection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import com.HF;
import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IDataObject;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.IProgram;
import com.agile.api.IQualityChangeRequest;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;
import com.agile.api.NodeConstants;
import com.agile.api.ProgramConstants;
import com.agile.api.QualityChangeRequestConstants;
import com.agile.px.EventActionResult;
import com.agile.px.IEventDirtyCell;
import com.agile.px.IEventDirtyRowUpdate;
import com.agile.px.IEventDirtyTable;
import com.agile.px.IEventInfo;
import com.agile.px.IObjectEventInfo;
import com.agile.px.IUpdateTableEventInfo;

public class CAPAPopup extends SuggestionPopup {

	@Override
	public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req) {
		System.out.println("CAPAPopup()...");
		init(session, req, CAPA_OUTPUT_PATH, CAPA_HTML_OUTPUT, CAPA_HTML_TEMPLATE,CAPA_DATABASE_TABLE, true, false);
		return super.doAction(session, node, req);
	}

	@Override
	protected String checksField() {
			return null;
	}
	  public static Connection getMySQLConnection2(String username, String password, String url)
		      throws SQLException, ClassNotFoundException,Exception {
		    Connection conn;
		    Class.forName("org.mariadb.jdbc.Driver");
		    Properties connectionProps = new Properties();
		    connectionProps.put("user", username);
		    connectionProps.put("password", password);
		    conn = DriverManager.getConnection(url, connectionProps);
		    System.out.println("Connection established");
		    return conn;
	  }
	@Override
	protected void writeToFile(List<List> infoList) throws IOException {
		 Connection conn_sql;
			
			
			try {
				conn_sql = getMySQLConnection2(Constants.MYSQLUSERNAME, Constants.MYSQLPASSWORD, Constants.MYSQLURL);
				String value ="";
				String column ="";
				String sql;
				List sqlResult= new LinkedList(); ;
				//sqlResult.add(getTargetItem(getEventInfo()).getName());   
				for(int i=0; i<infoList.size(); i++){
				      for(int j = 0; j< infoList.get(0).size(); j++){
				    	  sqlResult.add(infoList.get(i).get(j));   	 
				      }
				    }
				for(int i=0; i<sqlResult.size();i++){	
					if (i==0) {
						column += "column"+String.valueOf(i+1);
						value += "'"+sqlResult.get(i)+"'";
					}
					else {
						column += ", column"+String.valueOf(i+1);
						value +=  ", '"+sqlResult.get(i)+"'";
					}
						
				}
				
				
				sql = "INSERT INTO "+CAPA_DATABASE_TABLE+" ("+column+") VALUES ("+value+")" ;
				System.out.println(sql);
				Statement stat = conn_sql.createStatement();
				stat.executeQuery(sql);
				conn_sql.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	protected List<List> convertObjectToInfo(List l) throws APIException {

		List<List> info = new LinkedList();
		for (int i = 0; i < l.size(); i++) {
			List itemInfo = new LinkedList();
			Map.Entry entry = (Entry) l.get(i);
			IQualityChangeRequest qcr = (IQualityChangeRequest) entry.getKey();
			String item_url = (String) getSession().getAdminInstance().getNode(NodeConstants.NODE_SERVER_LOCATION)
					.getProperty("Web Server URL").getValue()
					+ "?fromPCClient=true&module=QCRHandler&requestUrl=module%3DQCRHandler%26opcode%3DdisplayObject%26classid%3D4928%26objid%3D"
					+ qcr.getObjectId() + "%26tabid%3D0%26";
			itemInfo.add("<a href=\"" + item_url + "\" target=\"_blank\" >" + qcr.getName() + "</a>");
			itemInfo.add(qcr.getValue(QualityChangeRequestConstants.ATT_COVER_PAGE_DESCRIPTION));
			String person = "n/a";
			person = qcr.getCell(QualityChangeRequestConstants.ATT_COVER_PAGE_ORIGINATOR).getReferent().getName();
			itemInfo.add(person);
			info.add(itemInfo);
		}
		return info;
	}

	@Override
	// TODO getItemAdvice
	protected List getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
			throws APIException, ClassNotFoundException, SQLException {
		// get the item
		HashMap altItemOccurance = new HashMap();
		LinkedList tempItems1 = null;
		LinkedList tempItems2 = null;
		LinkedList altItems = new LinkedList();
		List itemList = null;
		Connection conn = null;
		try {
			conn = getConnection(USERNAME, PASSWORD, URL);

			IObjectEventInfo objinfo = (IObjectEventInfo) getEventInfo();
			IProgram rootProgram = (IProgram) objinfo.getDataObject();
			
			String Taskname = rootProgram.getValue(ProgramConstants.ATT_GENERAL_INFO_NAME).toString();
			String TaskID = rootProgram.getObjectId().toString();
			
			String SQL1 = "select qcr.qcr_number from (select relationship.eff_objid,relationship.eff_objtype from activity INNER JOIN relationship on activity.id=relationship.ctr_objid and relationship.eff_objtype = '4928' and relationship.ctr_objid != '"
					+ TaskID + "' and activity.name = '" + Taskname + "') A inner join qcr on A.eff_objid = qcr.id";

			String SQL2 = "select qcr.qcr_number from (select relationship.ctr_objid,relationship.ctr_objtype from activity INNER JOIN relationship on activity.id=relationship.eff_objid and relationship.ctr_objtype = '4928' and relationship.ctr_objid != '"
					+ TaskID + "' and activity.name = '" + Taskname + "') A inner join qcr on A.ctr_objid = qcr.id";
			tempItems1 = executeSQL(conn, SQL1, false);
			if(tempItems1!=null||tempItems1.size()!=0){
				for(int i = 0; i < tempItems1.size(); i++){
					try{
						IQualityChangeRequest get_qcr_try = (IQualityChangeRequest) getSession().getObject(IQualityChangeRequest.OBJECT_TYPE, tempItems1.get(i));
						altItems.push(tempItems1.get(i));
					}catch(APIException e){
						if(e.getErrorCode().toString().equals("407")){
							System.out.println("權限不足");
							continue;
						}
						e.printStackTrace();
						throw e;
					}
				}
			}
			tempItems2 = executeSQL(conn, SQL1, false);
			if(tempItems2!=null||tempItems2.size()!=0){
				for(int i = 0; i < tempItems2.size(); i++){
					try{
						IQualityChangeRequest get_qcr_try = (IQualityChangeRequest) getSession().getObject(IQualityChangeRequest.OBJECT_TYPE, tempItems2.get(i));
						altItems.push(tempItems2.get(i));
					}catch(APIException e){
						if(e.getErrorCode().toString().equals("407")){
							System.out.println("權限不足");
							continue;
						}
						e.printStackTrace();
						throw e;
					}
				}
			}
			System.out.println(altItems);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Exception in CAPAPopup.getItemAdvice(): " + e.getMessage());
		} finally {
			// DbUtils.closeQuietly(conn);
			conn.close();
		}
		System.out.println("altItems: " + altItems.toString());
		for (int i = 0; i < altItems.size(); i++) {
			if (!altItemOccurance.containsKey(altItems.get(i))) {
				altItemOccurance.put(getSession().getObject(IQualityChangeRequest.OBJECT_TYPE, altItems.get(i)),
						Collections.frequency(altItems, altItems.get(i)));
				
			}
		}
		itemList = extractTop(altItemOccurance, 3);
		System.out.println("returned: " + itemList.toString());
		return itemList;
	}

	@Override
	protected IAgileObject getTargetItem(IEventInfo req) throws APIException {
		// get the target item
		IObjectEventInfo info = (IObjectEventInfo) req;
	    IDataObject obj = info.getDataObject();
	    return obj;
	}

}
