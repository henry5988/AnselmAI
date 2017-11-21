import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class TableButtonEditor extends DefaultCellEditor {

  protected JButton btn;
  private String lbl;
  private Boolean clicked;

  public TableButtonEditor(JTextField txt) {
    super(txt);

    btn = new JButton();
    btn.setOpaque(true);

    //WHEN BUTTON IS CLICKED
    btn.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }

  public Component getTableCellEditorComponent(JTable table, Object obj, boolean isSelected,
      int row, int column) {

    lbl = (obj == null) ? "" : obj.toString();
    btn.setText(lbl);
    clicked = true;

    return btn;
  }

  ;

  //IF BUTTON CELL VALUE CHANGES, IF CLICKEDTHAT IS
  public Object getCellEditorValue() {
    if (clicked) {
      JOptionPane.showMessageDialog(btn, lbl + "clicked");
    }
    return new String(lbl);
  }

  public boolean stopCellEditing() {
    return super.stopCellEditing();

  }

  protected void fireEditingStopped() {
    super.fireEditingStopped();
  }

}
