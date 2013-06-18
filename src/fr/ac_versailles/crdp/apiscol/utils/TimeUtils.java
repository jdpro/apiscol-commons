package fr.ac_versailles.crdp.apiscol.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class TimeUtils {
	public static String toRFC3339(long utcTime) {
		DateTime dt = new DateTime(utcTime);
		DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		return fmt.print(dt);
	}
}
