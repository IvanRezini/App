package com.example.administradorfinanceiro.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
    LinearLayout HS;
    private String dataInicio;
    private String dataFim;
    private int relatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
            Bundle bundle = getIntent().getExtras();
            this.dataFim=bundle.getString("dataFim");
            this.dataInicio=bundle.getString("dataInicio");
            this.relatorio=bundle.getInt("relatorio");
        HS = (LinearLayout) findViewById(R.id.layoutSc);
        relatorioAbastecimento();
    }

    public void relatorioAbastecimento() {

        List<RelatorioAbastecimentoModel> ab = new ArrayList<RelatorioAbastecimentoModel>();
        RelatorioAbastecimentoModel a = new RelatorioAbastecimentoModel();
        RelatorioController control = new RelatorioController();
        ab = control.rela("0000/00/00", "2023/12/23", 0, this);

        int aux = ab.size();

        LinearLayout layout = new LinearLayout(this);
        TableLayout tabela = new TableLayout(this);
        tabela.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
        tabela.setGravity(Gravity.CENTER_HORIZONTAL);
        tabela.setPadding(5, 5, 5, 5);
        tabela.setHorizontalScrollBarEnabled(false);

        for (int i = 0; i < aux; i++) {
            a = ab.get(i);
            TableRow linha = new TableRow(this);
            linha.setBackgroundColor(a.getCor());

            TextView tex;
            tex = this.coluna();
            tex.setText(a.getVeicolo());
            linha.addView(tex);
            tex = this.coluna();
            tex.setText(a.getDate());
            linha.addView(tex);
            tex = this.coluna();
            tex.setText(a.getValorLitro());
            linha.addView(tex);
            tex = this.coluna();
            tex.setText(a.getLitrosTotal());
            linha.addView(tex);
            tex = this.coluna();
            tex.setText(a.getValorTotal());
            linha.addView(tex);
            tex = this.coluna();
            tex.setText(a.getMedia());
            linha.addView(tex);
            tex = this.coluna();
            tex.setText(a.getKmPercorido());
            linha.addView(tex);
            tex = this.coluna();
            tex.setText(a.getPosto());
            linha.addView(tex);
            tabela.addView(linha);
        }
        TableRow linha = new TableRow(this);
        TextView ttex = this.coluna();
        ttex.setText(this.dataFim);
        linha.addView(ttex);

        ttex = this.coluna();
        ttex.setText(this.dataInicio);
        linha.addView(ttex);

        ttex = this.coluna();
        ttex.setText(this.relatorio+"    relatorio");
        linha.addView(ttex);
        tabela.addView(linha);





        layout.addView(tabela);
        HS.addView(layout);

    }

    private TextView coluna() {
        TextView coluna = new TextView(this);
        coluna.setTextColor(Color.BLACK);
        coluna.setTypeface(null, Typeface.BOLD_ITALIC);
        coluna.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        coluna.setGravity(Gravity.CENTER_HORIZONTAL);
        coluna.setPadding(5, 5, 5, 5);
        coluna.setHorizontalScrollBarEnabled(false);
        coluna.setWidth(150);//importante
        return coluna;
    }
}