package com.team4.webservice.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConvertApiController {

    /** ANSI SQL convert to Oracle SQL
     *
     * @param param
     * @return T^T
     * */
    @PostMapping("/ansi/oracle")
    public Map<String, String> ansiToOracle(@RequestBody Map<String, String> param) {
        
        
       String str = param.get("targetText");
       String[] strArr = str.split("\n");
       
       for(String a : strArr) {
    	   System.out.println(a);
    	   System.out.println("!!");
    	   
       }
        return null;    // TODO 임시
    }

}
