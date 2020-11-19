package com.checksammy.loca.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.checksammy.loca.dto.SupportPageContactDto;
import com.checksammy.loca.dto.UserDtoWithCustomFieldDto;
import com.checksammy.loca.dto.UserRoleDto;
import com.checksammy.loca.model.DriverInfo;
import com.checksammy.loca.model.User;
import com.checksammy.loca.model.UserCardMapping;
import com.checksammy.loca.model.UserFieldInstance;
import com.checksammy.loca.model.UserNote;
import com.checksammy.loca.model.UserRoleDetailsView;
import com.checksammy.loca.service.CustomFieldInstanceService;
import com.checksammy.loca.service.DriverInfoService;
import com.checksammy.loca.service.EmailService;
import com.checksammy.loca.service.StorageService;
import com.checksammy.loca.service.UserCardMappingService;
import com.checksammy.loca.service.UserNoteService;
import com.checksammy.loca.service.UserRoleDetailsViewService;
import com.checksammy.loca.service.UserRoleService;
import com.checksammy.loca.service.UserService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;
import com.checksammy.loca.utility.GlobalValues;

/**
 * 
 * @author kartik.thakre
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/user")
public class UserController {
	
	public static final String SMS_URL = "https://application.textline.com/api/customers.json";
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	EmailService emailService;

	@Autowired
	UserRoleDetailsViewService userRoleDetailsViewService;

	@Autowired
	UserNoteService userNoteService;

//	NEW CHANGES - dev_sp5
	@Autowired
	DriverInfoService driverInfoService;

	@Autowired
	CustomFieldInstanceService customInstanceService;

	@Autowired
	StorageService storageService;
	
	@Autowired
	UserCardMappingService userCardMappingService;

	@PostMapping("/register")
	public ResponseEntity<ServiceResponse> registerUser(@RequestBody UserRoleDto userRoleDto) {
		
		ServiceResponse response = new ServiceResponse();
		try {

			try {
				userService.findByUsername(userRoleDto.getUser().getUsername());
			} catch (UsernameNotFoundException ue) {
				Boolean status = userService.spUpsertUser(userRoleDto.getUser(), userRoleDto.getRoles());
				if (status) {
					User userInf = userService.findByUsername(userRoleDto.getUser().getUsername());
					DriverInfo driverInfo = new DriverInfo();
					if (userRoleDto.getDriverInfo() != null) {
						userRoleDto.getDriverInfo().setUserId(userInf.getUserId());
						driverInfo = driverInfoService.save(userRoleDto.getDriverInfo());
						userInf.setDriverInfo(driverInfo);
					} else {
						userInf.setDriverInfo(null);
					}
					if (userRoleDto.getCustomFieldInstance() != null
							&& userRoleDto.getCustomFieldInstance().size() > 0) {
						for (UserFieldInstance iterable_element : userRoleDto.getCustomFieldInstance()) {
							iterable_element.setUserId(userInf.getUserId());
//							iterable_element.setCreatedBy(userInf.getCreatedBy());
						}
						customInstanceService.saveAllList(userRoleDto.getCustomFieldInstance());
					}
					if (userRoleDto.getUserNotes() != null && userRoleDto.getUserNotes().size() > 0) {
						for (UserNote userNote : userRoleDto.getUserNotes()) {
							User userData = userService.getUserId(userNote.getCreatedBy());
							userNote.setUserId(userInf.getUserId());
//							userNote.setCreatedBy(userInf.getCreatedBy())
							userNote.setCreatedUserDetails(userData.getFirstName() + " " + userData.getLastName());
							userNote.setCreatedTs(Instant.now());
						}
						userNoteService.saveNotes(userRoleDto.getUserNotes());
					}
					/* UUID Changes */
//					String uuidSMSKey = null;
//					try {
//						if (userRoleDto.getUser().getUserId() == null || userRoleDto.getUser().getUserId() == 0) {
//							uuidSMSKey = createUserAccountForSMSkey(userRoleDto.getUser());
//						}
//					} catch (Exception e) {
//						logger.error(e.getMessage());
//						if (e.getMessage().contains("400")) {
//							logger.info("Already exsites or Invaild");
//						}
//						uuidSMSKey = null;
//					}
//
//					if (uuidSMSKey != null) {
//						userService.updateUserUUIDSmsKey(userInf.getUserId(), uuidSMSKey);
//					}
					/*-----*/
					userService.updateUserDriverInfoId(userInf.getUserId(), driverInfo.getId(),
							userRoleDto.getUser().getUserStatus(), userRoleDto.getUser().getIsActive(),
							userRoleDto.getUser().getFCMToken());
				}

				response.setReturnObject("User registration status: " + status);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
				return ResponseEntity.ok().body(response);
			}
			response.setErrorMessage("User already exists: " + userRoleDto.getUser().getUsername());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);

	}

	/**
	 * Create SMS uuid key using time
	 * 
	 * @throws UnsupportedEncodingException
	 * 
	 * @throws IOException
	 */
	private String createUserAccountForSMSkey(User user) throws UnsupportedEncodingException, IOException {
		String userUUIDKey = null;

		URL url = new URL(SMS_URL);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("X-TGP-ACCESS-TOKEN", GlobalValues.SMS_ACCESS_TOKEN);
		con.setDoOutput(true);
		String jsonInputString = "{\"customer\": {\"phone_number\": \"+91" + user.getPhone() + "\",\"email\": \""
				+ user.getUsername() + "\",\"name\": \"" + user.getFirstName() + " " + user.getLastName()
				+ "\",\"notes\": \"some samples notes for the contact\",\"tags\": \"foo bar\"}}";

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());

			JSONObject jsonObject = new JSONObject(response.toString());
			System.out.println(jsonObject.get("customer"));
			JSONObject customerObject = (JSONObject) jsonObject.get("customer");
			userUUIDKey = customerObject.get("uuid").toString();
			System.out.println("key UUID -> " + userUUIDKey);

		}
		return userUUIDKey;
	}

	@PostMapping("/update")
	public ResponseEntity<ServiceResponse> updateUser(@RequestBody UserRoleDto userRoleDto) {
		ServiceResponse response = new ServiceResponse();
		try {
			if (userRoleDto.getUser().getUserId() != null && userRoleDto.getUser().getUserId() > 0)
				userRoleService.deleteUserRole(userRoleDto.getUser().getUserId());

			Boolean status = userService.spUpsertUser(userRoleDto.getUser(), userRoleDto.getRoles());
			if (status) {
				User userInf = userService.findByUsername(userRoleDto.getUser().getUsername());
				DriverInfo driverInfo = new DriverInfo();
				if (userRoleDto.getDriverInfo() != null) {
					userRoleDto.getDriverInfo().setUserId(userInf.getUserId());
					driverInfo = driverInfoService.save(userRoleDto.getDriverInfo());
					userInf.setDriverInfo(driverInfo);
				} else {
					userInf.setDriverInfo(null);
				}
				if (userRoleDto.getCustomFieldInstance() != null && userRoleDto.getCustomFieldInstance().size() > 0) {
					for (UserFieldInstance iterable_element : userRoleDto.getCustomFieldInstance()) {
						iterable_element.setUserId(userInf.getUserId());
//						iterable_element.setCreatedBy(userInf.getCreatedBy());
					}
					List<UserFieldInstance> newCat = customInstanceService
							.saveAllList(userRoleDto.getCustomFieldInstance());
				}
				if (userRoleDto.getUserNotes() != null && userRoleDto.getUserNotes().size() > 0) {
					for (UserNote userNote : userRoleDto.getUserNotes()) {
						User userData = userService.getUserId(userNote.getCreatedBy());
						userNote.setUserId(userInf.getUserId());
//						userNote.setCreatedBy(userInf.getCreatedBy());
						userNote.setCreatedUserDetails(userData.getFirstName() + " " + userData.getLastName());
						userNote.setCreatedTs(Instant.now());
					}
					userNoteService.saveNotes(userRoleDto.getUserNotes());
				}

				/* UUID Changes */
//				String uuidSMSKey = null;
//				try {
//					uuidSMSKey = createUserAccountForSMSkey(userRoleDto.getUser());
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//					if (e.getMessage().contains("400")) {
//						logger.info("Already exsites or Invaild");
//					}
//					uuidSMSKey = null;
//				}
//
//				if (uuidSMSKey != null) {
//					userService.updateUserUUIDSmsKey(userInf.getUserId(), uuidSMSKey);
//				}
				/*-----*/

				userService.updateUserDriverInfoId(userInf.getUserId(), driverInfo.getId(),
						userRoleDto.getUser().getUserStatus(), userRoleDto.getUser().getIsActive(),
						userRoleDto.getUser().getFCMToken());

			}
			response.setReturnObject("User update status: " + status);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);

	}

	@DeleteMapping("/changestatus/{userId}/{status}")
	public ResponseEntity<ServiceResponse> changeStatus(@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "status") Boolean status) {
		ServiceResponse response = new ServiceResponse();
		try {
			userService.changeStatus(userId, status);
			response.setReturnObject("User deletion status: " + Boolean.TRUE);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/getall")
	public ResponseEntity<ServiceResponse> getAllUsers() {
		ServiceResponse response = new ServiceResponse();
		try {
			List<UserRoleDetailsView> userRoleDetails = userRoleDetailsViewService.getAll();
			response.setReturnObject(userRoleDetails);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/exists/{username}")
	public ResponseEntity<ServiceResponse> isExistingUser(@PathVariable("username") String userName) {
		ServiceResponse response = new ServiceResponse();
		try {
			UserDtoWithCustomFieldDto user = userService.findByUsernameNew(userName);
			response.setReturnObject(user);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/approval")
	public ResponseEntity<ServiceResponse> grantAccess(@RequestParam("username") String username) {
		ServiceResponse response = new ServiceResponse();
		try {
			User user = userService.findByUsername(username);
			if (user != null) {
				String emailContaint = "<html><body><h3>Hello <b> " + user.getFirstName() + ",</b></h3>"

						+ "<p>Your access has been granted for CheckSammy LocA. You can now use the application. <br/>Kindly proceed with login.</p>"
						+ "<br/>" + "Thanks, <br>" + "CheckSammy LocA Team" + "</body></html>";

				emailService.sendEmailHtml(emailContaint, username, "CheckSammy LocA: Access granted");
				response.setReturnObject("Approval email sent successfully.");
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			} else {
				response.setReturnObject("User with email id: " + username + " does not exist.");
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

	@GetMapping("/web/count")
	public ResponseEntity<ServiceResponse> getUserCount() {
		ServiceResponse response = new ServiceResponse();
		try {
			Long totalCount = userService.getTotalNoOfUsers();
			response.setReturnObject(totalCount);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/contact/uspage")
	public ResponseEntity<ServiceResponse> getContactUsPage(@RequestBody SupportPageContactDto contactPage) {
		ServiceResponse response = new ServiceResponse();
		try {
			String status = emailService.sendSupportEmailHtmlContactPage(contactPage.getContext(),
					contactPage.getFromEmail(), contactPage.getSubject());

			response.setReturnObject(status);
			if (status != "Fail") {
				response.setReturnObject(status);
				response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
			} else {
				response.setErrorMessage(status);
				response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* SPRINT 7 - delete Location notes */
	@DeleteMapping("/delete/notes/{notesID}")
	public ResponseEntity<ServiceResponse> deleteUserNotes(@PathVariable("notesID") Long notesID) {
		ServiceResponse response = new ServiceResponse();
		try {
			Boolean totalCount = userNoteService.deleteUserNote(notesID);
			response.setReturnObject(totalCount);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setErrorMessage(e.getMessage());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		}
		return ResponseEntity.ok().body(response);
	}

	/* User By Role */

	@GetMapping("/getuserby/driver/{roleId}")
	public ResponseEntity<ServiceResponse> getUsreRoleByDriver(@PathVariable("roleId") Long roleId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<User> user = userService.findUserByRole(roleId);
			response.setReturnObject(user);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/userby/{userId}")
	public ResponseEntity<ServiceResponse> getUserById(@PathVariable("userId") Long userId) {
		ServiceResponse response = new ServiceResponse();
		try {
			User user = userService.getUserId(userId);
			response.setReturnObject(user);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

//	Change status ACTIVE -  IN ACTIVE
	@PutMapping("/active/{userId}/{status}")
	public ResponseEntity<ServiceResponse> userActiveInActive(@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "status") Boolean status) {
		ServiceResponse response = new ServiceResponse();
		try {
			userService.changeActiveInactive(userId, status);
			response.setReturnObject("User Active: " + Boolean.TRUE);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
//	Change status ACTIVE -  IN ACTIVE
	@PutMapping("/userstatus/{userId}/{status}")
	public ResponseEntity<ServiceResponse> userStatusInActive(@PathVariable(name = "userId") Long userId,
			@PathVariable(name = "status") Boolean status) {
		ServiceResponse response = new ServiceResponse();
		try {
			userService.changeUserActiveInactive(userId, status);
			response.setReturnObject("User Active: " + Boolean.TRUE);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/setfcm/{userId}")
	public ResponseEntity<ServiceResponse> updateFcmToken(@PathVariable(name = "userId") Long userId,
			@RequestParam("fcmToken") String fcmToken) {
		ServiceResponse response = new ServiceResponse();
		try {
			userService.updateFcmToken(userId, fcmToken);
			response.setReturnObject("User Active: " + Boolean.TRUE);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	/* TODO: Update User few field from device 09-17-2020 */
	@PutMapping("/update/fromdevice")
	public ResponseEntity<ServiceResponse> updateUserFromDevice(@RequestBody User user) {
		ServiceResponse response = new ServiceResponse();
		try {
			user = userService.updateUserFromDeviceProfile(user);
			response.setReturnObject(user);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/upload/profile/{userId}")
	public ResponseEntity<ServiceResponse> uploadMultipleFiles(@PathVariable("userId") Long userId,
			@RequestParam("file") MultipartFile file) {
		ServiceResponse response = new ServiceResponse();
		try {
			storageService.uploadProfilePic(file, userId);
			response.setReturnObject(file.getOriginalFilename() + " photos is/are added successfully.");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setErrorMessage(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

//	GET DRIVER availability
	@GetMapping("/availability/{driverId}")
	public ResponseEntity<ServiceResponse> checkDriverAvailability(@PathVariable(name = "driverId") Long driverId) {
		ServiceResponse response = new ServiceResponse();
		try {
			Boolean avalibility = userService.checkAvailablity(driverId);
			response.setReturnObject(avalibility);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/cardlist/{userId}")
	public ResponseEntity<ServiceResponse> userCardList(@PathVariable(name = "userId") Long userId) {
		ServiceResponse response = new ServiceResponse();
		try {
			List<UserCardMapping> userCardList = userCardMappingService.getUserCardList(userId);
			response.setReturnObject(userCardList);
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}
}
