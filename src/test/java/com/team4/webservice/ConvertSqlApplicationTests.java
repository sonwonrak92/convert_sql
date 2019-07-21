package com.team4.webservice;

import com.team4.webservice.common.OptionCommaAndSort;
import com.team4.webservice.common.QueryConvertUtil;
import com.team4.webservice.controller.ConvertApiController;
import com.team4.webservice.service.ansi.AnsiToOracle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertSqlApplicationTests {
	@Autowired
	ConvertApiController controller;
	@Autowired
	AnsiToOracle ansi;

	@Test
	public void contextLoads() {
		final String FIND_SPACE_REGEX = "(\\s{2,})";
		final String BETWEEN_DQ_REGEX = "(\"([^\"]|\"\")*\")";
		String oldQuery = "SELECT     ID   AS \"사      번\" FROM EMP AS \"add  df\" INNER JOIN      DEP    AS \"345\" ON A.ID = B.ID WHERE A.ID > 1000 ;";
		String newQuery;

		newQuery = QueryConvertUtil.replaceAllSingleSpace(oldQuery);

		newQuery = QueryConvertUtil.setQueryText(newQuery,QueryConvertUtil.getQueryText(oldQuery));

		System.out.println(newQuery);

	}

	@Test
	public void alias(){
	    String inSql = "SELECT A.TITLE AS \"   title\", A.name \"NAME \", a.age AGE, a.addr FROM TEST A WHERE 1=1;";
        System.out.println("inSql :: " + inSql);

//        String result = ansi.makeAlias(inSql);
        String result = OptionCommaAndSort.optAddAlias(inSql, false);
        System.out.println("result :: " + result);
	}



}
