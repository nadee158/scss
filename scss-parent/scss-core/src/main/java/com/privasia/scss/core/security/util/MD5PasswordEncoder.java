/**
 * 
 */
package com.privasia.scss.core.security.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Janaka
 *
 */
@Component
public class MD5PasswordEncoder implements PasswordEncoder {

  private static Logger logger = LoggerFactory.getLogger(MD5PasswordEncoder.class);

  @Override
  public String encode(CharSequence rawPassword) {
    MessageDigest md;
    try {
      md = MessageDigest.getInstance("MD5");
      md.update(rawPassword.toString().getBytes());
      return HexString.bufferToHex(md.digest());
    } catch (NoSuchAlgorithmException e) {
      logger.error("No Such Algorithm Found To encode ");
    }

    return null;
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {

    boolean verify = false;

    String encodedTestPassword = encode(rawPassword);
    if (encodedTestPassword.equals(encodedPassword)) {
      verify = true;
    }

    return verify;
  }

}
