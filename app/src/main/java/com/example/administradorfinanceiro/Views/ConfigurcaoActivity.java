package com.example.administradorfinanceiro.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.administradorfinanceiro.Dao.PostoDao;
import com.example.administradorfinanceiro.Dao.VeicoloDao;
import com.example.administradorfinanceiro.Model.PostoModel;
import com.example.administradorfinanceiro.Model.VeicoloModel;
import com.example.administradorfinanceiro.R;

public class ConfigurcaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurcao);
        VeicoloDao v = new VeicoloDao(this);
        VeicoloModel m = new VeicoloModel();
        m.setHodometro("55555");
        m.setName("tr4 drty");
        v.Inserir(m);
        for (int i=0;i<1;i++){
        PostoDao p = new PostoDao(this);
        PostoModel po =new PostoModel();
        po.setName(i+" - artes postos fiacalo");
        p.Inserir(po);}
    }
}