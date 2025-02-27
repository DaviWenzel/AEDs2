import java.util.Scanner;

public class Q01 {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String palavra = scanner.nextLine();
        while(!palavra.equals("FIM")){
            int n = palavra.length();
            Boolean flag = true;
            for(int i = 0; i< n/2; i++){
                if(palavra.charAt(i) != palavra.charAt(n-1-i)){
                    flag = false;
                    break;
                }
            }
            if(flag) System.out.println("SIM");
            else System.out.println("NAO");
            palavra = scanner.nextLine();
       }
       scanner.close();
    }
}
