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
    // convert attachment file object to printed info
    // need file name, description, image, and viewer count
    // list could contain map entries, which would need to be access for keys to get the file names
    // both image and viewer count need their own private algorithm to get
    // image needs to grab the suffix of the file name and find the corresponding file image
    // viewer count needs a aggregate function to add up all relevant views
    Connection conn;
    List info = new LinkedList();
    List images = new LinkedList();
    List descriptions = new LinkedList();
    List names = new LinkedList();

    try {
      conn = getConnection(USERNAME, PASSWORD, URL);
      out("FileSuggestionPopup.convertObjectToInfo()...");
      for (Object f : list) {
        String file = (String) f;
        out("File: " + file);
        String sql =
            "SELECT Attachment.DESCRIPTION FROM ATTACHMENT join bo_attach_versions_history on attachment.id = bo_attach_versions_history.attach_id join files on bo_attach_versions_history.file_id = files.id and files.FILENAME = '"
                + file + "'";
        String description = (String) executeSQL(conn, sql).pop();
        String imageSrc = getImageSrc(file);
        names.add(file);
        descriptions.add(description);
        images.add(imageSrc);
        out("converted file: " + file);
      }
      // the order in which the lists are added does matter as they are being taken out, in the parent class, according to their indices
      info.add(names);
      info.add(images);
      info.add(descriptions);
    } catch (SQLException e) {
      out("Error when establishing database connection", "err");
    }
    out("FileSuggestionPopup.convertObjectToInfo() ends...");
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
      return details.replaceAll("^(\\S*\\s){3}", "");
    }

  }
}
