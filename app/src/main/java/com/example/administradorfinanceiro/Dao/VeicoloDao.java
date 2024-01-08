package com.example.administradorfinanceiro.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import androidx.annotation.Nullable;

import com.example.administradorfinanceiro.Model.VeicoloModel;

import java.util.ArrayList;
import java.util.List;

public class VeicoloDao extends ContextoDb {

    public VeicoloDao(@Nullable Context context) {
        super(context);
    }

    public static String CriarTabela() {

        String sql = " CREATE TABLE IF NOT EXISTS tbVeicolo " +
                "(Id INTEGER PRIMARY KEY AUTOINCREMENT" +
                " UNIQUE NOT NULL," +
                " Name VARCHAR NOT NULL," +
                " Hodometro VARCHAR NOT NULL," +
                " Status VARCHAR "+
        ");";
        return sql;
    }

    public String Inserir(VeicoloModel veicoloModel) {
        String msg = "";
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", veicoloModel.getName());
        contentValues.put("Hodometro", veicoloModel.getHodometro());
        contentValues.put("Status","A");
        try {
            conexao.insertOrThrow("tbVeicolo", null, contentValues);
        } catch (SQLException ex) {
            msg = "Falha no cadastro.";
        }
        return msg;
    }

    public List<VeicoloModel> Lista() {
        List<VeicoloModel> list = new ArrayList<VeicoloModel>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM tbVeicolo ");
        Cursor result = conexao.rawQuery(sql.toString(), null);
        if (result.getCount() > 0) {
            result.moveToFirst();
            do {
                VeicoloModel v = new VeicoloModel();
                v.setId(result.getInt(result.getColumnIndexOrThrow("Id")));
                v.setName(result.getString(result.getColumnIndexOrThrow("Name")));
                list.add(v);
            } while (result.moveToNext());
        }
        return list;
    }

    public String Update(VeicoloModel v) {
        String msg = "";
        ContentValues dataToInsert = new ContentValues();
        dataToInsert.put("Name", v.getName());
        dataToInsert.put("Hodometro", v.getHodometro());
        String where = "Id = ?";
        String[] whereArgs = new String[]{String.valueOf(v.getId())};
        try {
            conexao.update("tbVeicolo", dataToInsert, where, whereArgs);
        } catch (SQLException ex) {
            msg = "Falha au atualizar Hodometro \n"+v.getName()+"\n"+v.getHodometro()+"\n"+v.getId();
        }

        return msg;
    }

    public VeicoloModel getVeicolo(int codigo) {
        VeicoloModel v = new VeicoloModel();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM tbVeicolo ");
        sql.append(" WHERE Id = ?");
        String[] parametro = new String[1];
        parametro[0] = String.valueOf(codigo);
        Cursor result = conexao.rawQuery(sql.toString(), parametro);
        if (result.getCount() > 0) {
            result.moveToFirst();
            v.setId(result.getInt(result.getColumnIndexOrThrow("Id")));
            v.setName(result.getString(result.getColumnIndexOrThrow("Name")));
            v.setHodometro(result.getString(result.getColumnIndexOrThrow("Hodometro")));
        }

        return v;
    }
}
    /*private int Id;
    private String Name;
    private String Hodometro;*/