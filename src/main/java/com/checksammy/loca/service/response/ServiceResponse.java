/**
 * 
 */
package com.checksammy.loca.service.response;

/**
 * This class must be used by all controller to return the response to consumers.
 * 
 * @author Abhishek Srivastava
 *
 */
public class ServiceResponse {
	
	private Object returnObject;
	private String errorMessage;
	private String status;
	/**
	 * @return the returnObject
	 */
	public Object getReturnObject() {
		return returnObject;
	}
	/**
	 * @param returnObject the returnObject to set
	 */
	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
