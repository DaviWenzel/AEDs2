class FIla{
    public Fila(int tamanho){
        array = new int(tamanho+i);
        primeiro = ultimo = 0;
    }
    public boolean pesquisarIterativo(int x){                                                   
        boolean resp = false;
        for(int i = primeiro; i != ultimo; (i+1) % array.length()){
            if(array[i]==x){
                resp = true;
                i = ultimo;
            }
        }
        return resp;
    }
    public boolean pesquisarRec(int x){
        return pesquisarRec(x,primeiro);
    }
    private boolean pesquisarRec(int x, int i){
        boolean resp = false;
        if(i != ultimo){
            if(array[i] == x) resp = true;
            else pesquisarRec(x, (i+1)%array.length());
        } 
        return resp;
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
}
