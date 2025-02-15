import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class EchoThread extends Thread {
  protected Socket socket;

  public EchoThread(Socket clientSocket) {
    this.socket = clientSocket;
  }

  public void run() {
    InputStream inp = null;
    BufferedReader brinp = null;
    DataOutputStream out = null;
    try {
      inp = socket.getInputStream();
      brinp = new BufferedReader(new InputStreamReader(inp));
      out = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      return;
    }
    String line;
    while (true) {
      try {
        line = brinp.readLine();
        if ((line == null) || line.equalsIgnoreCase("QUIT")) {
          socket.close();
          return;
        } else {
          out.writeBytes(line + "\n\r");
          out.flush();
        }
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }
    }
  }
}