package org.lfh.blog_demo.handler;


import org.lfh.blog_demo.util.Result;
import org.lfh.blog_demo.util.ResultEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class AllExceptionHandler {
    //进行异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody //返回JSON数据
    public Result<Object> exception(Exception e) {
        e.printStackTrace();
        return Result.fail(ResultEnum.INTERCEPTOR_ERROR);
    }
}
