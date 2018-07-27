interface Constants{
  //database constants
//  String LOCALHOST = System.getenv("COMPUTERNAME");
//  String LOCALHOST = "192.168.88.130";
  String LOCALHOST = "WIN-OOI3VIU801V";
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
  String MYSQLURL = "jdbc:mariadb://192.168.88.125:3306/anselmai";
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
  String BOMPOPUP_OUTPUT_PATH = "C:\\anselmAIWeb\\serverSource\\bomPupup.txt";
  String BOMPOPUP_HTML_TEMPLATE = "C:\\anselmAIWeb\\serverSource\\bomPopup.html";
  String BOMPOPUP_HTML_OUTPUT = "C:\\anselmAIWeb\\serverSource\\bomPopup.html";
  // ProjectPopup
  String PROJECTPOPUP_OUTPUT_PATH = "C:\\anselmAIWeb\\serverSource\\createProject.txt";
  String PROJECTPOPUP_HTML_TEMPLATE = "C:\\anselmAIWeb\\createProject.htm";
  String PROJECTPOPUP_HTML_OUTPUT = "C:\\anselmAIWeb\\serverSource\\createProject.htm";
}
