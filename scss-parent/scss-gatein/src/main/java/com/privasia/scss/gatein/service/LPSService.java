package com.privasia.scss.gatein.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ClientDTO;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.common.util.LPS;
import com.privasia.scss.core.repository.ClientRepository;

@Service("lpsService")
@Transactional
public class LPSService {

  private static final Log log = LogFactory.getLog(LPSService.class);

  @Autowired
  private ClientRepository clientRepository;

  @Value("${lps.ip.address.default.port:20000}")
  private int defaultPort;

  @Value("${lps.default.timeout.length:3000}")
  private int defaultTimeoutLength;

  @Value("${lps.open.gate.signal:OGT}")
  public String sigOpenGate;

  @Transactional(readOnly = true)
  public String openGateByClientId(String clientId, boolean isCustoms) {
    String res = null;
    Optional<ClientDTO> clientDTOOptional = clientRepository.getlpsIPAddressInfoByClientID(Long.parseLong(clientId));
    ClientDTO clientDTO = clientDTOOptional.orElse(null);
    if (!(clientDTO == null)) {
      if (isCustoms) {
        res = openGateByIP(clientDTO.getLpsIPAddress(), defaultPort);
      } else {
        // No need to check it is GATE IN or GATE OUT
        // YONG if (clientType.equals(Client.CLI_TYPE_GATE_IN) || isCustomControl.equals("N")) {
        if (!(clientDTO.isCsmControl())) {
          res = LPS.parseResponseByWeb(openGateByIP(clientDTO.getLpsIPAddress(), defaultPort));
        } else {
          res = CommonUtil.getMessageCode("tx.success.lps.custom.control");
        }
      }
    } else {
      throw new ResultsNotFoundException("Client was not found!");
    }
    return res;
  }

  public String openGateByIP(String ip, int port) {
    String ret = "";

    Socket socket = null;
    BufferedReader in = null;
    PrintStream out = null;

    try {
      log.warn("Trying connect to " + ip + ":" + port);

      InetSocketAddress sa = new InetSocketAddress(ip, port);
      socket = new Socket();
      socket.connect(sa, defaultTimeoutLength);

      // no time out setting
      // socket = new Socket(ip, port);

      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintStream(socket.getOutputStream());
      // logger.info("Connected to " + socket.getInetAddress() + ":" + socket.getPort());

      try {
        socket.setSoTimeout(defaultTimeoutLength);
      } catch (SocketException se) {
        // logger.warning("Unable to set socket option SO_TIMEOUT");
      }

      // send open gate signal
      out.println(sigOpenGate);

      // waiting for the response
      String msg = null;
      try {
        while ((msg = in.readLine()) != null) {
          if (msg.equalsIgnoreCase("OK")) {
            ret = LPS.RES_OPEN;
          } else if (msg.equalsIgnoreCase("MCM")) {
            ret = LPS.RES_MANUAL_CONTROL_MODE;
          }
          break;
        }
      } catch (SocketTimeoutException e) {
        // connection time out during readLine()
        ret = LPS.RES_TIME_OUT;
        // logger.warning("Socket TIMEOUT. " + e.getMessage());
      }
    } catch (IOException ex) {
      // ex.printStackTrace();
      // connection failed
      ret = LPS.RES_FAIL;
      // logger.warning(ret + " IP add = " + ip);
      // logger.warning(ip);
    }

    if (socket != null) {
      try {
        socket.close();
      } catch (IOException ioe) {
        // logger.warning(ioe.getMessage());
      }
    }
    return ret;
  }


}
