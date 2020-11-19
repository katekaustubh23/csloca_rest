/**
 * 
 */
package com.checksammy.loca.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.model.User;
import com.checksammy.loca.service.EmailService;
import com.checksammy.loca.service.SecurityService;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.request.LoginForm;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;
import com.checksammy.loca.utility.Utility;

/**
 * @author Abhishek Srivastava
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api")
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @RequestMapping(value = "", method = RequestMethod.GET, produces = {MediaType.TEXT_PLAIN_VALUE})
    public String test() {
    	logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
        return "CS LocA APP Rest: Success\nDate: " + new Date().toString();
    }
	
	@PostMapping("/login")
	public ResponseEntity<ServiceResponse> login(@RequestBody LoginForm loginRequest){
		ServiceResponse response = new ServiceResponse();
		try{
			securityService.login(loginRequest.getUsername(), loginRequest.getPassword());
			User user = userService.findByUsername(loginRequest.getUsername());
			response.setReturnObject(user);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/forgot")
	public ResponseEntity<ServiceResponse> forgotPassword(@RequestParam("username") String username){
		ServiceResponse response = new ServiceResponse();
		try{
			User user = userService.findByUsername(username);
			String otp = Utility.generateOtp(4);
			logger.info("OTP ------"+otp);
			userService.setOTPByUserName(otp, username);
			String emailContaint = "<html><body><h3>Hello <b> " + user.getFirstName() + ",</b></h3>"
					+ "<p>To reset your password, please use the following code.</p>" + "<h1>Your code "
					+ otp + "</h1><br/>" + "Thanks, <br>" + "CheckSammy LocA Team"
					+ "</body></html>";
			
			emailService.sendEmailHtml(emailContaint, username, "Password Reset Request");
			response.setReturnObject("OTP sent successfully.");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping(value = "/verify")
	public ResponseEntity<ServiceResponse> verifyOTP(@RequestParam("username") String username, @RequestParam("otp") String otp){
		ServiceResponse response = new ServiceResponse();
		try{
			User user = userService.findByUsername(username);
			if(user.getOtp().equals(otp)) {
				userService.setOTPByUserName(null, username);
				response.setReturnObject("Valid OTP!");
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			}else {
				response.setErrorMessage("Invalid OTP!");
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			}			
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping(value = "/changePassword")
	public ResponseEntity<ServiceResponse> changePassword(@RequestBody User user){
		ServiceResponse response = new ServiceResponse();
		try{
			User existingUser = userService.findByUsername(user.getUsername());
			if(existingUser!=null)
				userService.changePassword(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()));
		
			response.setReturnObject("Password changed successfully!");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

}
