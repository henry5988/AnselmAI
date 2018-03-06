import static com.HF.out;

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
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AttachmentPopup extends Popup implements Constants {

  @Override
  public void frame(IAgileSession session, List infoList, String itemName) {
    out("retrieving folders array...");
    List folders = (List) infoList.get(0);
    out("retrieving name array...");
    List names = (List) infoList.get(1);
    out(infoList.get(1).toString());
    out("retrieving image array...");
    out(infoList.get(2).toString());
    List images = (List) infoList.get(2);
    out("retrieving description array...");
    out(infoList.get(3).toString());
    List descriptions = (List) infoList.get(3);
    out("retrieving related user count...");
    out(infoList.get(4).toString());
    List viewerCounts = (List) infoList.get(4);

    JFrame.setDefaultLookAndFeelDecorated(true);
    JDialog.setDefaultLookAndFeelDecorated(true);
    String frameTitle = "�ݹL "+ itemName +" ���H�]�ݹL";
    JFrame frame = new JFrame(frameTitle);
    frame.setBounds(50, 50, 700, 300);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
    gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
        0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    frame.getContentPane().setLayout(gridBagLayout);

    JLabel image1 = new JLabel("");
    ImageIcon imageIcon1 = new ImageIcon(new ImageIcon((String) images.get(0)).getImage()
        .getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    image1.setIcon(imageIcon1);
    GridBagConstraints gbc_image1 = new GridBagConstraints();
    gbc_image1.gridx = 0;
    gbc_image1.gridy = 0;
    gbc_image1.gridwidth = 7;
    gbc_image1.gridheight = 2;
    //gbc_image1.insets = new Insets(0, 0, 5, 5);

    JLabel image2 = new JLabel("");
    ImageIcon imageIcon2 = new ImageIcon(new ImageIcon((String) images.get(1)).getImage()
        .getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    image2.setIcon(imageIcon2);
    GridBagConstraints gbc_image2 = new GridBagConstraints();
    gbc_image2.gridx = 10;
    gbc_image2.gridy = 0;
    gbc_image2.gridwidth = 7;
    gbc_image2.gridheight = 2;
    gbc_image2.insets = new Insets(0, 100, 0,
        100);// you can modify the space between components by changing the value here

    JLabel image3 = new JLabel("");
    ImageIcon imageIcon3 = new ImageIcon(new ImageIcon((String) images.get(2)).getImage()
        .getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    image3.setIcon(imageIcon3);
    GridBagConstraints gbc_image3 = new GridBagConstraints();
    gbc_image3.gridx = 20;
    gbc_image3.gridy = 0;
    gbc_image3.gridwidth = 7;
    gbc_image3.gridheight = 2;
    //gbc_image3.insets = new Insets(0, 0, 5, 0);

    JLabel name1 = new JLabel("<HTML><U>" + names.get(0) + "</U></HTML>");
    name1.setFont(new Font("Stencil", Font.PLAIN, 20));
    name1.setForeground(Color.BLUE);
    GridBagConstraints gbc_name1 = new GridBagConstraints();
    gbc_name1.gridx = 0;
    gbc_name1.gridy = 2;
    gbc_name1.gridwidth = 7;
    gbc_name1.insets = new Insets(5, 0, 0, 0);

    name1.setCursor(new Cursor(Cursor.HAND_CURSOR));
    name1.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        suggestionMouseEvent(session, folders, 0, frame);
      }
    });

    JLabel name2 = new JLabel("<HTML><U>" + names.get(1) + "</U></HTML>");
    name2.setFont(new Font("Stencil", Font.PLAIN, 20));
    name2.setForeground(Color.BLUE);
    GridBagConstraints gbc_name2 = new GridBagConstraints();
    gbc_name2.gridx = 10;
    gbc_name2.gridy = 2;
    gbc_name2.gridwidth = 7;
    gbc_name2.insets = new Insets(5, 0, 0, 0);

    name2.setCursor(new Cursor(Cursor.HAND_CURSOR));
    name2.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        suggestionMouseEvent(session, folders, 1, frame);
      }
    });

    JLabel name3 = new JLabel("<HTML><U>" + names.get(2) + "</U></HTML>");
    name3.setFont(new Font("Stencil", Font.PLAIN, 20));
    name3.setForeground(Color.BLUE);
    GridBagConstraints gbc_name3 = new GridBagConstraints();
    gbc_name3.gridx = 20;
    gbc_name3.gridy = 2;
    gbc_name3.gridwidth = 7;
    gbc_name3.insets = new Insets(5, 0, 0, 0);

    name3.setCursor(new Cursor(Cursor.HAND_CURSOR));
    name3.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        suggestionMouseEvent(session, folders, 2, frame);
      }
    });

    JLabel description1 = new JLabel("(" + descriptions.get(0) + ")");
    description1.setFont(new Font("�з���", Font.PLAIN, 18));
    GridBagConstraints gbc_description1 = new GridBagConstraints();
    gbc_description1.gridx = 0;
    gbc_description1.gridy = 3;
    gbc_description1.gridwidth = 7;
    //gbc_description1.insets = new Insets(0, 0, 5, 5);

    JLabel description2 = new JLabel("(" + descriptions.get(1) + ")");
    description2.setFont(new Font("�з���", Font.PLAIN, 18));
    GridBagConstraints gbc_description2 = new GridBagConstraints();
    gbc_description2.gridx = 10;
    gbc_description2.gridy = 3;
    gbc_description2.gridwidth = 7;
    //gbc_description2.insets = new Insets(0, 0, 5, 5);

    JLabel description3 = new JLabel("(" + descriptions.get(2) + ")");
    description3.setFont(new Font("�з���", Font.PLAIN, 18));
    GridBagConstraints gbc_description3 = new GridBagConstraints();
    gbc_description3.gridx = 20;
    gbc_description3.gridy = 3;
    gbc_description3.gridwidth = 7;
    //gbc_description3.insets = new Insets(0, 0, 5, 5);

    JLabel amount1 = new JLabel(viewerCounts.get(0) + " �H�]�ݹL");
    amount1.setFont(new Font("�з���", Font.PLAIN, 18));
    GridBagConstraints gbc_amount1 = new GridBagConstraints();
    gbc_amount1.gridx = 0;
    gbc_amount1.gridy = 5;
    gbc_amount1.gridwidth = 7;
    gbc_amount1.insets = new Insets(20, 0, 0, 0);

    JLabel amount2 = new JLabel(viewerCounts.get(1) + " �H�]�ݹL");
    amount2.setFont(new Font("�з���", Font.PLAIN, 18));
    GridBagConstraints gbc_amount2 = new GridBagConstraints();
    gbc_amount2.gridx = 10;
    gbc_amount2.gridy = 5;
    gbc_amount2.gridwidth = 7;
    gbc_amount2.insets = new Insets(20, 0, 0, 0);

    JLabel amount3 = new JLabel(/*�ƶq+*/viewerCounts.get(2) + " �H�]�ݹL");
    amount3.setFont(new Font("�з���", Font.PLAIN, 18));
    GridBagConstraints gbc_amount3 = new GridBagConstraints();
    gbc_amount3.gridx = 20;
    gbc_amount3.gridy = 5;
    gbc_amount3.gridwidth = 7;
    gbc_amount3.insets = new Insets(20, 0, 0, 0);

    frame.getContentPane().add(image1, gbc_image1);
    frame.getContentPane().add(name1, gbc_name1);
    frame.getContentPane().add(description1, gbc_description1);
    frame.getContentPane().add(amount1, gbc_amount1);

    frame.getContentPane().add(image2, gbc_image2);
    frame.getContentPane().add(name2, gbc_name2);
    frame.getContentPane().add(description2, gbc_description2);
    frame.getContentPane().add(amount2, gbc_amount2);

    frame.getContentPane().add(image3, gbc_image3);
    frame.getContentPane().add(name3, gbc_name3);
    frame.getContentPane().add(description3, gbc_description3);
    frame.getContentPane().add(amount3, gbc_amount3);

    frame.setVisible(true);
  }

  private static boolean suggestionMouseEvent(IAgileSession session, List folders, int itemIndex, JFrame frame) {
    System.out.println("Mouse Clicked!!!");
    try {
      OutputStream outputStream = null;
      IFileFolder attachmentFile = (IFileFolder) session
          .getObject(IFileFolder.OBJECT_TYPE, folders.get(itemIndex));
      out("Folder " + folders.get(itemIndex).toString());
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
		  JOptionPane.showMessageDialog(frame,"�U������� "+DOWNLOADFILEPATH);
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

}
