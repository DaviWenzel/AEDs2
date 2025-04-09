import java.util.Calendar;

public class Carro {
    private int ano;
    private String modelo;
    public Carro(){
    }
    public Carro(int ano, String modelo){
        this.ano = ano;
        this.modelo = modelo;
    }
    public int getAno(){
        return ano;
    }
    public void setAno(int ano){
        Calendar cal = Calendar.getInstance();

        if(ano > 1900 && ano < cal.get(Calendar.YEAR) + 1)
            this.ano = ano;
    }
    public void setModelo(String modelo){
    if(modelo.length() > 0)
        this.modelo = modelo;
    }
    public String getModelo(){
        return modelo;
    }

    public String toString(){
        return "Modelo: "+ getModelo() + ",Ano: " + getAno();
    }
    public Carro cloneCarro(){
        Carro clonado = new Carro();
        clonado.setAno(this.getAno());
        clonado.setModelo(this.getModelo());
        return clonado;
    }
}
