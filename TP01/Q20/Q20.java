import java.util.Scanner;

public class Q20 {
    public static boolean isConsoante(String s, int i){
        if(i == s.length()) return true;
        if(isVogal(s, i, true)) return false; 
        if(isNumero(s, i, true)) return false;
        return isConsoante(s, i + 1);
        }
    public static boolean isVogal(String s, int i, boolean teste){
        if(i == s.length()) return true;
        if(s.charAt(i) == 'a' || s.charAt(i) == 'A'){
            if(teste) return true;
            else return isVogal(s, i + 1, false);
        }
        if(s.charAt(i) == 'e' || s.charAt(i) == 'E'){
            if(teste) return true;
            else return isVogal(s, i + 1, false);
        }
        if(s.charAt(i) == 'i' || s.charAt(i) == 'I'){
            if(teste) return true;
            else return isVogal(s, i + 1, false);
        }
        if(s.charAt(i) == 'o' || s.charAt(i) == 'O'){
            if(teste) return true;
            else return isVogal(s, i + 1, false);
        }
        if(s.charAt(i) == 'u' || s.charAt(i) == 'U'){
            if(teste) return true;
            else return isVogal(s, i + 1, false);
        }
        return false;
    }
    public static boolean isNumero(String s, int i, boolean teste){
        if(i == s.length()) return true;
        if(s.charAt(i) == '0' || s.charAt(i) == '1'){
            if(teste) return true;
            else return isVogal(s, i + 1, false);
        }
        if(s.charAt(i) == '2' || s.charAt(i) == '3'){
            if(teste) return true;
            else return isVogal(s, i + 1, false);
        }
        if(s.charAt(i) == '4' || s.charAt(i) == '5'){
            if(teste) return true;
            else return isVogal(s, i + 1, false);
        }
        if(s.charAt(i) == '6' || s.charAt(i) == '7'){
            if(teste) return true;
            else return isVogal(s, i + 1, false);
        }
        if(s.charAt(i) == '8' || s.charAt(i) == '9'){
            if(teste) return true;
            else return isVogal(s, i + 1, false);
        }
        return false;
    }
    public static boolean isReal(String s,int i, boolean flag){
        if(i == s.length()) return true;
        if(isNumero(s, 0, false)) return true;
        if(!isNumero(s, i, true)){
            if(s.charAt(i) == ',' || s.charAt(i) == '.'){
                if(flag) return false;
                else{
                    flag = true;
                    return isReal(s, i + 1, flag);
                }
            } 
        }
        if(isNumero(s, i, true)) return isReal(s, i + 1, flag);
        return false;
    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!s.equals("FIM")){
            if(isVogal(s, 0, false)) System.out.print("SIM "); else System.out.print("NAO ");
            if(isConsoante(s, 0)) System.out.print("SIM "); else System.out.print("NAO ");
            if(isNumero(s, 0, false)) System.out.print("SIM "); else System.out.print("NAO ");
            if(isReal(s, 0, false)) System.out.println("SIM"); else System.out.println("NAO");
            s = sc.nextLine();
        }
        sc.close();
    }
}
