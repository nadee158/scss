package com.privasia.scss.cosmos.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.privasia.scss.common.util.AGSClient;
import com.privasia.scss.cosmos.dto.exprequest.CSMCTL;
import com.privasia.scss.cosmos.dto.exprequest.GOTTRCINF;
import com.privasia.scss.cosmos.dto.exprequest.Message;
import com.privasia.scss.cosmos.dto.exprequest.SGS2Cosmos;

public class GateOutXMLRequestService {

	public static void main(String[] args) {
		//sendRequestExample();
		readResponseExample();
	}
	
	public static void readResponseExample(){
		
		try{
			File file = new File("D:\\SCSS\\scss\\scss-parent\\scss-cosmos\\src\\main\\resources\\response.xml");
			if(file.exists()){
				System.out.println("found");
			}else{
				System.out.println("not found");
			}
			JAXBContext jaxbContext = JAXBContext.newInstance(com.privasia.scss.cosmos.dto.response.SGS2Cosmos.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			com.privasia.scss.cosmos.dto.response.SGS2Cosmos response = (com.privasia.scss.cosmos.dto.response.SGS2Cosmos) jaxbUnmarshaller.unmarshal(file);
			System.out.println(response);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void sendRequestExample(){
		final SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyyMMdd");
	    final SimpleDateFormat hhmmss = new SimpleDateFormat("HHmmss");
		String date = yyyymmdd.format(new java.util.Date());
        String time = hhmmss.format(new java.util.Date());
		
		CSMCTL csmctl = new CSMCTL();
		csmctl.setRQST("GSRQS");
		csmctl.setACTN("CRT");
		csmctl.setRTNC("0");
		csmctl.setERRI("ErrorMessage");
		csmctl.setRQDS("CTEDSC");
		csmctl.setRTNM("AS");
		csmctl.setUSID(toUpperCase("isuru"));
		csmctl.setRQUI(String.valueOf(System.currentTimeMillis()));
		csmctl.setTRMC("WPT1");
		
		GOTTRCINF gottrcinf = new GOTTRCINF();
		gottrcinf.setMSGTSC("GOTTRCINF");
		gottrcinf.setLANESC(toUpperCase("gate08"));
		gottrcinf.setVMIDSC(toUpperCase("truck123"));
		gottrcinf.setATDDSC(date);
		gottrcinf.setATDTSC(time);
		
		Message message = new Message();
		message.setCSMCTL(csmctl);
		message.setGOTTRCINF(gottrcinf);
		message.setIndex(1);
		
		List<Message> massageList = new ArrayList<Message>();
		massageList.add(message);
		
		SGS2Cosmos cosmos = new SGS2Cosmos();
		cosmos.setMessage(massageList);
		
		try {
			//Marshalling
			JAXBContext jaxbContext = JAXBContext.newInstance(SGS2Cosmos.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller(); 
			
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(cosmos, System.out);
			java.io.StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(cosmos, sw);
			
			int portNo = 12013;
		    String host = "172.21.150.1";
		    
		    String replyXML = AGSClient.sendXMLMessage(sw.toString(), portNo);
		    System.out.println(replyXML);
		    
		    //Unmarshalling
		    JAXBContext jaxbContext2 = JAXBContext.newInstance(com.privasia.scss.cosmos.dto.expresponse.SGS2Cosmos.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext2.createUnmarshaller();
			InputStream is = new ByteArrayInputStream(replyXML.getBytes());
			com.privasia.scss.cosmos.dto.expresponse.SGS2Cosmos customer = (com.privasia.scss.cosmos.dto.expresponse.SGS2Cosmos) jaxbUnmarshaller.unmarshal(is);
			System.out.println(customer.getMessage().getCSMCTL().getERRI());
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public static String toUpperCase(String str) {
        if (str != null) {
            str = str.trim();
            return str.toUpperCase();
        } else {
            return str;
        }
    }
	
}
