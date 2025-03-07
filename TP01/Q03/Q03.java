import java.util.Scanner;

public class Q03{
    public static void Cifrar(){
        String palavra;
        Scanner sc = new Scanner(System.in);
        palavra = sc.nextLine();
        while(!palavra.equals("FIM")){
            int n = palavra.length();
            int i = 0;
            char cifrado[] = new char[n];
            while(i < n){
                if((palavra.charAt(i) >= 0) && (palavra.charAt(i) <= 127)){
                cifrado[i] = (char) (palavra.charAt(i)+3);
                } else { cifrado[i] =(char) palavra.charAt(i); }
                i++;
                
            }
            palavra = new String(cifrado);
            System.out.println(palavra);
            palavra = sc.nextLine();
        }
        sc.close();
    }

    public static void main(String[] args){
        Cifrar();
    }

}