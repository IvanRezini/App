package com.example.administradorfinanceiro.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.DefaultDatabaseErrorHandler;
import android.database.SQLException;

import androidx.annotation.Nullable;

import com.example.administradorfinanceiro.Model.ContasModel;
import com.example.administradorfinanceiro.Model.VeicoloModel;

public class ContasDao extends ContextoDb{
    public ContasDao(@Nullable Context context) {
        super(context);
    }

    public static String CriarTabela(){
        String sql = " CREATE TABLE IF NOT EXISTS tbContas ("+
                " Id   INTEGER PRIMARY KEY AUTOINCREMENT" +
                " UNIQUE NOT NULL," +
                " Name VARCHAR NOT NULL" +
                ");";

        return sql;
    }
    public String Inserir(ContasModel contasModel){
        String msg="";
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",contasModel.getName());
        try{
            conexao.insertOrThrow("tbContas",null,contentValues);
        }catch (SQLException ex){
            msg="Falha no cadastro.";
        }
        return msg;
    }
}
    /*private int Id;
    private String Name;*/