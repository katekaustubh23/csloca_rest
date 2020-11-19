package com.checksammy.loca.utility;

import org.springframework.stereotype.Service;

@Service
public class DateServiceUtil {
	
	public static String getMonthName(int month) {

		String monthName = null;
		switch (month) {
		case 0:
			monthName = "January";
			break;
		case 1:
			monthName = "February";
			break;
		case 2:
			monthName = "March";
			break;
		case 3:
			monthName = "April";
			break;
		case 4:
			monthName = "May";
			break;
		case 5:
			monthName = "June";
			break;
		case 6:
			monthName = "July";
			break;
		case 7:
			monthName = "August";
			break;
		case 8:
			monthName = "September";
			break;
		case 9:
			monthName = "October";
			break;
		case 10:
			monthName = "November";
			break;
		case 11:
			monthName = "December";
			break;
		case 12:
			monthName = "Overdue";
			break;
		case 13:
			monthName = "Today";
			break;
		case 14:
			monthName = "Unschedule";
			break;
		case 15:
			monthName = "Upcoming";
			break;
		}

		return monthName;
	}

}
