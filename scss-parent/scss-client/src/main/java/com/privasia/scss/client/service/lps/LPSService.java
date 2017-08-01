/**
 * 
 */
package com.privasia.scss.client.service.lps;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.repository.ClientRepository;

/**
 * @author Janaka
 *
 */
@Service("lpsService")
public class LPSService {
	
	@Value("${default.server.port}")
	private int defaultServerPort;
	
	@Value("${default.timeout.length}")
	private int defaultTimeOutLength;
	
	@Value("${open.gate.signal}")
	private String openGateSignal;
	
	private ClientRepository clientRepository;
	
	@Autowired
	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}
	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public String openGate(Long clientID) {
		Optional<Client> optClient = clientRepository.findOne(clientID);
		Client client = optClient.orElseThrow(()-> new ResultsNotFoundException("Invalid Client Id : "+clientID+" Not found."));
		
		String response = null;
		//172.16.178.11
		
		if(client.isCsmControl() || StringUtils.isEmpty(client.getLpsIPAddress())){
			response = "Transaction Successful. LPS controlled by Westports.";
		}else{
			System.out.println("client.getLpsIPAddress() "+client.getLpsIPAddress());
			System.out.println("client.isCsmControl() "+client.isCsmControl());
			System.out.println("defaultServerPort "+defaultServerPort);
			response = openGateByIP(client.getLpsIPAddress(), defaultServerPort);
			
			return LPSService.convertResponseToAMessage(response);
		}
		
		return response;
		
	}

	public String openGateByIP(String ip, int port) {

		Socket socket = null;
		DataOutputStream out = null;
		DataInputStream input = null;
		String response = null;
		try {

			InetSocketAddress serverAddress = new InetSocketAddress(ip, port);
			socket = new Socket();
		    socket.connect(serverAddress, defaultTimeOutLength);
		    socket.setSoTimeout(defaultTimeOutLength);
		    
		    out = new DataOutputStream(socket.getOutputStream());
		    input = new DataInputStream(socket.getInputStream());
		    out.writeUTF(openGateSignal);
		    
		    while(true){
		    	response = input.readUTF();
		    	if(StringUtils.isNotEmpty(response)){
		    		if (StringUtils.equalsIgnoreCase(response, "OK")) {
		    			response = ApplicationConstants.LPS_RES_OPEN;
		              } else if (StringUtils.equalsIgnoreCase(response, "MCM")) {
		            	  response = ApplicationConstants.LPS_RES_MANUAL_CONTROL_MODE;
		              }
		              break;
		    	}
		    }
		    
		} catch (SocketTimeoutException se) {
			response = ApplicationConstants.LPS_RES_TIME_OUT;
			se.printStackTrace();
		}catch (IOException ex) {
			response = ApplicationConstants.LPS_RES_FAIL;
			ex.printStackTrace();
		}finally{
			try {
				if (socket != null)
					socket.close();
				if (input != null)
					input.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return response;
	}
	
	private static String convertResponseToAMessage(String res) {
		
		System.out.println("Response message : "+res);
		
		switch (res) {
		case "O":
		case "o":	
			return "Transaction Successful.";
		
		case "M":
		case "m":	
			return "Transaction Successful. LPS is in Manual Control Mode.";
			
		case "T":
		case "t":	
			return "Transaction Successful. LPS time out. No response from LPS.";
			
		case "F":
		case "f":	
			return "Transaction Successful. LPS Connection failed.";

		default:
			return "???";
		}
	  }

}
