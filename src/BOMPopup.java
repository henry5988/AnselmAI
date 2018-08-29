import static com.HF.executeSQL;
import static com.HF.extractTop;
import static com.HF.getConnection;

import com.HF;
import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.IChange;
import com.agile.api.IDataObject;
import com.agile.api.IItem;
import com.agile.api.INode;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;
import com.agile.api.NodeConstants;
import com.agile.px.EventActionResult;
import com.agile.px.IEventDirtyCell;
import com.agile.px.IEventDirtyRowUpdate;
import com.agile.px.IEventDirtyTable;
import com.agile.px.IEventInfo;
import com.agile.px.IUpdateTableEventInfo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.commons.dbutils.DbUtils;

public class BOMPopup extends SuggestionPopup {

  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req){
    System.out.println("BOMPopup()...");
    init(session, req, BOMPOPUP_OUTPUT_PATH, BOMPOPUP_HTML_OUTPUT, BOMPOPUP_HTML_TEMPLATE,BOMPOPUP_DATABASE_TABLE, true, false);
    return super.doAction(session, node, req);
  }

  @Override
  protected String checksField() {
    String resultString = "Result: ";
    IEventInfo event = getEventInfo();
    IUpdateTableEventInfo info = (IUpdateTableEventInfo) event;
    try {
      IAgileSession session = getSession();
      IItem item = (IItem) session.getObject(ItemConstants.CLASS_PARTS_CLASS, info.getDataObject());
      ITable table = item.getTable(ItemConstants.TABLE_BOM);
      Iterator<?> it = table.iterator();
      IRow row = (IRow) it.next();
      int cost = (int) row.getValue(ItemConstants.ATT_PAGE_TWO_TEXT02); // base number for cost column in BOM table; can be any other text field
      if(cost <= 0) resultString += "cost of " + row.getReferent().getName() + " is not valid." + String.format("%n");

    } catch (APIException e) {
      System.err.println(e.getMessage());
    }
    if(resultString.equals("Result: ")) return "Column fields are correct.";
    else return resultString;
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
  protected void writeToFile(List<List> infoList) throws IOException{
	  Connection conn_sql;
		
		
		try {
			conn_sql = getMySQLConnection2(Constants.MYSQLUSERNAME, Constants.MYSQLPASSWORD, Constants.MYSQLURL);
			String value ="";
			String column ="";
			String sql;
			List sqlResult= new LinkedList(); ;
			System.out.println(getTargetItem(getEventInfo()).getName());
			sqlResult.add(getTargetItem(getEventInfo()).getName());  
			for(Object a :infoList) {
				System.out.println(a.toString());
			}
			
			for(int i=0; i<3; i++){
			      for(int j = 0; j< infoList.size(); j++){
			    	  sqlResult.add(infoList.get(j).get(i));   	 
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
			
			
			sql = "INSERT INTO "+BOMPOPUP_DATABASE_TABLE+" ("+column+") VALUES ("+value+")" ;
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
		
//	    System.out.println("Matrix Transpose...");
//	    infoList = HF.transposeMatrix(infoList);
//	    System.out.println("Transpose Complete!");
//	    File f = new File(getOutput_path());
//	    String existFile = null;
//	    try {
//	      existFile = replaceServerSource(getSession().getCurrentUser().getName(), EXIST);
//	    } catch (APIException e) {
//	      System.err.println("Error in BOMPopup.writeToFile(): " + e.getMessage());
//	    }
//	    File exist = new File(existFile);
//	    if(!exist.exists()){
//	      Files.createDirectories(Paths.get(exist.getPath()).getParent());
//	      exist.createNewFile();
//	    }
//	    if(!f.exists()){
//	      Files.createDirectories(Paths.get(f.getPath()).getParent());
//	      f.createNewFile();
//	    }
//	
//	    FileWriter existWriter = new FileWriter(exist);
//	    existWriter.write(getHtmlOutput());
//	    existWriter.close();
//	    FileWriter fw = new FileWriter(f);
//	    StringBuilder line = new StringBuilder();
//	    System.out.println(getFieldCheckResponse()+String.format("%n"));
//	    line.append(getFieldCheckResponse());
//	    line.append(String.format("%n"));
//	    try {
//	      System.out.println(getTargetItem(getEventInfo()).getName()+String.format("%n"));
//	      line.append(getTargetItem(getEventInfo()).getName());
//	      line.append(String.format("%n"));
//	    }catch(APIException e){
//	      System.err.println(e.getMessage());
//	    }
//	    for(int i=0; i<infoList.size(); i++){
//	      for(int j = 0; j< infoList.get(i).size(); j++){
//	        line.append(infoList.get(i).get(j) + String.format("%n"));
//	      }
//	    }
//	    fw.write(line.toString()); //TODO BOM data function logic
//	    fw.close();
  }

  @Override
  protected List<List> convertObjectToInfo(List l) throws APIException {

    List<List> info = new LinkedList();
    for(int i=0; i<l.size(); i++){
      List itemInfo = new LinkedList();
      Map.Entry entry = (Entry) l.get(i);
      IItem item = (IItem) entry.getKey();
      String item_url = (String) getSession().getAdminInstance().getNode(NodeConstants.NODE_SERVER_LOCATION)
    		     .getProperty("Web Server URL").getValue()+"?fromPCClient=true&module=ItemHandler&requestUrl=module%3DItemHandler%26opcode%3DdisplayObject%26classid%3D10000%26objid%3D"+item.getObjectId()+"%26tabid%3D0%26";  
      itemInfo.add("<a href=\""+item_url+"\" target=\"_blank\" >"+item.getName()+"</a>");
      itemInfo.add(item.getValue(ItemConstants.ATT_TITLE_BLOCK_DESCRIPTION));
      String itemQty = "n/a";
      IItem target = (IItem) getTargetItem(getEventInfo());
      ITable bom = target.getTable(ItemConstants.TABLE_BOM);
      Iterator it = bom.getTableIterator();
      while(it.hasNext()){
        IRow row = (IRow) it.next();
        if(row.getReferent().getName().equals(item.getName())){
          itemQty = (String) row.getValue(ItemConstants.ATT_BOM_QTY);
        }
      }
      itemInfo.add(itemQty);
      info.add(itemInfo);
    }
    return info;
  }

  @Override
  //TODO getItemAdvice
  protected List getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
			throws APIException, ClassNotFoundException, SQLException {
		// get the item
		HashMap altItemOccurance = new HashMap();
		LinkedList tempItems = null;
		LinkedList altItems = new LinkedList();
		List itemList = null;
		Connection conn = null;
		try {
			conn = getConnection(USERNAME, PASSWORD, URL);

			IUpdateTableEventInfo updateBOMEvent = (IUpdateTableEventInfo) getEventInfo();
			IEventDirtyTable table = updateBOMEvent.getTable();
			Iterator<?> it = table.iterator();
			IEventDirtyRowUpdate rowUpdate = (IEventDirtyRowUpdate) it.next();
			IItem item = (IItem) rowUpdate.getReferent();
			String itemName = item.getName();
			String find = (String) ((IEventDirtyCell) rowUpdate.getCell(ItemConstants.ATT_BOM_FIND_NUM)).getValue();
			System.out.println("Item name: " + itemName + " find num: " + find);
			if (find.equals("0"))
				return null;
			IItem root = (IItem) updateBOMEvent.getDataObject();
			System.out.println("root item Num: " + root);
			setRevtoLatest(root);
			ITable Bom_table = root.getTable(ItemConstants.TABLE_BOM);
			
			StringBuilder sb = new StringBuilder();
			
			Iterator<?> it_bom2 = Bom_table.iterator();
			while (it_bom2.hasNext()) {
				IRow row = (IRow) it_bom2.next();
//				if (row.getValue(ItemConstants.ATT_BOM_FIND_NUM).equals(find)) {
//					if(!row.getValue(ItemConstants.ATT_BOM_ITEM_NUMBER).equals(itemName))
						sb.append("and item_number != '"+ row.getValue(ItemConstants.ATT_BOM_ITEM_NUMBER) + "'");
//				}
			}
			
			Iterator<?> it_bom = Bom_table.iterator();
			while (it_bom.hasNext()) {
				IRow row = (IRow) it_bom.next();
				if (row.getValue(ItemConstants.ATT_BOM_FIND_NUM).equals(find)) {
					System.out.println(row.getValue(ItemConstants.ATT_BOM_ITEM_NUMBER));
					String sql = "select ITEM_NUMBER from bom where (find_number in (select find_number from BOM where item in (select item from BOM where ITEM_NUMBER = '"
							+ row.getValue(ItemConstants.ATT_BOM_ITEM_NUMBER)
							+ "' and change_in != '0') and item_number='"
							+ row.getValue(ItemConstants.ATT_BOM_ITEM_NUMBER)
							+ "' and change_in != '0') and item in (select item from BOM where ITEM_NUMBER = '"
							+ row.getValue(ItemConstants.ATT_BOM_ITEM_NUMBER)
							+ "' and change_in != '0')) and item_number != '"
							+ itemName + "'" + sb.toString();
					System.out.println("2: " + sql);
					tempItems = executeSQL(conn, sql, false);
				}
				if(tempItems==null||tempItems.size()==0)continue;
				for(int i = 0; i < tempItems.size(); i++){
					try{
						IItem get_item_try = (IItem) getSession().getObject(IItem.OBJECT_TYPE, tempItems.get(i));
						altItems.push(tempItems.get(i));
					}catch(APIException e){
						if(e.getErrorCode().toString().equals("407")){
							System.out.println("甈��雲");
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
			System.err.println("SQLException in BOMPopup.getItemAdvice(): " + e.getMessage());
		} finally {
			// DbUtils.closeQuietly(conn);
			conn.close();
		}
		System.out.println("altItems: " + altItems.toString());
		for (int i = 0; i < altItems.size(); i++) {
			if (!altItemOccurance.containsKey(altItems.get(i))){
				
					altItemOccurance.put(getSession().getObject(ItemConstants.CLASS_ITEM_BASE_CLASS, altItems.get(i)),
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
    IUpdateTableEventInfo info = (IUpdateTableEventInfo) req;
    IEventDirtyTable table = info.getTable();
    IDataObject obj = info.getDataObject();
    return obj;
  }
  private IItem setRevtoLatest(IItem item) throws APIException {
		Map revisions = item.getRevisions();
		Set set = revisions.entrySet();
		Iterator it = set.iterator();
		Map.Entry entry = (Map.Entry) it.next();
		String rev = (String) entry.getValue();
		item.setRevision(rev);
		return item;
	}//End of setRevtoLatest
}