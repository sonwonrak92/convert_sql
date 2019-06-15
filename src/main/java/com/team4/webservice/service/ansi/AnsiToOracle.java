package com.team4.webservice.service.ansi;

public class AnsiToOracle implements Ansi{


    @Override
    public void moveToFrom() {

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

    @Override
    public void removeSpaces() {
        //1
        String str = "";
        str.replaceAll("\\p{Z}","");
    }
}
