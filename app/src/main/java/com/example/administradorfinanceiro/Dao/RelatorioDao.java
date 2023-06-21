package com.example.administradorfinanceiro.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.administradorfinanceiro.Model.VeicoloModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RelatorioDao extends ContextoDb {

    public RelatorioDao(@Nullable Context context) {
        super(context);
    }

    public Cursor relatorioAbstecimento(String query) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = conexao.rawQuery(query, null);
        List<VeicoloModel> usuario = new ArrayList<VeicoloModel>();
       /* while (c.moveToNext()) {
            VeicoloModel user = new VeicoloModel();
            user.setId(c.getInt(c.getColumnIndexOrThrow("Id")));
            user.setName(c.getString(c.getColumnIndexOrThrow("Name")));
            usuario.add(user);
        }*/
        return c;
    }
}

