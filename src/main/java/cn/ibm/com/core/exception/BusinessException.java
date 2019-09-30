package cn.ibm.com.core.exception;


import cn.ibm.com.core.constant.ErrorCode;
import lombok.Data;

@Data
public class BusinessException extends SystemException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3627350416934071615L;
	
	private ErrorCode errorCode;
	
	private BusinessException() {
		super();
	}

	private BusinessException(ErrorCode errorCode) {
		super(errorCode.getCode(), errorCode.getMessage());
		this.errorCode = errorCode;
	} 

	public static BusinessException getInstance(ErrorCode errorCode){
		return new BusinessException(errorCode);
	}
	
}
