class Clone{
    public static void main(String[] args){
        Carro carro1 = new Carro();
        carro1.setAno(2012);
        carro1.setModelo("Palio Weekend");
        Carro carro3 = carro1;
        System.out.println(carro1);
        System.out.println(carro3); // jeito errado de clonar, se editar um, edita ambos
        Carro carro2 = new Carro();
        carro2 = carro1.cloneCarro(); //método clonar criado na classe carro
        System.out.println(carro1);
        System.out.println(carro2);
        carro2.setModelo("Honda City"); //agora terá dois carros iguais que podem ser modificador individualmente
        carro2.setAno(2013);
        System.out.println(carro1);
        System.out.println(carro2);
    }
}