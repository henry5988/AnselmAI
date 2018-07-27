import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class BounceHandler implements HttpHandler, Constants {

  private final static String FILE_TO_SEND = "C:\\serverSource\\serverTest.txt";
  private final static String SAVED_FILE = "C:\\serverSource\\saved.txt";
  static File NEWFILE = new File(FILE_TO_SEND);
  static File OLDFILE = new File(SAVED_FILE);

  @Override

  public void handle(HttpExchange he) throws IOException {
    // parse request
    String username ="serverSource";
    Map<String, Object> parameters = new HashMap<String, Object>();
    Headers headers = he.getRequestHeaders();
    URI requestedUri = he.getRequestURI();
    String query = requestedUri.getRawQuery();
    System.out.println("query: " + query);
    if(headers.containsKey("Username")) {
      username = headers.get("Username").get(0);
    }
    if (query!=null){
      username = query.substring(query.indexOf('=')+1,query.length());
    }
    System.out.println("username: " + username);

    EchoGetHandler.parseQuery(query, parameters);
    // send response
    Headers h = he.getResponseHeaders();
    h.set("Content-Type", "text/html");
    String response = "";
    if (isEventTriggered(username)) {

      //response += EchoGetHandler.readFile("C:\\bounce.html", Charset.forName("UTF8"));
      response += "<!DOCTYPE HTML><html><head><script>var objWin=window.self; objWin.open('','_self', '').focus(); popup(\"http://192.168.88.130:1705/echoGet?username="+username+"\"); objWin.close();function popup(mylink) {\n"
          + "                if (! window.focus)return true;\n"
          + "                var href;\n"
          + "                if (typeof(mylink) == 'string') href=mylink;\n"
          + "                else href=mylink.href;\n"
          + "                window.open(href, '_blank', 'location=no,height=550,width=900,scrollbars=yes,status=no, toolbar=no');\n"
          + "                return false;\n"
          + "            } </script></head><body></body></html>";
      OutputStream os = he.getResponseBody();
      he.sendResponseHeaders(200, response.length());
      System.out.println("Bounce request...");
      os.write(response.getBytes());
      os.close();
    } else {
      System.out.println("Empty response");
      he.sendResponseHeaders(204, response.length());
    }
    //overwrite old file
//    System.out.println("overwriting old file..");
//    copyFileUsingStream(NEWFILE, OLDFILE);
  }

  // isEventTriggered
  // return yes if theres a targeted event trigger
  static boolean isEventTriggered(String username) throws IOException {
    String existPath = SuggestionPopup.replaceServerSource(username, EXIST);
    System.out.println("Exist Path: " + existPath);
    File exist = new File(existPath);
    return exist.exists();
  }


  private static void copyFileUsingStream(File source, File dest) throws IOException {
    InputStream is = null;
    OutputStream os = null;
    try {
      is = new FileInputStream(source);
      os = new FileOutputStream(dest);
      byte[] buffer = new byte[1024];
      int length;
      while ((length = is.read(buffer)) > 0) {
        os.write(buffer, 0, length);
      }
    } finally {
      is.close();
      os.close();
    }
  }
}
