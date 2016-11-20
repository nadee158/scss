package com.privasia.scss.common.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AGSClient {

  private static final Log log = LogFactory.getLog(AGSClient.class);

  public static void main(String args[]) throws Exception {
    int portNo = 12001;
    String host = "172.21.150.1";
    if (args.length == 2) {
      host = args[0];
      portNo = Integer.parseInt(args[1]);
    } else if (args.length == 1) {
      System.err.println("Usage: java AGSSimulator <host> <port no>");
      System.exit(-1);
    }

    BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
    System.out.print("Enter filename : ");

    File file = new File(keyboard.readLine());
    BufferedReader f = new BufferedReader(new FileReader(file));
    String line = null;
    String requestXML = "";

    while ((line = f.readLine()) != null) {
      requestXML += line + SEPARATOR;
      //// System.out.println(line);
    }
    if (requestXML.endsWith(System.getProperty("line.separator"))) {
      requestXML = requestXML.substring(0, requestXML.length() - SEPARATOR.length());
    }
    //// System.out.println("File size is = " + file.length());
    //// System.out.println("--------------------------------------------------------------------");
    //// System.out.println(content);
    //// System.out.println("Content size is = " + content.length());

    String replyXML = AGSClient.sendXMLMessage(requestXML, portNo);
    // System.out.println(replyXML);
  }

  public static String sendXMLMessage(String requestXML, int portNo) throws Exception {
    return sendXMLMessage(requestXML, false, portNo);
  }

  public static String sendXMLMessage(String requestXML, boolean retry, int portNo) throws Exception {
    String replyXML = "";
    int retryCount = 0;
    int timeOutCount = 0;
    final int MAX_RETRY = 1;

    Socket socket = null;
    DataInputStream dataInputStream = null;
    InputStream in = null;
    OutputStream out = null;

    String s = null;
    byte[] header = new byte[HEADER_SIZE];
    int length = 0;
    String msgType = "";
    String msgId = "";
    init();
    while (retryCount < MAX_RETRY) {
      try {
        socket = new Socket(host, portNo);

        try {
          socket.setSoTimeout(120 * SECOND);
          socket.setKeepAlive(true);
          socket.setSoLinger(true, 120);

        } catch (Exception e) {
          System.err.println("Unable to set socket option SO_TIMEOUT");
        }

        dataInputStream = new DataInputStream(socket.getInputStream());
        in = socket.getInputStream();
        out = socket.getOutputStream();
        log.error("Connected to " + socket.getInetAddress() + ":" + socket.getPort());

        try {
          out.write(createRequestXMLMessage(System.currentTimeMillis() + "", requestXML).getBytes());
          timeOutCount = 0;
          while (timeOutCount < 120) {
            if (in.available() > 0) {
              in.read(header, 0, HEADER_SIZE);
              s = new String(header);
              log.error("-------------START AGS -----------");
              length = Integer.parseInt(s.substring(0, 6));
              msgType = s.substring(6, 16).trim();
              msgId = s.substring(16, 51).trim();
              byte[] buffer = new byte[length];
              int actualRead = in.read(buffer, 0, length);
              log.error(">> Length:" + length + " Actual Received:" + actualRead);
              // WPTSCSSSUP-65: fix issue in development server where not all data is received. TCP
              // packet issue
              int totalRead = actualRead;
              if (actualRead < length) {
                int retryAttempt = 0;
                while (totalRead < length) {
                  log.error(">> Did not get full content.. trying to read the rest of the file");
                  totalRead += in.read(buffer, totalRead, length - totalRead);
                  log.error(">> Length:" + length + " Actual Received:" + actualRead + " total Read:" + totalRead);
                  retryAttempt++;
                  Thread.sleep(500);
                  if (retryAttempt > 10) {
                    break;
                  }
                }
              }
              if (totalRead >= length)
                if (msgType.equals("REPLY") && !msgId.equals("LIVECHECK")) {
                  replyXML = new String(buffer);
                  retry = false;
                  break;
                }
            }
            timeOutCount++;
            delay(1000);
            log.error("-------------END AGS -----------");
          }
        } catch (IOException e) {
          // System.out.println("IOException" + e.getMessage());
          System.err.println("The first byte cannot be read or input stream has been closed or some other I/O error. "
              + e.getMessage());
        } catch (Exception e) {
          // System.out.println("Other Exception" + e.getMessage());
          System.err.println("Connection time out. " + e.getMessage());
        }
      } catch (Exception e) {
        System.err.println("Connection failed. " + e.getMessage());
      }

      if (socket != null) {
        try {
          socket.close();
        } catch (Exception e) {
          throw new Exception("Could not close socket. " + e.getMessage());
        }
      }

      if (retry) {
        // System.out.println("Re-connecting after 5 seconds.");
        delay(5 * SECOND);
        retryCount++;
      } else {
        retryCount = MAX_RETRY;
      }

    }

    return replyXML;
  }


  private static String createRequestXMLMessage(String msgId, String xml) {
    return padding(xml.length(), 6, '0', true) + padding("REQUEST", 10, ' ', false) + padding(msgId, 35, ' ', false)
        + xml;
  }

  private static synchronized String padding(int i, int size, char c, boolean leading) {
    return padding(i + "", size, c, leading);
  }

  private static synchronized String padding(String s, int size, char c, boolean leading) {
    StringBuffer sb = new StringBuffer(s);
    int length = sb.length();
    if (size > 0 && size > length) {
      for (int i = 0; i <= size; i++) {
        if (leading) {
          if (i < size - length) {
            sb.insert(0, c);
          }
        } else {
          if (i > length) {
            sb.append(c);
          }
        }
      }
    }
    return sb.toString();
  }

  public static void init() throws Exception {
    Properties p = new Properties();
    InputStream is = null;
    try {
      is = AGSClient.class.getResourceAsStream("ags.properties");
      p.load(is);
      host = p.getProperty("ags.host");
    } catch (Exception e) {
      throw e;
    } finally {
      if (is != null) {
        is.close();
      }
    }
  }


  private static void delay(long ms) throws Exception {
    Thread.sleep(ms);
  }

  private static String host = "";

  private static final int SECOND = 1000;
  private static final int HEADER_SIZE = 51;
  private static final String SEPARATOR = System.getProperty("line.separator");

  public static final String SEND = "SEND";
  public static final String RECEIVE = "RECEIVE";

}
