package com.team4.webservice;

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
		Map<String, String> param = new HashMap<>();
		param.put("targetText", inSql);
		controller.ansiToOracle(param);
	}
	@Test
	public void 이상훈_테스트2(){
		String inSql = "FROM test t\n" +
				"SELECT '123' AS number\n" +
				"WHERE 1=1";
		Map<String, String> param = new HashMap<>();
		param.put("targetText", inSql);
		controller.ansiToOracle(param);
	}
	@Test
	public void 이상훈_테스트3(){
		String inSql = "SELECT *\n" +
				"FROM TEST A\n" +
				"left outer join b\n" +
				"on a.title, b.title\n" +
				"GROUP BY A.TITLE\n" +
				"ORDER BY A.NAME;";
		Map<String, String> param = new HashMap<>();
		param.put("targetText", inSql);
		controller.ansiToOracle(param);
	}
	@Test
	public void 이상훈_테스트4(){
		String inSql = "      SELECT PRDM.WH_NO\n" +
				"          , PRDM.MD_ID AS MD_ID\n" +
				"          ,CMDD.STKI_CMD_ID  AS \"STKI_CMD_ID\"\n" +
				"          ,CMDD.STKI_CMD_DTL_SEQ \"STKI_CMD_DTL_SEQ\"\n" +
				"          ,POD.PO_NO AS PO_NO\n" +
				"          ,CMDM.STKI_CMD_PGS_STS_CD AS STKI_CMD_PGS_STS_CD\n" +
				"     FROM FUL_PO_M POM\n" +
				"           INNER JOIN FUL_PO_D POD ON POM.PO_NO = POD.PO_NO\n" +
				"           INNER JOIN PRD_PRD_M PRDM ON POD.PRD_ID = PRDM.PRD_ID\n" +
				"           LEFT OUTER JOIN FUL_STKI_CMD_D CMDD ON CMDM.STKI_CMD_ID = CMDD.STKI_CMD_ID AND POD.UNT_PRD_ID = CMDD.UNT_PRD_ID\n" +
				"           INNER JOIN PRD_UNT_PRD_D PRDD ON PRDM.PRD_ID = PRDD.PRD_ID AND PRDD.UNT_PRD_ID = POD.UNT_PRD_ID\n" +
				"           LEFT OUTER JOIN FUL_STKI_CMD_M CMDM ON CMDM.PO_NO = POM.PO_NO\n" +
				"           RIGHT OUTER JOIN PRD_DLR_M DLR ON PRDM.DLR_ID = DLR.DLR_ID\n" +
				"           LEFT OUTER JOIN PRD_DLR_M DLR ON PRDM.DLR_ID = DLR.DLR_ID\n" +
				"     WHERE POM.PO_NO = PO_NO\n" +
				"     ORDER BY CMDD.STKI_CMD_ID DESC, CMDD.STKI_CMD_DTL_SEQ";
		Map<String, String> param = new HashMap<>();
		param.put("targetText", inSql);
		controller.ansiToOracle(param);
	}


}
