package wang.ismy.blb.api.seller.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/10 10:16
 */
@Data
@ApiModel("商家注册参数")
public class SellerCreateDTO {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("身份证号")
    private String idNumber;
    @ApiModelProperty("店铺所属分类")
    private Long categoryId;
    @ApiModelProperty("店铺名称")
    private String shopName ;
    @ApiModelProperty("店铺标志")
    private String shopLogo ;
    @ApiModelProperty("店铺地址")
    private String shopAddress ;
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
