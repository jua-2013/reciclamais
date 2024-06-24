package model;

public class Residuo {

    public int id;
    public String nome;
    public int idCategoria;
    public double qtd;
    
    public String getNomeResiduo(){
        return this.nome;
    }
    public Integer getValorId(){
        return this.id;
    }
    public int getValorCategoria(){
        return this.idCategoria;
    }
    public double getValorQtd(){
        return this.qtd;
    }
   

}
