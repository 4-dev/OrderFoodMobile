package br.com.orderFood.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Class com vários utilitários para o sistema
 *
 * @author Ruan Alves
 */
public class Utils {

    private static NumberFormat nf = NumberFormat.getInstance();

    /**
     * @param precoDouble
     * @return
     * @author Ruan Alves
     */
    public static double converterDoubleDoisDecimais(double precoDouble) {
        try {
            DecimalFormat fmt = new DecimalFormat("0.00");
            String string = fmt.format(precoDouble);
            String[] part = string.split("[,]");
            String string2 = part[0] + "." + part[1];
            double preco = Double.parseDouble(string2);
            return preco;
        } catch (Exception e) {
            return precoDouble;
        }

    }

    public static String getMaskMoney(Double valor) {

        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);

        if (valor != null) {
            return nf.format(valor);
        } else {
            return null;
        }
    }

    public static String converterDoubleDoisDecimais3(Double precoDouble) {
        Locale.setDefault(new Locale("pt", "BR"));
        DecimalFormat fmt = new DecimalFormat("#,##0.00");
        String string = fmt.format(precoDouble);
        /*   String[] part = string.split("[,]");
        String string2 = part[0]+"."+part[1];  */

        return string;
    }

    public static double converterDoubleDoisDecimais2(double precoDouble) {
        Locale.setDefault(new Locale("pt", "BR"));
        DecimalFormat fmt = new DecimalFormat("0.00");
        String string = fmt.format(precoDouble);
        String[] part = string.split("[,]");
        String string2 = part[0] + "." + part[1];
        double preco = Double.parseDouble(string2);
        return preco;
    }

    public static boolean validateNotNull(View pView, String pMessage) {
        if (pView instanceof EditText) {
            EditText edText = (EditText) pView;
            Editable text = edText.getText();
            if (text != null) {
                String strText = text.toString();
                if (!TextUtils.isEmpty(strText)) {
                    return true;
                }
            }

            // em qualquer outra condi��o � gerado um erro
            edText.setError(pMessage);
            edText.setFocusable(true);
            edText.requestFocus();
            return false;
        }
        return false;
    }

    public static void setError(EditText edText, String msg) {
        if (!edText.isFocused()) {
            if (edText.getText().toString().trim().length() == 0) {
                edText.setError(msg);
                //etCliente.requestFocus();
            } else {
                edText.setError(null);
            }
        }
    }

    public static String formata_dd_MM_yyyy(Date data) {

        String DataNascDb = null;
        DataNascDb = new SimpleDateFormat("dd/MM/yyyy").format(data);

        return DataNascDb;
    }

    public static String formataDataString_YYYY_MM_DD(Date data) {

        String DataNascDb = null;
        DataNascDb = new SimpleDateFormat("yyyy-MM-dd").format(data);

        return DataNascDb;
    }

    public static Boolean compareDatas(Date data01, Date data02) {
        if (data01.compareTo(data02) >= 1) {
            //System.out.println("data01 Maior");
            return true;
        } else {
            //System.out.println("data02 Maior");
            return false;
        }
    }

    /**
     * @param dataLow  The lowest date
     * @param dataHigh The highest date
     * @return int
     * @author Ruan Alves
     * Método para comparar as das e retornar o numero de dias de diferença entre elas
     * <p/>
     * Compare two date and return the difference between them in days.
     */
    public static int dataDiff(Date dataLow, Date dataHigh) {

        GregorianCalendar startTime = new GregorianCalendar();
        GregorianCalendar endTime = new GregorianCalendar();

        GregorianCalendar curTime = new GregorianCalendar();
        GregorianCalendar baseTime = new GregorianCalendar();

        startTime.setTime(dataLow);
        endTime.setTime(dataHigh);

        int dif_multiplier = 1;

        // Verifica a ordem de inicio das datas
        if (dataLow.compareTo(dataHigh) < 0) {
            baseTime.setTime(dataHigh);
            curTime.setTime(dataLow);
            dif_multiplier = 1;
        } else {
            baseTime.setTime(dataLow);
            curTime.setTime(dataHigh);
            dif_multiplier = -1;
        }

        int result_years = 0;
        int result_months = 0;
        int result_days = 0;

        // Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import acumulando
        // no total de dias. Ja leva em consideracao ano bissesto
        while (curTime.get(GregorianCalendar.YEAR) < baseTime.get(GregorianCalendar.YEAR) ||
                curTime.get(GregorianCalendar.MONTH) < baseTime.get(GregorianCalendar.MONTH)) {

            int max_day = curTime.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            result_months += max_day;
            curTime.add(GregorianCalendar.MONTH, 1);

        }

        // Marca que é um saldo negativo ou positivo
        result_months = result_months * dif_multiplier;


        // Retirna a diferenca de dias do total dos meses
        result_days += (endTime.get(GregorianCalendar.DAY_OF_MONTH) - startTime.get(GregorianCalendar.DAY_OF_MONTH));

        return result_years + result_months + result_days;
    }

    /**
     * Método que formata a data no Seguinte Formato dd/MM/yyyy - HH:mm:ss
     *
     * @return
     */
    public static String retornaDateFormatadaDDMMYY_HHmmss(Date data) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data);
    }

    public static String retornaDateFormatada_YYYYMMDD_HHmmss(Date data) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String horaFormatada = df.format(data);
        return horaFormatada;
    }

    /**
     * Converte uma String para um objeto Date. Caso a String seja vazia ou nula,
     * retorna null - para facilitar em casos onde formulários podem ter campos
     * de datas vazios.
     *
     * @param data String no formato yyyy-MM-dd a ser formatada
     * @return Date Objeto Date ou null caso receba uma String vazia ou nula
     * @throws Exception Caso a String esteja no formato errado
     */
    public static Date formataStringData_YYYY_MM_DD_HHMMYYYY(String data) throws Exception {

        if (data == null || data.equals("")) return null;
        Date date = null;

        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            date = (Date) formatter.parse(data);
        } catch (ParseException e) {
            throw e;
        }

        return date;

    }

}
