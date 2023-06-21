package com.example.administradorfinanceiro.Model;

public class RelatorioAbastecimentoModel {

    private String Veicolo;
    private String Posto;
    private String LitrosTotal;
    private String ValorLitro;
    private String Date;
    private String kmPercorido;
    private String Hodometro;
    private String Media;
    private String ValorTotal;
    private String Cor;///cor da linha do RelatorioActivity

    public String getCor() { return Cor; }

    public void setCor(String cor) { Cor = cor; }

    public String getMedia() {
        return Media;
    }

    public void setMedia(String media) {
        Media = media;
    }

    public String getHodometro() {
        return Hodometro;
    }

    public void setHodometro(String hodometro) {
        Hodometro = hodometro;
    }

    public String getKmPercorido() {
        return kmPercorido;
    }

    public void setKmPercorido(String kmPercorido) {
        this.kmPercorido = kmPercorido;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getValorLitro() {
        return ValorLitro;
    }

    public void setValorLitro(String valorLitro) {
        ValorLitro = valorLitro;
    }

    public String getPosto() {
        return Posto;
    }

    public void setPosto(String posto) {
        Posto = posto;
    }

    public String getLitrosTotal() {
        return LitrosTotal;
    }

    public void setLitrosTotal(String litrosTotal) {
        LitrosTotal = litrosTotal;
    }

    public String getVeicolo() {
        return Veicolo;
    }

    public void setVeicolo(String veicolo) {
        Veicolo = veicolo;
    }

    public String getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(String valorTotal) {
        ValorTotal = valorTotal;
    }
}