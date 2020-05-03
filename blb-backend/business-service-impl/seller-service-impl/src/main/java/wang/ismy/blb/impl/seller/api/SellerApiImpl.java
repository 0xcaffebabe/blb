package wang.ismy.blb.impl.seller.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.seller.SellerApi;
import wang.ismy.blb.api.seller.pojo.SellerInfoDTO;
import wang.ismy.blb.api.seller.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerCreateDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerRegisterResultDTO;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.seller.service.SellerService;

/**
 * @author MY
 * @date 2020/5/3 8:38
 */
@RestController
@AllArgsConstructor
public class SellerApiImpl implements SellerApi {
    private final SellerService sellerService;
    @Override
    public Result<SellerInfoDTO> getSellerInfo(Long sellerId) {
        return Result.success(sellerService.getSellerInfo(sellerId));
    }

    @Override
    public Result<SellerRegisterResultDTO> register(SellerCreateDTO sellerCreateDTO) {
        return Result.success(sellerService.register(sellerCreateDTO));
    }

    @Override
    public Result<LoginResultDTO> login(String loginStr, String password) {
        return Result.success(sellerService.login(loginStr,password));
    }
}
