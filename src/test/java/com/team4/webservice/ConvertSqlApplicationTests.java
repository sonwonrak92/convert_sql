package com.team4.webservice;

import com.team4.webservice.service.ansi.Ansi;
import com.team4.webservice.service.ansi.AnsiToOracle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertSqlApplicationTests {
	@Autowired
	AnsiToOracle ansi;



	@Test
	public void contextLoads() {
	}

	@Test
	public void alias(){
	    String inSql = "A.TITLE   AS    \"   title\", A.name \"NAME \", a.age AGE, a.addr";
        System.out.println("inSql :: " + inSql);

        String result = ansi.makeAlias(inSql);
        System.out.println("result :: " + result);
	}
}
