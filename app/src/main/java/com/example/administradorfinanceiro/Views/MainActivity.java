package com.example.administradorfinanceiro.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administradorfinanceiro.Dao.ContextoDb;
import com.example.administradorfinanceiro.R;

public class MainActivity extends AppCompatActivity {

    private Button idReceitas;
    private Button idConta;
    private Button idSaque;
    private Button idAbastecimento;
    private Button idExtratoAbastecimento;
    private Button idExtratoConta;
    private Button idConfiguracao;
    private Button idSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContextoDb rr = new ContextoDb(MainActivity.this);

        idReceitas = findViewById(R.id.idReceitas);
        idConta = findViewById(R.id.idConta);
        idSaque = findViewById(R.id.idSaque);
        idAbastecimento = findViewById(R.id.idAbastecimento);
        idExtratoAbastecimento = findViewById(R.id.idExtartoAbastecimento);
        idExtratoConta = findViewById(R.id.idExtratoConta);
        idConfiguracao = findViewById(R.id.idConfiguracao);
        idSair = findViewById(R.id.idSair);


        idExtratoAbastecimento.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, RelatorioActivity.class);
                startActivity(it);
            }
        });
        idExtratoConta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, SelecaoRelatorioActivity.class);
                startActivity(it);
            }
        });
        idReceitas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, ReceitaActivity.class);
                startActivity(it);
            }
        });
        idSaque.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, SaqueActivity.class);
                startActivity(it);
            }

        });
        idConta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, ContasActivity.class);
                startActivity(it);
            }

        });
        idAbastecimento.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, AbastecimentoActivity.class);
                startActivity(it);
            }
        });
        idConfiguracao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, ConfigurcaoActivity.class);
                startActivity(it);
            }
        });
    }
}

//////https://www.youtube.com/watch?v=siOjlXa6eNQ&list=PLVawTLaO8Js9vhVjFj4Q18ncLHBdchenb&index=11