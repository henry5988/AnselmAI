import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.px.IEventInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CreateItem extends SuggestionPopup {
  /*
  * getSession() 可以得到當下使用者在的Agile Session
  * getEventInfo() 可以得到當下的EventInfo object
  * */


  /*
  * checksField()
  * 檢查欄位
  * input: 無
  * output: 欄位狀態字串,例如回傳"物件數量不能為零"
  * */
  @Override
  protected String checksField() {
    return null;
  }

  /*
  * writeToFile()
  * 將推薦信息寫到文件中，提供解讀程式分析成網頁html文檔
  * input：2維陣列，最底層為推薦物件的資料字串，由陣列將一個物件的資料作整理，在把多個物件陣列放到一個陣列做導入
  * output: 無
  * */
  @Override
  protected void writeToFile(List<List<String>> infoList) throws IOException {

  }

  /*
  * convertObjectToInfo()
  * 將推薦object轉換成文字字串訊息
  * input: 推薦object的陣列
  * output: 推薦object的訊息字串的2維陣列
  * */
  @Override
  protected List<List<String>> convertObjectToInfo(List l) throws APIException {
    return null;
  }

  /*
  * getItemAdvice()
  * 取得推薦物件
  * */
  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    return null;
  }

  @Override
  protected IAgileObject getTargetItem(IEventInfo req) throws APIException {
    return null;
  }
}
