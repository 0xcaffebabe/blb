package wang.ismy.blb.api.rider.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 展示根据订单号取得的骑手信息
 * @author MY
 * @date 2020/4/8 13:11
 */
@Data
@ApiModel("展示根据订单号取得的骑手信息DTO")
public class OrderRiderDTO {
    @ApiModelProperty("骑手ID")
    private Long riderId;
    @ApiModelProperty("骑手电话号码")
    private String phone;
    @ApiModelProperty("骑手头像")
    private String avatar;
    @ApiModelProperty("骑手真实姓名")
    private String realName;
    /** 性别，T为男 F为女 */
    @ApiModelProperty(value = "骑手性别")
    private Boolean gender;
}
