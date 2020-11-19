/**
 * 
 */
package com.checksammy.loca.service;

/**
 * @author Abhishek Srivastava
 *
 */
public interface SecurityService {
	String findLoggedInUsername();

    void login(String username, String password);
}
