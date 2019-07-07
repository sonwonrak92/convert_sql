package com.team4.webservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.team4.webservice.common.QueryConvertUtil;
import com.team4.webservice.service.ansi.AnsiToOracle;

@RestController
public class ConvertApiController {
	@Autowired
	AnsiToOracle ato;
	
    @PostMapping("/ansi/oracle")
    public Map<String, String> ansiToOracle(@RequestBody Map<String, String> param) {
    	
    	Map<String, String> result = new HashMap<>();
    	String newQuery = null;
    	
        String query = param.get("targetText");
        String valChkQuery = param.get("targetText");
        
        String[] strArr = valChkQuery.split("\n");
        boolean check = false;
        
        valChkQuery = valChkQuery.trim().toUpperCase(); //앞뒤공백제거 대문자변환
        valChkQuery = QueryConvertUtil.replaceLnToSpace(valChkQuery);
        check = QueryConvertUtil.valCheck(valChkQuery);

        if(check){
        	ArrayList<String> oldQueryText = QueryConvertUtil.getQueryText(query);
        	newQuery = ato.exec(query);
        	newQuery = QueryConvertUtil.setQueryText(newQuery,oldQueryText);  
        	result.put("resultQuery", newQuery);
        }
        //옵션넣읍시다
        return result;    // TODO 임시
    }

}
