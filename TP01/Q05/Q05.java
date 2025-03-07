import java.util.Scanner;

public class Q05 {
    public static int pular(String s, int i, int contador){
        if (i == s.length()) return i;
        if (s.charAt(i) == ')' && contador == 0) return i + 1;
        if (s.charAt(i) == ')' && contador > 0) return pular(s, i + 1, contador - 1);
        if (s.charAt(i) == '(') return pular(s, i + 1, contador + 1);
        return pular(s, i + 1, contador);
    }
    public static int and(String s, int i, int total, boolean primeiro, int A, int B, int C){
        if (i == s.length()) return total;
        if(s.charAt(i) == 'A'){
            if(primeiro) return and(s, i + 1, A, false, A, B, C);
            else return and(s, i + 1, total * A, false, A, B, C); 
        }
        if(s.charAt(i) == 'B'){
            if(primeiro) return and(s, i + 1, B, false, A, B, C);
            else return and(s, i + 1, total * B, false, A, B, C); 
        }
        if(s.charAt(i) == 'C'){
            if(primeiro) return and(s, i + 1, C, false, A, B, C);
            else return and(s, i + 1, total * C, false, A, B, C);
        }
        if(s.charAt(i) == 'a'){
            if(primeiro) and(s, pular(s, i, -1), and(s, i+2, 0, true, A, B, C), false, A, B, C);
            else return and(s, pular(s, i, -1), total * and(s, i+2, 0, true, A, B, C), primeiro, A, B, C);
        }
        if(s.charAt(i) == 'o'){
            if(primeiro) return and(s, pular(s, i, -1), or(s, i+1, 0, true, A, B, C), false, A, B, C);
            else return and(s, pular(s, i, -1), total * or(s, i+1, 0, true, A, B, C), primeiro, A, B, C);
        }
        if(s.charAt(i) == 'n'){
            if(primeiro) return and(s, pular(s, i, -1), not(s, i+2, 0, A, B, C), false, A, B, C);
            else return and(s, pular(s, i, -1), total * not(s, i+2, 0, A, B, C), primeiro, A, B, C);
        }
        if(s.charAt(i) == ')'){
            return total;
        }
        return and(s, i + 1, total, primeiro, A, B, C);
    }
    public static int or(String s, int i, int total, boolean primeiro, int A, int B, int C){
        if(total > 1) total = 1;
        if (i == s.length()) return total;
        if(s.charAt(i) == 'A'){
            if(primeiro) return or(s, i + 1, A, false, A, B, C);
            else return or(s, i + 1, total + A, false, A, B, C); 
        }
        if(s.charAt(i) == 'B'){
            if(primeiro) return or(s, i + 1, B, false, A, B, C);
            else return or(s, i + 1, total + B, false, A, B, C); 
        }
        if(s.charAt(i) == 'C'){
            if(primeiro) return or(s, i + 1, C, false, A, B, C);
            else return or(s, i + 1, total + C, false, A, B, C);
        }
        if(s.charAt(i) == 'a'){
            if(primeiro) return or(s, pular(s, i, -1), and(s, i+2, 0, true, A, B, C), false, A, B, C);
            else return or(s, pular(s, i, -1), total + and(s, i+2, 0, true, A, B, C), primeiro, A, B, C);
        }
        if(s.charAt(i) == 'o'){
            if(primeiro) return or(s, pular(s, i, -1), or(s, i+1, 0, true, A, B, C), false, A, B, C);
            else return or(s, pular(s, i, -1), total + or(s, i+1, 0, true, A, B, C), primeiro, A, B, C);
        }
        if(s.charAt(i) == 'n'){
            if(primeiro) return or(s, pular(s, i, -1), not(s, i+2, 0, A, B, C), false, A, B, C);
            else return or(s, pular(s, i, -1), total + not(s, i+2, 0, A, B, C), primeiro, A, B, C);
        }
        if(s.charAt(i) == ')'){
            return total;
        }
        return or(s, i + 1, total, primeiro, A, B, C);
    }
    public static int not(String s, int i, int total, int A, int B, int C){
        if (i == s.length()) return total;
        if(s.charAt(i) == 'A') return not(s, i + 1, 1 - A, A, B, C); 
        if(s.charAt(i) == 'B') return not(s, i + 1, 1 - B, A, B, C); 
        if(s.charAt(i) == 'C') return not(s, i + 1, 1 - C, A, B, C); 
        if(s.charAt(i) == 'a') return not(s, pular(s, i, -1), 1 - and(s, i+2, 0, true, A, B, C), A, B, C);
        if(s.charAt(i) == 'o') return not(s, pular(s, i, -1), 1 - or(s, i+1, 0, true, A, B, C) , A, B, C);
        if(s.charAt(i) == 'n') return not(s, pular(s, i, -1), 1 - not(s, i+2, 0, A, B, C), A, B, C);
        if(s.charAt(i) == ')') return total;
        return not(s, i + 1, total, A, B, C);
    }
       public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(s.charAt(0) - '0' > 1){
            int a = 0;
            if(s.charAt(0) - '0' == 3) a = 2;
            int A = s.charAt(2) - '0'; 
            int B = s.charAt(4) - '0';
            int C = s.charAt(6) - '0'; 
            if(s.charAt(6+a) == 'a') System.out.println(and(s, 9+a, 0, true, A, B, C));
            if(s.charAt(6+a) == 'n') System.out.println(not(s, 9+a, 0, A, B, C));
            if(s.charAt(6+a) == 'o') System.out.println(or(s, 8+a, 0, true, A, B, C));
            s = sc.nextLine();
        }
        sc.close();
    }
}