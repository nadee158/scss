/**
 * 
 */
package com.privasia.scss.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Janaka
 *
 */
public enum CollectionType implements Enumable {

	ZIP_FILE_COLLECTION("zipFile"), PDF_FILE_COLLECTION("pdfFile"), SOLAS_CERTIFICATE_COLLECTION("solasCertificate");

	private final String collectionType;

	private CollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	/**
	 * @return the collectionType
	 */
	public String getValue() {
		return collectionType;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, CollectionType> LOOKUP = new HashMap<String, CollectionType>();

	static {
		for (CollectionType collectionType : EnumSet.allOf(CollectionType.class)) {
			LOOKUP.put(collectionType.getValue(), collectionType);
		}
	}

	public static CollectionType fromValue(String value) {
		return LOOKUP.get(value);
	}

}
