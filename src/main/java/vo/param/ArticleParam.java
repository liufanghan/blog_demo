package vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "文章参数实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleParam {
    @Schema(description = "文章题目")
    private String title;
    @Schema(description = "文章内容")
    private String content;
}
