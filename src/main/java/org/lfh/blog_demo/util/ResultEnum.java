package org.lfh.blog_demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ResultEnum {

    SUCCESS(200, "成功"),
    ERROR(500, "失败"),
    INSERT_ARTICLE_ERROR(501, "新增文章失败"),
    GET_ARTICLE_ERROR(502, "未找到指定文章"),
    UPDATE_ARTICLE_ERROR_1(503, "更新文章失败"),
    UPDATE_ARTICLE_ERROR_2(504, "不是自己的文章，无法修改"),
    REGISTER_ERROR_1(505, "注册失败，填写项不正确"),
    REGISTER_ERROR_2(506, "注册失败，已有用户注册"),
    REGISTER_ERROR_3(507, "注册失败，请稍后重试"),
    LOGIN_ERROR_1(508, "邮箱或密码错误"),
    LOGIN_ERROR_2(509, "注册失败，填写项不正确"),
    INTERCEPTOR_ERROR(510, "系统异常，请稍后重试"),
    DELETE_ARTICLE_ERROR_1(511, "只能删除自己的文章"),
    DELETE_ARTICLE_ERROR_2(512, "删除失败"),
    PAGE_SIZE_ERROR(513,"页数或者页码出错");


    private final int code;
    private final String msg;

}
