
package com.privasia.scss.gateout.test;


import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.privasia.scss.gateout.GateOutEntryPoint;

/**
 * @author Janaka
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {GateOutEntryPoint.class})
public class GateOutAbstractTest {

  protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
