import java.util.Scanner;

public class TP07 {
    public static String inversao(String s){
        int n = s.length();
        char inversao[] = new char[n];
        for(int i = 0; i < n/2 + 1; i++){
            inversao[i] = s.charAt(n-i-1);
            inversao[n-1-i] = s.charAt(i);
        }
        String invertido = new String(inversao);
        return invertido;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!s.equals("FIM")){
            s = inversao(s);
            System.out.println(s);
            s = sc.nextLine();
        }
        sc.close();
    }
}

