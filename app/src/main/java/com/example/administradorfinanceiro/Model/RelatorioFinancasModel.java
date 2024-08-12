package com.example.administradorfinanceiro.Model;

public class RelatorioFinancasModel {
    private int Id;
    private String Data;
    private String Valor;
    private String Origem;
    private String EntradaSaida;
    private String conta;
    private int Cor;///cor da linha do RelatorioActivity
    private int CorTexto;///cor da linha do RelatorioActivity
    private String Obs;

    public String getObs() {return Obs;}

    public void setObs(String obs) {Obs = obs;}

    public int getCorTexto() { return CorTexto; }

    public void setCorTexto(int corTexto) {
        CorTexto = corTexto;
    }

    public String getOrigem() {
        return Origem;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public void setOrigem(String origem) {
        Origem = origem;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }

    public int getCor() {
        return Cor;
    }

    public void setCor(int cor) {
        Cor = cor;
    }


    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getEntradaSaida() {
        return EntradaSaida;
    }

    public void setEntradaSaida(String entradaSaida) {
        EntradaSaida = entradaSaida;
    }
}
