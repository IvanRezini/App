package com.example.administradorfinanceiro.utilidades;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.administradorfinanceiro.Dao.ContasDao;
import com.example.administradorfinanceiro.Dao.PostoDao;
import com.example.administradorfinanceiro.Dao.VeicoloDao;
import com.example.administradorfinanceiro.Model.ContasModel;
import com.example.administradorfinanceiro.Model.PostoModel;
import com.example.administradorfinanceiro.Model.VeicoloModel;
import com.example.administradorfinanceiro.R;

import java.util.ArrayList;
import java.util.List;





public class SetarMenu {

    public static ArrayList spinnerVeicolo(Context context) {
        VeicoloDao v = new VeicoloDao(context);
        List<VeicoloModel> list = new ArrayList<VeicoloModel>();
        list = v.Lista();
        ArrayList<String> l = new ArrayList<String>();
        l.add("Selecione um Veicolo:");
        for (int i = 0; i < list.size(); i++) {
            VeicoloModel vm = new VeicoloModel();
            vm = list.get(i);
            l.add(" " + vm.getId() + " - " + vm.getName());
        }
        return l;
    }
    public static ArrayList spinnerPosto(Context context) {
        PostoDao v = new PostoDao(context);
        List<PostoModel> list = new ArrayList<>();
        list= v.Lista();
        ArrayList<String> l =new ArrayList<String>();
        ArrayAdapter<String> adapter;
        l.add("Selecione um posto:");
        for (int i=0; i<list.size();i++){
            PostoModel vm= new PostoModel();
            vm= list.get(i);
            l.add(" "+vm.getId()+" - "+vm.getNome());
        }
        return l;
    }
    public static ArrayList spinnerConta(Context context) {
        ContasDao v = new ContasDao(context);
        List<ContasModel> list = new ArrayList<>();
        list= v.Lista();
        ArrayList<String> l =new ArrayList<String>();
        ArrayAdapter<String> adapter;
        l.add("Selecione uma conta:");
        for (int i=0; i<list.size();i++){
            ContasModel vm= new ContasModel();
            vm= list.get(i);
            l.add(" "+vm.getId()+" - "+vm.getName());
        }
        return l;
    }
    }
