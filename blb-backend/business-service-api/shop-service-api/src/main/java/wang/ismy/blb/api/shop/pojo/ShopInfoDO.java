package wang.ismy.blb.api.shop.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import wang.ismy.blb.common.BaseDO;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/9 11:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "tb_shop_info")
public class ShopInfoDO extends BaseDO implements Serializable {
    /**  */
    @Id
    private Long shopId ;
    /** 店铺名称 */
    private String shopName ;
    /** 店铺标志 */
    private String shopLogo ;
    /** 店铺地址 */
    private String shopAddress ;
    /** 店铺联系电话 */
    private String phone ;
    /** 店铺简介 */
    private String shopDesc ;
    /** 店铺标语 */
    private String shopSlogan ;
    /** 配送费 */
    private BigDecimal deliveryFee ;
    /** 起送价 */
    private BigDecimal startingPrice ;
    /** 店铺所在经纬度 */
    private String location ;
    /** 营业时间 */
    private String businessHour ;
    /** 营业执照图片地址 */
    private String businessLicense ;
    /** 餐饮服务许可证图片地址 */
    private String foodServiceLicense ;
}
