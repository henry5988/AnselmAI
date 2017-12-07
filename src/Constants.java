interface Constants{
  //database constants
  String LOCALHOST = "win-ooi3viu801v";
  String USERNAME = "agile";
  String PASSWORD = "tartan";
  String URL = "jdbc:oracle:thin:@"+ LOCALHOST +":1521:agile9";

  // admin client credentials
  String ADMINUSERNAME = "admin";
  String ADMINPASSWORD = "agile936";
  String CLIENTURL = "http://" + LOCALHOST + ":7001/Agile";

  // image path constants
  String NOPATH = "C:\\demo\\images\\noLogo.png";
  String XLSXPATH = "C:\\demo\\images\\excelLogo.png";
  String DOCXPATH = "C:\\demo\\images\\wordLogo.png";
  String PPTXPATH = "C:\\demo\\images\\pptLogo.png";
  String PDFPATH = "C:\\demo\\images\\pdfLogo.png";
  String TXTPATH = "C:\\demo\\images\\txtLogo.jpg";
  String XLSXURL = "https://botw-pd.s3.amazonaws.com/styles/logo-thumbnail/s3/022017/untitled-3_4.jpg?itok=jxziWG7L";
  String DOCXURL = "http://vectorlogo4u.com/wp-content/uploads/2016/06/microsoft-word-vector.png";
  String PPTXURL = "https://cdn.worldvectorlogo.com/logos/microsoft-powerpoint-2013.svg";
  String PDFURL = "https://lh3.googleusercontent.com/8VF8Oom0BIT89x24dFfhjexnQ2EQy_vNx5Qxob9rWxzPDyuc55IzB5POJ1Vcm2Xve4o2=w300";
  String TXTURL = "https://image.freepik.com/free-icon/txt-open-file-format_318-45188.jpg";

  // Download file path
  String DOWNLOADFILEPATH = "C:\\demo\\";

  // Suggestion Display sequence
  // TODO BUG: changing the sequence result in array index error
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
