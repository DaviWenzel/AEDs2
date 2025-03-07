package Q04;
import java.util.Scanner;
import java.util.Random;

public class TP04 {
    public static void alteracao(){
    Random gerador = new Random();
    gerador.setSeed(4);
    Scanner sc = new Scanner(System.in);
    String palavra = sc.nextLine();
    while(!palavra.equals("FIM")){
        char fator =(char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        char fatorado =(char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        int n = palavra.length();
        char aleatorio[] = new char[n];
        for(int i = 0; i<n; i++){
        aleatorio[i] =(char) palavra.charAt(i);
        if(aleatorio[i] == fator)
            aleatorio[i] = fatorado;
        }
    palavra = new String(aleatorio);
    System.out.println(palavra);
    palavra = sc.nextLine();
    }
    sc.close();
    }
    public static void main(String[] args){
        alteracao();
    }
}
