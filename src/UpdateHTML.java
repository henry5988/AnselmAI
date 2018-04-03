import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class UpdateHTML {

  private String txtDirectory;
  private String templateDirectory;
  private String outputDirectory;

  UpdateHTML(String txt, String template, String output) {
    setTxtDirectory(txt);
    setTemplateDirectory(template);
    setOutputDirectory(output);
  }

  public void update() {

    String line;
    String html = "";
    String[] tempArray = new String[10];
    int br_data_line = 0;
    int i = 0;

    String data_path = getTxtDirectory();//��Ƥ����|
    FileReader fr_data = null;
    try {
      fr_data = new FileReader(data_path);

      BufferedReader br_data = new BufferedReader(fr_data);

      String html_path = getTemplateDirectory();//���������|
      FileReader fr_html = new FileReader(html_path);
      BufferedReader br_html = new BufferedReader(fr_html);
      StringBuffer sb = new StringBuffer();

      //��X�����W�ٻP�s�X�]�w
      BufferedWriter fw = new BufferedWriter(
          new OutputStreamWriter(new FileOutputStream(getOutputDirectory()), "big5"));

      while ((line = br_data.readLine()) != null) { //�v��Ū��txt�ɡA�Ӧ檺���e�s�iline
        tempArray[br_data_line] = line;
        br_data_line++;
      }

      while ((line = br_html.readLine()) != null) {//�v��Ū��html�A�Ӧ檺���e�s�iline
        if (line.indexOf("{(anselmai)}") != -1) { //�P�_�Ӧ�O�_�t������r
          if (tempArray[i].equals("{(anselmai)}")) {
            tempArray[i] = "";
            html += line.replace("{(anselmai)}", tempArray[i]) + String.format("%n");
          } else {
            html += line.replace("{(anselmai)}", tempArray[i]) + String.format("%n");
          }
          i++;
        } else {
          html += line + String.format("%n");
        }
      }
      fw.write(html);//�N�Ȧs���e�g�i�ɮפ�
      fw.close();
      fr_data.close();
      br_data.close();
      fr_html.close();
      br_html.close();
    } catch (FileNotFoundException e) {
      System.err.println("FileNotFoundException in UpdateHTML()");
      System.err.println(e.getMessage());
    } catch (UnsupportedEncodingException e) {
      System.err
          .println("UnsupportedEncodingException in UpdateHTML(); Current Encoding set to: big5");
      System.err.println(e.getMessage());
    } catch (IOException e) {
      System.err.println("IOException in UpdateHTML()");
      System.err.println(e.getMessage());
    }
  }

  /*Getters and Setters*/

  public String getTxtDirectory() {
    return txtDirectory;
  }

  public void setTxtDirectory(String txtDirectory) {
    this.txtDirectory = txtDirectory;
  }

  public String getTemplateDirectory() {
    return templateDirectory;
  }

  public void setTemplateDirectory(String templateDirectory) {
    this.templateDirectory = templateDirectory;
  }

  public String getOutputDirectory() {
    return outputDirectory;
  }

  public void setOutputDirectory(String outputDirectory) {
    this.outputDirectory = outputDirectory;
  }
}

