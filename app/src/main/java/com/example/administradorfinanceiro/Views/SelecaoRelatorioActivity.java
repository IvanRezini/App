package com.example.administradorfinanceiro.Views;

        import static android.widget.Toast.makeText;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.app.DatePickerDialog;
        import android.view.View;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.TextView;

        import com.example.administradorfinanceiro.R;
        import com.example.administradorfinanceiro.Utilidades.ManipularData;

        import java.util.Calendar;

public class SelecaoRelatorioActivity extends AppCompatActivity {
    Button dateInicio;
    Button dateFim;
    DatePickerDialog datePickerDialog;
    Button gerarFinanceiro;
    Button gerarAbastecimento;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_selecao_relatorio);

        dateInicio = (Button) findViewById(R.id.idDateInicio);
        dateFim = (Button) findViewById(R.id.idDateFim);
        gerarFinanceiro = (Button) findViewById(R.id.idGerarFinanceiro);
        gerarAbastecimento = (Button) findViewById(R.id.idGerarAbastecimento);
        titulo = (TextView) findViewById(R.id.idTextTitulo);

        this.setarData();
    }

    public void DateClickInicio(View v) {
        // calender class's instance and get current date , month and year from calende
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(SelecaoRelatorioActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        dateInicio.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void DateClickFim(View v) {
        // calender class's instance and get current date , month and year from calende
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(SelecaoRelatorioActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        dateFim.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void setarData() {
        ManipularData m = new ManipularData();
        String d;
        d = m.obterData("dd/MM/yyyy");
        dateFim.setText(d);
        d = m.obterDataMenos("dd/MM/yyyy");
        dateInicio.setText(d);
    }
    public void relatorioAbastecimento(View v) {
        String dataInicio = dateInicio.getText().toString();
        String dataFim = dateFim.getText().toString();
        int relatorio = 1;//relatorio abastecimento
        Bundle bundle = new Bundle();
        bundle.putString("dataInicio", dataInicio);
        bundle.putString("dataFim",dataFim);
        bundle.putInt("relatorio",relatorio);
        Intent in = new Intent(SelecaoRelatorioActivity.this, RelatorioActivity.class);
        in.putExtras(bundle);
        startActivity(in);
    }
    public void relatorioFinancia(View v) {
        String dataInicio = dateInicio.getText().toString();
        String dataFim = dateFim.getText().toString();
        int relatorio = 2; //relatorio finanças
        Bundle bundle = new Bundle();
        bundle.putString("dataInicio", dataInicio);
        bundle.putString("dataFim",dataFim);
        bundle.putInt("relatorio",relatorio);
        Intent in = new Intent(SelecaoRelatorioActivity.this, RelatorioActivity.class);
        in.putExtras(bundle);
        startActivity(in);
    }
}
///https://www.devmedia.com.br/navegacao-entre-telas-no-android/38781