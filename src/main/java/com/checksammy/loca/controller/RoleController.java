/**
 * 
 */
package com.checksammy.loca.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.checksammy.loca.model.Role;
import com.checksammy.loca.service.RoleService;
import com.checksammy.loca.service.response.ServiceResponse;
import com.checksammy.loca.utility.ConstantUtil;

/**
 * @author Abhishek Srivastava
 *
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/loca/api/role")
public class RoleController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	RoleService roleService;
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ServiceResponse> delete(@PathVariable("id") Long id) {
		ServiceResponse response = new ServiceResponse();
		try {
			roleService.deleteRoleById(id);
			response.setReturnObject("Role is deleted successfully.");
			response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e.fillInStackTrace());
			response.setStatus(ConstantUtil.RESPONSE_FAILURE);
			response.setReturnObject(e.getMessage());
		}
		return ResponseEntity.ok().body(response);
	}

	@PostMapping("/add")
	public ResponseEntity<ServiceResponse> addRole(@RequestBody Role role) {
		ServiceResponse response = new ServiceResponse();
		try {
		role = roleService.addRole(role);
		response.setReturnObject(role);
		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("e.getLocalizedMessage=====>"+e.getCause());
		logger.error(e.getMessage(), e.fillInStackTrace());
		response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		response.setReturnObject(e.getMessage());
	}
	return ResponseEntity.ok().body(response);
	}

	@GetMapping("/getall")
	public ResponseEntity<ServiceResponse> getAllRoles(Pageable pageable) {
		ServiceResponse response = new ServiceResponse();
		try {
		Page<Role> roles = roleService.getAllRoles(pageable);
		response.setReturnObject(roles);
		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage(), e.fillInStackTrace());
		response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		response.setReturnObject(e.getMessage());
	}
	return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/web/list")
	public ResponseEntity<ServiceResponse> getFindAllRole() {
		ServiceResponse response = new ServiceResponse();
		try {
		List<Role> roles = roleService.getAllRoles();
		response.setReturnObject(roles);
		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage(), e.fillInStackTrace());
		response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		response.setReturnObject(e.getMessage());
	}
	return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/activeones")
	public ResponseEntity<ServiceResponse> getActiveRoles() {
		ServiceResponse response = new ServiceResponse();
		try {
		List<Role> roles = roleService.getActiveRoles();
		response.setReturnObject(roles);
		response.setStatus(ConstantUtil.RESPONSE_SUCCESS);

	} catch (Exception e) {
		e.printStackTrace();
		logger.error(e.getMessage(), e.fillInStackTrace());
		response.setStatus(ConstantUtil.RESPONSE_FAILURE);
		response.setReturnObject(e.getMessage());
	}
	return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping("/changestatus/{roleId}/{status}")
	public ResponseEntity<ServiceResponse> changeStatus(@PathVariable(name="roleId") Long roleId, @PathVariable(name="status") Boolean status) {
		ServiceResponse response = new ServiceResponse();
		try {
			roleService.changeStatus(roleId, status);
			response.setReturnObject("Role deletion status: " + Boolean.TRUE);
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
