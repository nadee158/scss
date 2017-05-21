package com.privasia.scss.cosmos.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.util.ApplicationConstants;

@Component("cosmosMessageCode")
public class COSMOSMessageCode {

	private ResourceLoader resourceLoader;

	private Set<Object> keys = null;

	private Properties prop = null;

	@Autowired
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	private COSMOSMessageCode() throws IOException {
		InputStream is = null;
		try {
			this.prop = new Properties();
			is = resourceLoader.getResource(ApplicationConstants.COSMOS_MSG_CODE_PROPERTY_FILE).getInputStream();
			prop.load(is);
			getAllKeys();
		} finally {
			if (is != null) {
				is.close();

			}
		}

	}

	private Set<Object> getAllKeys() {
		keys = prop.keySet();
		return keys;
	}

	public String getMessageFromCode(String msgcode) {
		return this.prop.getProperty(msgcode);
	}

}
