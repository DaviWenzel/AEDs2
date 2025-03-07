/* 
recebe string e retorna true ou false caso senha seja valida
senha válida pelo menos 8 caracteres, uma letra maiuscula, uma minuscula, um numero, um caractere especial 
retorne SIM NÃO
ascii
numero = 48 a 57 
especiais = 21 23 24 25 26 43 45 46 / na pub out: 21 23 24 40 94
lower = 
upper = 
*/ 

import java.util.Scanner;

class Q12{
    public static boolean valido(String s){
        boolean valido = false;
        int n = s.length();
        if(n < 8) n = 0;
        boolean maiuscula = false;
        boolean minuscula = false; 
        boolean numero = false; 
        boolean especial = false;
        String especiais = new String("%!@#$,^><+-.?");
        for(int i = 0; i < n; i++){
            if((s.charAt(i) >= 'a') && (s.charAt(i) <= 'z')) minuscula = true;
            if((s.charAt(i) >= 'A') && (s.charAt(i) <= 'Z')) maiuscula = true;
            if((s.charAt(i) >= '0') && (s.charAt(i) <= '9')) numero = true;
            for(int j  = 0; j < especiais.length(); j++){
                if(s.charAt(i) == especiais.charAt(j)) especial = true;
            }
        }
        if((maiuscula && minuscula)&&(numero && especial)) valido = true;
        return valido;
    }
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!s.equals("FIM")){
            if(valido(s)) System.out.println("SIM"); else System.out.println("NÃO");
            s = sc.nextLine();
        }
        sc.close();
    }
}