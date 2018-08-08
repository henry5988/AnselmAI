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
  String MYSQLURL = "jdbc:mariadb://"+ LOCALHOST +":3306/anselmai";
  String MYSQLUSERNAME = "root";
  String MYSQLPASSWORD = "tartan";
  // admin client credentials
  String ADMINUSERNAME = "admin";
  //String ADMINPASSWORD = "agile936d";
  String ADMINPASSWORD = "agile936d";
  String CLIENTURL = "http://" + LOCALHOST + ":7001/Agile";

  // Output File Path
  String EXIST = "C:\\anselmAIWeb\\serverSource\\exist.txt";
  // BOMPopup
  String BOMPOPUP_OUTPUT_PATH = "C:\\anselmAIWeb\\serverSource\\bomPupup.txt";
  String BOMPOPUP_HTML_TEMPLATE = "C:\\anselmAIWeb\\serverSource\\bomPopup.htm";
  String BOMPOPUP_HTML_OUTPUT = "C:\\anselmAIWeb\\serverSource\\bomPopup.html";
  String BOMPOPUP_DATABASE_TABLE = "bomPopup";
  // ProjectPopup
  String PROJECTPOPUP_OUTPUT_PATH = "C:\\anselmAIWeb\\serverSource\\creatProjectPopup.txt";
  String PROJECTPOPUP_HTML_TEMPLATE = "C:\\anselmAIWeb\\serverSource\\creatProjectPopup(HR).htm";
  String PROJECTPOPUP_HTML_OUTPUT = "C:\\anselmAIWeb\\serverSource\\creatProjectPopup(HR).html";
  String PROJECTPOPUP_DATABASE_TABLE = "creatProjectPopup";
  
  String GETFILEPOPUP_OUTPUT_PATH =  "C:\\anselmAIWeb\\serverSource\\getFilePopup.txt";
  String GETFILEPOPUP_HTML_TEMPLATE = "C:\\anselmAIWeb\\serverSource\\getFilePopup.htm";
  String GETFILEPOPUP_HTML_OUTPUT = "C:\\anselmAIWeb\\serverSource\\getFilePopup.html";
  String GETFILEPOPUP_DATABASE_TABLE = "getFilePopup";
  
  String CAPA_OUTPUT_PATH =  "C:\\anselmAIWeb\\serverSource\\CAPAPopup.txt";
  String CAPA_HTML_TEMPLATE = "C:\\anselmAIWeb\\serverSource\\CAPAPopup.htm";
  String CAPA_HTML_OUTPUT = "C:\\anselmAIWeb\\serverSource\\CAPAPopup.html";
  String CAPA_DATABASE_TABLE = "CAPAPopup";



}
