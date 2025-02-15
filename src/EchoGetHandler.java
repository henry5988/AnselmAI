import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EchoGetHandler implements HttpHandler, Constants {

  @Override

  public void handle(HttpExchange he) throws IOException {
    int count = 0;
    // parse request
    Map<String, Object> parameters = new HashMap<String, Object>();
    URI requestedUri = he.getRequestURI();
    String query = requestedUri.getRawQuery();
    parseQuery(query, parameters);

    // parse file to response


    // send response
    Headers h = he.getResponseHeaders();
    h.set("Content-Type", "text/html; charset=big5");
    String response = "";
    //response += "<script>window.open(window.location.href, '_blank', 'location=yes,height=570,width=520,scrollbars=yes,status=yes')</script>";
    he.getResponseHeaders();
    he.sendResponseHeaders(200, (long) response.length());
    //response +="<body>";
    //response+= "<body><a id=\"link\" href=\"http://192.168.1.119:1978/echoGet/1\"></a>";
    //response += "<h1>"+readFile("C://serverTest.txt", Charset.defaultCharset())+"</h1></body>";
    response += readFile(responseHTML((String) parameters.get("username")), Charset.forName("big5"));
    OutputStream os = he.getResponseBody();

      System.out.println("Get request...");
      os.write(response.getBytes());
      //System.out.println("response: " + response);
      //System.out.println("overwriting old file..");
      //PrintWriter writer = new PrintWriter(new File(Client.SAVED_FILE));
      //writer.print(response);
      //writer.flush();
    os.close();
  }

  private String responseHTML(String username) throws IOException {
    String responseHTML;
    //File file = getLatestFilefromDir("C:\\");
    System.out.println("Latest File: " + username);
    String existFile = "C:\\anselmAIWeb\\"+username+"\\exist.txt";
    String content = readFile(existFile, Charset.defaultCharset());
    System.out.println(content);
    responseHTML = content;
    System.out.println(responseHTML);
    Files.delete(Paths.get(existFile));
    if(Files.exists(Paths.get(existFile))){
      System.out.println("exist file did not get deleted");
    }
    return responseHTML;
  }

  public static void parseQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {

    if (query != null) {
      String pairs[] = query.split("[&]");
      for (String pair : pairs) {
        String param[] = pair.split("[=]");
        String key = null;
        String value = null;
        if (param.length > 0) {
          key = URLDecoder.decode(param[0],
              System.getProperty("file.encoding"));
        }

        if (param.length > 1) {
          value = URLDecoder.decode(param[1],
              System.getProperty("file.encoding"));
        }

        if (parameters.containsKey(key)) {
          Object obj = parameters.get(key);
          if (obj instanceof List<?>) {
            List<String> values = (List<String>) obj;
            values.add(value);

          } else if (obj instanceof String) {
            List<String> values = new ArrayList<String>();
            values.add((String) obj);
            values.add(value);
            parameters.put(key, values);
          }
        } else {
          parameters.put(key, value);
        }
      }
    }
  }

  static String readFile(String path, Charset encoding)
      throws IOException
  {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }

  private File getLatestFilefromDir(String dirPath){
    File dir = new File(dirPath);
    File[] files = dir.listFiles();
    if (files == null || files.length == 0) {
      return null;
    }

    File lastModifiedFile = files[0];
    for (int i = 1; i < files.length; i++) {
      if (lastModifiedFile.lastModified() < files[i].lastModified()) {
        lastModifiedFile = files[i];
      }
    }
    return lastModifiedFile;
  }
}