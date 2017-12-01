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
    }else{
      imagePath.put("xlsx", XLSXURL);
      imagePath.put("docx", DOCXURL);
      imagePath.put("pptx", PPTXURL);
      imagePath.put("pdf", PDFURL);
      imagePath.put("txt", TXTURL);
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
      IAttachmentFile file = (IAttachmentFile) row;
      out("file type detected: " + file.toString());
      String fileType = getFileTypeExtension(file.toString());
      fileList.add(fileType);
    }
    for (Object s : fileList) {
      if(!fileTypes.containsKey(s)){
        fileTypes.put(s, Collections.frequency(fileList, s));
      }
    }
    return (String) extractTop(fileTypes, 1).get(0);
  }
}
