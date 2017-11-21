import static com.HF.out;

import com.agile.api.APIException;
import com.agile.api.AgileSessionFactory;
import com.agile.api.IAgileSession;
import com.agile.api.IDataObject;
import com.agile.api.ItemConstants;
import com.agile.px.IEventInfo;
import com.agile.px.IObjectEventInfo;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class GetItemAdviceTest {

  final static String URL = "http://agile936d:7001/Agile";
  final static String USERNAME = "admin";
  final static String PASSWORD = "agile936";

  public static void main(String[] args) {
    try {
      GetFilePopup p = new GetFilePopup();

      IAgileSession session = sessionLogin(USERNAME, PASSWORD);
      IEventInfo info = new IObjectEventInfo() {
        @Override
        public IDataObject getDataObject() throws APIException {
          return (IDataObject) session.getObject(ItemConstants.CLASS_DOCUMENT, "D00001");
        }

        @Override
        public int getEventType() throws APIException {
          return 0;
        }

        @Override
        public int getEventTriggerType() throws APIException {
          return 0;
        }

        @Override
        public String getEventName() throws APIException {
          return null;
        }

        @Override
        public String getEventSubscriberName() throws APIException {
          return null;
        }

        @Override
        public String getEventHandlerName() throws APIException {
          return null;
        }

        @Override
        public Map getUserDefinedMap() throws APIException {
          return null;
        }

        @Override
        public void setUserDefinedMap(Map map) throws APIException {

        }
      };
      LinkedList list = p.getItemAdvice(session, info);
      while(list.isEmpty() == false){
        LinkedList fields = (LinkedList) list.pop();
        while(fields.isEmpty() == false){
          out(fields.pop().toString() + ", ");
        }
      }
    } catch (APIException e) {
      out("Error during GetItemAdviceTest()", "err");
    } catch (SQLException e) {
      out("Error during SQL query", "err");
      e.printStackTrace();
    }
  }

  //helper functions
  private static IAgileSession sessionLogin (String username, String password) throws APIException {
    AgileSessionFactory instance = AgileSessionFactory.getInstance(URL);
    HashMap params = new HashMap();
    params.put(AgileSessionFactory.USERNAME, username);
    params.put(AgileSessionFactory.PASSWORD, password);
    return instance.createSession(params);
  }
}