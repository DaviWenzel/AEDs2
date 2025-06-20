
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
public class Q05 {
    public static void gerarLog(long inicio, long fim, int comparacoes) {
        long tempo = (fim - inicio) / 1000;
	    int matricula = 857268;
	    String nomelog = "matricula_selecao.txt";
		File logFile = new File(nomelog);
		try {
			FileWriter file = new FileWriter(nomelog, true); 
			file.write(matricula + "\t" + tempo + "ms\t" + comparacoes + "\n");
			file.close();
		} catch (IOException e) {
			System.err.println("eu odeio a vida" + e.getMessage());
			e.printStackTrace();
		}
	}
  public static Show[] gerarArray() throws FileNotFoundException{
    File file = new File("/tmp/disneyplus.csv");
    Scanner fileSc = new Scanner(file);
    fileSc.nextLine();
	Show[] show = new Show[1368];
    for(int i = 0; i < 1368; i++){
      if(fileSc.hasNextLine()){
        String s = fileSc.nextLine();
        show[i] = new Show();
        show[i].read(s);
      }
      else {
        break;
      }
    }
	fileSc.close();
    return show;
  }

  public static void main(String[] args) throws FileNotFoundException {
    long inicio = System.nanoTime();
    int comparacoes = 0;
    Show[] show = gerarArray(); 
    Scanner sc = new Scanner(System.in);
    int[] ids = new int[1368];
    int i = 0;
    String s = sc.nextLine();
    while (!s.equals("FIM")) {
        String num = s.replaceAll("[^0-9]", "");
        //123
        int temp = Integer.parseInt(num) - 1;
        //temp = 123
        ids[i] = temp;
        //ids[0] = 123
        s = sc.nextLine();
        i++;
    }
    for (int j = 0; j < i; j++) { 
        int menor = j;
        //menor = 0
        for (int k = j + 1; k < i; k++) { 
            comparacoes++;
            //show ids[0] (123) comparado com show ids[k] (tudo)
            if (show[ids[menor]].getTitle().compareTo(show[ids[k]].getTitle()) > 0) {
                menor = k;  
            }
        }
        //temp = 123
        int temp = ids[j];
        // id j = id no id menor
        ids[j] = ids[menor];
        // id menor = 123
        ids[menor] = temp;
    }
    for (int j = 0; j < i; j++) {
        show[ids[j]].printShow();
    }
    long fim = System.nanoTime();
    gerarLog(inicio, fim, comparacoes);
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