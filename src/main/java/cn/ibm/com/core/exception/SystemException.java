package cn.ibm.com.core.exception;

import lombok.Data;

@Data
public class SystemException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	
	private String message;


	public SystemException() {
		super();
	}


	public SystemException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	} 
	
	
	

}
