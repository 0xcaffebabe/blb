package wang.ismy.blb.impl.seller.service;

import wang.ismy.blb.api.seller.pojo.SellerInfoDTO;
import wang.ismy.blb.api.seller.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerCreateDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerRegisterResultDTO;

/**
 * @author MY
 * @date 2020/5/3 8:40
 */
public interface SellerService {
    /**
     * 根据ID获取卖家信息
     * @param sellerId
     * @return
     */
    SellerInfoDTO getSellerInfo(Long sellerId);

    /**
     * 卖家注册
     * @param sellerCreateDTO
     * @return
     */
    SellerRegisterResultDTO register(SellerCreateDTO sellerCreateDTO);

    /**
     * 卖家登录
     * @param loginStr
     * @param password
     * @return
     */
    LoginResultDTO login(String loginStr, String password);

    /**
     * 根据用户名查询商家
     * @param username
     * @return
     */
    SellerInfoDTO getSellerInfo(String username);
}
