#include <stdio.h>
#include <stdlib.h>

int main(void) {
    FILE *fptr;
    int tamanho;
    double n;
    fptr = fopen("file.txt", "wb");
    scanf("%d", &tamanho);
    for (int i = 0; i < tamanho; i++) {
        scanf("%lf", &n);  
        fwrite(&n, sizeof(double), 1, fptr);  
    }
    fclose(fptr);  
    fptr = fopen("file.txt", "rb");
    for (int i = tamanho - 1; i >= 0; i--) {
        double num;
        long int x = (long int)(sizeof(double) * i); 
        fseek(fptr, x, SEEK_SET);  
        fread(&num, sizeof(double), 1, fptr); 
        printf("%.6g\n", num);  
    }
    fclose(fptr);
}
