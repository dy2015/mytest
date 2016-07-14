package com;

import java.util.Calendar;
import java.util.Date;

public class TestCalendarTime {

	public static void main(String[] args) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MONTH, 2);
		Date date = now.getTime();
		System.out.println(date);
	}

}
