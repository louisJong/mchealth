package com.song.project.mchealth.admin.test;


import org.junit.Test;
import org.unitils.spring.annotation.SpringBean;

import com.song.project.mchealth.admin.service.impl.UserServiceImpl;

public class ServiceTest extends BaseTestCase {

	
	@SpringBean("userServiceImpl")
	UserServiceImpl userService;
	
	@Test
	public void test_01(){
		System.out.println(userService.login("admin", "123456"));
	}
	
}
