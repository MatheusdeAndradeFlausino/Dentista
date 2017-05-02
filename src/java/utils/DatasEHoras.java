/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author valderlei
 */
public class DatasEHoras {

    public static String weekDay(Calendar cal) {
        return new DateFormatSymbols().getWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
    }

    /**
     * Este método verifica se um horário está entre um intervalo de outros 2
     * horários. Exemplo: 10:00 está entre 8:00 e 12:00 porém 13:00 não está
     * neste mesmo intervalo.
     */
    public static boolean isHorarioNoIntervalo(String inferior, String superior, String hora) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date dHora = sdf.parse(hora);
            Date dInferior = sdf.parse(inferior);
            Date dSuperior = sdf.parse(superior);

            return dInferior.before(dHora) && dSuperior.after(dHora);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String extrairHoraEMinutosDaData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(data);
    }

    public static Date criarDateAPartirDeUmaHora(String hora) {
        Calendar c = new GregorianCalendar();
        String[] valores = hora.split(":");
        String horas = valores[0];
        String minutos = valores[1];
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horas));
        c.set(Calendar.MINUTE, Integer.parseInt(minutos));
        return c.getTime();
    }
    
    public static Date criarDateAPartirDeUmaData(String data){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.parse(data);
        } catch (ParseException ex) {
            Logger.getLogger(DatasEHoras.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Date adicionarHorasAUmDate(Date data, String hora) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String horaDoDate = sdf.format(data);
        String[] valores = hora.split(":");
        String[] valoresDate = horaDoDate.split(":");
        int horaFinal = Integer.parseInt(valores[0]) + Integer.parseInt(valoresDate[0]);
        int minutosFinal = Integer.parseInt(valores[1]) + Integer.parseInt(valoresDate[1]);
        Calendar c = new GregorianCalendar();
        c.setTime(data);
        c.set(Calendar.HOUR_OF_DAY, horaFinal);
        c.set(Calendar.MINUTE, minutosFinal);
        return c.getTime();
    }

    public static Date adicionarMesesAUmDate(Date data, int meses) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String mesDoDate = sdf.format(data);
        mesDoDate = mesDoDate.split("/")[1];
        int mes = Integer.parseInt(mesDoDate) + meses;
        Calendar c = new GregorianCalendar();
        c.setTime(data);
        c.set(Calendar.MONTH, mes - 1);
        return c.getTime();
    }

    public static String horaAtual() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date());
    }
    
    public static String converterData(Date data){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(data);
    }
    
    public static String converterDataFormatoPadrao(Date data){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }
    

}
