package com.example.administradorfinanceiro.Model;

public class FinancasModel {
    private int Id;
    private int IdConta; // Id 0 è uma entrada
    private int Origem;//1 - Salario", "2 - Extra", "3 - Doação", "4 - Outro" Origem 5 significa um saque, 0 é uma conta paga
    private String Date;
    private String Valor;
    private String EntradaSaida;//E entrada S saida N para pagamento em dinheiro no qual ja foi sacado

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdConta() {
        return IdConta;
    }

    public void setIdConta(int idConta) {
        IdConta = idConta;
    }

    public int getOrigem() {
        return Origem;
    }

    public void setOrigem(int origem) {
        Origem = origem;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }

    public String getEntradaSaida() {
        return EntradaSaida;
    }

    public void setEntradaSaida(String entradaSaida) {
        EntradaSaida = entradaSaida;
    }
}
