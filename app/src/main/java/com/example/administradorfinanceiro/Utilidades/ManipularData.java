package com.example.administradorfinanceiro.Utilidades;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ManipularData {

        public static String obterData(String mascara) {
            Date data = new Date();
            if (mascara == null) {
                mascara = "dd/MM/yyyy";
            }
            SimpleDateFormat formatador = new SimpleDateFormat(mascara);
            String dataTxt = formatador.format(data);

            return dataTxt;
        }
    public static String obterDataMenos(String mascara) {
        Date data = new Date();
        data.setDate(data.getDate()-30);
        if (mascara == null) {
            mascara = "dd/MM/yyyy";
        }
        SimpleDateFormat formatador = new SimpleDateFormat(mascara);
        String dataTxt = formatador.format(data);

        return dataTxt;
    }
    public String DataBanco(String data){

        String []aux = data.split("/");
       if( aux[1].length()==1){
           aux[1]="0"+aux[1];
       }
        if( aux[0].length()==1){
            aux[0]="0"+aux[0];
        }
        return aux[2]+"-"+aux[1]+"-"+aux[0];
    }
    public String DataView(String data){
        String []aux = data.split("-");
        return aux[0]+"/"+aux[1]+"/"+aux[2];
    }
}
