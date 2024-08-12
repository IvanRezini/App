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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.administradorfinanceiro.Controller.RelatorioController;
import com.example.administradorfinanceiro.Dao.RelatorioDao;
import com.example.administradorfinanceiro.Model.RelatorioAbastecimentoModel;
import com.example.administradorfinanceiro.Model.RelatorioFinancasModel;
import com.example.administradorfinanceiro.Model.VeicoloModel;
import com.example.administradorfinanceiro.R;
import com.example.administradorfinanceiro.Utilidades.ManipularData;
import com.example.administradorfinanceiro.Utilidades.SetarMenu;

import java.util.ArrayList;
import java.util.List;

public class RelatorioActivity extends AppCompatActivity {
    LinearLayout HS;
    private String dataInicio;
    private String dataFim;
    private int relatorio;
    private TextView titulo;
    private Button gerar;
    private Spinner selecao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        Bundle bundle = getIntent().getExtras();
        this.dataFim = bundle.getString("dataFim");
        this.dataInicio = bundle.getString("dataInicio");
        this.relatorio = bundle.getInt("relatorio");//1 = relatorio abastecimento   2= relatorio finanças
        this.titulo = (TextView) findViewById(R.id.textTitulo);
        this.gerar = (Button) findViewById(R.id.idGerar);
        this.selecao = (Spinner) findViewById(R.id.spinnerSelecao);
        HS = (LinearLayout) findViewById(R.id.layoutSc);

        this.caregarRelatorio();

    }

    public void caregarRelatorio() {
        this.converterData();
        //1 = relatorio abastecimento   2= relatorio finanças
        if (this.relatorio == 1) {
            this.titulo.setText("Relatoro Abastecimento");
            SetarMenu set = new SetarMenu();

            ArrayList s = set.spinnerVeicolo(this);

            ArrayAdapter<String> adapterS;
            adapterS = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, s);
            selecao.setAdapter(adapterS);
            // this.relatorioAbastecimento();
        } else if (this.relatorio == 2) {
            this.titulo.setText("Relatoro de Contas");
            SetarMenu set = new SetarMenu();

            ArrayList c = set.spinnerConta(this);

            ArrayAdapter<String> adapterC;
            adapterC = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, c);
            selecao.setAdapter(adapterC);
            //this.relatorioFinancia();
        }
    }

    public void gerarRelatorio(View v) {
        String aux[];
        int i = 0;
        if (selecao.getSelectedItemPosition() > 0) {
            aux = selecao.getSelectedItem().toString().trim().split(" ");
            i = (Integer.valueOf(aux[0]).intValue());

        }

        //1 = relatorio abastecimento   2= relatorio finanças
        if (this.relatorio == 1) {
            this.titulo.setVisibility(View.GONE);
            this.selecao.setVisibility(View.GONE);
            this.gerar.setVisibility(View.GONE);
            this.relatorioAbastecimento(i);
        } else if (this.relatorio == 2) {
            this.titulo.setVisibility(View.GONE);
            this.selecao.setVisibility(View.GONE);
            this.gerar.setVisibility(View.GONE);
            this.relatorioFinancia(i);
        }
    }


    public void converterData() {
        ManipularData m = new ManipularData();
        this.dataFim = m.DataBanco(this.dataFim);
        this.dataInicio = m.DataBanco(this.dataInicio);
    }

    public void relatorioAbastecimento(int vei) {
        List<RelatorioAbastecimentoModel> ab = new ArrayList<RelatorioAbastecimentoModel>();
        RelatorioAbastecimentoModel a = new RelatorioAbastecimentoModel();
        RelatorioController control = new RelatorioController();

        ab = control.relatorioAbastecimento(dataInicio, dataFim, vei, this);

        int aux = ab.size();
        int i;
        LinearLayout layout = new LinearLayout(this);
        TableLayout tabela = new TableLayout(this);
        tabela.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
        tabela.setGravity(Gravity.CENTER_HORIZONTAL);
        tabela.setPadding(5, 5, 5, 5);
        tabela.setHorizontalScrollBarEnabled(false);

        for ( i = 0; i < (aux-4); i++) {
            a = ab.get(i);
            TableRow linha = new TableRow(this);
            linha.setBackgroundColor(a.getCor());
            ManipularData m = new ManipularData();

            TextView tex;
            tex = this.coluna();
            tex.setText(a.getVeicolo());
            linha.addView(tex);
            tex = this.coluna();
            tex.setText(a.getDate());
            linha.addView(tex);
            tex = this.coluna();
            tex.setWidth(150);
            tex.setText(a.getValorLitro());
            linha.addView(tex);
            tex = this.coluna();
            tex.setWidth(150);
            tex.setText(a.getLitrosTotal());
            linha.addView(tex);
            tex = this.coluna();
            tex.setWidth(150);
            tex.setText(a.getValorTotal());
            linha.addView(tex);
            tex = this.coluna();
            tex.setWidth(150);
            tex.setText(a.getMedia());
            linha.addView(tex);
            tex = this.coluna();
            tex.setWidth(200);
            tex.setText(a.getKmPercorido());
            linha.addView(tex);
            tex = this.coluna();
            tex.setWidth(290);
            tex.setText(a.getPosto());
            linha.addView(tex);
            tex = this.coluna();
            tex.setWidth(290);
            tex.setText(a.getObs());
            linha.addView(tex);
            tabela.addView(linha);
        }

        for ( i =aux-4 ;i < aux; i++) {
            a = ab.get(i);
            TableRow linha = new TableRow(this);
            linha.setBackgroundColor(a.getCor());
            ManipularData m = new ManipularData();

            TextView tex;
            tex = this.coluna();
            tex.setWidth(200);
            tex.setText(a.getDate());
            linha.addView(tex);
            tex = this.coluna();
            tex.setWidth(250);
            tex.setText(a.getValorLitro());
            linha.addView(tex);

            tabela.addView(linha);
        }

        layout.addView(tabela);
        HS.addView(layout);

    }

    public void relatorioFinancia(int con) {

        List<RelatorioFinancasModel> ab = new ArrayList<RelatorioFinancasModel>();
        RelatorioFinancasModel a = new RelatorioFinancasModel();

        RelatorioController control = new RelatorioController();
        ab = control.relatorioFinacas(dataInicio, dataFim, con, this);

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
            tex.setTextColor(a.getCorTexto());
            tex.setWidth(80);
            tex.setText(a.getEntradaSaida()+"");
            linha.addView(tex);
            tex = this.coluna();
            tex.setWidth(250);
            tex.setTextColor(a.getCorTexto());
            tex.setText(a.getData());
            linha.addView(tex);
            tex = this.coluna();
            tex.setWidth(250);
            tex.setTextColor(a.getCorTexto());
            tex.setText(a.getValor());
            linha.addView(tex);
            tex = this.coluna();
            tex.setWidth(300);
            tex.setTextColor(a.getCorTexto());
            tex.setText(a.getConta());
            linha.addView(tex);
            tex = this.coluna();
            tex.setWidth(290);
            tex.setText(a.getObs());
            linha.addView(tex);
            tabela.addView(linha);
        }


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
        coluna.setWidth(210);//importante

        return coluna;
    }
}