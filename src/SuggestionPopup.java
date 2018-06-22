import static com.HF.getConnection;
import static com.HF.out;

import com.agile.api.APIException;
import com.agile.api.IAgileObject;
import com.agile.api.IAgileSession;
import com.agile.api.INode;
import com.agile.api.IUser;
import com.agile.px.ActionResult;
import com.agile.px.EventActionResult;
import com.agile.px.IEventAction;
import com.agile.px.IEventDirtyFile;
import com.agile.px.IEventInfo;
import com.agile.px.IFileEventInfo;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;

public abstract class SuggestionPopup implements IEventAction, Constants {
	static final Object NO_EVENT = null;
	private boolean test = false;
	private IAgileSession session;
	private IEventInfo eventInfo;
	private String output_path;
	private String actionCode;
	private boolean fieldCheck; // this boolean has to be set at the beginning of doAction
	private String htmlTemplate; // directory for html templates
	private String htmlOutput; // directory for modified html
	private String fieldCheckResponse;

	String getActionCode() {
		return actionCode;
	}

	void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	@Override
	public EventActionResult doAction(IAgileSession session, INode node, IEventInfo req) {
		String returnString = "";
		try {
			String username = session.getCurrentUser().getName();
			// field checks
			System.out.println("isFieldCheck()...");
			if (isFieldCheck()) {
				System.out.println("field check: true");
				setFieldCheckResponse(checksField());
			} else {
				setFieldCheckResponse("");
			}
			// get suggestions
			System.out.println("getTargetItem()...");
			IAgileObject obj = (IAgileObject) getTargetItem(req);
			if (obj == null) {
				return new EventActionResult(req, new ActionResult(ActionResult.STRING, "No Event"));
			}
			System.out.println("getItemAdvice()...");
			List list = getItemAdvice(session, obj, req);
			// out("List: " + list.toString());
			System.out.println("convertObjectToInfo()...");
			List<List> infoList = convertObjectToInfo(list);
			// System.out.println("Test:"+test);
			if (!isTest()) {
				System.out.println("Info: " + infoList.toString());
				if (list.size() < 3) {
					// out("list has fewer than 3 items, does nothing");
					while (infoList.size() < 3 && infoList.get(0) != null) {
						infoList = addEmptyInfoToList(infoList);
					}
					System.out.println("Adjusted Info: " + infoList.toString());
				}
				// out("convert Object to String info...");
				System.out.println("writeToFile()...");
				writeToFile(infoList);

				System.out.println("Creating BOM HTML for client...");
				UpdateHTML htmlMaker = new UpdateHTML(getOutput_path(), getHtmlTemplate(), getHtmlOutput());
				System.out.println("HTML Updating..");
				htmlMaker.update();
			} else {
				setHtmlOutput(getHtmlTemplate());
				System.out.println("Printing Test html...");
				File exist = new File(replaceServerSource(getSession().getCurrentUser().getName(), EXIST));
				File emptyOutput = new File(getOutput_path());
				if (!emptyOutput.exists()) {
					Files.createDirectories(Paths.get(emptyOutput.getPath()).getParent());
					emptyOutput.createNewFile();
				}
				if (!exist.exists()) {
					Files.createDirectories(Paths.get(exist.getPath()).getParent());
					exist.createNewFile();
				}
				FileWriter existWriter = new FileWriter(exist);
				existWriter.write(getHtmlTemplate());
				existWriter.close();
			}

			System.out.println("Composing returnString");
			returnString = req.getEventHandlerName() + "!";

		} catch (SQLException | APIException | ClassNotFoundException e) {
			// out("Error occured", "err");
			e.getMessage();
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// out("JFrame info printed");
		return new EventActionResult(req, new ActionResult(ActionResult.STRING, returnString));
	}

	protected void init(IAgileSession session, IEventInfo req, String output_path, String html_output,
			String html_template, boolean fieldCheck, boolean test) {
		setSession(session);
		setEventInfo(req);
		try {
			setOutput_path(replaceServerSource(getSession().getCurrentUser().getName(), output_path));
			setHtmlOutput(replaceServerSource(getSession().getCurrentUser().getName(), html_output));
			setHtmlTemplate(html_template);
		} catch (APIException e) {
			System.err.println("Error in init(): " + e.getMessage());
		}
		setFieldCheck(fieldCheck);
		setTest(test);
	}

	protected List addEmptyInfoToList(List<List> infoList) {
		while (infoList.size() < 3) {
			infoList.add(new LinkedList<>());
			for (int i = 0; i < infoList.get(0).size(); i++) {
				infoList.get(infoList.size() - 1).add("n/a");
			}
		}
		return infoList;
	}

	protected abstract String checksField();

	protected boolean checkEventType(IEventInfo req, int eventType, String actionCode) {
		try {
			// out("Event type: " + req.getEventType());
			setActionCode(String.valueOf(actionCode));
			if (req.getEventType() == eventType)
				return true;
		} catch (APIException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected abstract void writeToFile(List<List> infoList) throws IOException;

	private String getDownloadedFileName(IFileEventInfo info) throws APIException {
		IEventDirtyFile[] files = info.getFiles();
		return files[0].getFilename();
	}

	private String getDownloadedFolderName(IFileEventInfo info) throws APIException {
		IEventDirtyFile[] files = info.getFiles();
		return files[0].getFileFolder().toString();
	}

	protected abstract List<List> convertObjectToInfo(List l) throws APIException;

	protected abstract List getItemAdvice(IAgileSession session, IAgileObject obj, IEventInfo req)
			throws SQLException, APIException, ClassNotFoundException;

	protected static void writeToFileTemp(String output_path, String eventTypeString, String contentString)
			throws IOException {
		File f = new File(output_path);
		File exist = new File(EXIST);
		if (!exist.exists()) {
			Files.createDirectories(Paths.get(exist.getPath()).getParent());
			exist.createNewFile();
		}
		if (!f.exists()) {
			Files.createDirectories(Paths.get(f.getPath()).getParent());
			f.createNewFile();
		}

		FileWriter existWriter = new FileWriter(exist);
		existWriter.write(eventTypeString + String.format("%n" + System.currentTimeMillis()));
		FileWriter fw = new FileWriter(f);
		fw.write(contentString); // TODO createProject logic
		existWriter.close();
		fw.close();
	}

	protected static String replaceServerSource(String username, String templateString) {
		templateString = templateString.replace("serverSource", username);
		return templateString;
	}

	public boolean isFieldCheck() {
		return fieldCheck;
	}

	public void setFieldCheck(boolean fieldcheck) {
		this.fieldCheck = fieldcheck;
	}

	protected abstract IAgileObject getTargetItem(IEventInfo req) throws APIException;

	public IAgileSession getSession() {
		return session;
	}

	public void setSession(IAgileSession session) {
		this.session = session;
	}

	public IEventInfo getEventInfo() {
		return eventInfo;
	}

	public void setEventInfo(IEventInfo eventInfo) {
		this.eventInfo = eventInfo;
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public String getOutput_path() {
		return output_path;
	}

	public void setOutput_path(String output_path) {
		this.output_path = output_path;
	}

	public String getHtmlTemplate() {
		return htmlTemplate;
	}

	public void setHtmlTemplate(String htmlTemplate) {
		this.htmlTemplate = htmlTemplate;
	}

	public String getHtmlOutput() {
		return htmlOutput;
	}

	public void setHtmlOutput(String htmlOutput) {
		this.htmlOutput = htmlOutput;
	}

	public String getFieldCheckResponse() {
		return fieldCheckResponse;
	}

	public void setFieldCheckResponse(String fieldCheckResponse) {
		this.fieldCheckResponse = fieldCheckResponse;
	}
}
