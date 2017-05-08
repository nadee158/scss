/**
 * 
 */
package com.privasia.scss.cosmos.xml.element.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.privasia.scss.cosmos.xml.element.CSMCTL;

/**
 * @author Janaka
 *
 */
@Service("csmctlService")
public class CSMCTLService {
	
	public CSMCTL constructCSMCTL(String loginUser, String uniqueId){
		
		CSMCTL csmctl = new CSMCTL();
		csmctl.setRQST("GSRQS");
		csmctl.setACTN("CRT");
		csmctl.setRTNC("0");
		csmctl.setRQDS("CTEDSC");
		csmctl.setRTNM("AS");
		csmctl.setUSID(StringUtils.upperCase(loginUser));
		csmctl.setRQUI(uniqueId);
		csmctl.setTRMC("WPT1");
		return csmctl;
	}
	
	
	/*+ "<CSMCTL>\n"
    + "<RQST>GSRQS</RQST>\n" //Request Code : To hard code
    + "<ACTN>CRT</ACTN>\n" //Action Code : To hard code
    + "<RTNC>0</RTNC>\n" //Return Code : To hard code
    + errXMLMsg1
    + "<RQDS>CTEDSC</RQDS>\n" //Requestor Data Structure : To hard code
    + "<RTNM>AS</RTNM>\n" //Return Mode : To hard code
    + "<USID>" + toUpperCase(username) + "</USID>\n" //User ID : To capture SCSS user id
    + "<RQUI>" + msgUniqueId + "</RQUI>\n" //To input msg unique id
    + "<TRMC>WPT1</TRMC>\n" //Terminal : To hard code
    + "</CSMCTL>\n"*/

}
