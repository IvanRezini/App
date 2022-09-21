package com.example.administradorfinanceiro.Views;

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

import com.example.administradorfinanceiro.R;
import com.example.administradorfinanceiro.utilidades.ManipularData;

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
        // calender class's instance and get current date , month and year from calender
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

        builder.setTitle("confirmar");
        builder.setMessage("Agora foi essa pora")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // START THE GAME!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
///Site spiner
///https://jafapps.com.br/spinner-android-studio/