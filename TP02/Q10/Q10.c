#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_REGISTROS 1500
#define MAX_LINHA 1024

int comparacoes = 0;

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
    while (isspace((unsigned char)token[inicio])) {
      inicio++;
    }
    int fim = strlen(token) - 1;
    while (fim >= inicio && isspace((unsigned char)token[fim])) {
      fim--;
    }
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

time_t parseDate(const char *dateStr) {
  struct tm tm = {0};
  char monthStr[20];
  int day, year;

  if (sscanf(dateStr, "%[^ ] %d, %d", monthStr, &day, &year) != 3) {
    return 0;
  }

  static const char *months[] = {
      "January", "February", "March", "April", "May", "June",
      "July", "August", "September", "October", "November", "December"};

  int month = -1;
  for (int i = 0; i < 12; i++) {
    if (strcasecmp(monthStr, months[i]) == 0) {
      month = i;
      break;
    }
  }

  if (month == -1) return 0;

  tm.tm_year = year - 1900;
  tm.tm_mon = month;
  tm.tm_mday = day;

  return mktime(&tm);
}

int compararPorDataETitulo(const DisneyPlus *a, const DisneyPlus *b) {
  comparacoes++;

  time_t t1 = parseDate(a->date_added);
  time_t t2 = parseDate(b->date_added);

  if (t1 != t2) {
    return (t1 < t2) ? -1 : 1;
  }

  comparacoes++;
  return strcasecmp(a->title, b->title);
}

void quicksort(DisneyPlus *arr, int esq, int dir) {
  int i = esq, j = dir;
  DisneyPlus pivo = arr[(esq + dir) / 2];

  while (i <= j) {
    while (compararPorDataETitulo(&arr[i], &pivo) < 0) i++;
    while (compararPorDataETitulo(&arr[j], &pivo) > 0) j--;

    if (i <= j) {
      DisneyPlus temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
      i++;
      j--;
    }
  }

  if (esq < j) quicksort(arr, esq, j);
  if (i < dir) quicksort(arr, i, dir);
}

int main() {
  clock_t inicio = clock();

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

  fgets(linha, MAX_LINHA, arquivo); // cabeçalho

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

  quicksort(registrosSelecionados, 0, totalSelecionados - 1);

  for (int i = 0; i < totalSelecionados; i++) {
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
           registrosSelecionados[i].country,
           registrosSelecionados[i].date_added,
           registrosSelecionados[i].release_year,
           registrosSelecionados[i].rating,
           registrosSelecionados[i].duration,
           registrosSelecionados[i].listed_in);
  }

  clock_t fim = clock();
  double tempo = (double)(fim - inicio) / CLOCKS_PER_SEC;

  FILE *log = fopen("857268_quicksort.txt", "w");
  if (log) {
    fprintf(log, "857268\t%.6lf\t%d\n", tempo, comparacoes);
    fclose(log);
  } else {
    fprintf(stderr, "Erro ao criar arquivo de log.\n");
  }

  return 0;
}
