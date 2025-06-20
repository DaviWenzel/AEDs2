
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_LINE 1024
#define MAX_FIELDS 12
#define MAX_SHOWS 1369

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

int compareTitle(const void* a, const void* b) {
  Show* s1 = *(Show**)a;
  Show* s2 = *(Show**)b;
  return strcmp(s1->title, s2->title);
}

int binarySearch(Show** arr, int n, const char* key, int* comparisons) {
  int low = 0, high = n - 1;
  while (low <= high) {
    (*comparisons)++;
    int mid = (low + high) / 2;
    int cmp = strcmp(arr[mid]->title, key);
    if (cmp == 0) return mid;
    else if (cmp < 0) low = mid + 1;
    else high = mid - 1;
  }
  return -1;
}

int main() {
  FILE* f = fopen("/tmp/disneyplus.csv", "r");
  if (!f) return -1;

  char line[MAX_LINE];
  Show* allShows[MAX_SHOWS];
  int totalCount = 0;

  fgets(line, sizeof(line), f); 
  while (fgets(line, sizeof(line), f) && totalCount < MAX_SHOWS) {
    trim(line);
    char* parts[MAX_FIELDS];
    parseCSVLine(line, parts);

    allShows[totalCount] = malloc(sizeof(Show));
    setShow(allShows[totalCount], parts);
    for (int i = 0; i < MAX_FIELDS; i++) free(parts[i]);
    totalCount++;
  }
  fclose(f);

  char input[256];
  Show* selected[MAX_SHOWS];
  int count = 0;

  while (fgets(input, sizeof(input), stdin)) {
    trim(input);
    if (strcmp(input, "FIM") == 0) break;

    for (int i = 0; i < totalCount; i++) {
      if (strcmp(allShows[i]->id, input) == 0) {
        selected[count++] = allShows[i];
        break;
      }
    }
  }
  qsort(selected, count, sizeof(Show*), compareTitle);

  int comparisons = 0;
  clock_t start = clock();

  while (fgets(input, sizeof(input), stdin)) {
    trim(input);
    if (strcmp(input, "FIM") == 0) break;

    int idx = binarySearch(selected, count, input, &comparisons);
    printf("%s\n", (idx != -1) ? "SIM" : "NAO");
  }

  clock_t end = clock();
  double time_spent = (double)(end - start) / CLOCKS_PER_SEC;

  FILE* log = fopen("857268_binaria.txt", "w"); 
  fprintf(log, "857268\t%lf\t%d\n", time_spent, comparisons);
  fclose(log);

  for (int i = 0; i < totalCount; i++) {
    free(allShows[i]->id); free(allShows[i]->type); free(allShows[i]->title); free(allShows[i]->director);
    for (int j = 0; j < allShows[i]->cast_size; j++) free(allShows[i]->cast[j]);
    free(allShows[i]->cast);
    free(allShows[i]->country); free(allShows[i]->date_added);
    free(allShows[i]->rating); free(allShows[i]->duration);
    for (int j = 0; j < allShows[i]->listed_in_size; j++) free(allShows[i]->listed_in[j]);
    free(allShows[i]->listed_in);
    free(allShows[i]->description); free(allShows[i]);
  }

  return 0;
}
