package com.example.administradorfinanceiro.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;

import androidx.annotation.Nullable;

import com.example.administradorfinanceiro.Model.AbastecimentoModel;
import com.example.administradorfinanceiro.Model.FinancasModel;

public class FinancasDao extends ContextoDb{
    public FinancasDao(@Nullable Context context) {
        super(context);
    }

    public static String CriarTabela(){

        String sql = " CREATE TABLE IF NOT EXISTS tbFinancas ("+
                " Id   INTEGER PRIMARY KEY AUTOINCREMENT" +
                " UNIQUE NOT NULL," +
                " IdConta INTEGER NOT NULL," +
                " Origem INTEGER NOT NULL," +
                " Date VARCHAR NOT NULL," +
                " Valor VARCHAR NOT NULL," +
                " EntradaSaida VARCHAR NOT NULL," +
                " Status VARCHAR NOT NULL"+
                ");";
        return sql;
    }
    public String Inserir(FinancasModel financasModel){
        String msg="";
        ContentValues contentValues = new ContentValues();
        contentValues.put("IdConta",financasModel.getIdConta());
        contentValues.put("Origem",financasModel.getOrigem());
        contentValues.put("Date",financasModel.getDate());
        contentValues.put("Valor",financasModel.getValor());
        contentValues.put("EntradaSaida",financasModel.getEntradaSaida());
        contentValues.put("Status","A");
        try{
            conexao.insertOrThrow("tbFinancas",null,contentValues);
        }catch (SQLException ex){
            msg="Falha no cadastro.\n"+ex;
        }
        return msg;
    }
}
/*private int Id;
private int IdConta; Id 1 é um abastecimento
private int Origem;//1 - Salario", "2 - Extra", "3 - Doação", "4 - Outro" Origem 5 significa um saque
private String Date ;
private String Valor;
private String EntradaSaida;//E entrada S saida N para pagamento em dinheiro no qual ja foi sacado*/