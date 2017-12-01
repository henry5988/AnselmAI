interface Constants{
  //database constants
  String USERNAME = "agile";
  String PASSWORD = "tartan";
  String URL = "jdbc:oracle:thin:@win-ooi3viu801v:1521:agile9";

  // admin client credentials
  String ADMINUSERNAME = "admin";
  String ADMINPASSWORD = "agile936";
  String CLIENTURL = "http://win-ooi3viu801v:7001/Agile";

  // image path constants
  String XLSXPATH = "..\\static\\excelLogo.png";
  String DOCXPATH = "..\\static\\wordLogo.png";
  String PPTXPATH = "..\\static\\pptLogo.png";
  String PDFPATH = "..\\static\\pdfLogo.png";
  String TXTPATH = "..\\static\\txtLogo.jpg";
  String XLSXURL = "https://imgur.com/uOJWLje";
  String DOCXURL = "https://imgur.com/8ohTpbp";
  String PPTXURL = "https://imgur.com/ej5QcMy";
  String PDFURL = "https://imgur.com/YjvWXJL";
  String TXTURL = "https://imgur.com/zica4de";

  // Download file path
  String DOWNLOADFILEPATH = "C:\\demo\\";

  // Suggestion Display sequence
  int IMAGEPRINTSQ = 0;
  int NAMEPRINTSQ = 1;
  int DESCRIPTIONPRINTSQ = 2;
  int VIEWERPRINTSQ = 3;

  // utility constants
  int GETFILEEVENTTYPE = 2000011562;
  int CHECKOUTFILEEVENTTYPE = 2000011559;
  String GETFILEACTIONCODE = "15";
  String CHECKOUTFILEACTIONCODE = "22";

  // switches
  Boolean LOCALPATH = true;
  Boolean URLPATH= false;
}
