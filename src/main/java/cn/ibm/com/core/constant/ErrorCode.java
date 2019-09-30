package cn.ibm.com.core.constant;

import cn.ibm.com.core.exception.BusinessException;

public enum ErrorCode {
	NO_USER("100","User not exists"),
	SESSION_TIMEOUR("100","Session timeout or Not logged in"),
	ILLEGAL_PARAMS("102","Illegal Parameters"),
	NO_CODE_INFO("103", "no code information"),
	SERVER_ERROR("500","System error"),
	USER_EXISTS("201","User is existed");
	
	private String code;

	private String message;

	private ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setmessage(String message) {
		this.message = message;
	}

	public static String message(ErrorCode code) {
		return code.getMessage();
	}

	public static String message(String code) {
		for (ErrorCode errorCode : ErrorCode.values()) {
			if (errorCode.getCode().equals(code)) {
				return errorCode.getMessage();
			}
		}
		throw BusinessException.getInstance(ErrorCode.NO_CODE_INFO);
	}

}
