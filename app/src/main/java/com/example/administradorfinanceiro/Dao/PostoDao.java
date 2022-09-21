package com.example.administradorfinanceiro.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import androidx.annotation.Nullable;
import com.example.administradorfinanceiro.Model.PostoModel;
import java.util.ArrayList;
import java.util.List;

public class PostoDao extends ContextoDb{
    public PostoDao(@Nullable Context context) {
        super(context);
    }

    public static String CriarTabela(){
        String sql = " CREATE TABLE IF NOT EXISTS tbPosto ("+
                " Id INTEGER PRIMARY KEY AUTOINCREMENT" +
                " UNIQUE NOT NULL," +
                " Name VARCHAR NOT NULL" +
                ");";
        return sql;
    }
    public String Inserir(PostoModel postoMode){
        String msg="";
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name",postoMode.getNome());
        try{
            conexao.insertOrThrow("tbPosto",null,contentValues);
        }catch (SQLException ex){
            msg="Falha no cadastro.";
        }
        return msg;
    }
    public List<PostoModel> Lista() {
        List<PostoModel> list = new ArrayList<PostoModel>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM tbPosto ");
        Cursor result = conexao.rawQuery(sql.toString(), null);
        if (result.getCount() > 0) {
            result.moveToFirst();
            do {
                PostoModel v = new PostoModel();
                v.setId(result.getInt(result.getColumnIndexOrThrow("Id")));
                v.setName(result.getString(result.getColumnIndexOrThrow("Name")));
                list.add(v);
            } while (result.moveToNext());
        }
        return list;
    }
    public PostoModel GetID(int id)
    {
        PostoModel pp = new PostoModel();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM tbPosto WHERE Id = "+id);
        Cursor result = conexao.rawQuery(sql.toString(), null);
        result.moveToFirst();
            pp.setId(result.getInt(result.getColumnIndexOrThrow("Id")));
            pp.setName(result.getString(result.getColumnIndexOrThrow("Name")));

        return pp;
    }
    public PostoModel GetName(String name)
    {PostoModel pp = new PostoModel();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * FROM tbPosto WHERE Name = "+name);
        Cursor result = conexao.rawQuery(sql.toString(), null);
        result.moveToFirst();
        pp.setId(result.getInt(result.getColumnIndexOrThrow("Id")));
        pp.setName(result.getString(result.getColumnIndexOrThrow("Name")));

        return pp;
    }
}
/* private int Id;
    private String Name;*/