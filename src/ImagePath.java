import static com.HF.extractTop;
import static com.HF.getFileTypeExtension;
import static com.HF.out;

import com.agile.api.APIException;
import com.agile.api.IAttachmentContainer;
import com.agile.api.IAttachmentFile;
import com.agile.api.IItem;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ITwoWayIterator;
import com.agile.api.ItemConstants;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ImagePath implements Constants{
  //fields
  private Map<String, String> imagePath = new HashMap<String, String>();

  //constructor
  ImagePath(Boolean dir){
    if(dir) {
      imagePath.put("xlsx", XLSXPATH);
      imagePath.put("docx", DOCXPATH);
      imagePath.put("pptx", PPTXPATH);
      imagePath.put("pdf", PDFPATH);
      imagePath.put("txt", TXTPATH);
      imagePath.put("null", NOPATH);
    }else{
      imagePath.put("xlsx", XLSXURL);
      imagePath.put("docx", DOCXURL);
      imagePath.put("pptx", PPTXURL);
      imagePath.put("pdf", PDFURL);
      imagePath.put("txt", TXTURL);
      imagePath.put("null", NOURL);
    }
  }

  public String getImagePath(String file) {
    String fileType;
    if(file.contains(".")) {
      fileType = getFileTypeExtension(file);
    }else{
      fileType = file;
    }
    return imagePath.get(fileType);
  }

  public String getImagePath(IItem item) throws APIException {
    Map fileTypes = new HashMap();
    List fileList = new LinkedList<String>();
    ITable attachments = item.getAttachments();
    ITwoWayIterator it = attachments.getTableIterator();
    IRow row;
    for(; it.hasNext();){
      row = (IRow) it.next();
      row.getName();
      //IAttachmentFile file = (IAttachmentFile) row;
      out("file type detected: " + row.getName());
      String fileType = getFileTypeExtension(row.getName());
      fileList.add(fileType);
    }
    for (Object o : fileList) {
      if(!fileTypes.containsKey(o)) fileTypes.put(o, 1);
      else{
        Integer oldValue = (Integer) fileTypes.get(o);
        Integer newValue = oldValue + 1;
        fileTypes.replace(o, oldValue, newValue);
      }
    }
    String topFileType = (String) ((Entry) extractTop(fileTypes, 1).get(0)).getKey();

    return imagePath.get(topFileType);
  }
}
