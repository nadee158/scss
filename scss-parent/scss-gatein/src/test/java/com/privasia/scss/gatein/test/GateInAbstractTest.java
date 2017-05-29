
package com.privasia.scss.gatein.test;


import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.privasia.scss.gatein.GateInEntryPoint;

/**
 * @author Janaka
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {GateInEntryPoint.class})
public class GateInAbstractTest {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
