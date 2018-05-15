interface Constants{
  //database constants
  //String LOCALHOST = "agile936d";
  String LOCALHOST = "win-ooi3viu801v";
  String USERNAME = "agile";
  String PASSWORD = "tartan";
  String URL = "jdbc:oracle:thin:@"+ LOCALHOST +":1521:agile9";
  //String LOCALHOST2 = "agile936d2";
  //String USERNAME2 = "agile02";
  String LOCALHOST2 = "aidb";
  String USERNAME2 = "aiUser";
  String PASSWORD2 = "tartan";
  String URL2 = "jdbc:oracle:thin:@"+ LOCALHOST2 +":1521:agile9";
  String MYSQLHOST = "LAPTOP-58ASB998";
  String MYSQLURL = "jdbc:MySQL://localhost:3306/anselmai";
  // admin client credentials
  String ADMINUSERNAME = "admin";
  //String ADMINPASSWORD = "agile936d";
  String ADMINPASSWORD = "agile936d";
  String CLIENTURL = "http://" + LOCALHOST + ":7001/Agile";

  // Output File Path
  String EXIST = "C:\\serverSource\\exist.txt";
  // BOMPopup
  String BOMPOPUP_OUTPUT_PATH = "C:\\serverSource\\bomPupup.txt";
  String BOMPOPUP_HTML_TEMPLATE = "C:\\BOMPopup.htm";
  String BOMPOPUP_HTML_OUTPUT = "C:\\serverSource\\BOMPopup.htm";
  // ProjectPopup
  String PROJECTPOPUP_OUTPUT_PATH = "C:\\serverSource\\createProject.txt";
  String PROJECTPOPUP_HTML_TEMPLATE = "C:\\createProject.htm";
  String PROJECTPOPUP_HTML_OUTPUT = "C:\\serverSource\\createProject.htm";
}
