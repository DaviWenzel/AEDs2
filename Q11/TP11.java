import java.util.Scanner;
public class TP11 {
    public static int comprimento(String s){
        int n = s.length();
        char sequencia[] = new char[n];
        char sequencia2[] = new char[n];
        int comprim = 1;
        sequencia[0] = s.charAt(0);
        sequencia2[0] = s.charAt(0);
        boolean fim = true;
        for(int i = 1; i < n; i++){
            sequencia[i] = s.charAt(i);
            sequencia2[i] = s.charAt(i);
            for(int j = 0; j<i; j++){
                if(sequencia2[j] == sequencia[i]){
                    j = n;
                    i = n;
                    fim = false;
                } 
            }
            if(fim) comprim++;
        }
        return comprim;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!s.equals("FIM")){
            System.out.println(comprimento(s));
            s = sc.nextLine();
        }
        sc.close();
    }
}
