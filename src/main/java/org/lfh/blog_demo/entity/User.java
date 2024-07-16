package org.lfh.blog_demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "用户实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Schema(description = "用户ID")
    private Integer userId;
    @Schema(description = "用户姓名")
    private String userName;
    @Schema(description = "用户密码")
    private String password;
    @Schema(description = "用户邮箱")
    private String email;
    @Schema(description = "用户注册时间")
    private LocalDateTime createTime;
    @Schema(description = "用户修改时间")
    private LocalDateTime updateTime;
}
