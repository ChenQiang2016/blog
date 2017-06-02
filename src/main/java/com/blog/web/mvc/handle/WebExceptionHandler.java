package com.blog.web.mvc.handle;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.framework.config.Config;
import com.blog.framework.exception.BusiException;
import com.blog.web.mvc.util.JsonResult;

/**
 * 异常管理
 *
 * @author ChenQiang
 */
@ControllerAdvice(basePackages = "com.blog.web.controller.web")
public class WebExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    /**
     * 处理业务异常
     *
     * @param exception
     * @return
     * @throws IOException
     */
    @ExceptionHandler(BusiException.class)
    @ResponseBody
    public Object handleCustomerException(BusiException exception) throws IOException {
        logger.debug("BusiException", exception);
        return new JsonResult.Builder().code(exception.getCode()).msg(exception.getMessage()).build();
    }

    /**
     * 处理其他异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonResult handleException(Exception exception) {
        logger.error("Exception", exception);
        return new JsonResult.Builder().code(Config.FAILE_CODE).msg(Config.FAILE_MSG).build();
    }
}