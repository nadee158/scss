package com.privasia.scss.cosmos.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.util.ApplicationConstants;

@Component("cosmosMessageCode")
public class COSMOSMessageCode  {

	private Set<Object> keys = null;

	private Properties prop = null;

	private ResourceLoader resourceLoader;
	
	@Autowired
	public COSMOSMessageCode(ResourceLoader resourceLoader) throws IOException {
		this.resourceLoader = resourceLoader;
		InputStream is = null;
		try {
			this.prop = new Properties();
			Resource file = this.resourceLoader.getResource(ApplicationConstants.COSMOS_MSG_CODE_PROPERTY_FILE);
			is = file.getInputStream();
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
