package com.privasia.scss.cosmos.util;

import java.util.ArrayList;

public class TextString {

  public static void main(String[] args) {
    String[] strs = new String[] {"1", "", null, "", "5", "6", "7"};
    strs = trimToSize(strs);

    for (int i = 0; i < strs.length; i++) {
      // System.out.println(strs[i]);
    }
  }

  /**
   ** pad a string s with a size of n with char c on the leading (true) or on the right(false)
   **/
  public static synchronized String padding(String s, int n, char c, boolean leading) {
    StringBuffer sb = new StringBuffer(s);
    int strLength = sb.length();
    if (n > 0 && n > strLength) {
      for (int i = 0; i <= n; i++) {
        if (leading) {
          if (i < n - strLength) {
            sb.insert(0, c);
          }
        } else {
          if (i > strLength) {
            sb.append(c);
          }
        }
      }
    }
    return sb.toString();
  }

  public static String format(Object o) {
    if (o == null) {
      return "";
    } else {
      return o.toString().trim();
    }
  }

  public static String[] trimToSize(String[] strs) {
    // to filter out all empty str
    ArrayList list = new ArrayList();

    int size = 0;
    for (int i = 0; i < strs.length; i++) {
      if (strs[i] != null) {
        if (!(strs[i].equals(""))) {
          list.add(size++, strs[i]);
        }
      }
    }

    String[] ret = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      ret[i] = (String) list.get(i);
    }

    return ret;
  }


  public static String format(String newIC, String oldIC, String passport) {
    String ret = "";

    if (newIC != null && !newIC.equals("")) {
      ret += newIC + ", ";
    }
    if (oldIC != null && !oldIC.equals("")) {
      ret += oldIC + ", ";
    }
    if (passport != null && !passport.equals("")) {
      ret += passport + ", ";
    }

    if (ret.endsWith(", ")) {
      ret = ret.substring(0, ret.length() - ", ".length());
    }

    return ret;
  }
}
