package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

//Useful Functions for general purposes
public class HF {

  /*Database*/
  public static Connection getConnection(String username, String password, String url) throws SQLException {
    // TODO Auto-generated method stub
    Connection conn;
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
      out("Got resultset: " + result.toString());
    }
    out("ResultSet exhausted.");
    return list;
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

  /*Container Operations*/

  // remove null elememts from a LinkedList
  public static LinkedList removeNull(LinkedList list) {
    list.removeIf(Objects::isNull);
    return list;
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
    if(fileList.contains(file)) {
      fileList.remove(fileList.indexOf(file));
      count = countOccurance(fileList, file, ++count);
    }
    return count;
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
