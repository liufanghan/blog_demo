package org.lfh.blog_demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.lfh.blog_demo.aop.PermissionAnnotation;
import org.lfh.blog_demo.service.ArticleService;
import org.lfh.blog_demo.util.Result;
import org.springframework.web.bind.annotation.*;
import org.lfh.blog_demo.vo.param.ArticleParam;


@Tag(name = "ArticleController", description = "文章控制器")
@RestController
@RequestMapping("/api/posts/")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @Operation(summary = "获取某个用户的所有文章列表", description = "获取某个用户的所有文章列表 支持分页/按创建时间正/倒叙")
    @Parameters({
            @Parameter(name = "uid", description = "用户ID", required = true),
            @Parameter(name = "page", description = "页数,默认为1"),
            @Parameter(name = "size", description = "每页大小,默认为10"),
            @Parameter(name = "sort", description = "1代表降序,0代表升序;默认为1"),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "查询成功"),
            @ApiResponse(responseCode = "513", description = "页数或者页码出错")
    })
    @GetMapping
    public Result listArticleById(@RequestParam Integer uid,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "1") Integer sort) {
        return articleService.listArticleById(uid, page, size, sort);
    }


    @Operation(summary = "创建新文章", description = "创建新文章（需要登录）")
    @Parameters({
            @Parameter(name = "articleParam", description = "文章题目和内容", required = true),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "新增文章成功"),
            @ApiResponse(responseCode = "501", description = "新增文章失败"),
            @ApiResponse(responseCode = "401", description = "未登录"),
    })
    @PostMapping
    public Result addArticle(@RequestBody ArticleParam articleParam) {
        return articleService.insertArticle(articleParam);
    }


    @Operation(summary = "获取单篇文章详情", description = "获取单篇文章详情")
    @Parameters({
            @Parameter(name = "id", description = "文章ID", required = true),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "新增文章成功"),
            @ApiResponse(responseCode = "502", description = "未找到指定文章"),
    })
    @GetMapping("{id}")
    public Result getArticleById(@PathVariable Integer id) {
        return articleService.getArticleById(id);
    }


    @Operation(summary = "更新文章", description = "更新文章(需要登录和权限判断)")
    @Parameters({
            @Parameter(name = "id", description = "文章ID", required = true),
            @Parameter(name = "articleParam", description = "更新的文章题目和内容", required = true),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "新增文章成功"),
            @ApiResponse(responseCode = "503", description = "更新文章失败"),
            @ApiResponse(responseCode = "510", description = "无权限"),
            @ApiResponse(responseCode = "401", description = "未登录"),
    })
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
    @Operation(summary = "删除文章", description = "删除指定文章ID的文章(需要登录和权限判断)")
    @Parameters({
            @Parameter(name = "id", description = "文章ID", required = true),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "删除文章成功"),
            @ApiResponse(responseCode = "512", description = "删除文章失败"),
            @ApiResponse(responseCode = "510", description = "无权限"),
            @ApiResponse(responseCode = "401", description = "未登录"),
    })
    @DeleteMapping("{id}")
    @PermissionAnnotation
    public Result deleteArticleById(@PathVariable Integer id) {
        return articleService.deleteArticleById(id);
    }

}
