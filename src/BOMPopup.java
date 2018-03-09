import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.px.IEventInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BOMPopup extends SuggestionPopup {

  @Override
  protected void writeToFile(List<List<String>> infoList, String fileName) throws IOException {

  }

  @Override
  protected List<List<String>> convertObjectToInfo(List l) throws APIException {
    return null;
  }

  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IItem obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    return null;
  }
}
