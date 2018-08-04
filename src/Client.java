import com.HF;
import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Client implements Constants {

  public final static int SOCKET_PORT = 1705;      // you may change this
  public final static String SERVER = "127.0.0.1"; //localhost
  //public final static String SERVER = "192.168.1.202";  // server
  //public final static String SERVER = "192.168.1.122"; // server2
//  public final static String SERVER = "192.168.1.102"; // local server;
//  public final static String SERVER = "172.16.70.139"; 
  public final static String
      FILE_TO_RECEIVED = "C:\\Users\\Riekon\\socket\\web\\source-downloaded.txt";  // you may change this
  public final static String SAVED_FILE = "C:\\saved.txt";
  // might have error for no user privilege need, follow https://answers.microsoft.com/en-us/insider/forum/insider_wintp-insider_files/error-0x80070522-build-10074-a-required-privilege/516f87a8-80a6-4acb-a278-8866b2080460
  // for possible solution
  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
  // should bigger than the file to be downloaded

  private static PrintWriter out;
  private static BufferedReader in;
  private static FileOutputStream fos = null;
  private static BufferedOutputStream bos = null;
  private static Socket sock = null;
  private static InputStream is;

  public static void main (String[] args )
      throws IOException, InterruptedException, SQLException, ClassNotFoundException {
    Date dt = new Date();

    SimpleDateFormat sdf =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String currentTime = sdf.format(dt);

    //get username
    Scanner scanner = new Scanner(System.in);
    String sql;
    PreparedStatement statement;
    System.out.println("Please enter your username:");
    String username = scanner.nextLine();
    System.out.println("username: " + username);
    //TODO implement a username checking mechanic
    Connection conn = HF.getMySQLConnection(Constants.MYSQLUSERNAME, Constants.MYSQLPASSWORD, Constants.MYSQLURL);
    sql = "SELECT * FROM userlogins WHERE username = ?";
    statement = conn.prepareStatement(sql);
    statement.setString(1, username);
    ResultSet r = statement.executeQuery();
    if(!r.next()){
      sql = "INSERT INTO userlogins (username, last_updated) VALUES (?, ?)";
      statement = conn.prepareStatement(sql);
      statement.setString(1, username);
      statement.setString(2, currentTime);
      System.out.println("INSERT: " + sql);
      statement.executeUpdate();
    }else{
      sql = "UPDATE userlogins SET last_updated = ? WHERE username = ?";
      System.out.println("UPDATE: " + sql);
      statement = conn.prepareStatement(sql);
      statement.setString(1, currentTime);
      statement.setString(2, username);
      statement.executeUpdate();
    }
    scanner.close();
    conn.close();
    String url = "http://" + SERVER + ":" + SOCKET_PORT + "/bounce";
    while(true) {

      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      con.setRequestMethod("GET");
      con.addRequestProperty("username", username);
      int responseCode = con.getResponseCode();
      System.out.println("\nSending 'GET' request to URL : " + url);
      System.out.println("Response Code : " + responseCode);
      BufferedReader in = new BufferedReader(
          new InputStreamReader(con.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();

      if(responseCode == 200) {
        Desktop.getDesktop().browse(URI.create(url+"?username="+username));
        Thread.sleep(5000);
      }
      //print result
      System.out.println(response.toString());
      con.disconnect();

    }
  }

  private static boolean isFileReceived(int bytesRead) {
    return bytesRead != -1;
  }

  private static void closeStreams() throws IOException {
    if (in != null) in.close();
    if (out != null) out.close();
    if (is != null) is.close();
    if (fos != null) fos.close();
    if (bos != null) bos.close();
    if (sock != null) sock.close();

  }

  public static String executePost(String targetURL, String urlParameters) {
    HttpURLConnection connection = null;

    try {
      //Create connection
      URL url = new URL(targetURL);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type",
          "application/x-www-form-urlencoded");

      connection.setRequestProperty("Content-Length",
          Integer.toString(urlParameters.getBytes().length));
      connection.setRequestProperty("Content-Language", "en-US");

      connection.setUseCaches(false);
      connection.setDoOutput(true);

      //Send request
      DataOutputStream wr = new DataOutputStream (
          connection.getOutputStream());
      wr.writeBytes(urlParameters);
      wr.close();

      //Get Response
      InputStream is = connection.getInputStream();
      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
      StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
      String line;
      while ((line = rd.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      rd.close();
      return response.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }
}