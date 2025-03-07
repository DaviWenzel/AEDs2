import java.util.Scanner;

public class TP10 {
    public static int palavras(String s){
        int n = s.length();
        int palavras = 1;
        for(int i = 0; i<n; i++){
            if(s.charAt(i) == ' '){
                palavras++;
            } 
        }
        return palavras;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!s.equals("FIM")){
            System.out.println(palavras(s));
            s = sc.nextLine();
        }
        sc.close();
    }
}
