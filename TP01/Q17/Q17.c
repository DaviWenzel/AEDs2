#include <stdio.h>
#include <string.h>
#include <stdbool.h>

bool palindromo(char a[], int i, int n){
    if(i >= n) return 1;
    if(a[i] == a[n]) return palindromo(a , i + 1, n - 1);;
    return 0;
}

int main(void){
    char a[500];
    scanf("%[^\n\r]", a);
    getchar();
    while(strcmp(a, "FIM") != 0){
        int n = strlen(a) - 1;
        if(palindromo(a, 0, n)) 
            printf("SIM\n"); 
        else 
            printf("NAO\n");
        a[n] = '\0';
        scanf("%[^\n\r]", a);
        getchar();
    }
}