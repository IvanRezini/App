package com.example.administradorfinanceiro.Views;



import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administradorfinanceiro.Dao.AbastecimentoDao;
import com.example.administradorfinanceiro.Dao.FinancasDao;
import com.example.administradorfinanceiro.Dao.PostoDao;
import com.example.administradorfinanceiro.Dao.VeicoloDao;
import com.example.administradorfinanceiro.Model.AbastecimentoModel;
import com.example.administradorfinanceiro.Model.FinancasModel;
import com.example.administradorfinanceiro.Model.PostoModel;
import com.example.administradorfinanceiro.Model.VeicoloModel;
import com.example.administradorfinanceiro.R;
import com.example.administradorfinanceiro.Utilidades.ManipularData;
import com.example.administradorfinanceiro.Utilidades.SetarMenu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AbastecimentoActivity extends AppCompatActivity {

    Button date;
    DatePickerDialog datePickerDialog;
    Button salvar;
    Button cancelar;
    EditText valor;
    EditText litroTotal;
    EditText valorLitro;
    EditText valorTotal;
    EditText novoPosto;
    EditText kmPercorido;
    Spinner posto;
    Spinner veicolo;
    RadioGroup radio;
    TextView hodometro;
    RadioButton pgDinheiro;
    RadioButton pgEletronico;
    RadioGroup formaPagamento;
    TextView label1;
    TextView label2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abastecimento);

        date = (Button) findViewById(R.id.idDate);
        cancelar = (Button) findViewById(R.id.idCancelar);
        salvar = (Button) findViewById(R.id.idSalvar);
        valor = (EditText) findViewById(R.id.idKmRodado);
        posto = (Spinner) findViewById(R.id.spinnerPosto);
        veicolo = (Spinner) findViewById(R.id.spinnerVeicolo);
        novoPosto = (EditText) findViewById(R.id.idNovoPosto);
        litroTotal = (EditText) findViewById(R.id.idTotalLitros);
        valorLitro = (EditText) findViewById(R.id.idValorLitro);
        valorTotal = (EditText) findViewById(R.id.idValorTotal);
        kmPercorido = (EditText) findViewById(R.id.idKmRodado);
        radio = (RadioGroup) findViewById(R.id.idFormaPagamento);
        hodometro = (TextView) findViewById(R.id.LabelHodometro);
        pgDinheiro = (RadioButton) findViewById(R.id.radioDinheiro);
        pgEletronico = (RadioButton) findViewById(R.id.radioEletronico);
        formaPagamento = (RadioGroup) findViewById(R.id.idFormaPagamento);
        label1=(TextView) findViewById(R.id.label1);
        label2=(TextView) findViewById(R.id.label2);
      this.limparCampos();
        /*
        Ouve o spiner Vicolo
         */
        veicolo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                HodometroVeicolo();
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        /*
        Ouve o Spiner posto
         */
        posto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                novoPosto.setText("");
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
/*
Chama o evento para setar os menus
 */
        this.setarMenus();

    }
    /*
    limpar o spiner posto
     */
    public void LimparSpiner(View v) {
        posto.setSelection(0);
    }
/*
Selecionador de datas
 */
    public void DateClick(View v) {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(AbastecimentoActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        date.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
/*
Seta os menus iniciais ao abrir a tela
 */
    public void setarMenus() {
        ManipularData m = new ManipularData();
        String d = m.obterData("dd/MM/yyyy");
        date.setText(d);
        SetarMenu set = new SetarMenu();

        ArrayList v = set.spinnerVeicolo(this);
        ArrayList p = set.spinnerPosto(this);

        ArrayAdapter<String> adapterV;
        adapterV = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, v);
        ArrayAdapter<String> adapterP;
        adapterP = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, p);
        posto.setAdapter(adapterP);
        veicolo.setAdapter(adapterV);
    }
/*
Pega o veicolo selecionado e busca o seu ultimo hodometro salvo
 */
    public void HodometroVeicolo() {
        VeicoloModel v = new VeicoloModel();
        VeicoloDao d = new VeicoloDao(this);
        String[] x;
        if (veicolo.getSelectedItemPosition() > 0) {
            this.mostrarCampos();
            x = veicolo.getSelectedItem().toString().trim().split(" ");
            v = d.getVeicolo(Integer.valueOf(x[0]).intValue());
            hodometro.setText(v.getHodometro());
        }
    }
/*
Ouve o botão salvar
 */
    public void salvarClick(View v) {
        this.HodometroVeicolo();
        String resposta = confirmar();
        if (resposta != "") {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Confirmar");
            builder.setMessage(resposta)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // START THE GAME!
                            salvar();

                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            limparCampos();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        /*private int Id ;
        private int Veicolo;
        private int Posto ;
        private String LitrosTotal ;
        private String ValorLitro ;
        private String Date ;
        private String kmPercorido ;
        private String Hodometro ;*/
        }
    }
/*
verifica forma de pagamento
 */
    private String verificar() {
        String resposta = "";
        if (pgDinheiro.isChecked()) {
            resposta = "Dinheiro";
        } else if (pgEletronico.isChecked()) {
            resposta = "Eletronico";
        }
        ;
        return resposta;
    }
/*
Verifica se todos os campos estão preechidos
 */
    public String confirmar() {
        String resposta = "";
        this.preecherCampos();
        float n;
        boolean result = false;
        if (veicolo.getSelectedItemPosition() > 0) {
            if (litroTotal.getText().toString().trim().length() > 0) {
                if (valorTotal.getText().toString().trim().length() > 0) {
                    if (valorLitro.getText().toString().trim().length() > 0) {
                        if (this.verificar() != "") {
                            if (posto.getSelectedItemPosition() > 0 || novoPosto.getText().length() > 10) {
                                if (kmPercorido.getText().toString().trim().length() > 0) {
                                    resposta = "Data: " + date.getText() + "\nTotal: " + valorTotal.getText() + "\nlitros de combustivel: " + litroTotal.getText();
                                    resposta += "\nValor do litro: " + valorLitro.getText() + "\nPagamento: " + verificar() + "\nVeicolo: " + veicolo.getSelectedItem().toString();
                                    resposta += "\nHodometro: " + ((Float.valueOf(String.valueOf(kmPercorido.getText())).floatValue()) + (Float.valueOf((String.valueOf((hodometro.getText()))))));
                                    resposta += "\nKm percorido: " + kmPercorido.getText();
                                    resposta += "\nMedia: " + ((Float.valueOf(String.valueOf(kmPercorido.getText())).floatValue()) / (Float.valueOf((String.valueOf((litroTotal.getText()))))));
                                    return resposta;
                                } else {
                                    makeText(this, "Informe o km percoridos", LENGTH_LONG).show();
                                }
                            } else {
                                if (posto.getSelectedItemPosition() > 0 && novoPosto.getText().length() < 10) {
                                    makeText(this, "Nome do posto muito curto", LENGTH_LONG).show();
                                } else {
                                    makeText(this, "Informe um posto ou selecione  um.", LENGTH_LONG).show();
                                }
                            }
                        } else {
                            makeText(this, "Selecione a forma de pagamento", LENGTH_LONG).show();
                        }
                    } else {
                        makeText(this, "Informe valor do litro", LENGTH_LONG).show();
                    }
                } else {
                    makeText(this, "Informe o total", LENGTH_LONG).show();
                }
            } else {
                makeText(this, "Informe quantia de litros de combustivel", LENGTH_LONG).show();
            }
        } else {
            makeText(this, "Selecione um veicolo", LENGTH_LONG).show();
        }

        return resposta;
    }

    /*
    Preeche os campos em brancos apartir de dois ja preechidos
     */
    private void preecherCampos() {

        if ((litroTotal.getText().toString().trim().length() > 0) && (litroTotal.getText().toString().matches("[+-]?\\d*(\\.\\d+)?"))) {
            if ((valorTotal.getText().toString().trim().length() > 0) && (valorTotal.getText().toString().matches("[+-]?\\d*(\\.\\d+)?"))) {
                valorLitro.setText((Float.valueOf(valorTotal.getText().toString()).floatValue()) / (Float.valueOf(litroTotal.getText().toString()).floatValue()) + "");
                this.formatarCampos();
            } else if ((valorLitro.getText().toString().trim().length() > 0) && (valorLitro.getText().toString().matches("[+-]?\\d*(\\.\\d+)?"))) {
                valorTotal.setText((Float.valueOf(litroTotal.getText().toString()).floatValue()) * (Float.valueOf(valorLitro.getText().toString()).floatValue()) + "");
                this.formatarCampos();
            }
        } else if ((valorTotal.getText().toString().trim().length() > 0) && (valorTotal.getText().toString().matches("[+-]?\\d*(\\.\\d+)?"))) {
            if ((valorLitro.getText().toString().trim().length() > 0) && (valorLitro.getText().toString().matches("[+-]?\\d*(\\.\\d+)?"))) {
                litroTotal.setText((Float.valueOf(valorTotal.getText().toString()).floatValue()) / (Float.valueOf(valorLitro.getText().toString()).floatValue()) + "");
                this.formatarCampos();
            } else if ((litroTotal.getText().toString().trim().length() > 0) && (litroTotal.getText().toString().matches("[+-]?\\d*(\\.\\d+)?"))) {
                valorLitro.setText((Float.valueOf(valorTotal.getText().toString()).floatValue()) / (Float.valueOf(litroTotal.getText().toString()).floatValue()) + "");
                this.formatarCampos();
            }
        } else if ((valorLitro.getText().toString().trim().length() > 0) && (litroTotal.getText().toString().matches("[+-]?\\d*(\\.\\d+)?"))) {
            if ((valorTotal.getText().toString().trim().length() > 0) && (valorTotal.getText().toString().matches("[+-]?\\d*(\\.\\d+)?"))) {
                litroTotal.setText((Float.valueOf(valorTotal.getText().toString()).floatValue()) * (Float.valueOf(valorLitro.getText().toString()).floatValue()) + "");
                this.formatarCampos();
            } else if ((litroTotal.getText().toString().trim().length() > 0) && (litroTotal.getText().toString().matches("[+-]?\\d*(\\.\\d+)?"))) {
                valorTotal.setText((Float.valueOf(litroTotal.getText().toString()).floatValue()) * (Float.valueOf(valorLitro.getText().toString()).floatValue()) + "");
                this.formatarCampos();
            }
        }
    }
    /*
    Formata os campos com tres casas apos a virgula
     */
    public void formatarCampos() {
        DecimalFormat dF = new DecimalFormat("0.000");
        valorLitro.setText(dF.format((Float.valueOf(valorLitro.getText().toString()).floatValue())) + "");
        valorTotal.setText(dF.format((Float.valueOf(valorTotal.getText().toString()).floatValue())) + "");
        litroTotal.setText(dF.format((Float.valueOf(litroTotal.getText().toString()).floatValue())) + "");
    }
/*
Salva o abastecimento no banco de dados
 */
    public void salvar() {
        /*private int Id ;
        private int Veicolo;
        private int Posto ;
        private String LitrosTotal ;
        private String ValorLitro ;
        private String Date ;
        private String kmPercorido ;
        private String Hodometro ;*/
        AbastecimentoModel ab = new AbastecimentoModel();
        String[] x;
        int p;
        x = veicolo.getSelectedItem().toString().trim().split(" ");
        ab.setVeicolo(Integer.valueOf(x[0]).intValue());

        if (posto.getSelectedItemPosition() <= 0) {
            PostoModel novo = new PostoModel();
            novo.setName(novoPosto.getText().toString());
            try {
                PostoDao postoDao = new PostoDao(this);////ira recuperar o id do posto cadastrado
                postoDao.Inserir(novo);
                novo = new PostoModel();
                novo = postoDao.GetName();
                x[0]= novo.getId()+"";

            } catch (Exception ex) {
                makeText(this, "Falha ao inserir o novo posto\n"+ex.toString(), LENGTH_LONG).show();
            }
        } else {
            x= posto.getSelectedItem().toString().trim().split(" ");
        }
       ab.setPosto(Integer.valueOf(x[0]).intValue());
        ab.setDate(date.getText().toString());
        ab.setKmPercorido(kmPercorido.getText().toString());
        ab.setValorLitro(valorLitro.getText().toString());
        ab.setLitrosTotal(litroTotal.getText().toString());
        AbastecimentoDao Ai = new AbastecimentoDao(this);

        FinancasModel f =new FinancasModel();
        f.setEntradaSaida("S");//  private String EntradaSaida;//E entrada S saida N para pagamento em dinheiro no qual ja foi sacado
        f.setIdConta(1);// 1 Significa que não é um abastecimento
        f.setDate(date.getText().toString());
        f.setValor(valorTotal.getText().toString());
        FinancasDao d = new FinancasDao(this);

        try {
            Ai.Inserir(ab);
            d.Inserir(f);
            String dd=x[0];
            this.atualizaHodometro();
        } catch (Exception e) {
            this.limparCampos();
            makeText(this, "Falha ao inserir o abastecimento", LENGTH_LONG).show();
        }
    }
    /*
    Atualiza o hodometro no banco de dados
     */
    public void atualizaHodometro() {
        VeicoloModel v = new VeicoloModel();
        VeicoloDao d = new VeicoloDao(this);
        String[] x;
        try {
            x = veicolo.getSelectedItem().toString().trim().split(" ");
            v.setId(Integer.valueOf(x[0]).intValue());
            v.setName(x[2]);
            v.setHodometro((Float.valueOf(String.valueOf(kmPercorido.getText())).floatValue()) + (Float.valueOf((String.valueOf((hodometro.getText()))))) + "");
            String res = d.Update(v);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String resposta = "Abastecimento salvo com sucesso";
            builder.setTitle("Confirmar");
            builder.setMessage(resposta)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            limparCampos();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            if (res.length() > 1) {
                makeText(this, res, LENGTH_LONG).show();
            }
        } catch (Exception e) {
            makeText(this, e.toString(), LENGTH_LONG).show();
        }
    }
/*
limpa os capos
 */
    public void limparCampos() {
        ManipularData m = new ManipularData();
        String d = m.obterData("dd/MM/yyyy");
        date.setText(d);
        valor.setText("");
        litroTotal.setText("");
        valorLitro.setText("");
        valorTotal.setText("");
        novoPosto.setText("");
        veicolo.setSelection(0);
        posto.setSelection(0);
        formaPagamento.clearCheck();
        hodometro.setText("");
        date.setVisibility(View.GONE);
        valor.setVisibility(View.GONE);
        litroTotal.setVisibility(View.GONE);
        valorLitro.setVisibility(View.GONE);
        valorTotal.setVisibility(View.GONE);
        novoPosto.setVisibility(View.GONE);
        posto.setVisibility(View.GONE);
        radio.setVisibility(View.GONE);
        hodometro.setVisibility(View.GONE);
        salvar.setVisibility(View.GONE);
        label1.setVisibility(View.GONE);
        label2.setVisibility(View.GONE);

    }
    public void mostrarCampos()
    {
        date.setVisibility(View.VISIBLE);
        valor.setVisibility(View.VISIBLE);
        litroTotal.setVisibility(View.VISIBLE);
        valorLitro.setVisibility(View.VISIBLE);
        valorTotal.setVisibility(View.VISIBLE);
        novoPosto.setVisibility(View.VISIBLE);
        posto.setVisibility(View.VISIBLE);
        radio.setVisibility(View.VISIBLE);
        hodometro.setVisibility(View.VISIBLE);
        salvar.setVisibility(View.VISIBLE);
        label1.setVisibility(View.VISIBLE);
        label2.setVisibility(View.VISIBLE);
    }

/*
ouve o botao cancelar
 */
    public void cancelar(View v) {
        this.limparCampos();
    }

}
///Site spiner
///https://jafapps.com.br/spinner-android-studio/