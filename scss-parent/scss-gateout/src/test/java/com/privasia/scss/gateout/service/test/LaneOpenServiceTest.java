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

import com.privasia.scss.common.dto.LaneOpenDTO;
import com.privasia.scss.gateout.service.LaneOpenService;
import com.privasia.scss.gateout.test.GateOutAbstractTest;

/**
 * @author Janaka
 *
 */
@Transactional(value = "transactionManager")
public class LaneOpenServiceTest extends GateOutAbstractTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Autowired
  private LaneOpenService laneOpenService;

  @Test
  public void testCheckLaneOpenStatus() {
    LaneOpenDTO laneOpenDTO = laneOpenService.checkLaneOpenStatus(10l);
    Assert.assertNotNull(laneOpenDTO);
  }



}
