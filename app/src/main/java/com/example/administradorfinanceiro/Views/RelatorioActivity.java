package com.example.administradorfinanceiro.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.administradorfinanceiro.Controller.RelatorioController;
import com.example.administradorfinanceiro.Dao.RelatorioDao;
import com.example.administradorfinanceiro.Model.RelatorioAbastecimentoModel;
import com.example.administradorfinanceiro.Model.VeicoloModel;
import com.example.administradorfinanceiro.R;

import java.util.ArrayList;
import java.util.List;

public class RelatorioActivity extends AppCompatActivity {
    TableLayout tabela;
    TableRow linha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.test();

    }

    public void test() {
        
        <TableLayout android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:stretchColumns="*" android:background="#ff0000">
        <TableRow android:background="#00ff00" android:layout_margin="2dip">
            <Button android:id="@+id/button" android:text="+" android:background="#0000ff" android:layout_margin="2dip"/>
            <TextView android:text="@string/label"  android:background="#0000ff" android:layout_margin="2dip"/>
            <TextView android:id="@+id/amount"  android:background="#0000ff" android:layout_margin="2dip"/>

</TableRow>  
        GridLayout grid = new GridLayout(this);
        grid.setColumnCount(1);
        Context t = this;
        List<RelatorioAbastecimentoModel> ab = new ArrayList<RelatorioAbastecimentoModel>();
        RelatorioController r = new RelatorioController();
        ab = r.rela("20/06/2023", "20/06/2320", 0, this);
        for (int i = 0; i < ab.size(); i++) {
            TableRow linha = new TableRow(this);
            RelatorioAbastecimentoModel a = ab.get(i);
            TextView nome = new TextView(this);
            nome.setText(a.getVeicolo()+"  fvvdvdbb");
            linha.addView(nome);
            grid.addView(linha);
            nome.setText(a.getDate());
            linha = new TableRow(this);
           /*linha.addView(nome);
            grid.addView(linha);
            nome.setText(a.getValorLitro());
            linha = new TableRow(this);
            linha.addView(nome);
            grid.addView(linha);
            nome.setText(a.getLitrosTotal());
            linha = new TableRow(this);
            linha.addView(nome);
            grid.addView(linha);
            nome.setText(a.getValorTotal());
            linha = new TableRow(this);
            linha.addView(nome);
            grid.addView(linha);
            nome.setText(a.getMedia());
            linha = new TableRow(this);
            linha.addView(nome);
            grid.addView(linha);
            nome.setText(a.getKmPercorido());
            linha = new TableRow(this);
            linha.addView(nome);
            grid.addView(linha);
            nome.setText(a.getPosto());
            linha = new TableRow(this);
            linha.addView(nome);*/

           // grid.addView(linha);
            setContentView(grid);
            TableRow linha1 = new TableRow(this);

             nome = new TextView(this);
            nome.setText("Nome: "+i+"  n " + ab.size());
            linha1.addView(nome);

            grid.addView(linha1);
            setContentView(grid);
        }



int x=0;
        for (int k = 0; k < 15; k++) {

            TableRow linha1 = new TableRow(this);
            if (x == 0) {
                linha1.setBackgroundColor(Color.RED);
                x = 1;
            } else {
                linha1.setBackgroundColor(Color.BLUE);
                x = 0;
            }

            TextView nome = new TextView(this);
            nome.setText("Nome: " + ab.size());
            linha1.addView(nome);

            grid.addView(linha1);

        }
        setContentView(grid);
    }

    //Cria layout
     /*   TableLayout tabela = new TableLayout(this);
        tabela.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
//Expande coluna 1
        tabela. isStretchAllColumns();
        //tabela.set
    for (int o = 1; o <10; o++) {
//Linha 1
            TableRow linha1 = new TableRow(this);
            TextView nome = new TextView(this);
            nome.setText("Nome:");
        linha1.addView(nome);
        nome = new TextView(this);
        nome.setText("Nome:");
        linha1.addView(nome);

         nome = new TextView(this);
        nome.setText("Nome:");
        linha1.addView(nome);

         nome = new TextView(this);
        nome.setText("Nome:");
        linha1.addView(nome);

         nome = new TextView(this);
        nome.setText("Nome:");
            linha1.addView(nome);

            TextView nome2 = new TextView(this);
            nome2.setText("Nome2:");
            linha1.addView(nome2);
            EditText tnome = new EditText(this);
            tnome.requestFocus();
            linha1.addView(tnome);

//Linha 2
            TableRow linha2 = new TableRow(this);
            TextView senha = new TextView(this);
            senha.setText("Senha:");
            linha2.addView(senha);
            EditText tsenha = new EditText(this);
            tsenha.setTransformationMethod(new PasswordTransformationMethod());
            linha2.addView(tsenha);
//Linha3
            TableRow linha3 = new TableRow(this);
            linha3.setGravity(Gravity.RIGHT);
//Botão a direita
            Button ok = new Button(this);
            ok.setText("Login");
            linha3.addView(ok);
//Adiciona as linhas ao layout
            tabela.addView(linha1);
            tabela.addView(linha2);
            tabela.addView(linha3);
        }
//Informa o layout
        setContentView(tabela);
    }

    public void test1() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));

        for (int i = 1; i < 10; i++) {
            ;
            TextView titleView = new TextView(this);
            LinearLayout.LayoutParams lparams = new
                    LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            titleView.setLayoutParams(lparams);
            titleView.setText("Ola DevMedia      " + i);
            layout.addView(titleView);

            TextView titleView2 = new TextView(this);
            LinearLayout.LayoutParams lparams2 = new
                    LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            titleView2.setLayoutParams(lparams2);
            titleView2.setText("  Tudo bem?");
            layout.addView(titleView2);

        }
        setContentView(layout);
    }*/


}