package com.example.administradorfinanceiro.Model;

public class AbastecimentoModel {
    private int Id ;
    private int Veicolo;
    private int Posto ;
    private String LitrosTotal ;
    private String ValorLitro ;
    private String Date ;
    private String kmPercorido ;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getVeicolo() {
        return Veicolo;
    }

    public void setVeicolo(int veicolo) {
        Veicolo = veicolo;
    }

    public int getPosto() {
        return Posto;
    }

    public void setPosto(int posto) {
        Posto = posto;
    }

    public String getLitrosTotal() {
        return LitrosTotal;
    }

    public void setLitrosTotal(String litrosTotal) {
        LitrosTotal = litrosTotal;
    }

    public String getValorLitro() {
        return ValorLitro;
    }

    public String getKmPercorido() {
        return kmPercorido;
    }

    public void setKmPercorido(String kmPercorido) {
        this.kmPercorido = kmPercorido;
    }

    public void setValorLitro(String valorLitro) {
        ValorLitro = valorLitro;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
