import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.EventActionResult;
import com.agile.px.IEventAction;
import com.agile.px.IEventInfo;
import com.agile.px.IObjectEventInfo;

public class ProjectPopup extends JFrame implements IEventAction{

	@Override
	public EventActionResult doAction(IAgileSession arg0, INode arg1, IEventInfo arg2) {
		IObjectEventInfo  Dataobject =  (IObjectEventInfo) arg2;
		frame();
		return null;
		
	}

	private void frame() {
		JFrame frame = new JFrame("Project參考文件");
		frame.setBounds(10, 10, 417, 400);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel budget = new JLabel("預算建議(150,000-300,000)");
		budget.setFont(new Font("新細明體", Font.PLAIN, 20));
		GridBagConstraints gbc_budget = new GridBagConstraints();
		gbc_budget.gridwidth = 4;
		gbc_budget.insets = new Insets(0, 0, 5, 5);
		gbc_budget.gridx = 10;
		gbc_budget.gridy = 0;
		frame.getContentPane().add(budget, gbc_budget);
		
		JLabel project01 = new JLabel("Project01");
		project01.setFont(new Font("新細明體", Font.PLAIN, 18));
		GridBagConstraints gbc_project01 = new GridBagConstraints();
		gbc_project01.insets = new Insets(0, 0, 5, 5);
		gbc_project01.gridx = 11;
		gbc_project01.gridy = 5;
		frame.getContentPane().add(project01, gbc_project01);
		
		JLabel budget1 = new JLabel("300,000");
		budget1.setFont(new Font("新細明體", Font.PLAIN, 18));
		GridBagConstraints gbc_budget1 = new GridBagConstraints();
		gbc_budget1.insets = new Insets(0, 0, 5, 5);
		gbc_budget1.gridx = 14;
		gbc_budget1.gridy = 5;
		frame.getContentPane().add(budget1, gbc_budget1);
		
		JLabel project02 = new JLabel("Project02");
		project02.setFont(new Font("新細明體", Font.PLAIN, 18));
		GridBagConstraints gbc_project02 = new GridBagConstraints();
		gbc_project02.insets = new Insets(0, 0, 5, 5);
		gbc_project02.gridx = 11;
		gbc_project02.gridy = 6;
		frame.getContentPane().add(project02, gbc_project02);
		
		JLabel budget2 = new JLabel("150,000");
		budget2.setFont(new Font("新細明體", Font.PLAIN, 18));
		GridBagConstraints gbc_budget2 = new GridBagConstraints();
		gbc_budget2.insets = new Insets(0, 0, 5, 5);
		gbc_budget2.gridx = 14;
		gbc_budget2.gridy = 6;
		frame.getContentPane().add(budget2, gbc_budget2);
		
		JLabel project03 = new JLabel("Project03");
		project03.setFont(new Font("新細明體", Font.PLAIN, 18));
		GridBagConstraints gbc_project03 = new GridBagConstraints();
		gbc_project03.insets = new Insets(0, 0, 5, 5);
		gbc_project03.gridx = 11;
		gbc_project03.gridy = 7;
		frame.getContentPane().add(project03, gbc_project03);
		
		JLabel budget3 = new JLabel("200,000");
		budget3.setFont(new Font("新細明體", Font.PLAIN, 18));
		GridBagConstraints gbc_budget3 = new GridBagConstraints();
		gbc_budget3.insets = new Insets(0, 0, 5, 5);
		gbc_budget3.gridx = 14;
		gbc_budget3.gridy = 7;
		frame.getContentPane().add(budget3, gbc_budget3);
		
		JLabel human_resource = new JLabel("人力資源");
		human_resource.setFont(new Font("新細明體", Font.PLAIN, 20));
		GridBagConstraints gbc_human_resource = new GridBagConstraints();
		gbc_human_resource.insets = new Insets(20, 0, 5, 5);
		gbc_human_resource.gridx = 10;
		gbc_human_resource.gridy = 10;
		frame.getContentPane().add(human_resource, gbc_human_resource);
		
		JLabel account1 = new JLabel("Account 01");
		account1.setFont(new Font("新細明體", Font.PLAIN, 18));
		GridBagConstraints gbc_account1 = new GridBagConstraints();
		gbc_account1.insets = new Insets(0, 0, 5, 5);
		gbc_account1.gridx = 11;
		gbc_account1.gridy = 12;
		frame.getContentPane().add(account1, gbc_account1);
		
		JLabel project_amount1 = new JLabel("5個專案執行中");
		project_amount1.setFont(new Font("新細明體", Font.PLAIN, 18));
		GridBagConstraints gbc_project_amount1 = new GridBagConstraints();
		gbc_project_amount1.insets = new Insets(0, 0, 5, 5);
		gbc_project_amount1.gridx = 14;
		gbc_project_amount1.gridy = 12;
		frame.getContentPane().add(project_amount1, gbc_project_amount1);
		
		JLabel account2 = new JLabel("Account 02");
		account2.setFont(new Font("新細明體", Font.PLAIN, 18));
		GridBagConstraints gbc_account2 = new GridBagConstraints();
		gbc_account2.insets = new Insets(0, 0, 5, 5);
		gbc_account2.gridx = 11;
		gbc_account2.gridy = 13;
		frame.getContentPane().add(account2, gbc_account2);
		
		JLabel project_amount2 = new JLabel("10個專案執行中");
		project_amount2.setFont(new Font("新細明體", Font.PLAIN, 18));
		GridBagConstraints gbc_project_amount2 = new GridBagConstraints();
		gbc_project_amount2.insets = new Insets(0, 0, 5, 5);
		gbc_project_amount2.gridx = 14;
		gbc_project_amount2.gridy = 13;
		frame.getContentPane().add(project_amount2, gbc_project_amount2);
		
		JLabel account3 = new JLabel("Account 03");
		account3.setFont(new Font("新細明體", Font.PLAIN, 18));
		GridBagConstraints gbc_account3 = new GridBagConstraints();
		gbc_account3.insets = new Insets(0, 0, 5, 5);
		gbc_account3.gridx = 11;
		gbc_account3.gridy = 14;
		frame.getContentPane().add(account3, gbc_account3);
		
		JLabel project_amount3 = new JLabel("0個專案執行中");
		project_amount3.setFont(new Font("新細明體", Font.PLAIN, 18));
		GridBagConstraints gbc_project_amount3 = new GridBagConstraints();
		gbc_project_amount3.insets = new Insets(0, 0, 5, 5);
		gbc_project_amount3.gridx = 14;
		gbc_project_amount3.gridy = 14;
		frame.getContentPane().add(project_amount3, gbc_project_amount3);
	
		
		frame.setVisible(true); 
		
		
	}

}
