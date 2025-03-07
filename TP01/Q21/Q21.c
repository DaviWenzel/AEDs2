#include <stdio.h>
#include <string.h>

char* invertido(char string[], int tamanho, int i, char inversao[]){
    inversao[i] = string[tamanho-1];
    tamanho--; 
    i++;
    if(tamanho > 0) return invertido(string, tamanho, i, inversao);
    else{
        inversao[i] = '\0';
        return inversao;
    }
}


int main(void){
    char string[500];
    scanf("%s", string);
    while(strcmp(string, "FIM") != 0){
        int n = strlen(string);
        char inversao[n+1];
        printf("%s\n", invertido(string, n, 0, inversao));
        scanf("%s", string);
    }
}