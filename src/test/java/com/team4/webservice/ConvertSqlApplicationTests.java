package com.team4.webservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.team4.webservice.common.QueryConverUtil;
import com.team4.webservice.service.ansi.AnsiToOracle;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConvertSqlApplicationTests {
	@Autowired
	AnsiToOracle ansi;

	@Test
	public void contextLoads() {
		final String FIND_SPACE_REGEX = "(\\s{2,})";
		final String BETWEEN_DQ_REGEX = "(\"([^\"]|\"\")*\")";
		String oldQuery = "SELECT     ID   AS \"사      번\" FROM EMP AS \"add  df\" INNER JOIN      DEP    AS \"345\" ON A.ID = B.ID WHERE A.ID > 1000 ;";
		String newQuery;
		
		newQuery = QueryConverUtil.replaceAllSingleSpace(oldQuery);
		
		newQuery = QueryConverUtil.SetQueryText(newQuery,QueryConverUtil.GetQueryText(oldQuery));
	
		System.out.println(newQuery);
		
	}

}
