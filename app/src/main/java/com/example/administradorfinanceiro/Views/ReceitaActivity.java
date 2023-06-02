package com.example.administradorfinanceiro.Views;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.administradorfinanceiro.Dao.FinancasDao;
import com.example.administradorfinanceiro.Model.FinancasModel;
import com.example.administradorfinanceiro.R;
import com.example.administradorfinanceiro.Utilidades.ManipularData;

import java.text.DecimalFormat;
import java.util.Calendar;

public class ReceitaActivity extends AppCompatActivity {
    Button date;
    DatePickerDialog datePickerDialog;
    Button salvar;
    EditText valor;
    Spinner origem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_receita);

        date = (Button) findViewById(R.id.idDate);
        salvar = (Button) findViewById(R.id.idSalvar);
        valor = (EditText) findViewById(R.id.idKmRodado);
       origem = (Spinner) findViewById(R.id.spinnerOrigem);


        this.setarData();
    }

    public void DateClick(View v) {
        // calender class's instance and get current date , month and year from calende
        valor.clearFocus();
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(ReceitaActivity.this,
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

    public void setarData() {
        ManipularData m = new ManipularData();
        String d = m.obterData("dd/MM/yyyy");
        date.setText(d);

    }
    public void salvarClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReceitaActivity.this);
        DecimalFormat dF = new DecimalFormat("0.000");
       if(  origem.getSelectedItemPosition() > 0) {
           if (valor.getText().toString().trim().length() > 0) {
               valor.setText(dF.format((Float.valueOf(valor.getText().toString()).floatValue())) + "");
               builder.setTitle("Confirmar");
               builder.setMessage( "Data: " + date.getText() + "\nValor: " + valor.getText() +
                       "\nOrigem: "+ origem.getSelectedItem().toString())
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
       }else {
           makeText(this, "Selecione uma origem.", LENGTH_LONG).show();
       }
    }
    public void salvar() {
        FinancasModel f =new FinancasModel();
        f.setEntradaSaida("E");//  private String EntradaSaida;//E entrada S saida N para pagamento em dinheiro no qual ja foi sacado
        String x[] = origem.getSelectedItem().toString().trim().split(" ");
        f.setOrigem(Integer.valueOf(x[0]).intValue());//  private int Origem;//1 - Salario", "2 - Extra", "3 - Doação", "4 - Outro" Origem 5 significa um saque
        f.setIdConta(0);// 0 Significa que não é um deposito
        f.setDate(date.getText().toString());
        f.setValor(valor.getText().toString());
        FinancasDao d = new FinancasDao(this);
        try {
            d.Inserir(f);
            makeText(this, "Entrada salva", LENGTH_LONG).show();
            limparCampos();
        }catch (Exception e){
            makeText(this, "Falha ao salvar.\n"+e, LENGTH_LONG).show();
        }
    }
    public  void limparCampos(){
        ManipularData m = new ManipularData();
        String d = m.obterData("dd/MM/yyyy");
        date.setText(d);
        valor.setText("");
        origem.setSelection(0);
    }
}
///Site spiner
///https://jafapps.com.br/spinner-android-studio/