package wang.ismy.blb.api.product.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.awt.*;

/**
 * @author MY
 * @date 2020/4/10 9:08
 */
@Data
@ApiModel("商品ID与规格ID")
public class CartProductGetDTO {
    @ApiModelProperty("商品ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId;
    @ApiModelProperty("规格ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long specId;

    public static CartProductGetDTO create(Long productId,Long specId){
        CartProductGetDTO dto = new CartProductGetDTO();
        dto.productId = productId;
        dto.specId = specId;
        return dto;
    }
}
