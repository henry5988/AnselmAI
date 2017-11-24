import java.util.HashMap;
import java.util.Map;

public class ImagePath {
  //fields
  private final static String XLSXPATH = "..\\static\\xlsx.png";
  private final static String DOCXPATH = "..\\static\\docx.png";
  private final static String PPTXPATH = "..\\static\\pptx.png";
  private final static String PDFPATH = "..\\static\\pdf.png";
  private Map<String, String> imagePath = new HashMap<String, String>();

  //constructor
  ImagePath(){
    imagePath.put("xlsx", XLSXPATH);
    imagePath.put("docx", DOCXPATH);
    imagePath.put("pptx", PPTXPATH);
    imagePath.put("pdf", PDFPATH);
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

  public String getImagePath(String fileType) {
    return imagePath.get(fileType);
  }
}
