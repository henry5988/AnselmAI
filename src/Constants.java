/* Constants used in the system, including paths for images,
   download directory, and database credentials.*/

interface Constants{
  //database constants
  String USERNAME = "agile";
  String PASSWORD = "tartan";
  String URL = "jdbc:oracle:thin:@win-ooi3viu801v:1521:agile9";

  // image path constants
  String XLSXPATH = "..\\static\\excelLogo.png";
  String DOCXPATH = "..\\static\\wordLogo.png";
  String PPTXPATH = "..\\static\\pptLogo.png";
  String PDFPATH = "..\\static\\pdfLogo.png";
  String TXTPATH = "..\\static\\txtLogo.jpg";

  // Download file path
  String DOWNLOADFILEPATH = "C:\\demo\\";


  // utility constants
  int GETFILEEVENTTYPE = 2000011562;
  int CHECKOUTFILEEVENTTYPE = 2000011559;
  String GETFILEACTIONCODE = "15";
  String CHECKOUTFILEACTIONCODE = "22";
}
