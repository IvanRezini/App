package com.example.administradorfinanceiro.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.example.administradorfinanceiro.Dao.PostoDao;
import com.example.administradorfinanceiro.Model.PostoModel;
import com.example.administradorfinanceiro.R;

import java.util.ArrayList;
import java.util.List;

public class SaqueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saque);
        this.setarEspiner();
    }
    public void setarEspiner() {
        Log.e("\n\n\n\nLista:  "," Entrou aqui");

        PostoDao v = new PostoDao(this);

        List<PostoModel> list = new ArrayList<PostoModel>();
        list= v.Lista();
        ArrayList<String> l= new ArrayList<>() ;
        String rt;
        l.add("Selecione um veicolo");
        rt = list.size()+" tamanho\n";
        for (int i=0; i<list.size();i++){
            PostoModel vm= new PostoModel();
            vm= list.get(i);
           rt+= "\n "+vm.getId()+" - "+vm.getNome();
            l.add(" "+vm.getId()+" - "+vm.getNome());
            Log.e("\n\nLista:  "," "+vm.getId()+" - "+vm.getNome());
        }
        Log.e("\n\nLista:  ",l.get(0));
        AlertDialog.Builder builder = new AlertDialog.Builder(SaqueActivity.this);

        builder.setTitle("confirmar");
        builder.setMessage("Agorar "+rt)
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

        // veicolo.setAdapter((SpinnerAdapter) l);


    }
}