import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.EventActionResult;
import com.agile.px.IEventAction;
import com.agile.px.IEventInfo;
import com.agile.px.IObjectEventInfo;

public class document_approve  extends JFrame implements IEventAction{

	@Override
	public EventActionResult doAction(IAgileSession arg0, INode arg1, IEventInfo arg2) {
		JFrame a= new JFrame("����");
		JOptionPane.showMessageDialog(a,"�t�Τw�۰ʶ�g�ӽг椺�e�A���ˬd���X");
		IObjectEventInfo  Dataobject =  (IObjectEventInfo) arg2;
		frame();
		
		return null;
	}

	private void frame() {
		
		JFrame frame = new JFrame("���y�{����");
		frame.setBounds(10, 10, 250, 350);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		List label  = new LinkedList();
		List gbc_label = new LinkedList();
		for (int i=0; i<5;i++) {
			label.add(i,new JLabel("approver"+i));
			gbc_label.add(i, new GridBagConstraints());
		}
		/*
		label.add(0,new JLabel("���p��"));
		label.add(1,new JLabel("�\�K�]"));
		label.add(2,new JLabel("���p��"));
		label.add(3,new JLabel("���ө�"));
		label.add(4,new JLabel("�L�a��"));
		*/
		JLabel Workflow1 = new JLabel("�y�{�W��:Workflow1");
		Workflow1.setFont(new Font("�з���", Font.PLAIN, 20));
		GridBagConstraints gbc_Workflow1 = new GridBagConstraints();
		gbc_Workflow1.gridwidth = 4;
		gbc_Workflow1.insets = new Insets(0, 0, 10, 5);
		gbc_Workflow1.gridx = 0;
		gbc_Workflow1.gridy = 0;
		frame.getContentPane().add(Workflow1, gbc_Workflow1);
	
		JLabel approve = new JLabel("ñ�֤H��");
		approve.setFont(new Font("�з���", Font.PLAIN, 20));
		GridBagConstraints gbc_approver = new GridBagConstraints();
		//gbc_approver.gridwidth =5;
		gbc_approver.insets = new Insets(0, 0, 5, 5);
		gbc_approver.gridx = 0;
		gbc_approver.gridy = 5;
		frame.getContentPane().add(approve, gbc_approver);
		
		for(int i =0; i<label.size();i++) {
			JLabel approver = (JLabel) label.get(i);
			approver.setFont(new Font("�з���", Font.PLAIN, 20));
			//GridBagConstraints gbc_budget = (GridBagConstraints) gbc_label.get(0);
			((GridBagConstraints)gbc_label.get(i)).gridwidth = 4;
			((GridBagConstraints)gbc_label.get(i)).insets = new Insets(0, 0, 5, 5);
			((GridBagConstraints)gbc_label.get(i)).gridx = 0;
			((GridBagConstraints)gbc_label.get(i)).gridy = 6+i;
			frame.getContentPane().add((JLabel) label.get(i), (GridBagConstraints)gbc_label.get(i));
		}
		
		frame.setVisible(true); 
	}

}
