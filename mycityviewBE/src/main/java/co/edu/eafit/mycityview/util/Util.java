package co.edu.eafit.mycityview.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public static Date stringToDate(String fecha, String format) throws ParseException {
		SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
		return simpleFormat.parse(fecha);
	}
	

	public static Double getMinutesBetweenDates(Date fechaInicio, Date fechaFin) {
		return (Math.floor(fechaFin.getTime()) - Math.floor(fechaInicio.getTime())) / ((1000 * 60));
	}

}
