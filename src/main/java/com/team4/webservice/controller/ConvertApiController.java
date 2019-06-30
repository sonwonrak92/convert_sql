package com.team4.webservice.controller;

import java.util.Map;

import com.team4.webservice.common.QueryConvertUtil;
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
       boolean check = false;
        System.out.println(str);

        str = QueryConvertUtil.replaceLnToSpace(str);
        check = QueryConvertUtil.valCheck(str);
        System.out.println("ConvertApiController[채유진]");
        System.out.println(check);

        String newQuery;

        if(check){
            newQuery = QueryConvertUtil.replaceAllSingleSpace(str);

            newQuery = QueryConvertUtil.SetQueryText(newQuery,QueryConvertUtil.GetQueryText(str));

            //System.out.println(newQuery);


        }

        return null;    // TODO 임시
    }

}
