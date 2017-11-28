import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CheckOutFilePopup extends FileSuggestionPopup{

  @Override
  protected List getAttachmentAdvice(Connection conn, IEventDirtyFile file,
      IAgileSession session) throws APIException, SQLException {
    setActionCode("22");
    return null;
  }
}
