package com.blog.web.mvc.util;

import com.blog.framework.config.Config;

/**
 * 通用Json返回对象
 * @author ChenQiang
 */
public class JsonResult {

	private String code = Config.SUCCESS_CODE;
	private String msg = Config.SUCCESS_MSG;
	private String token;
	private Object data = null;

    public static class Builder{
        private String code = Config.SUCCESS_CODE;
        private String msg = Config.SUCCESS_MSG;
        private String token;
        private Object data = null;

        public Builder code(String code){
            this.code = code;
            return this;
        }

        public Builder msg(String msg){
            this.msg = msg;
            return this;
        }

        public Builder token(String token){
            this.token = token;
            return this;
        }
        public Builder data(Object data){
            this.data = data;
            return this;
        }

        public JsonResult build(){
            return new JsonResult(this);
        }
    }

    private JsonResult(Builder builder){
        this.code = builder.code;
        this.msg = builder.msg;
        this.token = builder.token;
        this.data = builder.data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
