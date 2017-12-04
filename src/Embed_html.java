import static com.HF.executeSQL;
import static com.HF.getConnection;
import static com.HF.out;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import com.agile.api.*;
import java.util.LinkedList;
import java.util.List;

public class Embed_html implements Constants{
	public static void main (String[] args) throws SQLException {
		IAgileSession session = connect();
		try {
			ItemSuggestion is = new ItemSuggestion();

			IQuery query = (IQuery) session.createObject(IQuery.OBJECT_TYPE, "SELECT " +
					"[1001]" +
					"FROM " +
					"[Items]" +
					"WHERE " +
					"[1001]!= 'null'");

			ITable results = query.execute();
			ITwoWayIterator it = results.getTableIterator();

			while (it.hasNext()) {
				IRow row = (IRow) it.next();
				IItem item = (IItem) row.getReferent();
				List suggestions = is.getItemSuggestion(session, item);
				List<List> infoList = is.convertItemsToInfo(suggestions);
				List images = infoList.get(IMAGEPRINTSQ);
				List names = infoList.get(NAMEPRINTSQ);
				List descriptions = infoList.get(DESCRIPTIONPRINTSQ);
				List viewerCounts = infoList.get(VIEWERPRINTSQ);
        out("image url: " + images.get(0));
				String itemNumber = row.getValue(1001).toString();
				System.out.println(itemNumber);
				
				item = (IItem) session.getObject(ItemConstants.CLASS_ITEM_BASE_CLASS, itemNumber);
				String html = "<table>" 
						+"	<tbody>"    
						+"		<tr>"  //images
						+"			<td style=\"text-align: center; width:150px;\"><img src=\"" + images.get(0) +"\"  style=\" height:80px; width:80px\" ></td>"
						+"          <td style=\"text-align: center; width:150px; \"><img src=\"" + images.get(1) +"\"  style=\" height:80px; width:80px\"></td>"
						+"          <td style=\"text-align: center; width:150px; \"><img src=\"" + images.get(2) +"\"  style=\" height:80px; width:80px\"></td>"
						+"		</tr>"
						+"		<tr>"  //Name
						+       nameHTML((String) names.get(0))
						+       nameHTML((String) names.get(1))
						+       nameHTML((String) names.get(2))
						+"		</tr>" 
						+"		<tr>" //descriptions 
						+"			<td style=\"text-align: center;\">"+ descriptions.get(0) +"</td>"
						+"			<td style=\"text-align: center;\">"+ descriptions.get(1) +"</td>"
						+"			<td style=\"text-align: center;\">"+ descriptions.get(2) +"</td>"
						+"		</tr>"
						+"		<tr>" //amount
						+"			<td style=\"text-align: center;\">"+ viewerCounts.get(0).toString() +" 人看過</td>"
						+"			<td style=\"text-align: center;\">"+ viewerCounts.get(1).toString() +" 人看過</td>"
						+"			<td style=\"text-align: center;\">"+ viewerCounts.get(2).toString() +" 人看過</td>"
						+"		</tr>"
						+"	</tbody>"  
						+"</table>";
				item.setValue(ItemConstants.ATT_PAGE_TWO_LARGETEXT01, html);
			}
			} catch(APIException | SQLException e){
				// TODO Auto-generated catch block
				out(e.getMessage());
			} catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
	
	static IAgileSession connect() {
		IAgileSession session = null;
		try {
			HashMap params = new HashMap(); 
			params.put(AgileSessionFactory.USERNAME, ADMINUSERNAME);
			params.put(AgileSessionFactory.PASSWORD, ADMINPASSWORD);
			AgileSessionFactory factory;
			factory = AgileSessionFactory.getInstance(CLIENTURL);
			session = factory.createSession(params);
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		return session;
	}

	private static String nameHTML(String name) throws SQLException, ClassNotFoundException {
		String sql = "select ID from ITEM where ITEM_NUMBER = '" + name + "'";
		Connection conn = getConnection(USERNAME, PASSWORD, URL);
		String ID = executeSQL(conn, sql).pop();
    conn.close();
		return "<td style=\"text-align: center;\"><a href=\"http://" + LOCALHOST + ":7001/Agile/PLMServlet?fromPCClient=true&module=ItemHandler&requestUrl=module%3DItemHandler%26opcode%3DdisplayObject%26classid%3D10000%26objid%3D"+ ID +"%26tabid%3D0%26\">"+ name +"</a></td>";
	}
}

