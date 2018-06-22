interface Constants{
  //database constants
  //String LOCALHOST = "agile936d";
//  String LOCALHOST = "WIN-PLQBKCCHG6I";
	String LOCALHOST = "192.168.88.125";
  String USERNAME = "agile";
  String PASSWORD = "tartan";
  String URL = "jdbc:oracle:thin:@"+ LOCALHOST +":1521:agile9";
  //String LOCALHOST2 = "agile936d2";
  //String USERNAME2 = "agile02";
  String LOCALHOST2 = "aidb";
  String USERNAME2 = "aiUser";
  String PASSWORD2 = "tartan";
  String URL2 = "jdbc:oracle:thin:@"+ LOCALHOST2 +":1521:agile9";
//  String MYSQLHOST = "LAPTOP-58ASB998";
  String MYSQLURL = "jdbc:mariadb://"+LOCALHOST+":3306/anselmai";
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
  String BOMPOPUP_HTML_TEMPLATE = "C:\\anselmAIWeb\\BOMPopup.htm";
  String BOMPOPUP_HTML_OUTPUT = "C:\\anselmAIWeb\\serverSource\\BOMPopup.htm";
  // ProjectPopup
  String PROJECTPOPUP_OUTPUT_PATH = "C:\\anselmAIWeb\\serverSource\\createProject.txt";
  String PROJECTPOPUP_HTML_TEMPLATE = "C:\\anselmAIWeb\\createProject.htm";
  String PROJECTPOPUP_HTML_OUTPUT = "C:\\anselmAIWeb\\serverSource\\createProject.htm";
}
