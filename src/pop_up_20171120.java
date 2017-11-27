import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.EventActionResult;
import com.agile.px.IEventAction;
import com.agile.px.IEventInfo;
import com.agile.px.IObjectEventInfo;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class pop_up_20171120 extends JFrame implements IEventAction{

		

	@Override
	public EventActionResult doAction(IAgileSession arg0, INode arg1, IEventInfo arg2) {
		// TODO Auto-generated method stub
		IObjectEventInfo  Dataobject =  (IObjectEventInfo) arg2;
		
		frame();
		return null;
	}

	private void frame() {
		// TODO Auto-generated method stub
		JFrame.setDefaultLookAndFeelDecorated(true);
	    JDialog.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("看過這個的人也看過");
		frame.setBounds(50,50, 600, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		
		
		
		
		JLabel image1 = new JLabel("");
		ImageIcon imageIcon1 = new ImageIcon(new ImageIcon("D:\\Agile\\Agile936\\integration\\sdk\\extensions\\images\\images1.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		image1.setIcon(imageIcon1);
		GridBagConstraints gbc_image1 = new GridBagConstraints();
		gbc_image1.gridx = 0;
		gbc_image1.gridy = 0;
		gbc_image1.gridwidth = 7;
		gbc_image1.gridheight = 2;
		//gbc_image1.insets = new Insets(0, 0, 5, 5);
		
		frame.getContentPane().add(image1, gbc_image1);
		
		
		JLabel image2 = new JLabel("");
		ImageIcon imageIcon2 = new ImageIcon(new ImageIcon("D:\\Agile\\Agile936\\integration\\sdk\\extensions\\images\\images2.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		image2.setIcon(imageIcon2);
		GridBagConstraints gbc_image2 = new GridBagConstraints();
		gbc_image2.gridx = 10;
		gbc_image2.gridy = 0;
		gbc_image2.gridwidth = 7;
		gbc_image2.gridheight = 2;
		gbc_image2.insets = new Insets(0, 100, 0, 100);// you can modify the space between components by changing the value here 
		
		frame.getContentPane().add(image2, gbc_image2);
		
		
		JLabel image3 = new JLabel("");
		ImageIcon imageIcon3 = new ImageIcon(new ImageIcon("D:\\Agile\\Agile936\\integration\\sdk\\extensions\\images\\images3.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
		image3.setIcon(imageIcon3);
		GridBagConstraints gbc_image3 = new GridBagConstraints();
		gbc_image3.gridx = 20;
		gbc_image3.gridy = 0;
		gbc_image3.gridwidth = 7;
		gbc_image3.gridheight = 2;
		//gbc_image3.insets = new Insets(0, 0, 5, 0);
		
		frame.getContentPane().add(image3, gbc_image3);
		
		
		JLabel name1 = new JLabel("<HTML><U>PC00001</U></HTML>");
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
	                    try {
	                            Desktop.getDesktop().browse(new URI("www.google.com"));
	                    } catch (URISyntaxException | IOException ex) {
	                            //It looks like there's a problem
	                    }
	            }
	     });
		
		frame.getContentPane().add(name1, gbc_name1);
		
		
		JLabel name2 = new JLabel("<HTML><U>CS00001</U></HTML>");
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
	                    try {
	                            Desktop.getDesktop().browse(new URI("tw.yahoo.com"));
	                    } catch (URISyntaxException | IOException ex) {
	                            //It looks like there's a problem
	                    }
	            }
	     });
		
		frame.getContentPane().add(name2, gbc_name2);
		
		
		JLabel name3 = new JLabel("<HTML><U>PS00001</U></HTML>");
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
	                    try {
	                            Desktop.getDesktop().browse(new URI("www.amazon.com"));
	                    } catch (URISyntaxException | IOException ex) {
	                            //It looks like there's a problem
	                    }
	            }
	     });
		
		frame.getContentPane().add(name3, gbc_name3);
		
		
		JLabel description1 = new JLabel("(FDA510K法規)");
		description1.setFont(new Font("標楷體", Font.PLAIN, 18));
		GridBagConstraints gbc_description1 = new GridBagConstraints();
		gbc_description1.gridx = 0;
		gbc_description1.gridy = 3;
		gbc_description1.gridwidth = 7;
		//gbc_description1.insets = new Insets(0, 0, 5, 5);
		
		frame.getContentPane().add(description1, gbc_description1);
		
		
		JLabel description2 = new JLabel("(客戶要求規格)");
		description2.setFont(new Font("標楷體", Font.PLAIN, 18));
		GridBagConstraints gbc_description2 = new GridBagConstraints();
		gbc_description2.gridx = 10;
		gbc_description2.gridy = 3;
		gbc_description2.gridwidth = 7;
		//gbc_description2.insets = new Insets(0, 0, 5, 5);
		
		frame.getContentPane().add(description2, gbc_description2);
		
		
		JLabel description3 = new JLabel("(產品規格書)");
		description3.setFont(new Font("標楷體", Font.PLAIN, 18));
		GridBagConstraints gbc_description3 = new GridBagConstraints();
		gbc_description3.gridx = 20;
		gbc_description3.gridy = 3;
		gbc_description3.gridwidth = 7;
		//gbc_description3.insets = new Insets(0, 0, 5, 5);
		
		frame.getContentPane().add(description3, gbc_description3);
		
		
		JLabel amount1 = new JLabel("x 人看過");
		amount1.setFont(new Font("標楷體", Font.PLAIN, 18));
		GridBagConstraints gbc_amount1 = new GridBagConstraints();
		gbc_amount1.gridx = 0;
		gbc_amount1.gridy = 5;
		gbc_amount1.gridwidth = 7;
		gbc_amount1.insets = new Insets(20, 0, 0, 0);
		
		frame.getContentPane().add(amount1, gbc_amount1);
		
		
		JLabel amount2 = new JLabel("y 人看過");
		amount2.setFont(new Font("標楷體", Font.PLAIN, 18));
		GridBagConstraints gbc_amount2 = new GridBagConstraints();
		gbc_amount2.gridx = 10;
		gbc_amount2.gridy = 5;
		gbc_amount2.gridwidth = 7;
		gbc_amount2.insets = new Insets(20, 0, 0, 0);
		
		frame.getContentPane().add(amount2, gbc_amount2);
		
		
		JLabel amount3 = new JLabel(/*數量+*/"z 人看過");
		amount3.setFont(new Font("標楷體", Font.PLAIN, 18));
		GridBagConstraints gbc_amount3 = new GridBagConstraints();
		gbc_amount3.gridx = 20;
		gbc_amount3.gridy = 5;
		gbc_amount3.gridwidth = 7;
		gbc_amount3.insets = new Insets(20, 0, 0, 0);
		
		frame.getContentPane().add(amount3, gbc_amount3);
		
		
		frame.setVisible(true); 
	}
}