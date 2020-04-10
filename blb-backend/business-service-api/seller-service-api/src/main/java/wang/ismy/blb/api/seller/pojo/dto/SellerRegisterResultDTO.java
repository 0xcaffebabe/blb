package wang.ismy.blb.api.seller.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/10 10:18
 */
@Data
@ApiModel("商家注册结果")
public class SellerRegisterResultDTO {
    @ApiModelProperty("注册欢迎语")
    private String greeting;
    @ApiModelProperty("商家注册数量")
    private Long sellerNumber;
}
