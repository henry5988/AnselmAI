import static com.HF.executeSQL;
import static com.HF.getConnection;
import static com.HF.out;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.IUser;
import com.agile.api.ItemConstants;
import com.agile.api.UserConstants;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class privilegeTest implements Constants{
  public static void main (String[] args) {
    IAgileSession session = connect();
    try {
      String itemName = "D00001";
      IUser user = session.getCurrentUser();
      out("user: " + user.getName());
      IItem item = (IItem) session.getObject(ItemConstants.CLASS_ITEM_BASE_CLASS, itemName);
      out("read priv: " + user.hasPrivilege(UserConstants.PRIV_READ, item));
      out("discover priv: " + user.hasPrivilege(UserConstants.PRIV_DISCOVER, item));
      session.close();
    } catch(APIException e){
      // TODO Auto-generated catch block
      out(e.getMessage());
    }
  }

  private static IAgileSession connect() {
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
