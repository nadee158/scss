/**
 * 
 */
package com.privasia.scss.cosmos.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.cosmos.dto.request.CosmosGateInWriteRequest;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutWriteRequest;
import com.privasia.scss.cosmos.dto.request.CosmosGateWriteRequest;
import com.privasia.scss.cosmos.xml.element.SGS2CosmosRequest;
import com.privasia.scss.cosmos.xml.element.SGS2CosmosResponse;

/**
 * @author Janaka
 *
 */
@Service("agsClientService")
public class AGSClientService {

	private static final Log log = LogFactory.getLog(AGSClientService.class);

	@Value("${ags.max.timeout}")
	private int agsMaxTimeOut;

	@Value("${ags.host}")
	private String agsHost;

	private static final int SECOND = 1000;
	private static final int HEADER_SIZE = 51;

	public SGS2CosmosResponse sendToCosmos(SGS2CosmosRequest writeRequest, int portNo) throws JAXBException {
		
		// Marshalling
		JAXBContext jaxbContext = JAXBContext.newInstance(SGS2CosmosRequest.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(writeRequest, System.out);
		java.io.StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(writeRequest, sw);

		String responseXML = null;
		responseXML = sendRequestXml(sw.toString(), portNo);
		
		if(StringUtils.isEmpty(responseXML))
			throw new BusinessException("No response received from cosmos for the request !  "+writeRequest.getCSMCTL().getRQUI());
		
		jaxbContext = JAXBContext.newInstance(SGS2CosmosResponse.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

		StringReader reader = new StringReader(responseXML);
		SGS2CosmosResponse response = (SGS2CosmosResponse) unmarshaller.unmarshal(reader);
		

		return response;

	}

	private String sendRequestXml(String requestXML, int portNo) {

		Socket socket = null;
		DataOutputStream out = null;
		DataInputStream input = null;
		String responseXMl = StringUtils.EMPTY;
		String s = null;
		byte[] header = new byte[HEADER_SIZE];
		int length = 0;
		String msgType = StringUtils.EMPTY;
		String msgId = StringUtils.EMPTY;
		int timeOutCount = 0;
		try {

			socket = new Socket(agsHost, portNo);
			socket.setSoTimeout(agsMaxTimeOut * SECOND);
			socket.setKeepAlive(true);
			socket.setSoLinger(true, agsMaxTimeOut);

			out = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			out.write(createRequestXMLMessage(System.currentTimeMillis() + "", requestXML).getBytes());
			timeOutCount = 0;
			while (timeOutCount < 120) {
				if (input.available() > 0) {
					input.read(header, 0, HEADER_SIZE);
					s = new String(header);
					log.error("-------------START AGS -----------");
					length = Integer.parseInt(s.substring(0, 6));
					msgType = s.substring(6, 16).trim();
					msgId = s.substring(16, 51).trim();
					byte[] buffer = new byte[length];
					int actualRead = input.read(buffer, 0, length);
					log.error(">> Length:" + length + " Actual Received:" + actualRead);
					int totalRead = actualRead;
					if (actualRead < length) {
						int retryAttempt = 0;
						while (totalRead < length) {
							log.error(">> Did not get full content.. trying to read the rest of the file");
							totalRead += input.read(buffer, totalRead, length - totalRead);
							log.error(">> Length:" + length + " Actual Received:" + actualRead + " total Read:"
									+ totalRead);
							retryAttempt++;
							Thread.sleep(500);
							if (retryAttempt > 10) {
								break;
							}
						}
					}
					if (totalRead >= length)
						if (msgType.equals("REPLY") && !msgId.equals("LIVECHECK")) {
							responseXMl = new String(buffer);
							break;
						}
				}
				timeOutCount++;
				Thread.sleep(1000);
				log.error("-------------END AGS -----------");
			}

		} catch (SocketTimeoutException se) {
			se.printStackTrace();
			throw new BusinessException("AGS Connection time out. " + se.getMessage());
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BusinessException(
					"The first byte cannot be read or input stream has been closed or some other I/O error. "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException("AGS Connection failed. " + ex.getMessage());
		} finally {
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

		return responseXMl;
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

}
