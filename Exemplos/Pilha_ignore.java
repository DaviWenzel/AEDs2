class Pilha {
    private Celula topo;
    public Pilha(){
        topo = null;
    }    
    public void inserir(int x){
        Celula tmp = new Celula(x);
        tmp.prox = topo;
        topo = tmp;
        tmp = null;
    }
    public int remover(){
        int x = 0;
        //...
        return x;
    }
    public void mostrar(){
        //...
    }
}

class Celula{
    int elemento;
    Celula prox;
    public Celula(int x){
        //...
    }
}

