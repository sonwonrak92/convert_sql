package com.team4.webservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HomeController {
	
	@GetMapping("/main")
	public String main(Model model) {

		return "main";
	}
	@PostMapping("/test")
	public String test(@RequestBody String str) {
		System.out.println(str);
		return "main";
	}
	
	

}
