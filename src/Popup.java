import com.agile.api.IAgileSession;
import java.util.List;
import javax.swing.JFrame;

public abstract class Popup extends JFrame implements Constants {
  JFrame f;

  Popup(){
    f = new JFrame();
  }

  Popup(String title){
    f = new JFrame(title);
  }

  public abstract void frame(IAgileSession session, List infoList, String itemName);
}
