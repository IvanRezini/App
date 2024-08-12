package com.example.administradorfinanceiro.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;

import androidx.annotation.Nullable;

import com.example.administradorfinanceiro.Model.AbastecimentoModel;
import com.example.administradorfinanceiro.Model.ContasModel;
import com.example.administradorfinanceiro.Model.VeicoloModel;

public class AbastecimentoDao extends  ContextoDb{
    public AbastecimentoDao(@Nullable Context context) {
        super(context);
    }

    public static String CriarTabela(){
        String sql = " CREATE TABLE IF NOT EXISTS tbAbastecimento ("+
                " Id   INTEGER PRIMARY KEY AUTOINCREMENT" +
                " UNIQUE NOT NULL," +
                " Veicolo INTEGER NOT NULL," +
                " Posto INTEGER NOT NULL," +
                " LitrosTotal VARCHAR NOT NULL," +
                " ValorLitro VARCHAR NOT NULL," +
                " Date VARCHAR NOT NULL," +
                " kmPercorido VARCHAR NOT NULL," +
                " Status VARCHAR, "+
                "Obs VARCHAR "+
                ");";
        return sql;
    }
    public String Inserir(AbastecimentoModel abastecimentoModel){
        String msg="";
        ContentValues contentValues = new ContentValues();
       contentValues.put("Veicolo",abastecimentoModel.getVeicolo());
        contentValues.put("Posto",abastecimentoModel.getPosto());
        contentValues.put("LitrosTotal",abastecimentoModel.getLitrosTotal());
        contentValues.put("ValorLitro",abastecimentoModel.getValorLitro());
        contentValues.put("Date",abastecimentoModel.getDate());
        contentValues.put("kmPercorido",abastecimentoModel.getKmPercorido());
        contentValues.put("Obs",abastecimentoModel.getObs());
        contentValues.put("Status","A");

        try{
            msg = "";
            conexao.insertOrThrow("tbAbastecimento",null,contentValues);
        }catch (SQLException ex){
            msg="Falha no cadastro. " +ex;
        }
        return msg;
    }

}
    /*private int Id ;
    private int Veicolo;
    private int Posto ;
    private String LitrosTotal ;
    private String ValorLitro ;
    private String Date ;
    private String kmPercorido ;
    private String Hodometro ;*/

