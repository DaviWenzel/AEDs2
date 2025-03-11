#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main(void){
    FILE *fptr;
    fptr = fopen("arquivo.txt", "wb");   
    int n;
    scanf("%d", &n);
    for(int i = 0; i < n; i++){
        double a;
        scanf("%lf\n", &a);
        fprintf(fptr, "%lf\n", a);
    }
    fclose(fptr);
    
    fptr = fopen("arquivo.txt", "rb");
    for(int i = 0; i < n; i++){
        double a;
        fseek(fptr,  , SEEK_SET);
        fscanf(fptr, "%lf", &a);
        if(a ==(int) a) printf("%d\n",(int) a);
        else printf("%.3f\n", a);
        }
        fclose(fptr);
    }
