package com.blog.framework.exception;

/**
 * 业务异常
 * @author ChenQiang
 */
public class BusiException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String code;

    public BusiException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
