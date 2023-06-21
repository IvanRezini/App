package com.example.administradorfinanceiro.Controller;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;

import com.example.administradorfinanceiro.Dao.RelatorioDao;
import com.example.administradorfinanceiro.Model.AbastecimentoModel;
import com.example.administradorfinanceiro.Model.RelatorioAbastecimentoModel;
import com.example.administradorfinanceiro.Model.VeicoloModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RelatorioController {


    public List<RelatorioAbastecimentoModel> rela(String inicio, String fim, int veicolo, Context co)///veicolo id 0 padrão para nem um veicolo selecionado
    {
        /*
        Cria a query pra consulta
         */
        String auxInicio = inicio;
        String auxFim = fim;
        String query = "SELECT V.Name AS Veicolo, P.Name AS Posto, A.LitrosTotal, A.ValorLitro, A.Date, A.kmPercorido FROM" +
                " tbAbastecimento AS A " +
                " JOIN tbPosto AS P ON A.Posto = P.Id " +
                "JOIN tbVeicolo AS V ON A.Veicolo = V.Id " +
                "WHERE A.Date BETWEEN '"+
                auxInicio + "' AND '" + auxFim + "'";

        if (veicolo > 0) {
            query += " AND A.Veicolo = " + veicolo;
        }
        query += " ORDER BY A.Date";
        /*
        faz a consulta
         */
        List<RelatorioAbastecimentoModel> ab = new ArrayList<RelatorioAbastecimentoModel>();
        RelatorioAbastecimentoModel a = new RelatorioAbastecimentoModel();
        Cursor c;
        RelatorioDao r = new RelatorioDao(co);

        c = r.relatorioAbstecimento(query);
        /*
        Monta a cabeça do relatorio
         */
        a.setMedia("Media");
        a.setValorTotal("Total ");
        a.setValorLitro("VlLitro ");
        a.setKmPercorido("KmTotal");
        a.setCor(Color.BLUE);
        a.setDate("Data");
        a.setLitrosTotal("LiTotal");
        a.setPosto("Posto");
        a.setVeicolo("Veicolo");
        ab.add(a);
        /*
        Monta o relatoro
         */
        double tG = 0;
        double tL = 0;
        double tK = 0;
        int auxCor = 0;


        while (c.moveToNext()) {
            a = new RelatorioAbastecimentoModel();
            if (auxCor == 0) {
                a.setCor(Color.GREEN);
                auxCor = 1;
            } else {
                a.setCor(Color.YELLOW);
                auxCor = 0;
            }

            a.setVeicolo(c.getString(c.getColumnIndexOrThrow("Veicolo")));
            a.setPosto(c.getString(c.getColumnIndexOrThrow("Posto")));
            a.setLitrosTotal(c.getString(c.getColumnIndexOrThrow("LitrosTotal")));
            a.setValorLitro(c.getString(c.getColumnIndexOrThrow("ValorLitro")));
            a.setDate(c.getString(c.getColumnIndexOrThrow("Date")));
            a.setKmPercorido(c.getString(c.getColumnIndexOrThrow("kmPercorido")));

            double auxL = (Double.parseDouble(c.getString(c.getColumnIndexOrThrow("LitrosTotal"))));
            double auxV = (Double.parseDouble(c.getString(c.getColumnIndexOrThrow("ValorLitro"))));
            double auxK = (Double.parseDouble(c.getString(c.getColumnIndexOrThrow("kmPercorido"))));

            DecimalFormat dF = new DecimalFormat("0.000");//mascara para formatar numeros
            a.setValorTotal(dF.format((auxL * auxV)));
            a.setMedia(dF.format((auxK / auxL)));

            tL += auxL;
            tG += auxV;
            tK += auxK;

            ab.add(a);
        }
          /*
        Monta a rodape do relatorio
         */
        a = new RelatorioAbastecimentoModel();
        a.setCor(Color.WHITE);
        ab.add(a);
        a = new RelatorioAbastecimentoModel();
        a.setDate("Total gasto");
        a.setValorLitro(tG+" $");
        a.setCor(Color.BLUE);
        ab.add(a);
        a = new RelatorioAbastecimentoModel();
        a.setDate("Total Litros");
        a.setValorLitro(tL+"");
        a.setCor(Color.BLUE);
        ab.add(a);
        a = new RelatorioAbastecimentoModel();
        a.setDate("Total km rodado  "+c.getCount());
        a.setValorLitro(tK+"");
        a.setCor(Color.BLUE);
        ab.add(a);
        a = new RelatorioAbastecimentoModel();
        a.setDate("Media geral");
        a.setValorLitro((tK/tL)+"");
        a.setCor(Color.BLUE);
        ab.add(a);
        return ab;
    }
}
/*
        public string Veicolo { get; set; }
        public string Posto { get; set; }
        public string LitrosTotal { get; set; }
        public string ValorLitro { get; set; }
        public string Date { get; set; }
        public string kmPercorido { get; set; }
        public string Hodometro { get; set; }
        public string Media { get; set; }
        public string ValorTotal { get; set; }
        public string Cor { get; set; }*/

