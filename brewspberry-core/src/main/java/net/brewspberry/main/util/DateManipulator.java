package net.brewspberry.main.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.brewspberry.main.business.beans.brewing.DurationBO;

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
	
	/**
	 * Method used for returning date from date parameter and delay. 
	 * 
	 * For date in the past, delay is negative
	 * 
	 * @param date
	 * @param delay
	 * @param delayUnit
	 * @return
	 */
	public Date getDateFromDateAndDelay(Date date, float delay, String delayUnit){
		
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(date);
		
		switch (delayUnit){
		

		case "SECONDS" :
			
			cal.add(Calendar.SECOND, (int) delay);
		break;
		

		case "MINUTES" :
			
			cal.add(Calendar.MINUTE, (int) delay);
		break;

		case "HOURS" :
			
			cal.add(Calendar.HOUR, (int) delay);
		break;
		
		}
		
		
		
		return cal.getTime();		
		
	}

	/**
	 * Checks if date is between - timeAmount and + timeAmount 
	 * @param date
	 * @param calendarType
	 * @param timeAmount
	 * @return
	 */
	public static boolean isDateInRange(Date date, int timeAmount, int calendarType) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		Calendar calAfter = Calendar.getInstance();
		calAfter.setTime(date);
		calAfter.add(timeAmount, calendarType);
		
		Calendar calBefore = Calendar.getInstance();
		calBefore.setTime(date);
		calBefore.add(timeAmount, -calendarType);
		
		
		if (cal.after(calBefore) && cal.before(calAfter)){
			return true;
		}
		return false;
	}

	public static Date addTimeToDate(Date date, DurationBO duration) {
		if (date != null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(duration.getUnit(), duration.getDuration());	
		}
		return null;
	}
}