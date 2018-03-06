import static com.HF.out;
import static com.HF.executeSQL;
import static com.HF.executeInsertSQL;
import static com.HF.extractTop;
import static com.HF.getConnection;
import static com.HF.getWordInParen;
import static com.HF.out;
import static com.HF.removeNull;
import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IFileFolder;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public abstract class Popup extends JFrame implements Constants {
  JFrame f;

  Popup(){
    f = new JFrame();
  }

  private static boolean suggestionMouseEvent(IAgileSession session, List folders, int itemIndex, JFrame frame) {
    System.out.println("Mouse Clicked!!!");
    try {
      OutputStream outputStream = null;
      IFileFolder attachmentFile = (IFileFolder) session
          .getObject(IFileFolder.OBJECT_TYPE, folders.get(itemIndex));
      //out("Folder " + folders.get(itemIndex).toString());
      InputStream input = attachmentFile.getFile();
      ZipInputStream zis = new ZipInputStream(input);
      ZipEntry ze = zis.getNextEntry();
      try {
        if (!ze.isDirectory()) {
          outputStream =
              new FileOutputStream(new File(DOWNLOADFILEPATH + ze.getName()));

          int read = 0;
          byte[] bytes = new byte[8 * 1024];

          while ((read = zis.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
          }
          input.close();
          zis.close();
          outputStream.close();
          
          Container cp=frame.getContentPane();
		  cp.setLayout(null);
		  JOptionPane.showMessageDialog(frame,"下載"+DOWNLOADFILEPATH);
        }

        System.out.println("Done!");

      } catch (IOException e1) {
        e1.printStackTrace();
        return false;
      } finally {
        if (input != null) {
          try {
            input.close();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
        if (outputStream != null) {
          try {
            // outputStream.flush();
            outputStream.close();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }
      }
    } catch (APIException | IOException e1) {
      e1.printStackTrace();
      return false;
    }
    return true;
  }
  
  private static void savepoints(String OriginalFile, String ClickFile, String OtherFile1, String OtherFile2) throws ClassNotFoundException, SQLException {
	 
	  Connection conn = getConnection(USERNAME2, PASSWORD2, URL2);
	  String sql = "SELECT * FROM  AI WHERE ORIGINALFILE = '"+OriginalFile+"' AND FILENAME = '"+ClickFile+"'";
	  List userSet = executeSQL(conn, sql, true);
	  if (userSet.isEmpty() & ClickFile !="n/a") {
		  String sqlinsert = "INSERT INTO AI(ORIGINALFILE,FILENAME,COUNT,POINTS) VALUES ('"+OriginalFile+"','"+ClickFile+"',1,3)";
		
		  int Insert = executeInsertSQL(conn, sqlinsert);
	  }
	  else {
		  String sqlupdate = "UPDATE AI SET COUNT = COUNT +1,POINTS = POINTS +3 WHERE ORIGINALFILE = '"+OriginalFile+"' AND FILENAME = '"+ClickFile+"'";
		  int Insert = executeInsertSQL(conn, sqlupdate);
	  }
	  if(OtherFile1!="n/a")
	  {
		  String sqlSearchOtherFile1 = "SELECT * FROM  AI WHERE ORIGINALFILE = '"+OriginalFile+"' AND FILENAME = '"+OtherFile1+"'";
		  List OtherFile01 = executeSQL(conn, sqlSearchOtherFile1, true);
		  if (OtherFile01.isEmpty()) {
			  String sqlinsert = "INSERT INTO AI(ORIGINALFILE,FILENAME,COUNT,POINTS) VALUES ('"+OriginalFile+"','"+OtherFile1+"',0,-1)";
			  int Insert = executeInsertSQL(conn, sqlinsert);
		  }
		  else {
			  String sqlupdate = "UPDATE AI SET POINTS = POINTS -1 WHERE ORIGINALFILE = '"+OriginalFile+"' AND FILENAME = '"+OtherFile1+"'";
			  int Insert = executeInsertSQL(conn, sqlupdate);
		  }
	  }
	  if(OtherFile2!="n/a")
	  {
		  String sqlSearchOtherFile2 = "SELECT * FROM  AI WHERE ORIGINALFILE = '"+OriginalFile+"' AND FILENAME = '"+OtherFile2+"'";
		  List OtherFile02 = executeSQL(conn, sqlSearchOtherFile2, true);
		  if (OtherFile02.isEmpty()) {
			  String sqlinsert = "INSERT INTO AI(ORIGINALFILE,FILENAME,COUNT,POINTS) VALUES ('"+OriginalFile+"','"+OtherFile2+"',0,-1)";
			  int Insert = executeInsertSQL(conn, sqlinsert);
		  }
		  else {
			  String sqlupdate = "UPDATE AI SET POINTS = POINTS -1 WHERE ORIGINALFILE = '"+OriginalFile+"' AND FILENAME = '"+OtherFile2+"'";
			  int Insert = executeInsertSQL(conn, sqlupdate);
		  }
	  }
	}



  public abstract void frame(IAgileSession session, List infoList, String itemName);
}
