package com.team4.webservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.team4.webservice.common.QueryConvertUtil;
import com.team4.webservice.service.ansi.AnsiToOracle;

@RestController
public class ConvertApiController {
	@Autowired
	AnsiToOracle ato;
	
    @PostMapping("/ansi/oracle")
    public Map<String, String> ansiToOracle(@RequestBody Map<String, Object> param) {
    	
    	Map<String, String> result = new HashMap<>();
        boolean check = false;
        
        String query = param.get("query").toString(); //get Input Query
        ArrayList<String> oldQueryText = QueryConvertUtil.getQueryText(query); //get Input Query Text
        /*
         *	comma : 콤마옵션
         *	newLine : 개행옵션
         *	upperCase : 대소문자옵션
         */     
        Map option = (Map<String, Boolean>)param.get("option"); //get Input Option
    	 
       System.out.println(option.get("comma"));
       System.out.println(option.get("newLine"));
       System.out.println(option.get("upperCase"));
    	
    	/* Query Validation Check */ 
        String queryValChk = param.get("query").toString();
        queryValChk = queryValChk.trim().toUpperCase(); //앞뒤공백제거 대문자변환
        queryValChk = QueryConvertUtil.replaceLnToSpace(queryValChk); //개행-> 공백으로 변환
        check = QueryConvertUtil.valCheck(queryValChk);

    	/* Pass Validation Process */ 
        if(check){
        	String newQuery = ato.exec(query); //Convert ANSI to Oracle exec
        	newQuery = QueryConvertUtil.defaultLnSetting(newQuery); //set new line
        	newQuery = QueryConvertUtil.setQueryOption(newQuery, option); //set Option
        	newQuery = QueryConvertUtil.setQueryText(newQuery, oldQueryText); //set Text
        	result.put("resultQuery", newQuery);
        }
        return result;  
        
    }

}
