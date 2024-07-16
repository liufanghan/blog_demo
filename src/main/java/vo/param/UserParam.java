package vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "用户参数实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserParam {
    @Schema(description = "用户姓名")
    private String userName;
    @Schema(description = "用户密码")
    private String password;
    @Schema(description = "用户邮箱")
    private String email;
}
