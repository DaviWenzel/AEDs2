    /*
    Crie uma classe shows seguindo todas as regras apresentadas no slide 
    unidade00l_conceitosBasicos_introducaoOO.pdf. Sua classe terá os atributos 
    privado show_id: string, type: string, title: string, director: string, 
    cast: string[], country: string, date_added: date, release_year: int,
    rating: string, duration: string, listed_in: string[]. Sua classe também terá 
    pelo menos dois construtores, e os métodos gets, sets, clone, imprimir e ler.
    https://github.com/icei-pucminas/aeds2/blob/master/aulas/u00%20Nivelamento/unidade00l_nivelamento_introducaoOO.pdf


    O método imprimir mostra os atributos do registro (ver cada linha da saída 
    padrão) e o ler lê os atributos de um registro. Atenção para o arquivo de 
    entrada, pois em alguns registros faltam valores e esse deve ser substituído 
    pelo valor NaN. A entrada padrão é composta por várias linhas e cada uma contém
    um número inteiro indicando o show_id do shows a ser lido.

    A última linha da entrada contém a palavra FIM. A saída padrão também contém 
    várias linhas, uma para cada registro contido em uma linha da entrada padrão, 
    no seguinte formato: [=> id ## type ## title ## director ## [cast] ## country 
    ## date_added ## release_year ## rating ## duration ## [listed_in].
    */
    import java.util.Scanner;
    import java.time.LocalDate;
    import java.time.format.DateTimeFormatter;
    import java.io.FileReader;
    import java.io.BufferedReader;
    import java.time.format.DateTimeFormatter;
    import java.util.regex.*;
    import java.util.Arrays;


    public class Q01 {
        public static void main(String[] args) throws Exception{
            String filme = "";
            Scanner sc = new Scanner(System.in);
            String id = sc.nextLine();
            Filme[] filmes = new Filme[1369];
            while(!id.equals("FIM")){
                int n = Integer.parseInt(id.replaceAll("[^0-9]", ""));
                BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));
                for(int i = 0; i <= n; i++){
                    filme = br.readLine();
                }
                filmes[n] = new Filme();
                filmes[n].ler(filme);
                filmes[n].print();
                id = sc.nextLine();
            }
            sc.close();
        }
    }

    class Filme{
        private String show_id;    
        private String type;
        private String title;
        private String director;
        private String[] cast;
        private String country;
        private LocalDate date_added;
        private int release_year;
        private String rating;
        private String duration;
        private String[] listed_in;
        private String description;

        public Filme(){
            show_id = "";
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

        public Filme(String show_id, String type, String title, String director, String[] cast, String country, LocalDate date_added, int release_year, String rating, String duration, String[] listed_in, String description){
            this.show_id = show_id;
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

        public void setShow_id(String show_id){
            this.show_id = show_id;
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
            return show_id;
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

        public String listed_inToString(){
            Arrays.sort(cast);
            String s ="[" + String.join(",", listed_in) + "]";
            s = s.replace("\"", "");
            return s;
        }

        public String castToString(){
            cast[0] = cast[0].trim();
            Arrays.sort(cast);
            for (int i = 0; i < cast.length; i++) {
                cast[i] = cast[i].trim();
            }
            String s ="[" + String.join(", ", cast) + "]";
            s = s.replace("\"", "");
            return s;
        }

        public void print(){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            date_added.format(formatter)
            title = title.replace("\"", "");
            System.out.println("=> " + show_id + " ## " + title + " ## " + type + " ## " + director + " ## " + castToString() + " ## " + country + " ## " + date_added.format(formatter) + " ## " + release_year + " ## " + rating + " ## " + duration + " ## " + listed_inToString() + " ##");
        }
        
        public void ler(String s) {
            String[] fields = s.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            this.show_id = fields[0].trim(); // show_id -> String
            this.type = fields[1].trim(); // type -> String
            this.title = fields[2].trim(); // title -> String
            if(fields[3].isEmpty()) this.director = "NaN"; else this.director = fields[3]; // director -> String can be NaN
            if(fields[4].isEmpty()){ this.cast = new String[1]; this.cast[0] = "NaN"; } else this.cast = fields[4].split(","); // cast -> String[] can be NaN
            if(fields[5].isEmpty()) this.country = "NaN"; else this.country = fields[5]; // country -> String can be NaN
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            fields[6] = fields[6].replace("\"", "").trim();
            this.date_added = LocalDate.parse(fields[6], formatter); // date_added -> LocalDate MMMM dd, yyyy
            this.release_year = Integer.parseInt(fields[7]); // release_year -> int 
            if(fields[8].isEmpty()) this.rating = "NaN"; else this.rating = fields[8];  // rating -> String can be NaN
            this.duration = fields[9]; // duration -> String
            this.listed_in = fields[10].split(","); // listed_in -> String[]
            this.description = fields[11]; // description -> String
            }

        public Filme clone(){
            Filme clone = new Filme(show_id, type, title, director, cast, country, date_added, release_year, rating, duration, listed_in, description);
            return clone;
        }
    }   

