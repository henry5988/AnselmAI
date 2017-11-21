import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Popup extends JFrame {

  public static void frame(List names, List images, List descriptions) {

    JFrame f;
    JFrame.setDefaultLookAndFeelDecorated(true);
    JDialog.setDefaultLookAndFeelDecorated(true);
    f = new JFrame("JTable Test");
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Close JFrame
    f.setSize(1000, 600);
    f.setLocationRelativeTo(null);
    Container cp = f.getContentPane();

    String[] columns = {"Name", "Picture", "Description", "openFile"};
    Object[][] data = new Object[3][4];
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < data.length; j++) {
        if (j == 0) {
          data[i][j] = names.get(i);
        } else if (j == 1) {
          data[i][j] = images.get(i);
        } else if (j == 2) {
          data[i][j] = descriptions.get(i);
        } else if (j == 3) {
          data[i][j] = "openFile";
        }
      }
    }

    DefaultTableModel model = new DefaultTableModel(data, columns);
    JTable table = new JTable(model) {
      //  Returning the Class of each column will allow different
      //  renderers to be used based on Class
      public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
      }

      //only allow column 3 to be editable
      public boolean isCellEditable(int row, int column) {
        //Only the third column
        return column == 3;
      }
    };

    table.getColumnModel().getColumn(3).setCellRenderer(new TableButtonRenderer());

    table.getColumnModel().getColumn(3).setCellEditor(new TableButtonEditor(new JTextField()));

    JScrollPane pane = new JScrollPane(table);
    f.getContentPane().add(pane);

    cp.add(new JScrollPane(table), BorderLayout.CENTER);
    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
    tcr.setHorizontalAlignment(JLabel.CENTER);
    table.getTableHeader().setReorderingAllowed(false);//防拖拉
    table.setRowHeight(80);//設定儲存格高度
    f.setVisible(true);
  }

}
