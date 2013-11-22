package com.balancetask.testspring.test;

import org.springframework.ui.ModelMap;

import com.balancetask.testspring.TestController;

import junit.framework.TestCase;

public class TestControllerTest extends TestCase {

	public void testHelloWorld() {
		TestController c = new TestController();
		ModelMap m = new ModelMap();
		c.helloWorld(m);
		
		assertTrue(((String)m.get("message")).startsWith("Hello from"));
	}
	
	public void testHelloName() {
		TestController c = new TestController();
		ModelMap m = new ModelMap();
		c.helloName("TestHelloName", m);
		
		assertTrue(((String)m.get("message")).contains("TestHelloName"));
	}
}
