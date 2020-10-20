package wang.ismy.blb.api.product.pojo.dto.eval;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author MY
 * @date 2020/4/10 9:44
 */
@Data
@ApiModel("店铺评价信息")
public class ShopEvalInfo {
    @ApiModelProperty("综合评价分数")
    private BigDecimal rating;

    private Double winningRate;

    @ApiModelProperty("词云列表")
    private List<WordCloudEntry> wordCloud;
}
