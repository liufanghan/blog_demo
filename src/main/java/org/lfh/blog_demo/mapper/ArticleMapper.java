package org.lfh.blog_demo.mapper;

import org.lfh.blog_demo.entity.Article;

import java.util.List;

public interface ArticleMapper {
    List<Article> listArticleByUId(Integer uid, Integer sort);


    int insertArticle(Article article);

    Article getArticleById(Integer id);

    int deleteArticleById(Integer id);

    int updateArticleById(Article article);
}
