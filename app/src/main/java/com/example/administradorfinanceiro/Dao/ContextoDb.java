package com.example.administradorfinanceiro.Dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.SQLDataException;

public class ContextoDb extends SQLiteOpenHelper {
        private static final String DbName = "dados";
        private static final int version = 1;
        public static SQLiteDatabase conexao;
        private SQLiteDatabase db;

        private ContextoDb contextoDb = this;

        public ContextoDb(@Nullable Context context) {
            super(context, DbName, null, version);
            db = getWritableDatabase();
            if(db.getVersion()<1) {
                this.onCreate(db);
                Log.e("Banco de dados ", " vesfddf\n\n" + db.getVersion() + "\n\n" + db.getPath());
            }
            this.Conexao();
        }
        /**
         * Called when the database is created for the first time. This is where the
         * creation of tables and the initial population of the tables should happen.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
           try {
               db.execSQL(VeicoloDao.CriarTabela());
           db.execSQL(PostoDao.CriarTabela());
           db.execSQL(FinancasDao.CriarTabela());
            db.execSQL(ContasDao.CriarTabela());
            db.execSQL(AbastecimentoDao.CriarTabela());
        }catch (Exception e){
            Log.e(e.toString()," Tabelas não foram criadas ");
        }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
        public void  Conexao() {
            try{
            conexao = contextoDb.getWritableDatabase();
            }catch (SQLException ex){
                Log.e("Criação de conexão"," Falha na criaçao\n"+ex);

            }
        }
    }

////https://www.youtube.com/playlist?list=PLVawTLaO8Js9vhVjFj4Q18ncLHBdchenb
////https://www.youtube.com/watch?v=zmjERwv_9Oc  aulão