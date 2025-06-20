
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Scanner;
import java.io.*;
public class Q01 {
    public static Show[] show;
    static {
        try {
            show = gerarArray();
        } catch (FileNotFoundException e) {
            System.out.println("Erro");
        }
    }

    public static Show[] gerarArray() throws FileNotFoundException {
        File file = new File("/tmp/disneyplus.csv");
        Scanner fileSc = new Scanner(file);
        fileSc.nextLine();
        Show[] show = new Show[1368];
        for (int i = 0; i < 1368; i++) {
            if (fileSc.hasNextLine()) {
                String s = fileSc.nextLine();
                show[i] = new Show();
                show[i].read(s);
            } else {
                break;
            }
        }
        fileSc.close();
        return show;
    }
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Lista lista = new Lista();

        String id = sc.nextLine();
        while (!id.equals("FIM")) {
            int i = Integer.parseInt(id.substring(1)) - 1;
            lista.inserirFim(show[i]);
            id = sc.nextLine();
        }

        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            String c = sc.nextLine();
            if (c.startsWith("II")) {
                int j = Integer.parseInt(c.substring(4)) - 1;
                lista.inserirInicio(show[j]);
            } else if (c.startsWith("IF")) {
                int j = Integer.parseInt(c.substring(4)) - 1;
                lista.inserirFim(show[j]);  
            } else if (c.startsWith("I*")) {
                String[] p = c.split(" ");
                int pos = Integer.parseInt(p[1].trim());
                int j = Integer.parseInt(p[2].substring(1)) -;
                lista.inserir(show[j], pos);
            } else if (c.equals("RI")) {
                Show removido = lista.removerInicio();
                System.out.println("(R) " + removido.getTitle());
            } else if (c.equals("RF")) {
                Show removido = lista.removerFim();
                System.out.println("(R) " + removido.getTitle());
            } else if (c.startsWith("R*")) {
                int pos = Integer.parseInt(c.split(" ")[1]);
                Show removido = lista.remover(pos);
                System.out.println("(R) " + removido.getTitle());
            }
        }

        lista.mostrar();
        sc.close();
    }
}




class Show {
    public   String   id;
    private  String   type;
    private  String   title;
    private  String   director;
    private  String[] cast;
    private  String   country;
    private  LocalDate date_added;
    private  int      release_year;
    private  String   rating;
    private  String   duration;
    private  String[] listed_in;
    private  String   description;
  
    public Show(){
      id = "";
      type = "";
      title = "";
      director = "";
      cast = new String[1];
      country = "";
      date_added = LocalDate.now();
      release_year = 0;
      rating = "";
      duration = "";
      listed_in = new String[1];
      description = "";
    }
  
    public Show(String id, String type, String title, String director, String[] cast, String country, LocalDate date_added, int release_year, String rating, String duration, String[] listed_in, String description){
      this.id = id;
      this.type = type;
      this.title = title;
      this.director = director;
      this.cast = cast;
      this.country = country;
      this.date_added = date_added;
      this.release_year = release_year;
      this.rating = rating;
      this.duration = duration;
      this.listed_in = listed_in;
      this.description = description;
    }
  
    public void setShow_id(String id){
        this.id = id;
    }
  
    public void setType(String type){
          this.type = type;
      }
  
    public void setTitle(String title){
      this.title = title;
    }
  
    public void setDirector(String director){
      this.director = director;
    }
  
    public void setCast(String[] cast){
      this.cast = cast;
    }
  
    public void setCountry(String country){
      this.country = country;
    }
  
    public void setDate_added(LocalDate date_added){
      this.date_added = date_added;
    }
  
    public void setRelease_year(int release_year){
      this.release_year = release_year;
    }
  
    public void setRating(String rating){
      this.rating = rating;
    }
  
    public void setDuration(String duration){
      this.duration = duration;
    }
  
    public void setListed_in(String[] listed_in){
      this.listed_in = listed_in;
    }
  
    public void setDescription(String description){
      this.description = description;
    }
  
    public String getShow_id(){
      return id;
    }
  
    public String getType(){
      return type;
    }
  
    public String getTitle(){
      return title;
    }
  
    public String getDirector(){
      return director;
    }
  
    public String[] getCast(){
      return cast;
    }
  
    public String getCountry(){
      return country;
    }
  
    public LocalDate getDate(){
      return date_added;
    }
  
    public int getRelease_year(){
      return release_year;
    }
  
    public String getRating(){
      return rating;
    }
  
    public String getDuration(){
      return duration;
    }
  
    public String[] getListed_in(){
      return listed_in;
    }
  
    public String getDescription(){
      return description;
    }
  
    public Show clone(){
      Show clone = new Show(this.id, this.type, this.title, this.director, this.cast, this.country, this.date_added, this.release_year, this.rating, this.duration, this.listed_in, this.description);
      return clone;
    }
  
    public void read(String s){
      String[] fields = new String[12];
      int j = 0, k = 0;
      for(int i = 0; i < s.length(); i++){
        // ultimo campo
        if(j == 11){
          fields[j] = s.substring(i);
          i = s.length();
        } 
        else {
          boolean onQuotes = false;
          if((s.charAt(i) == '"')) onQuotes = true;
          if(onQuotes){
            for(int m = i + 1; onQuotes; m++){
              if((s.charAt(m) == '"') && (s.charAt(m + 1) == ',')){
                k = i;
                i = m + 1;
                onQuotes = false;
              } 
            }
            onQuotes = true;
          }
          //campo qualquer
          if(s.charAt(i) == ','){
            if(((j == 3 || j == 4) || (j == 5 || j == 8)) && (s.charAt(i - 1) == ',')){
                fields[j] = "NaN";
                j++;
                k = i + 1;
            } 
            else {
                if(onQuotes){
                  fields[j] = s.substring(k + 1, i - 1);
                  j++;
                  k = i + 1;
                }
                else{
                  fields[j] = s.substring(k, i);
                  if(j < 11) j++;
                  k = i + 1;
                }
            }
          }
        }
      }
      // preenchimento
      id = fields[0];
      type = fields[1];
      title = fields[2];
      director = fields[3];
      cast = fields[4].split(",");
      country = fields[5];
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
      if (fields[6] == null || fields[6].trim().isEmpty()) {
            date_added = LocalDate.now();
        } 
        else {
            date_added = LocalDate.parse(fields[6].trim(), formatter);
        }
      release_year = Integer.parseInt(fields[7]);
      rating = fields[8];
      duration = fields[9];
      listed_in = fields[10].split(",");
      description = fields[11];
    }
  
    public void printShow(){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
      for(int i = 0; i < cast.length; i++){
        if(cast[i].startsWith(" ")) cast[i] = cast[i].substring(1);
        cast[i] = cast[i].replace("\"", "");
      }
      for(int i = 0; i < listed_in.length; i++){
        listed_in[i] = listed_in[i].trim();
      }
      Arrays.sort(cast);
      System.out.println("=> " + id +
      " ## " + title.replaceAll("\"", "") +
      " ## " + type +
      " ## " + director +
      " ## " + Arrays.toString(cast) + 
      " ## " + country +
      " ## " + date_added.format(formatter) +
      " ## " + release_year +
      " ## " + rating + 
      " ## " + duration + 
      " ## " + Arrays.toString(listed_in) +
      " ##");
    }
  }

  class Lista {
    private Celula primeiro;
    private Celula ultimo;

    public Lista() {
        primeiro = new Celula(); 
        ultimo = primeiro;
    }

    public void inserirInicio(Show s) {
        Celula tmp = new Celula(s);
        tmp.prox = primeiro.prox;
        tmp.ant = primeiro;

        if (primeiro.prox != null) {
            primeiro.prox.ant = tmp;
        } else {
            ultimo = tmp;
        }

        primeiro.prox = tmp;
    }

    public void inserirFim(Show s) {
        Celula tmp = new Celula(s);
        tmp.ant = ultimo;
        ultimo.prox = tmp;
        ultimo = tmp;
    }

    public void inserir(Show s, int pos) throws Exception {
        int tamanho = tamanho();
        if (pos < 0 || pos > tamanho) throw new Exception("existe naum");

        if (pos == 0) {
            inserirInicio(s);
        } else if (pos == tamanho) {
            inserirFim(s);
        } else {
            Celula i = primeiro;
            for (int j = 0; j < pos; j++) i = i.prox;

            Celula tmp = new Celula(s);
            tmp.prox = i.prox;
            tmp.ant = i;
            i.prox.ant = tmp;
            i.prox = tmp;
        }
    }

    public Show removerInicio() throws Exception {
        if (primeiro == ultimo) throw new Exception("Lista vazia");

        Celula tmp = primeiro.prox;
        Show resp = tmp.s;

        primeiro.prox = tmp.prox;
        if (tmp.prox != null) {
            tmp.prox.ant = primeiro;
        } else {
            ultimo = primeiro;
        }

        return resp;
    }

    public Show removerFim() throws Exception {
        if (primeiro == ultimo) throw new Exception("Lista vazia");

        Show resp = ultimo.s;
        ultimo = ultimo.ant;
        ultimo.prox = null;

        return resp;
    }

    public Show remover(int pos) throws Exception {
        int tamanho = tamanho();
        if (pos < 0 || pos >= tamanho) throw new Exception("Posição inválida");

        if (pos == 0) return removerInicio();
        if (pos == tamanho - 1) return removerFim();

        Celula i = primeiro.prox;
        for (int j = 0; j < pos; j++) i = i.prox;

        Show resp = i.s;
        i.ant.prox = i.prox;
        i.prox.ant = i.ant;

        return resp;
    }

    public int tamanho() {
        int count = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox) count++;
        return count;
    }

    public void mostrar() {
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
            i.s.printShow();
        }
    }
}


class Celula {
    public Show s;
    public Celula ant;
    public Celula prox;

    public Celula() {
        this.s = null;
        this.ant = null;
        this.prox = null;
    }

    public Celula(Show s) {
        this.s = s;
        this.ant = null;
        this.prox = null;
    }
}