package com.team4.webservice.service.ansi;

import com.team4.webservice.service.common.CommonInterface;

public interface Ansi extends CommonInterface {
//    SELECT *
//      FROM A (1)
//      INNER JOIN B (1)
//        ON A.a = B.b; (2)

//        WHERE (2)  (+)(3)
    //(1)From 옆으로 가는 경우
    void moveToFrom();

    //(2) Where절로 ON에 있는 내용 이동하는 경우
    void onToWhere();

    //(3) OUTER JOIN 시 (+) 기호 붙이는 경우
    void attachPlus();
}
