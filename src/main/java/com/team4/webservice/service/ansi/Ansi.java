package com.team4.webservice.service.ansi;

import java.util.ArrayList;
import java.util.HashMap;

import com.team4.webservice.service.common.CommonInterface;
import com.team4.webservice.service.oracle.Oracle;


public interface Ansi extends CommonInterface {
//    SELECT *
//      FROM A (1)
//      INNER JOIN B (1)
//        ON A.a = B.b; (2)

//        WHERE (2)  (+)(3)

    //문자열 자르기
    default HashMap<String, ArrayList<String>> splitStr(String str){
        str = "SELECT A.A AS \"text\" FROM TABLE_A AS \"A\" INNER JOIN TABLE_B AS \"B\" ON A.A = B.B;";

        // 공백 제거 및 배열 변환
        String[] array = str.split(" ");

        //Ansi문법 인덱스 변수 생성
        int selectIdx = 0;
        int fromIdx = 0;
        int joinIdx = 0;
        int whereIdx = 0;
        int finishIdx = array.length;
        //
        for(int i=0;i<array.length;i++) {
            //System.out.println(array[i]);
            /*if("SELECT".equals(array[i])){
                System.out.println(i);
            }*/
            switch (array[i]){
                case "SELECT" :
                    selectIdx = i; //0
                    System.out.println(selectIdx);
                    break;
                case "FROM" :
                    fromIdx = i; //4
                    System.out.println(fromIdx);
                    break;
                case "INNER" :
                    joinIdx = i; //8
                    System.out.println(joinIdx);
                    break;
/*                case "WHERE" :
                    whereIdx = i;
                    System.out.println(whereIdx);*/
                default:
                    break;
            }
        }



        ArrayList<String> selectArr = new ArrayList<>();
        ArrayList<String> fromArr = new ArrayList<>();
        ArrayList<String> joinArr = new ArrayList<>();

        for(int i=0;i<array.length;i++){
//            if(selectIdx+1 >= i && i <= fromIdx-1){
//                selectArr.add(array[i]);
//            }
//            else if(fromIdx+1 >= i && i <= joinIdx-fromIdx-1){
//                selectArr.add(array[i]);
//            }
//            else if(joinIdx+2 >= i && i <= finishIdx-joinIdx-2){
//                selectArr.add(array[i]);
//            }
            if (joinIdx < i) {
                if (i == joinIdx+1) {
                    continue;
                }
                joinArr.add(array[i]);
            }
            else if (fromIdx < i) {
                if (i == joinIdx) {
                    continue;
                }
                fromArr.add(array[i]);
            }
            else if (selectIdx < i) {
                if (i == fromIdx) {
                    continue;
                }
                selectArr.add(array[i]);
            }


        }
       /* System.arraycopy(array, selectIdx+1, selectArr, 0, fromIdx-1);
        System.arraycopy(array, fromIdx+1, fromArr, 0, joinIdx-fromIdx-1);
        System.arraycopy(array, joinIdx+2, joinArr, 0, finishIdx-joinIdx-2);*/

        //arrMap.put(array[selectIdx], selectArr);
        for(int i=0;i<selectArr.size();i++){
            System.out.println(selectArr.get(i));
        }
        System.out.println("         ");
        for(int i=0;i<fromArr.size();i++){
            System.out.println(fromArr.get(i));
        }
        System.out.println("         ");
        for(int i=0;i<joinArr.size();i++){
            System.out.println(joinArr.get(i));
        }

        HashMap< String, ArrayList<String> > arrMap = new HashMap<>();
        arrMap.put("SELECT", selectArr);
        arrMap.put("FROM", fromArr);
        arrMap.put("INNERJOIN", joinArr);
        //TODO enum 추가!!
        System.out.println("1");
        System.out.println(selectArr);
        System.out.println("2");
        System.out.println(fromArr);
        System.out.println("3");
        System.out.println(joinArr);

        return arrMap;

    }
    //(1)From 옆으로 가는 경우
    void moveToFrom();

    //(2) Where절로 ON에 있는 내용 이동하는 경우
    void onToWhere();

    //(3) OUTER JOIN 시 (+) 기호 붙이는 경우
    void attachPlus();
    
}
