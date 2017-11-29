import java.util.HashMap;
import java.util.Map;

public class ImagePath implements Constants{
  //fields
  private Map<String, String> imagePath = new HashMap<String, String>();

  //constructor
  ImagePath(){
    imagePath.put("xlsx", XLSXPATH);
    imagePath.put("docx", DOCXPATH);
    imagePath.put("pptx", PPTXPATH);
    imagePath.put("pdf", PDFPATH);
    imagePath.put("txt", TXTPATH);
  }

  //getter
  public static String getXLSXPATH() {
    return XLSXPATH;
  }

  public static String getDOCXPATH() {
    return DOCXPATH;
  }

  public static String getPPTXPATH() {
    return PPTXPATH;
  }

  public static String getPDFPATH() {
    return PDFPATH;
  }

  public static String getTXTPATH() { return TXTPATH; }

  public String getImagePath(String file) {
    String fileType;
    if(file.contains(".")) {
      fileType = file.substring(file.indexOf('.') + 1, file.length());
    }else{
      fileType = file;
    }
    return imagePath.get(fileType);
  }
}
