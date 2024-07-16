package org.lfh.blog_demo.controller;

import jakarta.annotation.Resource;
import org.lfh.blog_demo.aop.PermissionAnnotation;
import org.lfh.blog_demo.service.ArticleService;
import org.lfh.blog_demo.util.Result;
import org.springframework.web.bind.annotation.*;
import vo.param.ArticleParam;

@RestController
@RequestMapping("/api/posts/")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    /**
     * 获取某个用户的所有文章列表 支持分页/按创建时间正/倒叙
     *
     * @param uid  用户ID
     * @param page 页数
     * @param size 每页大小
     * @param sort 按创建时间排序 1表示倒序,0表示正序
     * @return 结果列表
     */
    @GetMapping
    public Result listArticleById(@RequestParam Integer uid,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "2") int size,
                                  @RequestParam(defaultValue = "1") Integer sort) {
        return articleService.listArticleById(uid, page, size, sort);
    }

    /**
     * 创建新文章
     *
     * @param articleParam 文章数据(用户只需要填文章题目和内容)
     * @return 创建结果
     */
    @PostMapping
    public Result addArticle(@RequestBody ArticleParam articleParam) {
        return articleService.insertArticle(articleParam);
    }

    /**
     * 根据文章ID查询文章
     *
     * @param id 文章ID
     * @return 指定文章数据
     */
    @GetMapping("{id}")
    public Result getArticleById(@PathVariable Integer id) {
        return articleService.getArticleById(id);
    }

    /**
     * 更新文章（需要登录和权限判断）
     *
     * @param id           文章ID
     * @param articleParam 更新的内容
     * @return 更新后的文章数据
     */
    @PutMapping("{id}")
    @PermissionAnnotation
    public Result updateArticleById(@PathVariable Integer id, @RequestBody ArticleParam articleParam) {
        return articleService.updateArticleById(id, articleParam);
    }

    /**
     * 删除指定id的文章
     *
     * @param id 文章ID
     * @return 操作结果
     */
    @DeleteMapping("{id}")
    @PermissionAnnotation
    public Result deleteArticleById(@PathVariable Integer id) {
        return articleService.deleteArticleById(id);
    }

}
