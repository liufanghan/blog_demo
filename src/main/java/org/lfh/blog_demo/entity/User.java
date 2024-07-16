package org.lfh.blog_demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userId;
    private String userName;
    private String password;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
