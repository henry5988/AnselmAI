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
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class BounceHandler implements HttpHandler {

  private final static String FILE_TO_SEND = "C:\\serverSource\\serverTest.txt";
  private final static String SAVED_FILE = "C:\\serverSource\\saved.txt";
  private static File NEWFILE = new File(FILE_TO_SEND);
  private static File OLDFILE = new File(SAVED_FILE);

  @Override

  public void handle(HttpExchange he) throws IOException {
    // parse request
    Map<String, Object> parameters = new HashMap<String, Object>();
    URI requestedUri = he.getRequestURI();
    String query = requestedUri.getRawQuery();
    EchoGetHandler.parseQuery(query, parameters);

    // send response
    Headers h = he.getResponseHeaders();
    h.set("Content-Type", "text/html");
    String response = "";
    if (isEventTriggered()) {
      response += EchoGetHandler
          .readFile("C:\\bounce.html", Charset.defaultCharset());
      for (String key : parameters.keySet())
        response += " = " + parameters.get(key) + "\n";
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
    System.out.println("overwriting old file..");
    copyFileUsingStream(NEWFILE, OLDFILE);
  }

  // isEventTriggered
  // return yes if theres a targeted event trigger
  static boolean isEventTriggered() throws IOException {
    return !FileUtils.contentEquals(OLDFILE, NEWFILE);
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
