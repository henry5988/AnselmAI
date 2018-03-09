import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;

class ThreadedEchoServer {


  static final int PORT = 1705;
  private static BufferedReader in;
  private static PrintWriter out;
  private static Socket sock;
  private static FileInputStream fis = null;
  private static BufferedInputStream bis = null;
  private static OutputStream os = null;

  public static void main(String args[]) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
    server.createContext("/", new RootHandler());
    server.createContext("/echoHeader", new EchoHeaderHandler());
    server.createContext("/echoGet", new EchoGetHandler());
    server.createContext("/echoPost", new EchoPostHandler());
    server.createContext("/bounce", new BounceHandler());
    server.createContext("/bomPopup", new BOMPopupHandler());
    server.setExecutor(null);
    server.start();
    System.out.println("Server starts...");

//    try {
//      serverSocket = new ServerSocket(PORT+1);
//    } catch (IOException e) {
//      e.printStackTrace();
//
//    }
//
//    while (true) {
//      try {
//        System.out.println("waiting...");
//        sock = serverSocket.accept();
//        System.out.println("Accepted connection : " + sock);
//        // if there's no new update in the event table database, then return and tell client to request again
//        // send file
//        // check if event triggered
//        while(!isEventTriggered()) {
//          // System.out.println("No event");
//          // Thread.yield();
//          // new thread for a client
//        }
//        System.out.println("Event");
//        File myFile = new File (FILE_TO_SEND);
//        byte [] mybytearray  = new byte [(int)myFile.length()];
//        fis = new FileInputStream(myFile);
//        bis = new BufferedInputStream(fis);
//        bis.read(mybytearray,0,mybytearray.length);
//        os = sock.getOutputStream();
//        System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
//        os.write(mybytearray,0,mybytearray.length);
//
//        System.out.println("overwriting old file..");
//        PrintWriter writer = new PrintWriter(new File(SAVED_FILE));
//        writer.print(new String(mybytearray));
//        writer.flush();
//        os.flush();
//        System.out.println("Done.");
//      }catch(SocketException e){
//        System.out.println(e);
//        continue;
//      }
//      finally {
//        if (bis != null) bis.close();
//        if (os != null) os.close();
//        if (sock!=null) sock.close();
//
//      }
//      // new thread for a client
//      new EchoThread(sock).start();
//    }
  }

 

  private String listenSocket(){
    while(true){
    try{
      String line = in.readLine();
//Send data back to client
      out.println(line);
    } catch (IOException e) {
      System.out.println("Read failed");
      System.exit(-1);
    }
    }
  }
}