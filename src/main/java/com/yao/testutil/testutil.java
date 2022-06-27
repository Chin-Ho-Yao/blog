package com.yao.testutil;

/**
 * Created by Jack Yao on 2022/1/13 11:13 AM
 */
public class testutil {
    public static void main(String[] args) {

        char a = 'A';
        char b = 'B';
        int n = 0;
        int m = 11;
        int p = -11;
        boolean t = true;
        System.out.println("-----------00----------");
        System.out.print(isOdd(m) ? a : 0);
        System.out.println("\n-----------4----------");
        System.out.print(isOdd(m) ? a : m);
    }
    public static boolean isOdd(int num){
        return (num % 2 == 1);
    }


}
