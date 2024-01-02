package com.example.administradorfinanceiro.Views;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administradorfinanceiro.Dao.ContasDao;
import com.example.administradorfinanceiro.Dao.PostoDao;
import com.example.administradorfinanceiro.Dao.VeicoloDao;
import com.example.administradorfinanceiro.Model.ContasModel;
import com.example.administradorfinanceiro.Model.PostoModel;
import com.example.administradorfinanceiro.Model.VeicoloModel;
import com.example.administradorfinanceiro.R;
import com.example.administradorfinanceiro.Utilidades.SetarMenu;

import java.util.ArrayList;

public class ConfigurcaoActivity extends AppCompatActivity {

    Button veicolo;
    Button posto;
    Button conta;
    Button excluir;
    Button salvar;
    Button cancelar;
    TextView campo1;
    EditText campo2;
    EditText campo3;
    Spinner selecao;
    int sele = 0; // auxiliar pra saber oque o spiner selcionou   1- veicolo  2- posto  3- contas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurcao);

        veicolo = (Button) findViewById(R.id.idVeicolo);
        posto = (Button) findViewById(R.id.idPosto);
        conta = (Button) findViewById(R.id.idConta);
        excluir = (Button) findViewById(R.id.idExcluir);
        cancelar = (Button) findViewById(R.id.idCancelar);
        salvar = (Button) findViewById(R.id.idSalvar);
        selecao = (Spinner) findViewById(R.id.spinnerSelecao);
        campo1 = (TextView) findViewById(R.id.campo1);
        campo2 = (EditText) findViewById(R.id.campo2);
        campo3 = (EditText) findViewById(R.id.campo3);
          /*
        Ouve o spiner Selecao
         */
        this.limparCampos();
        selecao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (selecao.getSelectedItemPosition() > 0) {
                    Selecao();
                    excluir.setVisibility(View.VISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void veicoloClick(View v) {

        this.limparCampos();
        sele = 1;
        campo2.setVisibility(View.VISIBLE);
        campo3.setVisibility(View.VISIBLE);
        selecao.setVisibility(View.VISIBLE);
        campo1.setText("Cadastre ou selecione");
        campo2.setHint("Novo veicolo");
        campo3.setHint("Hodometro");
        salvar.setVisibility(View.VISIBLE);
        SetarMenu set = new SetarMenu();
        ArrayList ve = set.spinnerVeicolo(this);
        ArrayAdapter<String> adapterV;
        adapterV = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ve);
        selecao.setAdapter(adapterV);
    }

    public void postoClick(View v) {
        this.limparCampos();
        sele = 2;
        campo2.setVisibility(View.VISIBLE);
        selecao.setVisibility(View.VISIBLE);
        campo1.setText("Cadastre ou selecione");
        campo2.setHint("Novo Posto");
        salvar.setVisibility(View.VISIBLE);
        SetarMenu set = new SetarMenu();
        ArrayList p = set.spinnerPosto(this);
        ArrayAdapter<String> adapterP;
        adapterP = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, p);
        selecao.setAdapter(adapterP);
    }

    public void contaClick(View v) {
        this.limparCampos();
        sele = 3;
        campo2.setVisibility(View.VISIBLE);
        selecao.setVisibility(View.VISIBLE);
        campo1.setText("Cadastre ou selecione");
        campo2.setHint("Nova conta");
        salvar.setVisibility(View.VISIBLE);
        SetarMenu set = new SetarMenu();
        ArrayList c = set.spinnerContaConfiguracao(this);
        ArrayAdapter<String> adapterP;
        adapterP = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, c);
        selecao.setAdapter(adapterP);
    }

    public boolean validar() {
        boolean res = false;
        if (campo2.getText().toString().trim().length() >= 6) {
            res = true;
            if (sele == 1) {
                res = false;
                if (campo3.getText().toString().trim().length() >= 6) {
                    res = true;
                } else {
                    makeText(this, "Hodometro deve conter 6 numeros:", LENGTH_LONG).show();
                }
            }
        } else {
            makeText(this, "Nome muito curto", LENGTH_LONG).show();
        }


        return res;
    }

    public void salvarClick(View v) {
        String[] x;
        if (validar()) {
            switch (sele) {
                case 1:
                    VeicoloModel ve = new VeicoloModel();
                    VeicoloDao d = new VeicoloDao(this);
                    if (selecao.getSelectedItemPosition() > 0) {
                        x = selecao.getSelectedItem().toString().trim().split(" ");
                        ve.setId(Integer.valueOf(x[0]).intValue());
                        ve.setName(campo2.getText().toString());
                        ve.setHodometro(campo3.getText().toString());
                        d.Update(ve);
                    } else {
                        ve.setName(campo2.getText().toString());
                        ve.setHodometro(campo3.getText().toString());
                        d.Inserir(ve);
                    }
                    this.limparCampos();
                    break;
                case 2:
                    PostoModel p = new PostoModel();
                    PostoDao pd = new PostoDao(this);

                    if (selecao.getSelectedItemPosition() > 0) {
                        x = selecao.getSelectedItem().toString().trim().split(" ");
                        p.setId(Integer.valueOf(x[0]).intValue());
                        p.setName(campo2.getText().toString());
                        pd.Update(p);
                    } else {
                        p.setName(campo2.getText().toString());
                        pd.Inserir(p);
                    }
                    this.limparCampos();
                    break;
                case 3:
                    ContasModel c = new ContasModel();
                    ContasDao cd = new ContasDao(this);

                    if (selecao.getSelectedItemPosition() > 0) {
                        x = selecao.getSelectedItem().toString().trim().split(" ");
                        c.setName(campo2.getText().toString());
                        c.setId(Integer.valueOf(x[0]).intValue());
                        cd.Update(c);
                    } else {
                        c.setName(campo2.getText().toString());
                        cd.Inserir(c);
                    }
                    this.limparCampos();
                    break;
            }

            makeText(this, "Entrada salva", LENGTH_LONG).show();
        }
    }

    public void cancelarClick(View v) {
        this.limparCampos();
    }

    public void excluirClick(View v) {
        this.limparCampos();
    }

    public void limparCampos() {
        campo1.setText("");
        campo2.setText("");
        campo3.setText("");
        campo1.setHint("");
        campo2.setHint("");
        campo3.setHint("");
        campo2.setVisibility(View.GONE);
        campo3.setVisibility(View.GONE);
        salvar.setVisibility(View.GONE);
        selecao.setVisibility(View.GONE);
        excluir.setVisibility(View.GONE);
        selecao.setSelection(0);
        sele = 0;
    }

    public void Selecao() {
        //int sele =0;  auxiliar pra saber oque o spiner selcionou   1- veicolo  2- posto  3- contas
        String[] x;
        switch (sele) {
            case 1:
                VeicoloModel v = new VeicoloModel();
                VeicoloDao d = new VeicoloDao(this);
                if (selecao.getSelectedItemPosition() > 0) {
                    x = selecao.getSelectedItem().toString().trim().split(" ");
                    v = d.getVeicolo(Integer.valueOf(x[0]).intValue());
                    campo1.setText(v.getId() + " - " + v.getName());
                    campo2.setText(v.getName());
                    campo3.setText(v.getHodometro());
                }
                break;
            case 2:
                PostoModel p = new PostoModel();
                PostoDao pd = new PostoDao(this);

                if (selecao.getSelectedItemPosition() > 0) {
                    x = selecao.getSelectedItem().toString().trim().split(" ");
                    p = pd.GetID(Integer.valueOf(x[0]).intValue());
                    campo1.setText(p.getId() + " - " + p.getNome());
                    campo2.setText(p.getNome());
                }
                break;
            case 3:
                ContasModel c = new ContasModel();
                ContasDao cd = new ContasDao(this);

                if (selecao.getSelectedItemPosition() > 0) {
                    x = selecao.getSelectedItem().toString().trim().split(" ");
                    c = cd.GetID(Integer.valueOf(x[0]).intValue());
                    campo1.setText(c.getId() + " - " + c.getName());
                    campo2.setText(c.getName());
                }
                break;
        }

    }
}




