package com.checksammy.loca.dto;

/**
*
* @author Jeevan Kulkarni
*/
public class EmailList {
	public String name;
	public Boolean selector;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getSelector() {
		return selector;
	}
	public void setSelector(Boolean selector) {
		this.selector = selector;
	}
	
	@Override
	public String toString() {
		return "EmailList [name=" + name + ", selector=" + selector + "]";
	}
	
}
