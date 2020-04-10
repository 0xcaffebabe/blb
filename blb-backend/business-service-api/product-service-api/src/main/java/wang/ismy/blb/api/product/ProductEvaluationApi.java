package wang.ismy.blb.api.product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.product.pojo.dto.eval.ConsumerEvalItem;
import wang.ismy.blb.api.product.pojo.dto.eval.EvalCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.eval.ShopEvalInfo;
import wang.ismy.blb.common.Page;
import wang.ismy.blb.common.Result;

import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/10 9:34
 */
@Api(tags = "商品评价服务")
@RequestMapping("eval")
public interface ProductEvaluationApi {

    /**
     * 获取店铺评分
     * @param shopId
     * @return
     */
    @ApiOperation("获取店铺评分")
    @ApiImplicitParam(paramType = "path", name = "shopId", dataType = "Long", required = true, value = "店铺ID")
    @GetMapping("shop/{shopId}")
    Result<BigDecimal> getShopEval(@PathVariable("shopId") Long shopId);

    /**
     * 获取店铺评价信息
     * @param shopId
     * @return
     */
    @ApiOperation("获取店铺评价信息")
    @ApiImplicitParam(paramType = "path", name = "shopId", dataType = "Long", required = true, value = "店铺ID")
    @GetMapping("shop/info/{shopId}")
    Result<ShopEvalInfo> getShopEvalInfo(@PathVariable("shopId") Long shopId);

    /**
     * 获取店铺评价列表
     * @param shopId
     * @return
     */
    @ApiOperation("获取店铺评价列表")
    @ApiImplicitParam(paramType = "path", name = "shopId", dataType = "Long", required = true, value = "店铺ID")
    @GetMapping("shop/list/{shopId}")
    Result<Page<ConsumerEvalItem>> getShopEvalList(@PathVariable("shopId") Long shopId);

    /**
     * 消费者添加评价
     * @param evalCreateDTO
     * @return
     */
    @ApiOperation("添加评价")
    @PostMapping("")
    Result<Void> createEval(@RequestBody EvalCreateDTO evalCreateDTO);
}
