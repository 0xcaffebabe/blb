package wang.ismy.blb.api.shop.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
    private Long categoryId;
    @ApiModelProperty("店铺名称")
    private String shopName ;
    @ApiModelProperty("店铺标志")
    private String shopLogo ;
    @ApiModelProperty("店铺地址")
    private String shopAddress ;
    @ApiModelProperty("店铺联系电话")
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
