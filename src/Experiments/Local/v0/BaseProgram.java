package Experiments.Local.v0;
import java.util.ArrayList;
import java.util.Scanner;
public class BaseProgram {
    public static boolean[] B = new boolean[50];
    public static char[] M = new char[256];
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Boolean[] I = new Boolean[10];
        for (int i = 0; i < n; i++) {
            String s = sc.next();
            for (int j = 0; j < 10; j++) {
                I[j] = (s.charAt(j) & 1) != 0;
            }
            f(I);
        }
        System.out.println(1000*sum(M)-n);
    }
    public static int sum(char[] M) {
        int retVal = 0;
        for (int i = 0; i < M.length; i++)
            retVal += M[i];
        return retVal;
    }
    public static void f(Boolean[] I) {
        boolean x[]=new boolean[10];    // no x[0]
        boolean y[]=new boolean[10];    // no y[0]
        boolean C[]=new boolean[10];
        for (int i = 4; i > 0; i--) {
            for (int j = 9; j >= 0; j--) {
                B[i*10+j] = B[(i-1)*10+j];
            }
        }
        for (int j = 9; j >= 0; j--) {
            B[j] = I[j];
        }
        x[0] =I[0]; ////our code
        for (int i = 1; i < x.length; i++) {
            x[i]=x[i-1]|I[i];
        }
        y[0]=(!x[9]); ////our code
        for (int i = 1; i < y.length; i++) {
            y[i]=y[i-1]|(x[i-1]&I[i]);
        }

        for (int i = 0; i < C.length; i++) {
            C[i]=false;
            for (int j = 0; j < 5; j++) {
                C[i]|=B[i+j*10];
            }
        }
        ArrayList<boolean[]> CList = new ArrayList<>();
        CList.add(0,null);
        for (int i = 1; i <= 9; i++) {
            CList.add(i, new boolean[10]);
        }

        //  init CList[0] // C10
        boolean[] C10 = CList.get(1);
        C10[0] = !(C[0] | C[1]);
        C10[1] =   C[0] ^ C[1];
        C10[2] =   C[0] & C[1];
        for (int i = 2; i < CList.size(); i++) {  // for C20-C90
            boolean[] prevC = CList.get(i-1);
            boolean[] curC = CList.get(i);
            boolean cd= C[i];
            for (int j = 0; j < curC.length; j++) {
                boolean a=false;
                if (j-1>=0){
                    a=prevC [j-1];
                }
                curC[j] = (a & cd)|(prevC[j] & (!cd));
            }
        }
        boolean c0=C[0],c5=C[5],
                c1=C[1],c6=C[6],
                c2=C[2],c7=C[7],
                c3=C[3],c8=C[8],
                c4=C[4],c9=C[9];

        boolean e=(!CList.get(9)[5])|y[9];
        boolean a[]= new boolean[10];
        a[0]=e|((((!c0)&(!c1)&(!c2)&(!c3)&(!c4))|(c0&c1&c2&c3&c4))^c0^c1^c2^c3^c4^(c3&
                (((c0^c8)&c1&c2&c4)^((((c0^c1)&c2&c5)^(c1&c4&c7))&c8))));
        a[1]=e|((((!c0)&(!c1)&(c2)&(!c5)&(c6))|(c0&c1&((!c2)&(!c6))&c5))^c0^c1^c2^c5^c6^(c4&
                ((c0&c1&((c2&c3)^(c5&c6)))^(((c1&c7)^(c6&c9))&c3&c8))));
        a[2]=e|((((!c0)&(!c1)&(c3)&(!c5)&(!c7))|(c0&c1&(!c3)&c5&c7))^c0^c1^c3^c5^c7^(c0&c1&c2&
                (c3^c4)&c5)^((c3^c4)&c5&c7&c8&c9));
        a[3]=e|((c3&c5)^(c3&c6)^(c3&c8)^(c3&c9)^(c5&c6)^(c5&c8)^(c5&c9)^(c6&c8)^(c6&c9)^
                (c8|c9)^c3^c5^c6^c8^c9^(c0&c1&c3&c6&c9));
        a[4]=e|((c2&c5)^(c2&c7)^(c2&c8)^(c2&c9)^(c5&c7)^(c5&c8)^(c5&c9)^(c7&c8)^(c7|c9)^
                (c8&c9)^c2^c5^c7^(((c0&c5&c6)^(c1&c3&c4))&c7&c8));
        a[5]=e|((c0&c1)^c0^c2^c4^c6^c7^(c0&c1&c2&c3&c4)^(((c0&((c3&c5)^(c2&c4)))^
                (c1&c4&c6))&c7&c8)^(c3&c4&c6&((c2&c9)^(c5&c7))));
        a[6]=e|(c0^c1^c3^c4^c7^(c0&c1&c2&c4&c9)^(c0&((c1&c4)^(c3&c8))&c5&c7)^
                ((((((c0^c1)&c5)^(c0&c4))&c2)^(c1&(c2^c7)&c4))&c6&c8));
        a[7]=e|(c2^c3^c4^(c0&((c2&c3)^((c2^c3)&c7))&c4&c8)^((((c0^c1)&c3&c5)^(((c0^c1)&
                (c4^c5))&c6))&c7&c8));
        int val = 0;
        for (int i = 7; i >= 0; i--) {
            val = val << 1;
            if (a[i]) val = val | 1;
        }
        M[val]=1;
    }

}