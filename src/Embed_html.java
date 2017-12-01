import static com.HF.out;

import java.sql.SQLException;
import java.util.HashMap;

import com.agile.api.*;
import java.util.List;

public class Embed_html implements Constants{
	public static void main (String[] args) {
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

				String itemNumber = row.getValue(1001).toString();
				System.out.println(itemNumber);
				
				item = (IItem) session.getObject(ItemConstants.CLASS_ITEM_BASE_CLASS, itemNumber);
				String html = "<table>" 
						+"	<tbody>"    
						+"		<tr>"  //images
						+"			<td style=\"text-align: center; width:150px;\"><img src=\"" + PDFURL +"\"  style=\" height:80px; width:80px\" ></td>"
						+"          <td style=\"text-align: center; width:150px; \"><img src=\"" + PDFURL +"\"  style=\" height:80px; width:80px\"></td>"
						+"          <td style=\"text-align: center; width:150px; \"><img src=\"" + PDFURL +"\"  style=\" height:80px; width:80px\"></td>"
						+"		</tr>"
						+"		<tr>"  //Name
						+"			<td style=\"text-align: center;\"><a href=\"http://agile936d:7001/Agile/PLMServlet?fromPCClient=true&module=ItemHandler&requestUrl=module%3DItemHandler%26opcode%3DdisplayObject%26classid%3DD00001%26objid%3D6018835%26tabid%3D0%26\">PC00001</a></td>"
						+"			<td style=\"text-align: center;\">CS00001</td>" 
						+"			<td style=\"text-align: center;\">PS00001</td>"
						+"		</tr>" 
						+"		<tr>" //descriptions 
						+"			<td style=\"text-align: center;\">FDA510K法規</td>"
						+"			<td style=\"text-align: center;\">客戶要求規格</td>"
						+"			<td style=\"text-align: center;\">產品規格書</td>"
						+"		</tr>"
						+"		<tr>" //amount
						+"			<td style=\"text-align: center;\">42人看過</td>"
						+"			<td style=\"text-align: center;\">36人看過</td>"
						+"			<td style=\"text-align: center;\">20人看過</td>"
						+"		</tr>"
						+"	</tbody>"  
						+"</table>";
				item.setValue(ItemConstants.ATT_PAGE_TWO_LARGETEXT01, html);
			}
			} catch(APIException e){
				// TODO Auto-generated catch block
				out(e.getMessage());
			} catch (SQLException e) {
			  out(e.getMessage());
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
}

