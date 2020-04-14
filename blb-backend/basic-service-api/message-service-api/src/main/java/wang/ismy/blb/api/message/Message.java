package wang.ismy.blb.api.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MY
 * @date 2020/4/11 15:03
 */
@Data
@ApiModel("消息参数")
public class Message implements Serializable {
    /** 可选值:email weixin*/
    @ApiModelProperty(value = "消息类型")
    private String type;
    @ApiModelProperty("消息内容")
    private String content;
    @ApiModelProperty("邮箱或者openid")
    private String receiverId;
}
