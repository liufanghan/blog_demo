package org.lfh.blog_demo.service;

import org.lfh.blog_demo.util.Result;
import org.lfh.blog_demo.vo.param.ArticleParam;

public interface ArticleService {
    Result listArticleById(Integer uid, Integer page, Integer size, Integer sort);

    Result insertArticle(ArticleParam articleParam);

    Result getArticleById(Integer id);

    Result deleteArticleById(Integer id);

    Result updateArticleById(Integer id, ArticleParam articleParam);
}
