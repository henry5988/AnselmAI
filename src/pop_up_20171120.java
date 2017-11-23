import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.EventActionResult;
import com.agile.px.IEventAction;
import com.agile.px.IEventInfo;
import com.agile.px.IObjectEventInfo;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class pop_up_20171120 extends JFrame implements IEventAction {


  @Override
  public EventActionResult doAction(IAgileSession arg0, INode arg1, IEventInfo arg2) {
    // TODO Auto-generated method stub
    IObjectEventInfo Dataobject = (IObjectEventInfo) arg2;

    frame();
    return null;
  }

  private void frame() {
    // TODO Auto-generated method stub
    JFrame frame = new JFrame("�ݹL�o�Ӫ��H�]�ݹL");
    frame.setBounds(100, 100, 1008, 455);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
    gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
        0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    frame.getContentPane().setLayout(gridBagLayout);

    JLabel image1 = new JLabel("");
    image1.setIcon(
        new ImageIcon("D:\\Agile\\Agile936\\integration\\sdk\\extensions\\images\\images1.png"));
    GridBagConstraints gbc_image1 = new GridBagConstraints();
    gbc_image1.gridx = 14;
    gbc_image1.gridy = 0;
    gbc_image1.gridwidth = 7;
    gbc_image1.gridheight = 2;
    gbc_image1.insets = new Insets(0, 0, 5, 0);

    frame.getContentPane().add(image1, gbc_image1);

    JLabel image2 = new JLabel("");
    image2.setIcon(
        new ImageIcon("D:\\Agile\\Agile936\\integration\\sdk\\extensions\\images\\images2.png"));
    GridBagConstraints gbc_image2 = new GridBagConstraints();
    gbc_image2.gridx = 0;
    gbc_image2.gridy = 0;
    gbc_image2.gridwidth = 7;
    gbc_image2.gridheight = 2;
    gbc_image2.insets = new Insets(0, 0, 5, 5);

    frame.getContentPane().add(image2, gbc_image2);

    JLabel image3 = new JLabel("");
    image3.setIcon(
        new ImageIcon("D:\\Agile\\Agile936\\integration\\sdk\\extensions\\images\\images3.png"));
    GridBagConstraints gbc_image3 = new GridBagConstraints();
    gbc_image3.gridx = 7;
    gbc_image3.gridy = 0;
    gbc_image3.gridwidth = 7;
    gbc_image3.gridheight = 2;
    gbc_image3.insets = new Insets(0, 0, 5, 5);

    frame.getContentPane().add(image3, gbc_image3);

    JLabel name1 = new JLabel("<HTML><U>google</U></HTML>");
    name1.setFont(new Font("Stencil", Font.PLAIN, 20));
    name1.setForeground(Color.BLUE);
    GridBagConstraints gbc_name1 = new GridBagConstraints();
    gbc_name1.gridx = 0;
    gbc_name1.gridy = 2;
    gbc_name1.gridwidth = 7;
    gbc_name1.insets = new Insets(0, 0, 5, 5);

    name1.setCursor(new Cursor(Cursor.HAND_CURSOR));
    name1.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          Desktop.getDesktop().browse(new URI("www.google.com"));
        } catch (URISyntaxException | IOException ex) {
          //It looks like there's a problem
        }
      }
    });

    frame.getContentPane().add(name1, gbc_name1);

    JLabel name2 = new JLabel("<HTML><U>yahoo</U></HTML>");
    name2.setFont(new Font("Stencil", Font.PLAIN, 20));
    name2.setForeground(Color.BLUE);
    GridBagConstraints gbc_name2 = new GridBagConstraints();
    gbc_name2.gridx = 7;
    gbc_name2.gridy = 2;
    gbc_name2.gridwidth = 7;
    gbc_name2.insets = new Insets(0, 0, 5, 5);

    name2.setCursor(new Cursor(Cursor.HAND_CURSOR));
    name2.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          Desktop.getDesktop().browse(new URI("tw.yahoo.com"));
        } catch (URISyntaxException | IOException ex) {
          //It looks like there's a problem
        }
      }
    });

    frame.getContentPane().add(name2, gbc_name2);

    JLabel name3 = new JLabel("<HTML><U>amazon</U></HTML>");
    name3.setFont(new Font("Stencil", Font.PLAIN, 20));
    name3.setForeground(Color.BLUE);
    GridBagConstraints gbc_name3 = new GridBagConstraints();
    gbc_name3.gridx = 14;
    gbc_name3.gridy = 2;
    gbc_name3.gridwidth = 7;
    gbc_name3.insets = new Insets(0, 0, 5, 5);

    name3.setCursor(new Cursor(Cursor.HAND_CURSOR));
    name3.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          Desktop.getDesktop().browse(new URI("www.amazon.com"));
        } catch (URISyntaxException | IOException ex) {
          //It looks like there's a problem
        }
      }
    });

    frame.getContentPane().add(name3, gbc_name3);

    JLabel description1 = new JLabel(
        "626213513513513513513513512131135131352122351513513513513513513513512135151353113513513513135");
    GridBagConstraints gbc_description1 = new GridBagConstraints();
    gbc_description1.gridx = 0;
    gbc_description1.gridy = 3;
    gbc_description1.gridwidth = 7;
    gbc_description1.insets = new Insets(0, 0, 5, 5);

    frame.getContentPane().add(description1, gbc_description1);

    JLabel description2 = new JLabel("New label");
    GridBagConstraints gbc_description2 = new GridBagConstraints();
    gbc_description2.gridx = 7;
    gbc_description2.gridy = 3;
    gbc_description2.gridwidth = 7;
    gbc_description2.insets = new Insets(0, 0, 5, 5);

    frame.getContentPane().add(description2, gbc_description2);

    JLabel description3 = new JLabel("New label");
    GridBagConstraints gbc_description3 = new GridBagConstraints();
    gbc_description3.gridx = 14;
    gbc_description3.gridy = 3;
    gbc_description3.gridwidth = 7;
    gbc_description3.insets = new Insets(0, 0, 5, 5);

    frame.getContentPane().add(description3, gbc_description3);

    JLabel amount1 = new JLabel("New label");
    GridBagConstraints gbc_amount1 = new GridBagConstraints();
    gbc_amount1.gridx = 0;
    gbc_amount1.gridy = 5;
    gbc_amount1.gridwidth = 7;
    gbc_amount1.insets = new Insets(0, 0, 5, 5);

    frame.getContentPane().add(amount1, gbc_amount1);

    JLabel amount2 = new JLabel("New label");
    GridBagConstraints gbc_amount2 = new GridBagConstraints();
    gbc_amount2.gridx = 7;
    gbc_amount2.gridy = 5;
    gbc_amount2.gridwidth = 7;
    gbc_amount2.insets = new Insets(0, 0, 5, 5);

    frame.getContentPane().add(amount2, gbc_amount2);

    JLabel amount3 = new JLabel("New label");
    GridBagConstraints gbc_amount3 = new GridBagConstraints();
    gbc_amount3.gridx = 14;
    gbc_amount3.gridy = 5;
    gbc_amount3.gridwidth = 7;
    gbc_amount3.insets = new Insets(0, 0, 5, 5);

    frame.getContentPane().add(amount3, gbc_amount3);

    frame.setVisible(true);
  }
}