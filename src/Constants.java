interface Constants{
  //database constants
  //String LOCALHOST = "agile936d";
  String LOCALHOST = "WIN-PLQBKCCHG6I";
  String USERNAME = "agile";
  String PASSWORD = "tartan";
  String URL = "jdbc:oracle:thin:@"+ LOCALHOST +":1521:agile9";
  //String LOCALHOST2 = "agile936d2";
  //String USERNAME2 = "agile02";
  String LOCALHOST2 = "aidb";
  String USERNAME2 = "aiUser";
  String PASSWORD2 = "tartan";
  String URL2 = "jdbc:oracle:thin:@"+ LOCALHOST2 +":1521:agile9";
  String MYSQLHOST = "192.168.43.70";
  String MYSQLURL = "jdbc:mariadb://"+ MYSQLHOST +":3306/anselmai";
  String MYSQLUSERNAME = "root";
  String MYSQLPASSWORD = "tartan";
  // admin client credentials
  String ADMINUSERNAME = "admin";
  //String ADMINPASSWORD = "agile936d";
  String ADMINPASSWORD = "agile935";
  String CLIENTURL = "http://" + LOCALHOST + ":7001/Agile";

  // Output File Path
  String EXIST = "C:\\anselmAIWeb\\serverSource\\exist.txt";
  // BOMPopup
  String BOMPOPUP_OUTPUT_PATH = "bomPupup";
  String BOMPOPUP_HTML_TEMPLATE = "C:\\anselmAIWeb\\serverSource\\bomPopup.htm";
  String BOMPOPUP_HTML_OUTPUT = "C:\\anselmAIWeb\\serverSource\\bomPopup.html";
  // ProjectPopup
  String PROJECTPOPUP_OUTPUT_PATH = "creatProjectPopup";
  String PROJECTPOPUP_HTML_TEMPLATE = "C:\\anselmAIWeb\\serverSource\\creatProjectPopup(HR).htm";
  String PROJECTPOPUP_HTML_OUTPUT = "C:\\anselmAIWeb\\serverSource\\creatProjectPopup(HR).html";
  
  String GETFILEPOPUP_OUTPUT_PATH =  "getFilePopup";
  String GETFILEPOPUP__HTML_TEMPLATE = "C:\\anselmAIWeb\\serverSource\\getFilePopup.htm";
  String GETFILEPOPUP_HTML_OUTPUT = "C:\\anselmAIWeb\\serverSource\\getFilePopup.html";
  



}
