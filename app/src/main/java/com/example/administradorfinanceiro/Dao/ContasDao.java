package com.example.administradorfinanceiro.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DefaultDatabaseErrorHandler;
import android.database.SQLException;

import androidx.annotation.Nullable;

import com.example.administradorfinanceiro.Model.ContasModel;
import com.example.administradorfinanceiro.Model.PostoModel;
import com.example.administradorfinanceiro.Model.VeicoloModel;

import java.util.ArrayList;
import java.util.List;

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
        ContentValues cv = new ContentValues();
        cv.put("Name",  "Abastecimento");
        conexao.insert("tbContas", null, cv);
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
    public String Update(ContasModel contasModel){
        String msg = "";
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put("Name", contasModel.getName());
        String where = "Id = ?";
        String[] whereArgs = new String[]{String.valueOf(contasModel.getId())};
        try {
            conexao.update("tbContas", dataToInsert, where, whereArgs);
        } catch (SQLException ex) {
            msg = "Falha au atualizar conta";
        }

        return msg;
    }
    public List<ContasModel> Lista() {
        List<ContasModel> list = new ArrayList<ContasModel>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM tbContas ");
        Cursor result = conexao.rawQuery(sql.toString(), null);
        if (result.getCount() > 0) {
            result.moveToFirst();
            do {
                ContasModel v = new ContasModel();
                v.setId(result.getInt(result.getColumnIndexOrThrow("Id")));
                v.setName(result.getString(result.getColumnIndexOrThrow("Name")));
                list.add(v);
            } while (result.moveToNext());
        }
        return list;
    }
    public ContasModel GetID(int id)
    {
        ContasModel pp = new ContasModel();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM tbContas WHERE Id = "+id);
        Cursor result = conexao.rawQuery(sql.toString(), null);
        result.moveToFirst();
        pp.setId(result.getInt(result.getColumnIndexOrThrow("Id")));
        pp.setName(result.getString(result.getColumnIndexOrThrow("Name")));

        return pp;
    }
    public ContasModel GetName()
    {
        ContasModel pp = new ContasModel();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM tbContas ORDER BY id DESC");
        Cursor result = conexao.rawQuery(sql.toString(), null);
        result.moveToFirst();
        pp.setId(result.getInt(result.getColumnIndexOrThrow("Id")));
        pp.setName(result.getString(result.getColumnIndexOrThrow("Name")));

        return pp;
    }
}
    /*private int Id;
    private String Name;*/