/**
 * 
 */
package com.privasia.scss.core.security.jwt.extractor;

/**
 * @author Janaka
 *
 */
public interface TokenExtractor {
	public String extract(String payload);
}
