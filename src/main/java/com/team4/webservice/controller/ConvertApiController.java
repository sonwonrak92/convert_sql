package com.team4.webservice.controller;

import java.util.ArrayList;
import java.util.Map;

import com.team4.webservice.common.QueryConvertUtil;
import com.team4.webservice.service.ansi.AnsiToOracle;
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
        str = str.trim().toUpperCase(); //앞뒤공백제거 대문자변환

        str = QueryConvertUtil.replaceLnToSpace(str);
        check = QueryConvertUtil.valCheck(str);
        //System.out.println("ConvertApiController[채유진] > "+ check + " :" + str);

        String newQuery;

        if(check){
            newQuery = QueryConvertUtil.replaceAllSingleSpace(str);

            //2019.06.30 이상훈 코드 추가(split 후 최준우 파라미터 전송)
            AnsiToOracle ansiToOracle = new AnsiToOracle();
            ArrayList<ArrayList<String>> list = ansiToOracle.parseStrToArr(newQuery);

            StringBuffer sb = ansiToOracle.moveToFrom(list);
            System.out.println("반환데이터(이상훈 > 최준우)");
            System.out.println(sb);

            newQuery = QueryConvertUtil.setQueryText(newQuery,QueryConvertUtil.getQueryText(str));

            //System.out.println(newQuery);


        }

        return null;    // TODO 임시
    }

}
