import static com.HF.out;

import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IItem;
import com.agile.api.ItemConstants;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ItemInfoConverter {

  //fields
  private String converterAtt;

  //constructors
  ItemInfoConverter() {
    this.converterAtt = "";
  }

  ItemInfoConverter(String converterAtt) {
    this.converterAtt = converterAtt;
  }

  //getters and setters
  public String getConverterAtt() {
    return converterAtt;
  }

  public void setConverterAtt(String converterAtt) {
    this.converterAtt = converterAtt;
  }

  //methods
  public String convert(IAgileObject obj) throws APIException {
    if (Objects.equals(this.getConverterAtt(), "name")) {
      return obj.getName() == null ? "name field" : obj.getName();
    } else if (Objects.equals(this.getConverterAtt(), "description")) {
      IItem item = (IItem) obj;
      String desc = (String) item.getValue(ItemConstants.ATT_TITLE_BLOCK_DESCRIPTION);
      out("Description is " + desc);
      return desc == null ? "description field" : desc;
    } else {
      return "certain attribute";
    }
  }

  public List orderLists(List list, String[] order) {
    out("ItemInfoConverter.orderLists()...");
    out("order to print out:" + order.toString());
    out("List to sort: " + list.toString());
    LinkedList orderedList = new LinkedList();
    List strArray = null;
    for (int i = 0; i < order.length; i++) {
      out("list is not empty");
      Iterator it = list.iterator();
      for (; it.hasNext(); ) {
        strArray = (List) it.next();
        out("array scanned: " + strArray.toString());
        if (order[i].equals(strArray.get(0))) {
          out("array content type matched!");
          List sub = strArray.subList(1, 4);
          out("sublist added: " + sub);
          orderedList.add(sub);
          break;
        }
      }

    }
    out("orderLists() ends");
    return orderedList;
  }

}
