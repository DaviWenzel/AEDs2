#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINE 1024
#define MAX_FIELDS 12
#define MAX_SHOWS 1369

//NESSE DIA, NESSE DOMINGO, PORTUGAL É BI CAMPEAO
//BORA TUGAS
//CR7 É O GOAT
//AWDIAOFHAOWEGFHA

typedef struct {
  char* id;
  char* type;
  char* title;
  char* director;
  char** cast;
  int cast_size;
  char* country;
  char* date_added;
  int release_year;
  char* rating;
  char* duration;
  char** listed_in;
  int listed_in_size;
  char* description;
} Show;

void trim(char* str) {
  int i;
  for (i = 0; str[i]; i++) {
    if (str[i] == '\n' || str[i] == '\r') {
      str[i] = '\0';
      break;
    }
  }
}

int isEmpty(char* str) {
  return str == NULL || strlen(str) == 0;
}

char** split(char* str, char* delim, int* count) {
  char* tmp = strdup(str);
  char* token = strtok(tmp, delim);
  char** result = malloc(sizeof(char*) * 50);
  int i = 0;

  while (token != NULL) {
    while (*token == ' ') token++;
    result[i++] = strdup(token);
    token = strtok(NULL, delim);
  }

  *count = i;
  free(tmp);
  return result;
}

void sort(char** arr, int n) {
  for (int i = 0; i < n - 1; i++) {
    for (int j = 0; j < n - i - 1; j++) {
      if (strcmp(arr[j], arr[j + 1]) > 0) {
        char* tmp = arr[j];
        arr[j] = arr[j + 1];
        arr[j + 1] = tmp;
      }
    }
  }
}

void printArray(char** arr, int n) {
  printf("[");
  for (int i = 0; i < n; i++) {
    printf("%s", arr[i]);
    if (i < n - 1) printf(", ");
  }
  printf("]");
}
//MANO EU ESQUECI O QUANTO EU ODIEI FAZER ISSO

void parseCSVLine(char* line, char* parts[]) {
  int i = 0, j = 0, k = 0, inQuotes = 0;
  char buffer[MAX_LINE];

  while (line[i]) {
    if (line[i] == '\"') {
      inQuotes = !inQuotes;
    } else if (line[i] == ',' && !inQuotes) {
      buffer[j] = '\0';
      parts[k++] = strdup(buffer);
      j = 0;
    } else {
      buffer[j++] = line[i];
    }
    i++;
  }
  buffer[j] = '\0';
  parts[k++] = strdup(buffer);
}


void setShow(Show* show, char* parts[]) {
  show->id         = isEmpty(parts[0]) ? strdup("NaN") : strdup(parts[0]);
  show->type       = isEmpty(parts[1]) ? strdup("NaN") : strdup(parts[1]);
  show->title      = isEmpty(parts[2]) ? strdup("NaN") : strdup(parts[2]);
  show->director   = isEmpty(parts[3]) ? strdup("NaN") : strdup(parts[3]);

  if (isEmpty(parts[4])) {
    show->cast = malloc(sizeof(char*));
    show->cast[0] = strdup("NaN");
    show->cast_size = 1;
  } else {
    show->cast = split(parts[4], ",", &show->cast_size);
    sort(show->cast, show->cast_size);
  }

  show->country    = isEmpty(parts[5]) ? strdup("NaN") : strdup(parts[5]);
  show->date_added = isEmpty(parts[6]) ? strdup("March 1, 1900") : strdup(parts[6]);
  show->release_year = isEmpty(parts[7]) ? -1 : atoi(parts[7]);
  show->rating     = isEmpty(parts[8]) ? strdup("NaN") : strdup(parts[8]);
  show->duration   = isEmpty(parts[9]) ? strdup("NaN") : strdup(parts[9]);

  if (isEmpty(parts[10])) {
    show->listed_in = malloc(sizeof(char*));
    show->listed_in[0] = strdup("NaN");
    show->listed_in_size = 1;
  } else {
    show->listed_in = split(parts[10], ",", &show->listed_in_size);
  }

  show->description = isEmpty(parts[11]) ? strdup("NaN") : strdup(parts[11]);
}

void printShow(Show* show) {
  printf("=> %s ## %s ## %s ## %s ## ", show->id, show->title, show->type, show->director);
  printArray(show->cast, show->cast_size);
  printf(" ## %s ## %s ## %d ## %s ## %s ## ", 
         show->country, show->date_added, show->release_year, show->rating, show->duration);
  printArray(show->listed_in, show->listed_in_size);
  printf(" ##\n");
}

typedef struct Celula {
    Show* show;
    struct Celula* prox;
    struct Celula* ant; 
} Celula;
//EU NAO QuERO NUNCA MAIS FAZER ISSO EM C (AMO C AINDA ASSIM)
typedef struct {
    Celula* primeiro;
    Celula* ultimo;
    int tamanho;
} Lista;

int mesParaNumero(const char* mes) {
    const char* meses[] = {"January", "February", "March", "April", "May", "June",
                           "July", "August", "September", "October", "November", "December"};
    for (int i = 0; i < 12; i++) {
        if (strcmp(mes, meses[i]) == 0) return i + 1;
    }
    return 0;
}

void extrairData(const char* data_str, int* ano, int* mes, int* dia) {
    char mes_str[20];
    sscanf(data_str, "%s %d, %d", mes_str, dia, ano);
    *mes = mesParaNumero(mes_str);
}

int compararData(Show* a, Show* b) {
    int a_ano, a_mes, a_dia;
    int b_ano, b_mes, b_dia;
    extrairData(a->date_added, &a_ano, &a_mes, &a_dia);
    extrairData(b->date_added, &b_ano, &b_mes, &b_dia);

    if (a_ano != b_ano) return a_ano - b_ano;
    if (a_mes != b_mes) return a_mes - b_mes;
    if (a_dia != b_dia) return a_dia - b_dia;

    return strcmp(a->title, b->title);  
}

void trocar(Show** a, Show** b) {
    Show* tmp = *a;
    *a = *b;
    *b = tmp;
}

Celula* ultimaCelula(Celula* no) {
    while (no && no->prox) no = no->prox;
    return no;
}

Celula* particionar(Celula* low, Celula* high) {
    Show* pivot = high->show;
    Celula* i = low->ant;

    for (Celula* j = low; j != high; j = j->prox) {
        if (compararData(j->show, pivot) <= 0) {
            i = (i == NULL) ? low : i->prox;
            Show* temp = i->show;
            i->show = j->show;
            j->show = temp;
        }
    }
    i = (i == NULL) ? low : i->prox;
    Show* temp = i->show;
    i->show = high->show;
    high->show = temp;

    return i;
}

void quickSort(Celula* low, Celula* high) {
    if (high != NULL && low != high && low != high->prox) {
        Celula* p = particionar(low, high);
        quickSort(low, p->ant);
        quickSort(p->prox, high);
    }
}

void ordenarPorData(Lista* lista) {
    Celula* ultimo = ultimaCelula(lista->primeiro);
    quickSort(lista->primeiro, ultimo);
}

void iniciarLista(Lista* lista) {
    lista->primeiro = lista->ultimo = NULL;
    lista->tamanho = 0;
}

void inserirInicio(Lista* lista, Show* show) {
    Celula* nova = malloc(sizeof(Celula));
    nova->show = show;
    nova->prox = lista->primeiro;
    lista->primeiro = nova;

    if (lista->tamanho == 0)
        lista->ultimo = nova;

    lista->tamanho++;
}


//AGORA BORA MEU VASCAO 

void mostrarLista(Lista* lista) {
    Celula* atual = lista->primeiro;
    while (atual != NULL) {
        printShow(atual->show);
        atual = atual->prox;
    }
}





int idToIndex(const char* id) {
    if (id[0] != 's') return -1;
    return atoi(id + 1);  // remove o 's' e converte o restante
}

void inserirFim(Lista* lista, Show* show) {
    Celula* nova = malloc(sizeof(Celula));
    nova->show = show;
    nova->prox = NULL;
    nova->ant = lista->ultimo;

    if (lista->tamanho == 0) {
        lista->primeiro = lista->ultimo = nova;
    } else {
        lista->ultimo->prox = nova;
        lista->ultimo = nova;
    }

    lista->tamanho++;
}

int main() {
    FILE *f = fopen("/tmp/disneyplus.csv", "r");
    if (f == NULL) {
        fprintf(stderr, "Erro ao abrir o arquivo.\n");
        return 1;
    }

    char line[MAX_LINE];
    Show* shows[MAX_SHOWS];
    int count = 0;

    fgets(line, sizeof(line), f);  // Pula o cabeçalho

    while (fgets(line, sizeof(line), f) && count < MAX_SHOWS) {
        trim(line);
        char* parts[MAX_FIELDS];
        parseCSVLine(line, parts);

        shows[count] = malloc(sizeof(Show));
        setShow(shows[count], parts);

        for (int i = 0; i < MAX_FIELDS; i++) {
            free(parts[i]);
        }
        count++;
    }
    fclose(f);

    Lista lista;
    iniciarLista(&lista);

    char input[100];
    while (fgets(input, sizeof(input), stdin)) {
        trim(input);
        if (strcmp(input, "FIM") == 0) break;

        int idx = idToIndex(input);
        if (idx >= 0 && idx < count) {
            inserirFim(&lista, shows[idx - 1]);
        }
    }

    ordenarPorData(&lista);
    mostrarLista(&lista);

    return 0;
}
