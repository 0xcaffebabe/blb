package wang.ismy.blb.api.product.pojo.dto.eval;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author MY
 * @date 2020/10/20 20:49
 */
@Data
public class WordCloudEntry {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String content;
    private Integer count;
    private Boolean positive;
}
