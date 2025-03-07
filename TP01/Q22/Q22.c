#include <stdio.h>
#include <string.h>

int soma(int n[], int tamanho, int somatorio){
    tamanho--;
    somatorio += n[tamanho]; 
    if(tamanho > 0) return soma(n, tamanho, somatorio);
    else return somatorio;
}

int main(void){
    char array[500];
    scanf("%s", array);
    while(strcmp(array, "FIM") != 0){
        int n = strlen(array);
        int numero[n];
        for(int i = 0; i < n; i++){
            numero[i] = array[i] - '0';
        }
        printf("%i\n", soma(numero, n, 0));
        scanf("%s", array);
    }
}