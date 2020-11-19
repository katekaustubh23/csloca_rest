package com.checksammy.loca.service.request;

import java.util.Set;

import javax.validation.constraints.Size;


public class Registration {
	
    @Size(min = 3, max = 100)
    private String firstName;
    @Size(min = 3, max = 100)
    private String lastName;
    @Size(max = 100)
    private String username;
    private Set<String> role;
    @Size(min = 6, max = 40)
    private String password;
    @Size(min = 6, max = 50)
    private String phone;
    @Size(min = 6, max = 200)
    private String companyName;
    
    private Character status;

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRole() {
    	return this.role;
    }
    
    public void setRole(Set<String> role) {
    	this.role = role;
    }

	
	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
    
}