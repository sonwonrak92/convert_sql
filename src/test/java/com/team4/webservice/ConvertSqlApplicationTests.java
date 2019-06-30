package com.team4.webservice;

import com.team4.webservice.common.QueryConvertUtil;
import com.team4.webservice.service.ansi.AnsiToOracle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

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

		newQuery = QueryConvertUtil.replaceAllSingleSpace(oldQuery);

		newQuery = QueryConvertUtil.SetQueryText(newQuery,QueryConvertUtil.GetQueryText(oldQuery));

		System.out.println(newQuery);

	}

	@Test
	public void alias(){
	    String inSql = "A.TITLE   AS    \"   title\", A.name \"NAME \", a.age AGE, a.addr";
        System.out.println("inSql :: " + inSql);

        String result = ansi.makeAlias(inSql);
        System.out.println("result :: " + result);
	}

	@Test
	public void sql파싱(){
		String inSql = "SELECT * FROM TEST_A AS \"A\" LEFT OUTER JOIN TEST_B AS \"B\" ON A.ID = B.ID INNER JOIN TEST_C AS \"C\" ON B.ID = C.ID WHERE 1=1 AND A.NAME = '주누';";
		System.out.println("inSql :: " + inSql);
		ArrayList<ArrayList<String>> result = ansi.parseStrToArr(inSql);
		System.out.println("result :: " + result);

		inSql = "SELECT * FROM TEST_A AS \"A\" LEFT OUTER JOIN TEST_B AS \"B\" ON A.ID = B.ID INNER JOIN TEST_C AS \"C\" ON B.ID = C.ID;";
		System.out.println("inSql :: " + inSql);
		result = ansi.parseStrToArr(inSql);
		System.out.println("result :: " + result);
	}

}
