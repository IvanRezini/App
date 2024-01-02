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

import com.example.administradorfinanceiro.Dao.ContasDao;
import com.example.administradorfinanceiro.Dao.FinancasDao;
import com.example.administradorfinanceiro.Model.ContasModel;
import com.example.administradorfinanceiro.Model.FinancasModel;
import com.example.administradorfinanceiro.R;
import com.example.administradorfinanceiro.Utilidades.ManipularData;
import com.example.administradorfinanceiro.Utilidades.SetarMenu;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ContasActivity extends AppCompatActivity {
    Button date;
    DatePickerDialog datePickerDialog;
    Button salvar;
    EditText valor;
    EditText novaConta;
    Spinner conta;
    RadioGroup formaPagamento;
    RadioButton pgDinheiro;
    RadioButton pgEletronico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contas);

        date = (Button) findViewById(R.id.idDate);
        salvar = (Button) findViewById(R.id.idSalvar);
        valor = (EditText) findViewById(R.id.idValor);
        conta = (Spinner) findViewById(R.id.spinnerConta);
        novaConta = (EditText) findViewById(R.id.idNovaConta);
        formaPagamento = (RadioGroup) findViewById(R.id.idFormaPaga);
        pgDinheiro = (RadioButton) findViewById(R.id.radioDi);
        pgEletronico = (RadioButton) findViewById(R.id.radioEle);

        this.setarMenus();

        conta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                LimparNovaConta();
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    /*
    limpar os campos dependendo se for selecionado ou criada uma nova conta
     */
    public void LimparSpiner(View v) {
        conta.setSelection(0);
    }

    public void LimparNovaConta() {
        novaConta.setText("");
    }

    /*
    Seta os menus iniciais ao abrir a tela
     */
    public void setarMenus() {
        ManipularData m = new ManipularData();
        String d = m.obterData("dd/MM/yyyy");
        date.setText(d);
        SetarMenu set = new SetarMenu();
        ArrayList c = set.spinnerConta(this);

        ArrayAdapter<String> adapterC;
        adapterC = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, c);
        conta.setAdapter(adapterC);
        valor.clearFocus();
        novaConta.clearFocus();
    }

    /*
    setar data
     */
    public void DateClick(View v) {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(ContasActivity.this,
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

    public void salvarClick(View v) {
        String aux = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(ContasActivity.this);
        DecimalFormat dF = new DecimalFormat("0.000");
        if (conta.getSelectedItemPosition() > 0 || novaConta.getText().length() > 2) {
            if (conta.getSelectedItemPosition() > 0) {
                aux = "Conta: " + conta.getSelectedItem().toString();
            } else {
                aux = "Nova Conta: " + novaConta.getText();
            }
            if (this.verificar() != "") {
                if (valor.getText().toString().trim().length() > 0) {
                    valor.setText(dF.format((Float.valueOf(valor.getText().toString()).floatValue())) + "");
                    builder.setTitle("Confirmar");
                    builder.setMessage("Data: " + date.getText() + "\nValor: " + valor.getText() +"   "+verificar()+
                            "\n" + aux)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    salvar();
                                }
                            })
                            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


        } else {
            makeText(this, "Informe um valor", LENGTH_LONG).show();
        }
    } else {
        makeText(this, "Selecione a forma de pagamento", LENGTH_LONG).show();
    }
        }  else {
        if (conta.getSelectedItemPosition() > 0 && novaConta.getText().length() < 2) {
            makeText(this, "Nome da nova conta muito curto", LENGTH_LONG).show();
        } else {
            makeText(this, "Informe uma conta ou selecione  uma.", LENGTH_LONG).show();
        }
    }

    }

    public void salvar() {
        FinancasModel f = new FinancasModel();
        String aux= verificar();
        if(aux =="Dinheiro"){
            f.setEntradaSaida("N");//  private String EntradaSaida;//E entrada S saida N para pagamento em dinheiro no qual ja foi sacado
        }else{
            f.setEntradaSaida("S");//  private String EntradaSaida;//E entrada S saida N para pagamento em dinheiro no qual ja foi sacado
        }
         String x[] = new String[4];

        if (conta.getSelectedItemPosition() == 0) {
            ContasModel nova = new ContasModel();

            nova.setName(novaConta.getText().toString());
            try {
                ContasDao contasDao = new ContasDao(this);
                contasDao.Inserir(nova);
                nova = new ContasModel();
                nova = contasDao.GetName();
                x[0] = nova.getId() + "";

            } catch (Exception ex) {
                    makeText(this, "Falha ao inserir a nova conta\n" + ex.toString(), LENGTH_LONG).show();
            }
        } else {
            x = conta.getSelectedItem().toString().trim().split(" ");
        }
        f.setOrigem(0);//1 - Salario", "2 - Extra", "3 - Doação", "4 - Outro" Origem 5 significa um saque, 0 é uma conta paga
        f.setIdConta(Integer.valueOf(x[0]).intValue());// Id 0 è uma entrada
f.setValor(valor.getText().toString());
        ManipularData m = new ManipularData();
        f.setDate(m.DataBanco( date.getText().toString()));
        FinancasDao d = new FinancasDao(this);
        try {
            String mg=d.Inserir(f);
 makeText(this, "Entrada salva", LENGTH_LONG).show();
            limparCampos();
        } catch (Exception e) {
            makeText(this, "Falha ao salvar.\n" + e, LENGTH_LONG).show();
        }
    }

    public void limparCampos() {
        ManipularData m = new ManipularData();
        String d = m.obterData("dd/MM/yyyy");
        date.setText(d);
        valor.setText("");
        conta.setSelection(0);
        novaConta.setText("");
        formaPagamento.clearCheck();
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