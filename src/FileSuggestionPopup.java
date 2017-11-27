import static com.HF.countOccurance;
import static com.HF.executeSQL;
import static com.HF.getConnection;
import static com.HF.out;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.px.IEventAction;
import com.agile.px.IEventInfo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public abstract class FileSuggestionPopup extends SuggestionPopup implements IEventAction {

  @Override
  protected abstract LinkedList getItemAdvice(IAgileSession session, IEventInfo req)
      throws SQLException, APIException;

  @Override
  protected List<List<String>> convertObjectToInfo(List list)
      throws APIException {
    // TODO convert attachment file object to printed info
    // TODO need file name, description, image, and viewer count
    // TODO list could contain map entries, which would need to be access for keys to get the file names
    // both image and viewer count need their own private algorithm to get
    // image needs to grab the suffix of the file name and find the corresponding file image
    // viewer count needs a aggregate function to add up all relevant views
    Connection conn;
    List info = new LinkedList();
    List scanned = new LinkedList();
    List fileNames = new LinkedList();
    List images = new LinkedList();
    List descriptions = new LinkedList();
    List viewerCounts = new LinkedList();
    try {
      conn = getConnection(USERNAME, PASSWORD, URL);

      out("GetFilePopup.convertObjectToInfo()...");

      for (Object file : list) {
        String sql =
            "SELECT ATTACHMENT.Description FROM ATTACHMENT JOIN FILES ON FILES.ID = ATTACHMENT.ID WHERE FILES.FILENAME = '"
                + file+ "'";

        String description = (String) executeSQL(conn, sql).pop();
        descriptions.add(description);
        String imageSrc = getImageSrc((String)file);
        images.add(imageSrc);
        if(!scanned.contains(file)) {
          scanned.add(file);
          Integer viewerCount = getViewerCount(list, (String) file);
          viewerCounts.add(viewerCounts.toString());
        }
      }
      info.add(descriptions);
      info.add(images);
      info.add(viewerCounts);


    } catch (SQLException e) {
      out("Error when establishing database connection", "err");
    }
    return (List<List<String>>) info;
  }

  protected String getImageSrc(String file) {
    String imageType = file.substring(file.indexOf('.') + 1, file.length());
    ImagePath ip = new ImagePath();
    return ip.getImagePath(imageType);
  }

  protected Integer getViewerCount(List fileList, String file){
    return countOccurance(fileList, file, 0);
  }

  protected class StringParser {

    String buildDetails(String folderNum, String folderVer, String fileName) {
      return folderNum + " V " + folderVer + " " + fileName;
    }

    String getDetailsFileName(String details) {
      return details.substring(details.lastIndexOf(' ') + 1, details.length());
    }

  }
}
