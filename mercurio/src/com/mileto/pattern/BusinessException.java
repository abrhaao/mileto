package com.mileto.pattern;

import com.backinbean.BackingBean;
import com.backinbean.NavegarBean;

public class BusinessException extends RuntimeException {

	public BusinessException(String sThrowable) {		
		super(sThrowable);
		
		NavegarBean bean = (NavegarBean)BackingBean.getBean("navegarBean");
		bean.addMessage(sThrowable);

	}
	

}
