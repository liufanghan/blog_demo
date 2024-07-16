package org.lfh.blog_demo.aop;

import jakarta.annotation.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.lfh.blog_demo.entity.Article;
import org.lfh.blog_demo.mapper.ArticleMapper;
import org.lfh.blog_demo.util.ResultEnum;
import org.lfh.blog_demo.util.UserHolder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Aspect
@Component
public class SecurityAspect {
    @Resource
    private ArticleMapper articleMapper;

    @Pointcut("@annotation(org.lfh.blog_demo.aop.PermissionAnnotation)")
    public void SecurityCheck() {
    }

    //前置通知
    @Before("SecurityCheck()")
    public void deBefore(JoinPoint jp) {
        Object[] args = jp.getArgs();
        Integer id = (Integer) args[0];
        Integer userId = UserHolder.getUser();
        Article article = articleMapper.getArticleById(id);

        if (article == null || article.getUserId().intValue() != userId.intValue()) {
            throw new RuntimeException(ResultEnum.INTERCEPTOR_ERROR.getMsg());
        }
    }
}
