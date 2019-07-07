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

		newQuery = QueryConvertUtil.setQueryText(newQuery,QueryConvertUtil.getQueryText(oldQuery));

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
	public void 이상훈_테스트1(){
		String inSql = "SELECT select_list\n" +
				"\n" +
				"FROM TABLE1 T1\n" +
				"\n" +
				"INNER JOIN TABLE2 T2 ON T1.COL1 = T2.COL2\n" +
				"\n" +
				"WHERE T1.A = 'test' AND T2.B = 1;";
		System.out.println("inSql :: " + inSql);
		ArrayList<ArrayList<String>> result = ansi.parseStrToArr(inSql);
		System.out.println("result :: " + result);
	}
	@Test
	public void 이상훈_테스트2(){
		String inSql = "FROM test t\n" +
				"SELECT '123' AS number\n" +
				"WHERE 1=1";
		System.out.println("inSql :: " + inSql);
		ArrayList<ArrayList<String>> result = ansi.parseStrToArr(inSql);
		System.out.println("result :: " + result);
	}
	@Test
	public void 이상훈_테스트3(){
		String inSql = "SELECT *\n" +
				"FROM TEST A\n" +
				"GROUP BY A.TITLE\n" +
				"ORDER BY A.NAME;";
		System.out.println("inSql :: " + inSql);
		ArrayList<ArrayList<String>> result = ansi.parseStrToArr(inSql);
		System.out.println("result :: " + result);
	}


}
