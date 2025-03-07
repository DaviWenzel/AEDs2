import java.util.Scanner;

public class Q16 {
    public static String palindromo(String s, int i, int n){
        if(i > n) return "SIM";
        if(s.charAt(i) == s.charAt(n)) return palindromo(s, i + 1, n - 1);;
        return "NAO";
    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!s.equals("FIM")){
            int n = s.length() - 1;
            System.out.println(palindromo(s, 0, n));
            s = sc.nextLine();
        }
    }
}


