
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Q01Funcional {
    public static void main(String[] args) throws Exception{
        Show[] show = new Show[1369];
        File file = new File("disneyplus.csv");
        Scanner fileScanner = new Scanner(file);
        fileScanner.nextLine();
        for(int i = 1; i < 1366; i++){
            if(fileScanner.hasNextLine()){
                String s = fileScanner.nextLine();
                show[i] = new Show();
                show[i].read(s);
            }
            else {
                break;
            }
        }
        fileScanner.close();
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        while(!s.equals("FIM")){
            String num = s.replaceAll("[^0-9]", "");  
            int i = Integer.parseInt(num);
            show[i].printShow();
            s = sc.nextLine();
        }
    }
    public static void testar(String s){
      Show show = new Show();
      show.test(s);
    }
    public static void testar(int i){
      System.out.println("\n\n\nErro na linha: " + i + "\n");
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
      date_added = LocalDate.parse(fields[6], formatter);
      release_year = Integer.parseInt(fields[7]);
      rating = fields[8];
      duration = fields[9];
      listed_in = fields[10].split(",");
      description = fields[11];
    }

    public void test(String s){
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
      String[] fields = new String[12];
      char[] chararray = s.toCharArray();
      int j = 0, k = 0;
      for(int i = 0; i < s.length(); i++){
        if(j == 4 && (s.charAt(i) == ',')) System.out.println(i + " " + k);
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
      for(int i = 0; i < 12; i++){
        System.out.println(fields[i] + " fields[" + i + "] " + fields[i].length());
      }
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