package org.lfh.blog_demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "文章实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Schema(description = "文章ID")
    private Integer postId;
    @Schema(description = "文章题目")
    private String title;
    @Schema(description = "文章内容")
    private String content;
    @Schema(description = "用户ID")
    private Integer userId;
    @Schema(description = "文章创建时间")
    private LocalDateTime createTime;
    @Schema(description = "文章更新时间")
    private LocalDateTime updateTime;
}
