#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_REGISTROS 1500
#define MAX_LINHA 1024
#define MAX_CAST 50
#define MAX_DIGITS 4 

typedef struct {
  char show_id[50];
  char type[50];
  char title[200];
  char director[200];
  char cast[500];
  char country[100];
  char date_added[50];
  int release_year;
  char rating[50];
  char duration[50];
  char listed_in[500];
} DisneyPlus;

void removerAspas(char *str) {
  int len = strlen(str);
  if (len >= 2 && str[0] == '"' && str[len - 1] == '"') {
    memmove(str, str + 1, len - 2);
    str[len - 2] = '\0';
  }
}

void separarCSV(char *linha, char **campos, int *numCampos) {
  int dentroAspas = 0;
  char *ptr = linha;
  char *inicio = linha;
  *numCampos = 0;

  while (*ptr) {
    if (*ptr == '"') {
      dentroAspas = !dentroAspas;
    } else if (*ptr == ',' && !dentroAspas) {
      *ptr = '\0';
      campos[(*numCampos)++] = inicio;
      inicio = ptr + 1;
    }
    ptr++;
  }
  campos[(*numCampos)++] = inicio;
}

int separarCast(char *castStr, char **atores) {
  int numAtores = 0;
  char *token = strtok(castStr, ",");
  while (token != NULL && numAtores < MAX_CAST) {

    int inicio = 0;
    while (isspace((unsigned char)token[inicio])) {
      inicio++;
    }
    int fim = strlen(token) - 1;
    while (fim >= inicio && isspace((unsigned char)token[fim])) {
      fim--;
    }
    if (fim >= inicio) {
      atores[numAtores] = (char *)malloc((fim - inicio + 2) * sizeof(char));
      if (atores[numAtores] != NULL) {
        strncpy(atores[numAtores], token + inicio, fim - inicio + 1);
        atores[numAtores][fim - inicio + 1] = '\0';
        numAtores++;
      } else {
        perror("Erro ao alocar memória para ator");
        return -1;
      }
    }
    token = strtok(NULL, ",");
  }
  return numAtores;
}

int compararAtores(const void *a, const void *b) {
  return strcasecmp(*(const char **)a, *(const char **)b);
}


int pegaDigito(int num, int place) {
  int divisor = 1;
  for (int i = 1; i < place; i++) {
    divisor *= 10;
  }
  return (num / divisor) % 10;
}


void radixSort(DisneyPlus *arr, int n) {
  if (n <= 0)
    return; 

  DisneyPlus *output = (DisneyPlus *)malloc(n * sizeof(DisneyPlus));
  if (!output) {
    perror("Erro ao alocar memória para output");
    return;
  }

  for (int place = 1; place <= MAX_DIGITS; place++) {
    int count[10] = {0}; 

    for (int i = 0; i < n; i++) {
      count[pegaDigito(arr[i].release_year, place)]++;
    }

    for (int i = 1; i < 10; i++) {
      count[i] += count[i - 1];
    }


    for (int i = n - 1; i >= 0; i--) {
      output[count[pegaDigito(arr[i].release_year, place)] - 1] = arr[i];
      count[pegaDigito(arr[i].release_year, place)]--;
    }


    memcpy(arr, output, n * sizeof(DisneyPlus));
  }

  free(output);

  for (int i = 0; i < n - 1; i++) {
    for (int j = 0; j < n - i - 1; j++) {
      if (arr[j].release_year == arr[j + 1].release_year &&
          strcasecmp(arr[j].title, arr[j + 1].title) > 0) {
 
        DisneyPlus temp = arr[j];
        arr[j] = arr[j + 1];
        arr[j + 1] = temp;
      }
    }
  }
}

int main() {
  FILE *arquivo = fopen("/tmp/disneyplus.csv", "r");
  if (!arquivo) {
    perror("Erro ao abrir arquivo");
    return 1;
  }

  DisneyPlus registros[MAX_REGISTROS];
  int totalRegistros = 0;
  char linha[MAX_LINHA];
  char *campos[20];
  int numCampos;

  fgets(linha, MAX_LINHA, arquivo);

  while (fgets(linha, MAX_LINHA, arquivo) && totalRegistros < MAX_REGISTROS) {
    separarCSV(linha, campos, &numCampos);
    DisneyPlus *reg = &registros[totalRegistros++];

    strncpy(reg->show_id, numCampos > 0 ? campos[0] : "NaN",
            sizeof(reg->show_id) - 1);
    reg->show_id[sizeof(reg->show_id) - 1] = '\0';
    strncpy(reg->type, numCampos > 1 ? campos[1] : "NaN", sizeof(reg->type) - 1);
    reg->type[sizeof(reg->type) - 1] = '\0';
    strncpy(reg->title, numCampos > 2 ? campos[2] : "NaN", sizeof(reg->title) - 1);
    reg->title[sizeof(reg->title) - 1] = '\0';
    removerAspas(reg->title);

    if (numCampos > 3) {
      strncpy(reg->director, campos[3], sizeof(reg->director) - 1);
      reg->director[sizeof(reg->director) - 1] = '\0';
      removerAspas(reg->director);
    } else {
      strncpy(reg->director, "NaN", sizeof(reg->director) - 1);
      reg->director[sizeof(reg->director) - 1] = '\0';
    }

    if (numCampos > 4) {
      strncpy(reg->cast, campos[4], sizeof(reg->cast) - 1);
      reg->cast[sizeof(reg->cast) - 1] = '\0';
      removerAspas(reg->cast);
    } else {
      strncpy(reg->cast, "NaN", sizeof(reg->cast) - 1);
      reg->cast[sizeof(reg->cast) - 1] = '\0';
    }

    strncpy(reg->country, numCampos > 5 ? campos[5] : "NaN",
            sizeof(reg->country) - 1);
    reg->country[sizeof(reg->country) - 1] = '\0';
    strncpy(reg->date_added, numCampos > 6 ? campos[6] : "NaN",
            sizeof(reg->date_added) - 1);
    reg->date_added[sizeof(reg->date_added) - 1] = '\0';
    reg->release_year =
        (numCampos > 7 && strlen(campos[7])) ? atoi(campos[7]) : -1;
    strncpy(reg->rating, numCampos > 8 ? campos[8] : "NaN",
            sizeof(reg->rating) - 1);
    reg->rating[sizeof(reg->rating) - 1] = '\0';
    strncpy(reg->duration, numCampos > 9 ? campos[9] : "NaN",
            sizeof(reg->duration) - 1);
    reg->duration[sizeof(reg->duration) - 1] = '\0';

    if (numCampos > 10) {
      strncpy(reg->listed_in, campos[10], sizeof(reg->listed_in) - 1);
      reg->listed_in[sizeof(reg->listed_in) - 1] = '\0';
      removerAspas(reg->listed_in);
    } else {
      strncpy(reg->listed_in, "NaN", sizeof(reg->listed_in) - 1);
      reg->listed_in[sizeof(reg->listed_in) - 1] = '\0';
    }
  }

  fclose(arquivo);

  DisneyPlus registrosSelecionados[MAX_REGISTROS];
  int totalSelecionados = 0;
  char entrada[50];

  while (1) {
    if (scanf("%s", entrada) == 1) {
      if (strcasecmp(entrada, "FIM") == 0) {
        break;
      }

      bool encontrado = false;
      for (int i = 0; i < totalRegistros; i++) {
        if (strcmp(registros[i].show_id, entrada) == 0) {
          registrosSelecionados[totalSelecionados++] = registros[i];
          encontrado = true;
          break;
        }
      }

      if (!encontrado) {
        printf("ID \"%s\" não encontrado no arquivo.\n", entrada);
      }
    } else {
      while (getchar() != '\n')
        ;
    }
  }

  radixSort(registrosSelecionados, totalSelecionados);

  for (int i = 0; i < totalSelecionados; i++) {
    char *atores[MAX_CAST];
    char castCopia[500];
    strncpy(castCopia, registrosSelecionados[i].cast, sizeof(castCopia) - 1);
    castCopia[sizeof(castCopia) - 1] = '\0';
    int numAtores = separarCast(castCopia, atores);
    if (numAtores > 0) {
      qsort(atores, numAtores, sizeof(char *), compararAtores);
    }

    char *generos[MAX_CAST];
    char listedInCopia[500];
    strncpy(listedInCopia, registrosSelecionados[i].listed_in, sizeof(listedInCopia) - 1);
    listedInCopia[sizeof(listedInCopia) - 1] = '\0';
    int numGeneros = separarCast(listedInCopia, generos);

    printf("=> %s ## %s ## %s ## %s ## [", registrosSelecionados[i].show_id,
           registrosSelecionados[i].title, registrosSelecionados[i].type,
           registrosSelecionados[i].director);

    for (int j = 0; j < numAtores; j++) {
      printf("%s", atores[j]);
      if (j < numAtores - 1) {
        printf(", ");
      }
      free(atores[j]);
    }

    printf("] ## %s ## %s ## %d ## %s ## %s ## [",
           registrosSelecionados[i].country, registrosSelecionados[i].date_added,
           registrosSelecionados[i].release_year, registrosSelecionados[i].rating,
           registrosSelecionados[i].duration);

    for (int j = 0; j < numGeneros; j++) {
      printf("%s", generos[j]);
      if (j < numGeneros - 1) {
        printf(", ");
      }
      free(generos[j]);
    }
    printf("] ##\n");
  }

  return 0;
}