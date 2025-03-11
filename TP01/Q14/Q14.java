import java.util.Scanner;
import java.io.RandomAccessFile;

public class Q14 {
    public static void main(String args[]) throws Exception{
        RandomAccessFile arquivo = new RandomAccessFile("./arquivo.txt", "rw");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        for(int i = 0; i < n; i++){
            arquivo.writeDouble(sc.nextDouble());
        }
        arquivo.close();
        arquivo = new RandomAccessFile("./arquivo.txt", "r");
        for(int i = n - 1; i >= 0; i--){
            arquivo.seek(i*8);
            double num = arquivo.readDouble();
            if(num ==(int) num) System.out.println((int) num);
            else System.out.println(num);
        }
        arquivo.close();
        sc.close();
    }

}
