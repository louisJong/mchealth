package com.song.project.mchealth.admin.test;
/**
 * 
 */


import org.apache.log4j.Logger;
import org.unitils.UnitilsJUnit3;
import org.unitils.spring.annotation.SpringApplicationContext;

/**
 * base test case
 * @author song
 */
@SpringApplicationContext({"classpath*:applicationContext-core-test.xml"})
public class BaseTestCase extends UnitilsJUnit3 {	
	public Logger logger = Logger.getLogger(BaseTestCase.class);
	public void test(){	
	}
}

