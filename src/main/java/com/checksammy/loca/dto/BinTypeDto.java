/**
 * 
 */
package com.checksammy.loca.dto;

import java.util.List;

import com.checksammy.loca.model.BinContent;
import com.checksammy.loca.model.BinType;

/**
 * @author Abhishek Srivastava
 *
 */
public class BinTypeDto {
	
	private BinType binType;
	private List<BinContent> binContents;
	
	public BinType getBinType() {
		return binType;
	}
	public void setBinType(BinType binType) {
		this.binType = binType;
	}
	public List<BinContent> getBinContents() {
		return binContents;
	}
	public void setBinContents(List<BinContent> binContents) {
		this.binContents = binContents;
	}

}
