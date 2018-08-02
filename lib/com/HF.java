package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;

//Useful Functions for general purposes
public class HF{
  private static class EntryValueCompare implements Comparator<Entry<String, Integer>>{
    @Override
    public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
      return o2.getValue().compareTo(o1.getValue());
    }
  }

  /*Database*/
  public static Connection getConnection(String username, String password, String url)
      throws SQLException, ClassNotFoundException {
    Connection conn;
    Class.forName("oracle.jdbc.driver.OracleDriver");
    Properties connectionProps = new Properties();
    connectionProps.put("user", username);
    connectionProps.put("password", password);
    conn = DriverManager.getConnection(url, connectionProps);
    out("Connection established");
    return conn;
  }

  public static Connection getMySQLConnection(String username, String password, String url)
      throws SQLException, ClassNotFoundException,Exception {
    Connection conn;
    Class.forName("org.mariadb.jdbc.Driver");
    Properties connectionProps = new Properties();
    connectionProps.put("user", username);
    connectionProps.put("password", password);
    conn = DriverManager.getConnection(url, connectionProps);
    out("Connection established");
    return conn;
  }

  public static LinkedList<String> executeSQL(Connection conn, String sql) throws SQLException {
    LinkedList list = new LinkedList();
    String result = "";
    Statement stmt = conn.createStatement();
    out("Executing statement...");
    ResultSet rs = stmt.executeQuery(sql);
    while (rs.next()) {
      result = rs.getString(1);
      list.push(result);
      out("Got resultset: " + result);
    }
    out("ResultSet exhausted.");
    return list;
  }

  public static boolean executeStatement(Connection conn, String sql) throws SQLException {
    LinkedList list = new LinkedList();
    String result = "";
    Statement stmt = conn.createStatement();
    out("Executing statement...");
    boolean success = stmt.execute(sql);
    return success;
  }
  
  public static int executeInsertSQL(Connection conn, String sql) throws SQLException {
	    Statement stmt = conn.createStatement();
	    out("Executing statement...");
	    int rs = stmt.executeUpdate(sql);
		return rs;
	    
	  }

  public static LinkedList executeSQL(Connection conn, String sql, boolean removeDup) throws SQLException{
    LinkedList list;
    list = executeSQL(conn, sql);
    if(removeDup){ list = removeDup(list); }
    return list;
  }

  /*String Operations*/
  public static String getWordInParen(String string) {
    out("getWordInParen()...");
    int left = string.indexOf("(");
    int right = string.indexOf(")");
    return string.substring(left + 1, right);
  }

  public static String getFirstWord(String string) {
    out("getFirstWord()...");
    int i = 0;
    i = string.indexOf(" ");
    out("i = " + i);
    if (i > 0) {
      out("first word is " + string.substring(0, i));
      return string.substring(0, i);
    } else {
      return string;
    }
  }

  public static String getFileTypeExtension(String file){
    return file.substring(file.indexOf('.') + 1, file.length());
  }

  /*Container Operations*/
  // Lists
  // remove null elememts from a LinkedList
  public static LinkedList removeNull(LinkedList list) {
    for(Object object:list){
      if (object==null){
        list.remove(object);
      }
    }
    return list;
  }

  public static List<List> transposeMatrix(List<List> table) {
    List<List> ret = new ArrayList<List>();
    final int N = table.get(0).size();
    for (int i = 0; i < N; i++) {
      List col = new ArrayList();
      for (List row : table) {
        col.add(row.get(i));
      }
      ret.add(col);
    }
    return ret;
  }

  // remove duplicate elements from a LinkedList
  public static LinkedList removeDup(LinkedList list) {
    LinkedList unique = new LinkedList();
    while (!list.isEmpty()) {
      Object item = list.pop();
      if (!unique.contains(item)) {
        unique.add(item);
      }
    }
    return unique;
  }

  public static Integer countOccurance(List fileList, Object file, int count) {
    count = Collections.frequency(fileList, file);
//    if(fileList.contains(file)) {
//      fileList.remove(fileList.indexOf(file));
//      count = countOccurance(fileList, file, ++count);
//    }
    return count;
  }

  //Maps
  public static List extractTop(Map map, Integer rank){
    List top = new LinkedList();
    for (Object entry: map.entrySet()) {
      Entry e = (Entry) entry;
      top.add(e);
    }
    Collections.sort(top, new EntryValueCompare());
    return top.size() >= rank ? top.subList(0, rank) : top.subList(0, top.size());
  }

  /*Print Screen*/
  public static void out(String outString) {
    System.out.println(outString);
  }

  public static void out(String outString, String dest) {
    if (Objects.equals(dest, "out")) {
      out(outString);
    } else if (dest.equals("err")) {
      System.err.println(outString);
    }
  }
}
