import java.util.Scanner;
public class TP08 {
    public static int soma(int soma, int i, String s){
        int n = s.length();
        soma +=(int) (s.charAt(i) - '0');
        i++;
        if(i < n) return soma(soma, i, s); else return soma;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!s.equals("FIM")){
            int i = 0;
            int soma = 0;
            System.out.println(soma(soma, i, s));
            s = sc.nextLine();
        }
        sc.close();
    }
}
