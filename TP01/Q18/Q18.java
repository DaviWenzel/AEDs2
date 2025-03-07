import java.util.Scanner;


public class Q18 {
    private static String ciframento(String s, int n, int i){
        if((s.charAt(i) >= 0) && (s.charAt(i) <= 127)){
        char[] cifrar = new char[n];
        cifrar = s.toCharArray();
        cifrar[i] =(char)(cifrar[i] + 3);
        s = new String(cifrar);
        }
        i++;
        if(i<n) return ciframento(s, n, i);
        else return s;
    }
    public static String ciframento(String s){
        int n = s.length();
        return ciframento(s, n, 0);
    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!s.equals("FIM")){
            System.out.println(ciframento(s));
            s = sc.nextLine();
        }
        sc.close();
    }
}
