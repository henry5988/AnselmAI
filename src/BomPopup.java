import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.EventActionResult;
import com.agile.px.IEventAction;
import com.agile.px.IEventInfo;

public class BomPopup  extends JFrame implements IEventAction{

	@Override
	public EventActionResult doAction(IAgileSession arg0, INode arg1, IEventInfo arg2) {
		frame();
		return null;
	}

	private void frame() {
		JFrame frame = new JFrame("BOM表推薦");
		frame.setBounds(0,0, 550, 250);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		List ItemNumber  = new LinkedList();
		List Description = new LinkedList();
		List gbc_ItemNumber = new LinkedList();
		List gbc_Description = new LinkedList();
		for (int i=0; i<3;i++) {
			ItemNumber.add(i,new JLabel("ItemNumber"+i));
			Description.add(i,new JLabel("Description"+i));
			gbc_ItemNumber.add(i, new GridBagConstraints());
			gbc_Description.add(i, new GridBagConstraints());
		}
		/*
		ItemNumber.add(0,new JLabel("IC00001"));
		ItemNumber.add(1,new JLabel("RAM00001"));
		ItemNumber.add(2,new JLabel("HD00001"));
		Description.add(0,new JLabel("(A-123型晶片)"));
		Description.add(1,new JLabel("(KS DDR4 8GB)"));
		Description.add(2,new JLabel("(1TB硬碟)"));
		*/
		JLabel total_used = new JLabel("使用此零件的BOM表數:36個");
		
		total_used.setFont(new Font("標楷體", Font.PLAIN, 20));
		GridBagConstraints gbc_total_used = new GridBagConstraints();
		gbc_total_used.gridwidth = 8;
		gbc_total_used.insets = new Insets(0, 0, 50, 5);
		gbc_total_used.gridx = 0;
		gbc_total_used.gridy = 0;
		frame.getContentPane().add(total_used, gbc_total_used);
		
		JLabel budget = new JLabel("使用此零件的BOM表也使用了");
		budget.setFont(new Font("標楷體", Font.PLAIN, 20));
		GridBagConstraints gbc_budget = new GridBagConstraints();
		gbc_budget.gridwidth = 9;
		gbc_budget.insets = new Insets(0, 0, 10, 5);
		gbc_budget.gridx = 0;
		gbc_budget.gridy = 5;
		frame.getContentPane().add(budget, gbc_budget);
		
		for (int i=0; i<3;i++) {
			((JLabel) ItemNumber.get(i)).setFont(new Font("標楷體", Font.PLAIN, 20));
			((GridBagConstraints)gbc_ItemNumber.get(i)).gridwidth = 4;
			((GridBagConstraints)gbc_ItemNumber.get(i)).gridx =3+i*4;
			((GridBagConstraints)gbc_ItemNumber.get(i)).gridy = 10;
			frame.getContentPane().add((JLabel) ItemNumber.get(i), (GridBagConstraints)gbc_ItemNumber.get(i));
		}
		
		for (int i=0; i<3;i++) {
			((JLabel) Description.get(i)).setFont(new Font("標楷體", Font.PLAIN, 18));
			((GridBagConstraints)gbc_Description.get(i)).gridwidth = 4;
			((GridBagConstraints)gbc_Description.get(i)).insets = new Insets(0, 0, 5, 5);
			((GridBagConstraints)gbc_Description.get(i)).gridx = 3+4*i;
			((GridBagConstraints)gbc_Description.get(i)).gridy = 12;
			frame.getContentPane().add((JLabel) Description.get(i), (GridBagConstraints)gbc_Description.get(i));
		}
		frame.setVisible(true); 
	}

}
