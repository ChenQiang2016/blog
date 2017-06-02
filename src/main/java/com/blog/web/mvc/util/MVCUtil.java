package com.blog.web.mvc.util;

import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import com.blog.framework.config.Config;
import com.blog.framework.exception.BusiException;

/**
 * MVC工具类
 * @author ChenQiang
 */
public class MVCUtil {

    public static JsonResult getJsonResult() {
        return getJsonResult(null);
    }

    public static JsonResult getJsonResult(Object data) {
        JsonResult.Builder builder = new JsonResult.Builder();
        
        if(data != null){
            builder.data(data);
        }

        return builder.build();
    }

    public static ModelAndView getModelAndView(String viewName) {
        return getModelAndView(viewName, null);
    }

    public static ModelAndView getModelAndView(String viewName, Object object){
        return new ModelAndView(viewName, "data", object);
    }
    
    /**
     * 服务器端跳转
     * @param path 跳转路径
     * @return
     * @author ChenQiang
     */
    public static ModelAndView redirect(String path) {
    	return new ModelAndView("redirect:" + path);
    }

    /**
     * 校验参数
     * @param result
     * @author ChenQiang
     */
    public static void valid(BindingResult result) {
        StringBuilder errorBuilder = new StringBuilder();
        List<ObjectError> errors = result.getAllErrors();
        
        for(ObjectError error : errors){
        	if(StringUtils.hasText(errorBuilder)) {
        		errorBuilder.append(" ");
        	}
            errorBuilder.append(error.getDefaultMessage());
        }
        
        if(StringUtils.hasText(errorBuilder.toString())){
            throw new BusiException(Config.PARAMS_ERROR_CODE,errorBuilder.toString());
        }
    }
}