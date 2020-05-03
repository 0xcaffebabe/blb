package wang.ismy.blb.impl.seller.service.impl;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.auth.User;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.seller.pojo.SellerDO;
import wang.ismy.blb.api.seller.pojo.SellerInfoDO;
import wang.ismy.blb.api.seller.pojo.SellerInfoDTO;
import wang.ismy.blb.api.seller.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerCreateDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerRegisterResultDTO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.SnowFlake;
import wang.ismy.blb.impl.seller.client.AuthApiClient;
import wang.ismy.blb.impl.seller.repository.SellerInfoRepository;
import wang.ismy.blb.impl.seller.repository.SellerRepository;
import wang.ismy.blb.impl.seller.service.SellerService;

import javax.transaction.Transactional;
import javax.validation.Valid;

/**
 * @author MY
 * @date 2020/5/3 8:52
 */
@Service
@AllArgsConstructor
@Setter
@Slf4j
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final SellerInfoRepository infoRepository;
    private final SnowFlake snowFlake;
    private AuthApiClient authApiClient;
    @Override
    public SellerInfoDTO getSellerInfo(Long sellerId) {
        SellerInfoDO infoDO = infoRepository.findById(sellerId).orElseThrow(()->new BlbException("商家不存在"));
        SellerInfoDTO dto = new SellerInfoDTO();
        dto.setPhone(infoDO.getConcatNumber());
        dto.setRealName(infoDO.getRealName());
        return dto;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public SellerRegisterResultDTO register(@Valid SellerCreateDTO sellerCreateDTO) {
        if (sellerRepository.existsByUsername(sellerCreateDTO.getUsername())){
            log.warn("用户名{}已存在",sellerCreateDTO.getUsername());
        }
        SellerDO sellerDO = new SellerDO();
        sellerDO.setEnable(true);
        sellerDO.setSellerId(snowFlake.nextId());
        sellerDO.setUsername(sellerCreateDTO.getUsername());
        sellerDO.setPassword(DigestUtils.md5Hex(sellerCreateDTO.getPassword()));
        sellerRepository.save(sellerDO);

        SellerInfoDO infoDO = new SellerInfoDO();
        infoDO.setSellerId(sellerDO.getSellerId());
        infoRepository.save(infoDO);

        SellerRegisterResultDTO result = new SellerRegisterResultDTO();
        result.setGreeting("欢迎成为饱了吧商家");
        result.setSellerNumber(sellerRepository.count());
        return result;
    }

    @Override
    public LoginResultDTO login(String loginStr, String password) {
        LoginResultDTO result = new LoginResultDTO();
        SellerDO sellerDO = sellerRepository.findByUsername(loginStr);
        if (sellerDO == null){
            result.setStatus(1);
            return result;
        }
        if (!sellerDO.getPassword().equals(DigestUtils.md5Hex(password))){
            result.setStatus(2);
            return result;
        }
        User user = new User();
        user.setUsername(loginStr);
        user.setUserId(sellerDO.getSellerId());
        user.setUserType(UserTypeEnum.SELLER.getType());
        var authRes = authApiClient.auth(user);
        if (!authRes.getSuccess()){
            log.warn("调用认证服务失败:{}",authRes);
            throw new BlbException("调用认证服务失败");
        }
        result.setStatus(0);
        result.setGreeting("登录成功");
        result.setToken(authRes.getData());
        return result;
    }
}
