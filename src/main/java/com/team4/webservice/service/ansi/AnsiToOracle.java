package com.team4.webservice.service.ansi;

import org.springframework.stereotype.Service;
//모든 코드는 데이터를 받아왔다는 전제로 한다.
//받아야하는 데이터 범위 : INNER부터  끝까지
//1) INNER JOIN OUTER RIGHT JOIN은
//현재 ,로 받아왔다는 전제로 코딩하였음
//만약 ,로 치환이 아니라면 list 하나에 [INNER JOIN] 통째로 넣어 주길 바람

//2) 데이터를 받아오는 형식은 ArrayList안에 ArrayList를 제네릭으로 받는 것으로 구성하였음
//	ex) ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

//3) 값을 넘길때는 밑에와같이 잘라서 넘겨주길 바람
//**************************************
//INNER JOIN DEPT1 AS 2    (0)
//**************************************
//
//**************************************
//ON A.ID = B.ID			(1)
//AND C.ID = D.ID
//**************************************
//
//**************************************
//LEFT OUTER JOIN DEPT2 AS 3	(2)
//**************************************
//
//**************************************
//ON A.ID = B.ID			(3)
//**************************************
//
//**************************************
//WHERE A.ID > 1000			(4)
// AND C.ID < 2000
//**************************************
import java.util.ArrayList;

@Service
public class AnsiToOracle implements Ansi{


    @Override
    public void moveToFrom() {
        // TODO Auto-generated method stub
        ArrayList<String> result = new ArrayList<>();
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();


        //,가 들어가는 것들 innerOuter
        //where 가 있는지 확인
        ArrayList<String> innerOuter = new ArrayList<>();
        ArrayList<String> where = new ArrayList<>();

        boolean where_yn = false; // where가 있을시 true로 전환
        int attachPlus_yn = 0;

        for(int i=0;i < list.size();i++) {
            ArrayList<String> inList = list.get(i);

            //where가 있는지 check
            if(inList.get(0).equals("WHERE")) {
                where_yn = true;
            }


            for(int j=0;j<inList.size();j++) {
                // inner join outer join은 , 가 들어와서 처리됨으로 , 가 있는지 확인
//				if(inList.get(0).equals("INNER") || inList.get(0).equals("LEFT") || inList.get(0).equals("RIGHT")) {
//					innerOuter.add(inList.get(j));
//				}
//				//LEFT 일때
//				if(inList.get(0).equals("LEFT")) {
//
//					list.get(i+1).add(2,"(+)");
//					innerOuter.add(inList.get(j));
//				}
//				//RIGHT 일때
//				if(inList.get(0).equals("RIGHT")) {
//
//					list.get(i+1).add(4,"(+)");
//					innerOuter.add(inList.get(j));
//				}

                switch (inList.get(0)) {
                    case "LEFT":
                        //해당 다음 번째 인덱스의 2 번째 번지에 (+) 추가   "*i+1은 ON 절이 있는 부분"
                        list.get(i+1).add(2,"(+)");
                        innerOuter.add(inList.get(j));
                        break;

                    case "RIGHT":
                        //해당 다음 번째 인덱스의 4 번째 번지에 (+) 추가
                        list.get(i+1).add(4,"(+)");
                        innerOuter.add(inList.get(j));
                        break;

                    default:
                        innerOuter.add(inList.get(j));
                        break;

                }
                //innerOuter.add(inList.get(j));


                if(j==0) {
                    inList.get(j).replace("ON", " ");
                }
                //그다음 들어오는 문자열이 ON일때 AND로 전환
                if(j!=0 && inList.get(j).equals("ON")) {
                    inList.get(j).replace("ON", "AND");
                }


                innerOuter.add(inList.get(j));

                //where의 arraylist에 추가
                if(inList.get(0).equals("WHERE")) {
                    where.add(inList.get(j));
                }

            }

            //where가 있을시 where arraylist에  innerOuter list 추가
            if(where_yn == true) {
                for(int i1=0;i1<innerOuter.size();i1++) {
                    where.add(innerOuter.get(i1));
                }
            }
            //where 가 없으면 innerOuter 그냥 리턴
            //innerOuter첫번째 방에 WHERE추가해야함
        }

    }

    @Override
    public void onToWhere() {

    }

    @Override
    public void attachPlus() {

    }

    @Override
    public void attachStr() {
        String str = "";
        //2) char 배열으로 넘길때
        char[] arr;
        arr = str.toCharArray();

        for(int i=0;i<arr.length;i++){
            if(97<=arr[i] && arr[i] <=122)
                arr[i] = (char)(arr[i]-32);
        }

        //1) string 값으로 넘길때
        //str.toUpperCase();

    }

    @Override
    public void splitStr() {

    }

    //@Override
    public void removeSpaces() {
        //1
        String str = "";
        str.replaceAll("\\p{Z}","");
    }
}
