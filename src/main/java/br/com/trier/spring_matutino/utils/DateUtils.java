package br.com.trier.spring_matutino.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	private static DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	private static ZoneId zonaHorarioLondres = ZoneId.of("Europe/London");
	
	public static ZonedDateTime strToZonedDateTime(String dateStr) {
		LocalDateTime localDate = LocalDateTime.parse(dateStr, formatacao);
		ZonedDateTime zonedDateTime = localDate.atZone(zonaHorarioLondres).minusHours(3);
		String dataHorarioLondres = zonedDateTime.format(formatacao);
		LocalDateTime parseLocalDate = LocalDateTime.parse(dataHorarioLondres, formatacao);
		return ZonedDateTime.of(parseLocalDate, ZoneId.systemDefault());
	}

	public static String zonedDateTimeToStr(ZonedDateTime data) {
		return formatacao.format(data);
	}
}
