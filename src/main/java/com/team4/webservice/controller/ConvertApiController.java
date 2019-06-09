package com.team4.webservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ConvertApiController {

    /** ANSI SQL convert to Oracle SQL
     *
     * @param param
     * @return T^T
     * */
    @GetMapping("/ansi/oracle")
    public Map<String, Object> ansiToOracle(@RequestBody String param) {
        System.out.println(param);
        return null;    // TODO 임시
    }

}
