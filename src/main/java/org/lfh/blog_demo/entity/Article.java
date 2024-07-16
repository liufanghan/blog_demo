package org.lfh.blog_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    private Integer postId;
    private String title;
    private String content;
    private Integer userId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
