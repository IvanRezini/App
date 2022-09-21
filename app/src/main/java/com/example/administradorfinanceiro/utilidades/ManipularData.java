package com.example.administradorfinanceiro.utilidades;

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
}
