package org.lfh.blog_demo.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<T>(200, "操作成功", data);
    }

    public static <T> Result<T> success() {
        return new Result<T>(200, "操作成功", null);
    }

    public static <T> Result<T> fail(ResultEnum resultEnum) {
        return new Result<T>(resultEnum.getCode(), resultEnum.getMsg(), null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<T>(404, msg, null);
    }

}
