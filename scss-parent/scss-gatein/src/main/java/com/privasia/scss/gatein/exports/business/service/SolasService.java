/**
 * 
 */
package com.privasia.scss.gatein.exports.business.service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Janaka
 *
 */
@Service("solasService")
public class SolasService {
	
	@Value("${solas.cert.name}")
	private String solasCertName;

	public String generateSolasCertificateId(LocalDateTime gateInOK) throws ParseException {

		StringBuffer buffer = new StringBuffer();
		buffer.append(solasCertName);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		buffer.append(gateInOK.format(formatter));
		return buffer.toString();

	}
	

}
