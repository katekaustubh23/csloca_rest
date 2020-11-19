/**
 * 
 */
package com.checksammy.loca.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Abhishek Srivastava
 *
 */
public class StringUtil {

	public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

	public static boolean isValidEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	public static String stringify(Object object) {
		Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
		return gson.toJson(object);
	}
}
