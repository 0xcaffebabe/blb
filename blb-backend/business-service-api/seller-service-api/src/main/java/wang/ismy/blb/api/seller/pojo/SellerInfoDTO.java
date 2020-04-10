package wang.ismy.blb.api.seller.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MY
 * @date 2020/4/10 10:07
 */
@Data
@ApiModel("商家信息")
public class SellerInfoDTO {
    @ApiModelProperty("商家电话")
    private String phone;
    @ApiModelProperty("商家真实姓名")
    private String realName;
}
