package com.checksammy.loca.dto;

import com.checksammy.loca.model.Location;
import com.checksammy.loca.model.PropertyDetails;

public class LocationPropertyManagmentDto {
	
	private Location location;
	private PropertyDetails propDetails;
	
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public PropertyDetails getPropDetails() {
		return propDetails;
	}
	public void setPropDetails(PropertyDetails propDetails) {
		this.propDetails = propDetails;
	}
	

}
