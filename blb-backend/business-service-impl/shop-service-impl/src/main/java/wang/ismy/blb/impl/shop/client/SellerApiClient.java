package wang.ismy.blb.impl.shop.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.seller.SellerApi;
import wang.ismy.blb.api.seller.pojo.SellerInfoDTO;
import wang.ismy.blb.common.enums.ServiceName;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/4/23 10:04
 */
@FeignClient(value = ServiceName.SELLER_SERVICE,fallback = SellerApiClient.Fallback.class)
public interface SellerApiClient extends SellerApi {
    /** mock todo*/
    @Component
    class Fallback extends SellerApi.Fallback implements SellerApiClient{
        @Override
        public Result<SellerInfoDTO> getSellerInfo(Long sellerId) {
            SellerInfoDTO dto = new SellerInfoDTO();
            dto.setRealName("ccc");
            dto.setPhone("123456");
            return Result.success(dto);
        }
    }
}
