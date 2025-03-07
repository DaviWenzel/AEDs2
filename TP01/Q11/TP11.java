import java.util.Scanner;
public class TP11 {
    public static int comprimento(String s){
        int n = s.length();
        char ja_vistos[] = new char[n];
        int maior = 0;
        for(int k = 0; k < n; k++){
            boolean nao_visto = true;
            int sequencia = 0;
            for(int i = k; i < n; i++){
                ja_vistos[i] = s.charAt(i);
                for(int j = k; j<i; j++){
                    if(ja_vistos[j] == s.charAt(i)){
                        j = n;
                        i = n;
                        nao_visto = false;
                    } 
                }
                if(nao_visto) sequencia++;
                if(sequencia > maior) maior = sequencia; 
            }
        }
        return maior;
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
