/**
 * 
 */
package com.privasia.scss.gateout.service.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.gateout.service.CustomsService;
import com.privasia.scss.gateout.test.GateOutAbstractTest;

/**
 * @author Janaka
 *
 */
@Transactional(value = "transactionManager")
public class CustomsServiceTest extends GateOutAbstractTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Autowired
  private CustomsService customsService;

  @Test
  public void testCheckCustomStatus() {
    String status = customsService.checkCustomStatus(0l);
    Assert.assertNotNull(status);
  }


}
