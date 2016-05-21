package net.brewspberry.util;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.brewspberry.business.beans.DurationBO;

public class DateManipulator {

	private static DateManipulator dateManipulator;

	private static String[] patterns = new String[] {
			"^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01]) [012][0-9]:[0-5]\\d:[0-5]\\d$", // yyyy-mm-dd
																												// HH:mm:ss
			"^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01]) [012][0-9]:[0-5]\\d:[0-5]\\d\\.\\d\\d\\d\\d$", // yyyy-mm-dd
																																// HH:mm:ss.SSSS
			"^(0[1-9]|[12][0-9]|3[01])[- \\/.](0[1-9]|1[012])[- \\/.](19|20)\\d\\d [012][0-9]:[0-5]\\d:[0-5]\\d$", // dd/mm/yyyy
																												// HH:mm:ss
			"^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d [012][0-9]:[0-5]\\d:[0-5]\\d\\.\\d\\d\\d\\d$", // dd/mm/yyyy
			// HH:mm:ss.SSSS
			"^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.]\\d\\d [012][0-9]:[0-5]\\d:[0-5]\\d\\.\\d\\d\\d\\d$", // dd/mm/yy
			// HH:mm:ss.SSSS
			"^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.]\\d\\d [012][0-9]:[0-5]\\d:[0-5]\\d$" // dd/mm/yy
			// HH:mm:ss
	};

	private DateManipulator() {

	}

	public static DateManipulator getInstance() {

		if (dateManipulator == null) {
			dateManipulator = new DateManipulator();
		}

		return dateManipulator;
	}

	public DurationBO getDurationBetween(Date beginning, Date end) {

		DurationBO result = new DurationBO();

		long beginningL = beginning.getTime();
		long endL = end.getTime();

		long diff = endL - beginningL;

		result.setWeek(getDurationInWeeks(diff));
		result.setDay(getDurationInDays(diff) - getDurationInWeeks(diff) * 7);
		result.setHour(getDurationInHours(diff) - getDurationInDays(diff) * 24);
		result.setMinute(getDurationInMinutes(diff) - getDurationInHours(diff) * 60);
		result.setSecond(diff - getDurationInMinutes(diff) * 60);
		result.setMilisecond(0);

		return result;

	}

	public String getDurationAsString(DurationBO duration) {

		return duration.getWeek() + ";" + duration.getDay() + ";" + duration.getHour() + ";" + duration.getMinute()
				+ ";" + duration.getSecond() + ";" + duration.getMilisecond();
	}

	/**
	 * 
	 * Convention : - Years - Months - Weeks - Days - Hours - Minutes - Seconds
	 * - Milliseconds => 8 fields
	 * 
	 * @throws
	 * 
	 **/
	public DurationBO buildDurationFromLongs(List<Long> durations) throws Exception {
		DurationBO result = new DurationBO();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

		Date date = new Date();

		if (durations.size() == 8) {

			result.setWeek(durations.get(2));
			result.setDay(durations.get(3));
			result.setHour(durations.get(4));
			result.setMinute(durations.get(5));
			result.setSecond(durations.get(6));
			result.setMilisecond(durations.get(7));

		} else
			throw new Exception("Incorrect size for Durations List");

		return result;
	}

	public long getDurationInWeeks(long diff) {

		return (long) (Double.valueOf(diff) / (60.0 * 60.0 * 24.0 * 7));

	}

	public long getDurationInDays(long diff) {

		return (long) (Double.valueOf(diff) / (60.0 * 60.0 * 24.0));

	}

	public long getDurationInHours(long diff) {

		return (long) (Double.valueOf(diff) / (60.0 * 60.0));

	}

	public long getDurationInMinutes(long diff) {

		return (long) (Double.valueOf(diff) / 60.0);

	}

	public static Calendar formatDateFromVariousPatterns(String date) {

		int id = 0;

		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;
		int milisecond = 0;
		
		boolean found = false;

		for (String pattern : patterns) {

			if (date.matches(pattern) && !found) {

				System.out.println("Found for id "+id);
				switch (id) {

				case 1:

					System.out.println("milisecs");
					milisecond = Integer.parseInt(date.substring(20, 24));

				case 0:
					

					year = Integer.parseInt(date.substring(0, 4));
					month = Integer.parseInt(date.substring(5, 7));
					day = Integer.parseInt(date.substring(8, 10));
					hour = Integer.parseInt(date.substring(11, 13));
					minute = Integer.parseInt(date.substring(14, 16));
					second = Integer.parseInt(date.substring(17, 19));
					System.out.println(date+" "+year+" "+day+" "+milisecond);

					break;

				case 3:

					milisecond = Integer.parseInt(date.substring(20, 24));

				case 2:

					day = Integer.parseInt(date.substring(0, 2));
					month = Integer.parseInt(date.substring(3, 5));
					year = Integer.parseInt(date.substring(6, 10));
					hour = Integer.parseInt(date.substring(11, 13));
					minute = Integer.parseInt(date.substring(14, 16));
					second = Integer.parseInt(date.substring(17, 19));

					break;

				case 5:

					milisecond = Integer.parseInt(date.substring(18, 21));

				case 4:

					day = Integer.parseInt(date.substring(0, 1));
					month = Integer.parseInt(date.substring(3, 4));
					year = Integer.parseInt(date.substring(6, 7));
					hour = Integer.parseInt(date.substring(9, 10));
					minute = Integer.parseInt(date.substring(12, 13));
					second = Integer.parseInt(date.substring(15, 16));

					break;

				}

				found = true;
			}
			
			if (found)
				break;
			else
				id++;

		}
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, milisecond);

		
		return cal;
	}
}