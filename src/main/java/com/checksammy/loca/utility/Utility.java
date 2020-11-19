/**
 * 
 */
package com.checksammy.loca.utility;

import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Abhishek Srivastava
 *
 */
public class Utility {
	
	public static String generateOtp(int length) {
		Random random = new Random();
		String strOtp = String.format("%0"+length+"d", random.nextInt(10000));
		return strOtp;
	}
	
	public static Integer getRandomNumber(int length) {
		Random random = new Random();
		String randomNumber = String.format("%0"+length+"d", random.nextInt(100000000));
		return Integer.parseInt(randomNumber);
	}
	
	public static String stringify(Object object) {
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		return gson.toJson(object);
	}

}
