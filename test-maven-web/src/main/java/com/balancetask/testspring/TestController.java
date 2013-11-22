package com.balancetask.testspring;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

	@RequestMapping(value="/hello")
	public String helloWorld(ModelMap model) {
		model.addAttribute("message", "Hello from TestController");
		
		return "index";
	}

	@RequestMapping(value="/hello/{name}")
	public String helloName(@PathVariable("name") String name, ModelMap model) {
		model.addAttribute("message", "Hello " + name + " from TestController");
		
		return "index";
	}
}
