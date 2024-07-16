package org.lfh.blog_demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.lfh.blog_demo.entity.Article;
import org.lfh.blog_demo.mapper.ArticleMapper;
import org.lfh.blog_demo.service.ArticleService;
import org.lfh.blog_demo.util.Result;
import org.lfh.blog_demo.util.ResultEnum;
import org.springframework.stereotype.Service;
import vo.param.ArticleParam;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Result listArticleById(Integer uid, Integer page, Integer size, Integer sort) {
        if (page < 1 || size < 0) {
            return Result.fail(ResultEnum.PAGE_SIZE_ERROR);
        }
        PageHelper.startPage(page, size);
        List<Article> articles = articleMapper.listArticleByUId(uid, sort);
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        return Result.success(pageInfo.getList());
    }

    @Override
    public Result insertArticle(ArticleParam articleParam) {
        LocalDateTime dateTime = LocalDateTime.now();
        Article article = new Article();
        article.setTitle(articleParam.getTitle());
        article.setContent(articleParam.getContent());
        article.setUserId(1);
        article.setCreateTime(dateTime);
        article.setUpdateTime(dateTime);
        int i = articleMapper.insertArticle(article);
        return i > 0 ? Result.success() : Result.fail(ResultEnum.INSERT_ARTICLE_ERROR);
    }

    @Override
    public Result getArticleById(Integer id) {
        Article article = articleMapper.getArticleById(id);
        return article != null ? Result.success(article) : Result.fail(ResultEnum.GET_ARTICLE_ERROR);
    }

    @Override
    public Result deleteArticleById(Integer id) {
        int i = articleMapper.deleteArticleById(id);
        return i > 0 ? Result.success() : Result.fail(ResultEnum.DELETE_ARTICLE_ERROR_2);

    }

    @Override
    public Result updateArticleById(Integer id, ArticleParam articleParam) {
        Article article = new Article();
        article.setPostId(id);
        article.setTitle(articleParam.getTitle());
        article.setContent(articleParam.getContent());
        article.setUpdateTime(LocalDateTime.now());
        int i = articleMapper.updateArticleById(article);
        return i > 0 ? Result.success() : Result.fail(ResultEnum.UPDATE_ARTICLE_ERROR_1);
    }
}
