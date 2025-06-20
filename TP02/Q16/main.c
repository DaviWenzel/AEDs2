#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_REGISTROS 1500
#define MAX_LINHA 1024

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
  while (token != NULL && numAtores < 50) {
    int inicio = 0;
    while (isspace((unsigned char)token[inicio])) inicio++;
    int fim = strlen(token) - 1;
    while (fim >= inicio && isspace((unsigned char)token[fim])) fim--;

    if (fim >= inicio) {
      atores[numAtores] = (char *)malloc((fim - inicio + 2) * sizeof(char));
      strncpy(atores[numAtores], token + inicio, fim - inicio + 1);
      atores[numAtores][fim - inicio + 1] = '\0';
      numAtores++;
    }
    token = strtok(NULL, ",");
  }
  return numAtores;
}

int compararAtores(const void *a, const void *b) {
  return strcasecmp(*(const char **)a, *(const char **)b);
}

int compararTypeETitulo(DisneyPlus a, DisneyPlus b) {
  int cmp = strcasecmp(a.type, b.type);
  if (cmp == 0) {
    return strcasecmp(a.title, b.title);
  }
  return cmp;
}

// Insertion sort parcial: ordena apenas os 10 primeiros elementos
void insercaoParcialPorTypeETitulo(DisneyPlus *arr, int n) {
  int limite = n < 10 ? n : 10;
  for (int i = 1; i < n; i++) {
    DisneyPlus temp = arr[i];
    int j = (i < 10) ? i - 1 : 9; // elementos depois do 10º só comparam com top 10

    while (j >= 0 && compararTypeETitulo(temp, arr[j]) < 0) {
      if (j + 1 < 10)
        arr[j + 1] = arr[j];
      j--;
    }

    if (j + 1 < 10)
      arr[j + 1] = temp;
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

  fgets(linha, MAX_LINHA, arquivo); // pular cabeçalho

  while (fgets(linha, MAX_LINHA, arquivo) && totalRegistros < MAX_REGISTROS) {
    separarCSV(linha, campos, &numCampos);
    DisneyPlus *reg = &registros[totalRegistros++];

    strncpy(reg->show_id, numCampos > 0 ? campos[0] : "NaN", sizeof(reg->show_id));
    strncpy(reg->type, numCampos > 1 ? campos[1] : "NaN", sizeof(reg->type));
    strncpy(reg->title, numCampos > 2 ? campos[2] : "NaN", sizeof(reg->title));
    removerAspas(reg->title);

    if (numCampos > 3) {
      strncpy(reg->director, campos[3], sizeof(reg->director));
      removerAspas(reg->director);
    } else strcpy(reg->director, "NaN");

    if (numCampos > 4) {
      strncpy(reg->cast, campos[4], sizeof(reg->cast));
      removerAspas(reg->cast);
    } else strcpy(reg->cast, "NaN");

    strncpy(reg->country, numCampos > 5 ? campos[5] : "NaN", sizeof(reg->country));
    strncpy(reg->date_added, numCampos > 6 ? campos[6] : "NaN", sizeof(reg->date_added));
    reg->release_year = (numCampos > 7 && strlen(campos[7])) ? atoi(campos[7]) : -1;
    strncpy(reg->rating, numCampos > 8 ? campos[8] : "NaN", sizeof(reg->rating));
    strncpy(reg->duration, numCampos > 9 ? campos[9] : "NaN", sizeof(reg->duration));

    if (numCampos > 10) {
      strncpy(reg->listed_in, campos[10], sizeof(reg->listed_in));
      removerAspas(reg->listed_in);
    } else strcpy(reg->listed_in, "NaN");
  }

  fclose(arquivo);

  DisneyPlus registrosSelecionados[MAX_REGISTROS];
  int totalSelecionados = 0;
  char entrada[50];

  while (1) {
    if (scanf("%s", entrada) == 1) {
      if (strcasecmp(entrada, "FIM") == 0) break;

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
      while (getchar() != '\n');
    }
  }

  insercaoParcialPorTypeETitulo(registrosSelecionados, totalSelecionados);

  int limiteImpressao = totalSelecionados < 10 ? totalSelecionados : 10;

  for (int i = 0; i < limiteImpressao; i++) {
    char *atores[10];
    char castCopia[500];
    strcpy(castCopia, registrosSelecionados[i].cast);
    int numAtores = separarCast(castCopia, atores);

    qsort(atores, numAtores, sizeof(char *), compararAtores);

    printf("=> %s ## %s ## %s ## %s ## [", registrosSelecionados[i].show_id,
           registrosSelecionados[i].title, registrosSelecionados[i].type,
           registrosSelecionados[i].director);

    for (int j = 0; j < numAtores; j++) {
      printf("%s", atores[j]);
      if (j < numAtores - 1) printf(", ");
      free(atores[j]);
    }

    printf("] ## %s ## %s ## %d ## %s ## %s ## [%s] ##\n",
           registrosSelecionados[i].country, registrosSelecionados[i].date_added,
           registrosSelecionados[i].release_year, registrosSelecionados[i].rating,
           registrosSelecionados[i].duration, registrosSelecionados[i].listed_in);
  }

  return 0;
}
