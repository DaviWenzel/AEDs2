
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

class TP09{
    public static boolean isAnagrama(String s){
        int n = s.length();
        char a[] = new char[n];
        char b[] = new char[n];
        boolean flag = true;
        boolean isAnagrama = true;
            for(int i = 0; i < n/2; i++){
                a[i] = s.charAt(i);
            }
            for(int i = n/2 + 2; i<n; i++){
                b[i] = s.charAt(i);
            }
            for(int i = 0; i < n/2 - 1; i++){
                for(int j = n/2 + 2; j<n; j++){
                    a[i] = Character.toLowerCase(a[i]);
                    b[j] = Character.toLowerCase(b[j]);
                    if(a[i] == b[j]){
                        a[i] = '0';
                        b[j] = '0';
                    }
                }
                if(a[i] != '0'){
                    isAnagrama = false;
                    i = n;
                }
    }
        return isAnagrama;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!s.equals("FIM")){
            if(isAnagrama(s)) System.out.println("SIM"); 
            else{
                String nao = new String(new byte[]{0x4E, (byte) 0xC3, (byte) 0x83,  0x4F}, StandardCharsets.UTF_8);
                System.out.println(nao);
            }
            s = sc.nextLine();
        }
    }
}