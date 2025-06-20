#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINE 1024
#define MAX_FIELDS 12
#define MAX_SHOWS 1369
#define QUEUE_SIZE 5


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
} Celula;
//EU NAO QuERO NUNCA MAIS FAZER ISSO EM C (AMO C AINDA ASSIM)
typedef struct {
    Celula* primeiro;
    Celula* ultimo;
    int tamanho;
} Lista;

typedef struct {
    Celula* topo;
    int tamanho;
} Pilha;

void iniciarPilha(Pilha* p) {
    p->topo = NULL;
    p->tamanho = 0;
}

void empilhar(Pilha* p, Show* s) {
    Celula* nova = malloc(sizeof(Celula));
    nova->show = s;
    nova->prox = p->topo;
    p->topo = nova;
    p->tamanho++;
}

Show* desempilhar(Pilha* p) {
    if (p->topo == NULL) {
        printf("Erro ao desempilhar!\n");
        exit(1);
    }
    Celula* tmp = p->topo;
    Show* s = tmp->show;
    p->topo = tmp->prox;
    free(tmp);
    p->tamanho--;
    return s;
}


void mostrarPilha(Pilha* p) {
    Celula* atual = p->topo;
    int pos = 0;
    while (atual != NULL) {
        printf("[%d] ", p->tamanho - pos - 1);
        printShow(atual->show);
        atual = atual->prox;
        pos++;
    }
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

void inserirFim(Lista* lista, Show* show) {
    Celula* nova = malloc(sizeof(Celula));
    nova->show = show;
    nova->prox = NULL;

    if (lista->tamanho == 0) {
        lista->primeiro = lista->ultimo = nova;
    } else {
        lista->ultimo->prox = nova;
        lista->ultimo = nova;
    }

    lista->tamanho++;
}

void inserir(Lista* lista, Show* show, int pos) {
    if (pos < 0 || pos > lista->tamanho) return;

    if (pos == 0) {
        inserirInicio(lista, show);
    } else if (pos == lista->tamanho) {
        inserirFim(lista, show);
    } else {
        Celula* nova = malloc(sizeof(Celula));
        nova->show = show;

        Celula* atual = lista->primeiro;
        for (int i = 0; i < pos - 1; i++)
            atual = atual->prox;

        nova->prox = atual->prox;
        atual->prox = nova;

        lista->tamanho++;
    }
}
//AGORA BORA MEU VASCAO 
Show* removerInicio(Lista* lista) {
    if (lista->tamanho == 0) return NULL;

    Celula* temp = lista->primeiro;
    Show* s = temp->show;

    lista->primeiro = temp->prox;
    if (lista->tamanho == 1)
        lista->ultimo = NULL;

    free(temp);
    lista->tamanho--;
    return s;
}

Show* removerFim(Lista* lista) {
    if (lista->tamanho == 0) return NULL;

    Show* s;
    if (lista->tamanho == 1) {
        s = lista->primeiro->show;
        free(lista->primeiro);
        lista->primeiro = lista->ultimo = NULL;
    } else {
        Celula* atual = lista->primeiro;
        while (atual->prox != lista->ultimo)
            atual = atual->prox;

        s = lista->ultimo->show;
        free(lista->ultimo);
        lista->ultimo = atual;
        atual->prox = NULL;
    }

    lista->tamanho--;
    return s;
}
typedef struct {
    Show* queue[QUEUE_SIZE];
    int front;
    int rear;
    int size;
  } CircularQueue;
  
  void initializeQueue(CircularQueue* q) {
    q->front = 0;
    q->rear = 0;
    q->size = 0;
  }
  
  int isQueueEmpty(CircularQueue* q) {
    return q->size == 0;
  }
  
  int isQueueFull(CircularQueue* q) {
    return q->size == QUEUE_SIZE;
  }
  
  void enqueue(CircularQueue* q, Show* s) {
    if (isQueueFull(q)) {
      Show* removed = q->queue[q->front];
      q->front = (q->front + 1) % QUEUE_SIZE;
      q->size--;
    }
    q->queue[q->rear] = s;
    q->rear = (q->rear + 1) % QUEUE_SIZE;
    q->size++;
  
    int sum = 0;
    for (int i = 0, idx = q->front; i < q->size; i++) {
      sum += q->queue[idx]->release_year;
      idx = (idx + 1) % QUEUE_SIZE;
    }
    int average = (int)((float)sum / q->size);
    printf("[Media] %d\n", average);
  }
  
  Show* dequeue(CircularQueue* q) {
    if (isQueueEmpty(q)) return NULL;
    Show* removed = q->queue[q->front];
    q->front = (q->front + 1) % QUEUE_SIZE;
    q->size--;
    return removed;
}

Show* remover(Lista* lista, int pos) {
    if (pos < 0 || pos >= lista->tamanho) return NULL;

    if (pos == 0) return removerInicio(lista);
    if (pos == lista->tamanho - 1) return removerFim(lista);

    Celula* atual = lista->primeiro;
    for (int i = 0; i < pos - 1; i++)
        atual = atual->prox;

    Celula* temp = atual->prox;
    Show* s = temp->show;
    atual->prox = temp->prox;

    free(temp);
    lista->tamanho--;
    return s;
}

void mostrarLista(Lista* lista) {
    Celula* atual = lista->primeiro;
    while (atual != NULL) {
        printShow(atual->show);
        atual = atual->prox;
    }
}



int main() {
    FILE* f = fopen("/tmp/disneyplus.csv", "r");
  if (!f) {
    perror("Error opening CSV file");
    return 1;
  }

  char line[MAX_LINE];
  Show* shows[MAX_SHOWS];
  int count = 0;

  fgets(line, sizeof(line), f);
  while (fgets(line, sizeof(line), f) && count < MAX_SHOWS) {
    trim(line);
    char* parts[MAX_FIELDS];
    parseCSVLine(line, parts);
    shows[count] = malloc(sizeof(Show));
    setShow(shows[count], parts);
    for (int i = 0; i < MAX_FIELDS; i++) free(parts[i]);
    count++;
  }
  fclose(f);

  CircularQueue queue;
  initializeQueue(&queue);

  while (fgets(line, sizeof(line), stdin)) {
    trim(line);
    if (strcmp(line, "FIM") == 0) break;
    for (int i = 0; i < count; i++) {
      if (strcmp(shows[i]->id, line) == 0) {
        enqueue(&queue, shows[i]);
        break;
      }
    }
  }

  int n;
  scanf("%d\n", &n);
  for (int i = 0; i < n; i++) {
    fgets(line, sizeof(line), stdin);
    trim(line);
    if (line[0] == 'I') {
      char id[50];
      sscanf(line + 2, "%s", id);
      for (int j = 0; j < count; j++) {
        if (strcmp(shows[j]->id, id) == 0) {
          enqueue(&queue, shows[j]);
          break;
        }
      }
    } else if (line[0] == 'R') {
      Show* removed = dequeue(&queue);
      if (removed) printf("(R) %s\n", removed->title);
    }
  }

  for (int i = 0, idx = queue.front; i < queue.size; i++) {
    printf("[%d] ", i);
    printShow(queue.queue[idx]);
    idx = (idx + 1) % QUEUE_SIZE;
  }

  for (int i = 0; i < count; i++) {
    free(shows[i]->id);
    free(shows[i]->type);
    free(shows[i]->title);
    free(shows[i]->director);
    for (int j = 0; j < shows[i]->cast_size; j++) free(shows[i]->cast[j]);
    free(shows[i]->cast);
    free(shows[i]->country);
    free(shows[i]->date_added);
    free(shows[i]->rating);
    free(shows[i]->duration);
    for (int j = 0; j < shows[i]->listed_in_size; j++) free(shows[i]->listed_in[j]);
    free(shows[i]->listed_in);
    free(shows[i]->description);
    free(shows[i]);
  }

  return 0;
}