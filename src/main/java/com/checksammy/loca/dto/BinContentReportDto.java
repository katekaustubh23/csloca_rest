package com.checksammy.loca.dto;

public class BinContentReportDto {
	
	private Integer content; //value
	
	private String contentName; //contentName
	
	private Long binContentId;

	public String getContentName() {
		return contentName;
	}

	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	public Integer getContent() {
		return content;
	}

	public void setContent(Integer content) {
		this.content = content;
	}

	public Long getBinContentId() {
		return binContentId;
	}

	public void setBinContentId(Long binContentId) {
		this.binContentId = binContentId;
	}

}
