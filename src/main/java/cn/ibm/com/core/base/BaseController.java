package cn.ibm.com.core.base;

import javax.servlet.http.HttpSession;
import com.google.code.kaptcha.Constants;

import cn.ibm.com.core.constant.ErrorCode;
import cn.ibm.com.core.exception.BusinessException;

public class BaseController {
	
	public void checkCaptcha(String captcha, HttpSession session) {
		String expect = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

		if (expect == null || !captcha.equalsIgnoreCase(expect)) {
			throw BusinessException.getInstance(ErrorCode.ILLEGAL_PARAMS);
		}
	}
	
}
