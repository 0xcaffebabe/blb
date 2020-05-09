package wang.ismy.blb.impl.product.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.product.ProductEvaluationApi;
import wang.ismy.blb.api.product.pojo.dto.eval.ConsumerEvalItem;
import wang.ismy.blb.api.product.pojo.dto.eval.EvalCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.eval.ShopEvalInfo;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.product.service.ProductEvalService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2020/4/19 8:45
 */
@AllArgsConstructor
@RestController
public class ProductEvaluationApiImpl implements ProductEvaluationApi {
    private final ProductEvalService productEvalService;
    @Override
    public Result<BigDecimal> getShopEval(Long shopId) {
        BigDecimal evalScore = productEvalService.getShopEval(shopId);
        return Result.success(evalScore);
    }

    @Override
    public Result<Map<Long,BigDecimal>> getShopEval(List<Long> shopIdList) {
        return Result.success(productEvalService.getShopEval(shopIdList));
    }

    @Override
    public Result<ShopEvalInfo> getShopEvalInfo(Long shopId) {
        ShopEvalInfo info = productEvalService.getShopEvalInfo(shopId);
        return Result.success(info);
    }

    @Override
    public Result<Page<ConsumerEvalItem>> getShopEvalList(Long shopId, Long page,Long size) {
        Page<ConsumerEvalItem> ret = productEvalService.getShopEvalList(shopId,Pageable.of(page, size));
        return Result.success(ret);
    }

    @Override
    public Result<Void> createEval(String token,EvalCreateDTO evalCreateDTO) {
        productEvalService.createEval(token,evalCreateDTO);
        return Result.success();
    }
}
