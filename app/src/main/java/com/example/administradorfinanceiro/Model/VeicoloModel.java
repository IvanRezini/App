package com.example.administradorfinanceiro.Model;

public class VeicoloModel {
    private int Id;
    private String Name;
    private String Hodometro;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getHodometro() {
        return Hodometro;
    }

    public void setHodometro(String hodometro) {
        Hodometro = hodometro;
    }
}
