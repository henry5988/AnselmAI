import java.awt.Desktop;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Client {

  public final static int SOCKET_PORT = 1705;      // you may change this
  //public final static String SERVER = "127.0.0.1"; //localhost
  public final static String SERVER = "192.168.1.111";  // shinih server
  //public final static String SERVER = "192.168.1.122"; // server2
  //public final static String SERVER = "192.168.1.127"; // local server
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
      throws IOException, ScriptException, URISyntaxException, InterruptedException {
    Desktop current = Desktop.getDesktop();

    String url = "http://" + SERVER + ":" + SOCKET_PORT + "/bounce";
    //current.browse(URI.create(url));
    while(true) {

      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      con.setRequestMethod("GET");
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
        //TODO Need to let client browse bounce page
        try (PrintWriter out = new PrintWriter("bounce.html")) {
          out.println(response.toString());
        }
        File f = new File("bounce.html");
//        if( Desktop.isDesktopSupported() ) {
//          Thread t = new Thread();
//          t.start();
//            try {
        Desktop.getDesktop().browse(URI.create("http://192.168.1.122:1705/bounce"));
//            } catch (IOException e1) {
//              e1.printStackTrace();
//            }
          Thread.sleep(5000);
//          t.join();
//        }
        if(f.delete()){
          System.out.println("bounce html deleted after opening");
        }
      }
      //print result
      System.out.println(response.toString());
//      int bytesRead = -1;
//      int current;
//      byte[] mybytearray;
//
//      try {
//        sock = new Socket(SERVER, SOCKET_PORT);
//        System.out.println("Connecting...");
//        // receive file
//        do {
//          mybytearray = new byte[FILE_SIZE];
//          is = sock.getInputStream();
//          in = new BufferedReader(new InputStreamReader(is));
//          out = new PrintWriter(sock.getOutputStream(), true);
//          fos = new FileOutputStream(FILE_TO_RECEIVED);
//          bos = new BufferedOutputStream(fos);
//          System.out.println("Receiving file");
//          bytesRead = is.read(mybytearray, 0, mybytearray.length);
//        } while (!isFileReceived(bytesRead));
//
//        System.out.println("File Received!");
//        current = bytesRead;
//        do {
//          bytesRead =
//              is.read(mybytearray, current, (mybytearray.length - current));
//          if (bytesRead >= 0)
//            current += bytesRead;
//        } while (bytesRead > -1);
//        bos.write(mybytearray, 0, current);
//        bos.flush();
//        System.out.println("File " + FILE_TO_RECEIVED + " downloaded (" + current + " bytes read)");
//      } catch (UnknownHostException e) {
//        System.out.println("Unknown Host: " + SERVER);
//        System.exit(1);
//      } catch (IOException e) {
//        System.out.println("No I/O");
//        System.exit(1);
//      } finally {
//        closeStreams();
//      }
//      File htmlFile = new File("http://"+SERVER+":"+ SOCKET_PORT);
//      System.out.println("Opening Popup Window...");
//      //Desktop.getDesktop().browse(htmlFile.toURI());
//     // File f = new File("http://" + SERVER + ":" + SOCKET_PORT + "/socket/web/index.html");
//      Desktop.getDesktop().browse(URI.create("http://" + SERVER + ":" + SOCKET_PORT + "/echoGet"));
//      //System.out.println(executePost("http://" + SERVER + ":" + SOCKET_PORT + "/socket/web/index.html", ""));
//
//      System.out.println("Done.");
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