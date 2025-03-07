import java.util.Scanner;

public class TP06 {
    public static boolean vogal(String s){
        boolean flag = true;
        char consoantes[] = {'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z'};
        int n = s.length();
        for(int i = 0; i<n; i++){
            if(( 'A' > s.charAt(i)) || ('z' < s.charAt(i))){
                    flag = false;
                    break;
                }
            if(('Z' < s.charAt(i))&&('a' > s.charAt(i))){
                    flag = false;
                    break;
                }
            
            for(int j = 0; j < 21 ; j++){
                if((s.charAt(i) == consoantes[j]) || (s.charAt(i) ==((char)(consoantes[j] - 32)))){
                    flag = false;
                    j = 21;
                    i = n;
                }
            }
        }
        return flag;
    }
    public static boolean consoante(String s){
        boolean flag = true;
        int n = s.length();
        char vogais[] = {'a', 'e', 'i', 'o', 'u'};
        for(int i = 0; i < n; i++){
            if(( 'A' > s.charAt(i)) || ('z' < s.charAt(i))){
                flag = false;
                break;
            }
        if(('Z' < s.charAt(i))&&('a' > s.charAt(i))){
                flag = false;
                break;
            }
        
            for(int j = 0; j < 5; j++){
                if((s.charAt(i) == vogais[j]) || (s.charAt(i) ==((char) (vogais[j] - 32)))){
                    flag = false;
                    j = 5;
                    i = n;
                }
            }

            }
        return flag;
        }
    public static boolean inteiro(String s){
        boolean flag = true;
        int n = s.length();
        for(int i = 0; i < n; i++){
                if((s.charAt(i) >(char)57 ) || (s.charAt(i) <(char)48)){
                    flag = false;
                    i = n;
                }
            }
        return flag;
    }
    public static boolean real(String s){
        boolean flag = true;
        boolean virgula = false;
        int n = s.length();
        for(int i = 0; i < n; i++){
                if((s.charAt(i) >(char)57 ) || (s.charAt(i) <(char)48)){
                    if(s.charAt(i) == ','){
                        if(virgula){
                            i = n;
                            flag = false;
                        }
                        virgula = true;
                    } else {
                        i = n;
                        flag = false;
                    }

                }
            }
        return flag;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!s.equals("FIM")){
        if(vogal(s)) System.out.print("SIM "); else System.out.print("NAO ");
        if(consoante(s)) System.out.print("SIM "); else System.out.print("NAO ");
        if(inteiro(s)) System.out.print("SIM "); else System.out.print("NAO ");
        if(real(s)) System.out.println("SIM"); else System.out.println("NAO");
        s = sc.nextLine();
    }
        sc.close();
    }
}
