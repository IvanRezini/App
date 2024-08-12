package com.example.administradorfinanceiro.Controller;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;

import com.example.administradorfinanceiro.Dao.RelatorioDao;
import com.example.administradorfinanceiro.Model.AbastecimentoModel;
import com.example.administradorfinanceiro.Model.FinancasModel;
import com.example.administradorfinanceiro.Model.RelatorioAbastecimentoModel;
import com.example.administradorfinanceiro.Model.RelatorioFinancasModel;
import com.example.administradorfinanceiro.Model.VeicoloModel;
import com.example.administradorfinanceiro.Utilidades.ManipularData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RelatorioController {

    public List<RelatorioFinancasModel> relatorioFinacas(String inicio, String fim, int conta, Context co)///veicolo id 0 padrão para nem um veicolo selecionado
    {  /*
     private int Id;
    private int IdConta; // Id 0 è uma entrada  ID 1 é um abastecimento
    private int Origem;//1 - Salario", "2 - Extra", "3 - Doação", "4 - Outro" Origem 5 significa um saque, 0 é uma conta paga
    private String Date;
    private String Valor;
    private String EntradaSaida;//E entrada S saida N para pagamento em dinheiro no qual ja foi sacado
    */


        /*
        Cria a query pra consulta
         */
        String auxInicio = inicio;
        String auxFim = fim;
        String query = "SELECT F.Id as id, F.Date as data ,F.Valor as valor, F.Origem as origem, " +
                "F.EntradaSaida as ensa,F.Obs, C.Name as nomeConta  FROM tbfinancas as F" +
                " LEFT  JOIN tbContas as C ON F.IdConta = C.Id" +
                " WHERE data BETWEEN '" +
                auxInicio + "' AND '" + auxFim + "'";
        if (conta > 0) {
            query += " AND F.IdConta = " + conta;
        }
        query += " ORDER BY data";
        /*
        faz a consulta
         */
        List<RelatorioFinancasModel> ab = new ArrayList<RelatorioFinancasModel>();
        RelatorioFinancasModel a = new RelatorioFinancasModel();
        Cursor c;
        RelatorioDao r = new RelatorioDao(co);

        c = r.relatorio(query);
         /*
        Monta a cabeça do relatorio
         */
        a.setId(0);
        a.setEntradaSaida("");
        a.setData("Data");
        a.setValor("Valor");
        a.setConta("Conta");
        a.setObs("OBS..:");
        a.setCor(Color.rgb(0, 255, 255));
        a.setCorTexto(Color.rgb(0, 0, 0));
        ab.add(a);
        /*
        Monta o relatoro
         */
        double tEn = 0;
        double tSa = 0;
        double tDi = 0;
        int auxCor = 0;
        DecimalFormat dF = new DecimalFormat("0.000");//mascara para formatar numeros

        while (c.moveToNext()) {
            a = new RelatorioFinancasModel();
            if (auxCor == 0) {
                a.setCor(Color.rgb(240, 255, 240));
                auxCor = 1;
            } else {
                a.setCor(Color.rgb(224, 255, 255));
                auxCor = 0;
            }
            ManipularData m = new ManipularData();
            a.setId(c.getInt(c.getColumnIndexOrThrow("id")));
            a.setData(m.DataView(c.getString(c.getColumnIndexOrThrow("data"))));
            a.setValor(c.getString(c.getColumnIndexOrThrow("valor")));
            a.setOrigem(c.getString(c.getColumnIndexOrThrow("origem")));
            a.setEntradaSaida(c.getString(c.getColumnIndexOrThrow("ensa")));
            a.setConta(c.getString(c.getColumnIndexOrThrow("nomeConta")));
            a.setObs(c.getString(c.getColumnIndexOrThrow("Obs")));
            a.setCorTexto(Color.rgb(0, 200, 0));
            double auxL = (Double.parseDouble(c.getString(c.getColumnIndexOrThrow("valor"))));

            //private int Origem;//1 - Salario", "2 - Extra", "3 - Doação", "4 - Outro" Origem 5 significa um saque, 0 é uma conta paga
            // private String EntradaSaida;//E entrada S saida N para pagamento em dinheiro no qual ja foi sacado

            if (c.getString(c.getColumnIndexOrThrow("ensa")).equals("E")) {
                a.setCorTexto(Color.rgb(0, 200, 0));
                tEn += auxL;
                switch (a.getOrigem()) {
                    case "1":
                        a.setConta("Salario");
                        break;
                    case "2":
                        a.setConta("Extra");
                        break;
                    case "3":
                        a.setConta("Doação");
                        break;
                    case "4":
                        a.setConta("Outro");
                        break;
                }
            } else if (c.getString(c.getColumnIndexOrThrow("ensa")).equals("S")) {
                a.setCorTexto(Color.rgb(255, 0, 0));
                tSa += auxL;
            } else if (c.getString(c.getColumnIndexOrThrow("ensa")).equals("N")) {
                a.setCorTexto(Color.rgb(0, 0, 0));
                tDi += auxL;
                //a.setConta("Dinheiro");
            }
            ab.add(a);
        }/*
        Monta a rodape do relatorio
         */
        a = new RelatorioFinancasModel();
        a.setCor(Color.WHITE);
        ab.add(a);
        a = new RelatorioFinancasModel();
        a.setEntradaSaida("");
        a.setData("Dinheiro:");
        a.setValor((dF.format(tDi)) + " $");
        a.setCor(Color.rgb(0, 255, 255));
        a.setCorTexto(Color.rgb(0, 0, 0));
        ab.add(a);
        a = new RelatorioFinancasModel();
        a.setEntradaSaida("");
        a.setData("Entrada:");
        a.setValor((dF.format(tEn)) + " $");
        a.setCor(Color.rgb(0, 255, 255));
        a.setCorTexto(Color.rgb(0, 100, 0));
        ab.add(a);
        a = new RelatorioFinancasModel();
        a.setEntradaSaida("");
        a.setData("Saidas:");
        a.setValor((dF.format(tSa)) + " $");
        a.setCor(Color.rgb(0, 255, 255));
        a.setCorTexto(Color.rgb(255, 0, 0));

        ab.add(a);

        return ab;

    }


    public List<RelatorioAbastecimentoModel> relatorioAbastecimento(String inicio, String fim, int veicolo, Context co)///veicolo id 0 padrão para nem um veicolo selecionado
    {
        /*
        Cria a query pra consulta
         */
        String auxInicio = inicio;
        String auxFim = fim;
        String query = "SELECT V.Name AS Veicolo, P.Name AS Posto, A.LitrosTotal, A.ValorLitro, A.Date, A.kmPercorido, A.Obs FROM" +
                " tbAbastecimento AS A " +
                " JOIN tbPosto AS P ON A.Posto = P.Id " +
                "JOIN tbVeicolo AS V ON A.Veicolo = V.Id " +
                "WHERE A.Date BETWEEN '" +
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

        c = r.relatorio(query);
        /*
        Monta a cabeça do relatorio
         */
        a.setMedia("Media");
        a.setValorTotal("Total ");
        a.setValorLitro("VlLitro ");
        a.setKmPercorido("KmTotal");
        a.setCor(Color.rgb(135, 206, 250));
        a.setDate("Data");
        a.setLitrosTotal("LiTotal");
        a.setPosto("Posto");
        a.setVeicolo("Veicolo");
        a.setObs("OBS..:");
        ab.add(a);
        /*
        Monta o relatoro
         */
        double tG = 0;
        double tL = 0;
        double tK = 0;
        int auxCor = 0;
        DecimalFormat dF = new DecimalFormat("0.000");//mascara para formatar numeros

        while (c.moveToNext()) {
            a = new RelatorioAbastecimentoModel();
            if (auxCor == 0) {
                a.setCor(Color.GREEN);
                auxCor = 1;
            } else {
                a.setCor(Color.YELLOW);
                auxCor = 0;
            }

            ManipularData m = new ManipularData();
            a.setVeicolo(c.getString(c.getColumnIndexOrThrow("Veicolo")));
            a.setPosto(c.getString(c.getColumnIndexOrThrow("Posto")));
            a.setLitrosTotal(c.getString(c.getColumnIndexOrThrow("LitrosTotal")));
            a.setValorLitro(c.getString(c.getColumnIndexOrThrow("ValorLitro")));
            a.setDate(m.DataView(c.getString(c.getColumnIndexOrThrow("Date"))));
            a.setKmPercorido(c.getString(c.getColumnIndexOrThrow("kmPercorido")));
            a.setObs(c.getString(c.getColumnIndexOrThrow("Obs")));

            double auxL = (Double.parseDouble(c.getString(c.getColumnIndexOrThrow("LitrosTotal"))));
            double auxV = (Double.parseDouble(c.getString(c.getColumnIndexOrThrow("ValorLitro"))));
            double auxK = (Double.parseDouble(c.getString(c.getColumnIndexOrThrow("kmPercorido"))));

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
        a.setValorLitro((dF.format(tG)) + " $");
        a.setCor(Color.rgb(135, 206, 250));
        ab.add(a);
        a = new RelatorioAbastecimentoModel();
        a.setDate("Total Litros");
        a.setValorLitro((dF.format(tL)) + "");
        a.setCor(Color.rgb(135, 206, 250));
        ab.add(a);
        a = new RelatorioAbastecimentoModel();
        a.setDate("Total km rodado");
        a.setValorLitro((dF.format(tK)) + " $");
        a.setCor(Color.rgb(135, 206, 250));
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

