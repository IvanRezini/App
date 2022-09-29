package com.example.administradorfinanceiro.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import com.example.administradorfinanceiro.Dao.AbastecimentoDao;
import com.example.administradorfinanceiro.Dao.PostoDao;
import com.example.administradorfinanceiro.Dao.VeicoloDao;
import com.example.administradorfinanceiro.Model.AbastecimentoModel;
import com.example.administradorfinanceiro.Model.PostoModel;
import com.example.administradorfinanceiro.Model.VeicoloModel;
import com.example.administradorfinanceiro.R;
import com.example.administradorfinanceiro.utilidades.ManipularData;
import com.example.administradorfinanceiro.utilidades.SetarMenu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        // Spinner click listener
            AdapterView.OnItemSelectedListener vv = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
String item =veicolo.getSelectedItem().toString();
                    Toast.makeText(AbastecimentoActivity.this, "selecionou "+item, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };
            veicolo.setOnItemSelectedListener(vv);
        this.setarMenus();

    }

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

    public void HodometroVeicolo() {
        VeicoloModel v = new VeicoloModel();
        VeicoloDao d = new VeicoloDao(this);
        String[] x;
        if (veicolo.getSelectedItemPosition() > 1) {
            x = veicolo.getSelectedItem().toString().trim().split(" ");
            d.getVeicolo(Integer.valueOf(x[0]).intValue());
        }
    }

    public void salvarClick(View v) {

        hodometro.setText("0000");
        String resposta = confirmar();
        if (resposta != "") {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("confirmar");
            builder.setMessage(resposta)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // START THE GAME!
                            verificar();

                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

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

    public String confirmar() {
        String resposta = "";
        this.preecherCampos();
        float n;
        boolean result = false;
        if (veicolo.getSelectedItemPosition() > 0) {
            result = litroTotal.getText().toString().matches("[+-]?\\d*(\\.\\d+)?");
            if (result) {
                result = valorTotal.getText().toString().matches("[+-]?\\d*(\\.\\d+)?");
                if (result) {
                    result = valorLitro.getText().toString().matches("[+-]?\\d*(\\.\\d+)?");
                    if (result) {
                        if (this.verificar() != "") {
                            if (posto.getSelectedItemPosition() > 0 || novoPosto.getText().length() > 10) {
                                result = kmPercorido.getText().toString().matches("[+-]?\\d*(\\.\\d+)?");
                                if (result) {
                                    resposta = "Data: " + date.getText() + "\nTotal: " + valorTotal.getText() + "\nlitros de combustivel: " + litroTotal.getText();
                                    resposta += "\nValor do litro: " + valorLitro.getText() + "\nPagamento: " + verificar() + "\nVeicolo: " + veicolo.getSelectedItem().toString();
                                    resposta += "\nHodometro: " + ((Float.valueOf(String.valueOf(kmPercorido.getText())).floatValue()) + (Float.valueOf((String.valueOf((hodometro.getText()))))));
                                    resposta += "\nKm percorido: " + kmPercorido.getText();
                                    resposta += "\nMedia: " + ((Float.valueOf(String.valueOf(kmPercorido.getText())).floatValue()) / (Float.valueOf((String.valueOf((litroTotal.getText()))))));
                                    return resposta;
                                } else {
                                    Toast.makeText(this, "Informe o km percoridos", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                if (posto.getSelectedItemPosition() > 0 && novoPosto.getText().length() < 10) {
                                    Toast.makeText(this, "Nome do posto muito curto", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(this, "Informe um posto ou selecione  um.", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Toast.makeText(this, "Selecione a forma de pagamento", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "Informe valor do litro", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Informe o total", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Informe quantia de litros de combustivel", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Selecione um veicolo", Toast.LENGTH_LONG).show();
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

    public void formatarCampos() {
        DecimalFormat dF = new DecimalFormat("#.000");
        valorLitro.setText(dF.format((Float.valueOf(valorLitro.getText().toString()).floatValue())) + "");
        valorTotal.setText(dF.format((Float.valueOf(valorTotal.getText().toString()).floatValue())) + "");
        litroTotal.setText(dF.format((Float.valueOf(litroTotal.getText().toString()).floatValue())) + "");
    }

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
        x = veicolo.getSelectedItem().toString().trim().split(" ");
        ab.setVeicolo(Integer.valueOf(x[0]).intValue());
        x = null;
        if (posto.getSelectedItemPosition() <= 0) {
            PostoModel novo = new PostoModel();
            novo.setName(novoPosto.getText().toString());
            try {
                PostoDao postoDao = new PostoDao(this);////ira recuperar o id do posto cadastrado
                postoDao.Inserir(novo);
                novo = postoDao.GetName(novo.getNome());
                x[0] = novo.getId() + "";
            } catch (Exception ex) {
                Toast.makeText(this, "Falha ao inserir o novo posto", Toast.LENGTH_LONG).show();
            }
        } else {
            x = posto.getSelectedItem().toString().trim().split("-");
        }
        ab.setPosto(Integer.valueOf(x[0]).intValue());
        ab.setDate(date.getText().toString());
        ab.setKmPercorido(kmPercorido.getText().toString());
        ab.setValorLitro(valorLitro.getText().toString());
        ab.setLitrosTotal(litroTotal.getText().toString());
        AbastecimentoDao Ai = new AbastecimentoDao(this);
        try {
            Ai.Inserir(ab);
        } catch (Exception e) {

            Toast.makeText(this, "Falha ao inserir o abastecimento", Toast.LENGTH_LONG).show();
        }
        this.atualizaHodometro();

    }

    public void atualizaHodometro() {
        VeicoloModel v = new VeicoloModel();
        VeicoloDao d = new VeicoloDao(this);
        String[] x;
        x = veicolo.getSelectedItem().toString().trim().split(" ");
        v.setId(Integer.valueOf(x[0]).intValue());
        v.setHodometro((Float.valueOf(String.valueOf(kmPercorido.getText())).floatValue()) + (Float.valueOf((String.valueOf((hodometro.getText()))))) + "");
        String res = d.Update(v);
        if (res.length() > 1) {
            Toast.makeText(this, res, Toast.LENGTH_LONG).show();
        }
    }

    public void cancelar() {
        ManipularData m = new ManipularData();
        String d = m.obterData("dd/MM/yyyy");
        date.setText(d);
        DatePickerDialog datePickerDialog;
        valor.setText("");
        litroTotal.setText("");
        valorLitro.setText("");
        valorTotal.setText("");
        novoPosto.setText("");
        Spinner veicolo;
        pgEletronico.setSelected(false);
        pgDinheiro.setSelected(false);
        hodometro.setText("");
    }

}
///Site spiner
///https://jafapps.com.br/spinner-android-studio/