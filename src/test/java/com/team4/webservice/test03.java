package com.team4.webservice;

import java.util.HashMap;
import java.util.Map;

public class test03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	     String str = 
	     		"SELECT PRDM.WH_NO,\r\n" + 
	     		"           PRDM.MD_ID AS MD_ID,\r\n" + 
	     		"          CMDD.STKI_CMD_ID  AS \"STKI_CMD_ID\",\r\n" + 
	     		"          CMDD.STKI_CMD_DTL_SEQ \"STKI_CMD_DTL_SEQ\",\r\n" + 
	     		"          POD.PO_NO AS PO_NO,\r\n" + 
	     		"          CMDM.STKI_CMD_PGS_STS_CD AS STKI_CMD_PGS_STS_CD,\r\n" + 
	     		"     FROM FUL_PO_M POM,\r\n" + 
	     		"\r\n" + 
	     		"FUL_PD_D POD,\r\n" + 
	     		"PRD_PRD_M PRDM,\r\n" + 
	     		"FUL_STKI_CMD_D CMDD,\r\n" + 
	     		"PRD_UNT_PRD PRDD,\r\n" + 
	     		"FUL_STKI_CMD_M CMDM,\r\n" + 
	     		"PRD_DLR_M DLR,\r\n" + 
	     		"PRD_DLR_M DLR,\r\n" + 
	     		"WHERE POM.PO_NO = PO_NO\r\n" + 
	     		"  AND POM.PO_NO = POD.PO_NO\r\n" + 
	     		"  AND POD.PRD_ID = PRDM.PRD_ID\r\n" + 
	     		"  AND CMDM.STKI_CMD_ID = CMDD.STKI_CMD_ID(+)\r\n" + 
	     		"  AND POD.UNT_PRD_ID = CMDD.UNT_PRD_ID(+)\r\n" + 
	     		"  AND PRDM.PRD_ID = PRDD.PRD_ID\r\n" + 
	     		"  AND PRDD.UNT_PRD_ID = POD.UNT_PRD_ID\r\n" + 
	     		"  AND CMDM.PO_NO = POM.PO_NO(+)\r\n" + 
	     		"  AND PRDM.DLR_ID(+) = DLR.DLR_ID\r\n" + 
	     		" AND PRDM.DLR_ID = DLR.DLR_ID(+)";
	      
	      Map<String, String> qr = new HashMap<>();

	      boolean sortLeft = true;
	      boolean commaLeft = false;
	      
	      qr = querySplit(str);

	      if(commaLeft) {
	         qr.put("SELECT", commaLeftSplit(qr.get("SELECT"), ","));
	         qr.put("FROM", commaLeftSplit(qr.get("FROM"), ","));
	         qr.put("WHERE", commaLeftSplit(qr.get("WHERE"), "AND"));
	      } else {
	         qr.put("SELECT", commaRightSplit(qr.get("SELECT"), ","));
	         qr.put("FROM", commaRightSplit(qr.get("FROM"), ","));
	         qr.put("WHERE", commaRightSplit(qr.get("WHERE"), "AND"));
	      }   
	      printMap(qr, sortLeft);
	      
	      System.out.println(printMap(qr, sortLeft));
	      
	   }
	   
	   public static Map<String, String> querySplit(String str){
	      Map<String, String> qr = new HashMap<>();
	      
	      int fromIndex = str.indexOf("FROM");
	      int whereIndex = str.indexOf("WHERE");
	      
	      String selectStr = str.substring(6,fromIndex);
	      String fromStr = str.substring(fromIndex + 4, whereIndex);
	      String whereStr = str.substring(whereIndex+5, str.length());
	      
	      qr.put("SELECT", selectStr.trim());
	      qr.put("FROM", fromStr.trim());
	      qr.put("WHERE", whereStr.trim());

	      
	      return qr;
	   }

	   public static String commaLeftSplit(String str, String sep) {
	      String[] strArr = str.split(sep);
	      String result = "";
	      
	      for(String temp : strArr) {
	         if(sep =="AND") {
	            result = result + "       " + sep + " " + temp.trim() + "\n";
	         }else {
	            result = result + "         " + sep + " " + temp.trim() + "\n";
	         }
	      }
	      //String.join("\n", strArr);
	      
	      result = result.replaceFirst("," , " ");
	      result = result.replaceFirst("AND", "   ");
	      
	      return result;
	      
	      /*
	       * String[] names = new String[] {"홍길동", "임꺽정", "슈퍼맨", "배트맨", "아이언맨" };
	       * List<String> nameList = Arrays.asList(names); String sql1 = nameList.stream()
	       * .map(name -> "'" + name + "'" ) .collect(Collectors.joining(","));
	       */
	   }
	   
	   public static String commaRightSplit(String str, String sep) {
	      String[] strArr = str.split(sep);
	      String result = "";
	      
	      for(String temp : strArr) {
	         result = result + "         " + temp.trim() + " "+ sep + "\n";
	      }
	      result = result.substring(0, result.lastIndexOf(sep));
	      return result;
	   }
	   
	   public static String printMap (Map<String, String> qr, boolean sortLeft) {
	      String returnStr = "";
	      if(sortLeft) {
	         returnStr = returnStr + "SELECT" + "\n" + qr.get("SELECT") + "\n";
	         returnStr = returnStr + "FROM  " + "\n" + qr.get("FROM")+ "\n";
	         returnStr = returnStr + "WHERE " + "\n" + qr.get("WHERE");
	      } else {
	         returnStr = returnStr + "SELECT" + "\n" + qr.get("SELECT") + "\n";
	         returnStr = returnStr + "  FROM" + "\n" + qr.get("FROM")+ "\n";
	         returnStr = returnStr + " WHERE" + "\n" + qr.get("WHERE");
	         
	      }
	      return returnStr;
	   }

	}
