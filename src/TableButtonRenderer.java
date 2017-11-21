import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TableButtonRenderer extends JButton implements TableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected,
      boolean hasFocus, int row,
      int col) {
    // TODO Auto-generated method stub
    setText((obj == null) ? "" : obj.toString());
    return this;
  }

}
