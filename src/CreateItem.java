import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.ICreateEventInfo;
import com.agile.px.IEventInfo;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


/*
* AI推薦程式模板
* CreateItem可以是任何AI推薦的Event Class, 例如CreateProject, updateAttachmentTable之類的Event
* */
public class CreateItem extends SuggestionPopup {
  /*
  * 可用的抓取資料通用method：
  * getSession() 可以得到當下使用者在的Agile Session
  * getEventInfo() 可以得到當下的EventInfo object
  * getOutput_path() 可以得到推薦資料文檔路徑
  * */

  /*
  * doAction()
  * Event主要程式, 執行完init()後直接執行parent class (SuggestionPopup)的doAction()
  * input: parent class的IAgileSession, INode, IEventInfo物件
  * output: 執行完成Event的字串
  * */
  @Override
  public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req) {
    setOutput_path("C:\\serverSource\\createItem.txt"); // 推薦資料output路徑
    setFieldCheck(false); // 是否有欄位檢查機制，如果是需要檢查欄位的event，請調整為true
    setTest(true); // 是否跳過抓取資料邏輯，此功能是為了可以讓demo視窗跳出，開發後期完成抓資料邏輯，再設為false
    init(session, req, getOutput_path(), isFieldCheck(), isTest()); // 初始class設定，通過傳遞Session, EventInfo, output_path, 欄位檢查flag和跳過邏輯測試flag來執行此Event
    super.doAction(session, node, req); // parent class的doAction()程式
    // super程式執行順序為
    // 1. checksField()
    // 2. getTargetItem()
    // 3. getItemAdvice()
    // 4. convertObjectToInfo()
    // 5. writeToFile()
    // 詳細method解釋請看下方function implementation註解
    
    return new EventActionResult(req, new ActionResult(ActionResult.STRING, "CreateItem() Event")); // 根據此Event Class來定義回傳字串
  }

  /*
   * getTargetItem()
   * 取得Event創造或是更改的物件
   * input: IEventInfo訊息物件
   * output: Event創造或是更改的物件
   * */
  @Override
  protected IAgileObject getTargetItem(IEventInfo req) throws APIException {
    ICreateEventInfo info = (ICreateEventInfo) req;
    return info.getDataObject();
  }

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
   * getItemAdvice()
   * 取得推薦物件
   * input: 執行中的IAgileSession物件、Event創造或更改的物件、IEventInfo訊息物件
   * output: 含有推薦物件的陣列物件
   * */
  @Override
  protected LinkedList getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
      throws SQLException, APIException, ClassNotFoundException {
    return new LinkedList();
  }

  /*
   * convertObjectToInfo()
   * 將推薦object轉換成文字字串訊息
   * input: 推薦object的陣列
   * output: 推薦object的訊息字串的2維陣列
   * */
  @Override
  protected List<List<String>> convertObjectToInfo(List l) throws APIException {
    return new LinkedList<>();
  }

  /*
  * writeToFile()
  * 將推薦信息寫到文件中，提供解讀程式分析成網頁html文檔
  * input：2維陣列，最底層為推薦物件的資料字串，由陣列將一個物件的資料作整理，在把多個物件陣列放到一個陣列做導入
  * output: 無
  * */
  @Override
  protected void writeToFile(List<List<String>> infoList) throws IOException {
    SuggestionPopup.writeToFileTemp(getOutput_path(),"createPartPopup", "Create Part Test String");
  }
}
