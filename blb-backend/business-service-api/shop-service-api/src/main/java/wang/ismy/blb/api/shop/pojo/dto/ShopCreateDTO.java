package wang.ismy.blb.api.shop.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.lang.ref.PhantomReference;
import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/9 13:24
 */
@Data
public class ShopCreateDTO {
    @ApiModelProperty("卖家ID")
    private Long sellerId;
    @ApiModelProperty("店铺所属分类")
    @NotEmpty(message = "商品分类ID不得为空")
    private Long categoryId;
    @NotEmpty(message = "店铺名称不得为空")
    @ApiModelProperty("店铺名称")
    private String shopName ;
    @NotEmpty(message = "店铺标志不得为空")
    @ApiModelProperty("店铺标志")
    private String shopLogo ;
    @NotEmpty(message = "店铺地址不得为空")
    @ApiModelProperty("店铺地址")
    private String shopAddress ;
    @ApiModelProperty("店铺联系电话")
    @NotEmpty(message = "店铺联系电话不得为空")
    private String phone ;
    @ApiModelProperty("店铺简介")
    private String shopDesc ;
    @ApiModelProperty("店铺标语")
    private String shopSlogan ;
    @ApiModelProperty("配送费")
    private BigDecimal deliveryFee ;
    @ApiModelProperty("起送价")
    private BigDecimal startingPrice ;
    @ApiModelProperty("店铺经纬度")
    private String location ;
    @ApiModelProperty("营业时间")
    private String businessHour ;
    @ApiModelProperty("营业执照")
    private String businessLicense ;
    @ApiModelProperty("餐饮服务许可证")
    private String foodServiceLicense ;
}
