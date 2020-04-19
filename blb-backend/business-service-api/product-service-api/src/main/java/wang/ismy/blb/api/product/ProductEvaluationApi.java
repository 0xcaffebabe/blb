package wang.ismy.blb.api.product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.product.pojo.dto.eval.ConsumerEvalItem;
import wang.ismy.blb.api.product.pojo.dto.eval.EvalCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.eval.ShopEvalInfo;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;

import java.math.BigDecimal;

/**
 * @author MY
 * @date 2020/4/10 9:34
 */
@Api(tags = "商品评价服务")
@RequestMapping(value = "v1/api/eval",produces = MediaType.APPLICATION_JSON_VALUE)
public interface ProductEvaluationApi {

    /**
     * 获取店铺评分
     *
     * @param shopId
     * @return
     */
    @ApiOperation("获取店铺评分")
    @ApiImplicitParam(paramType = "path", name = "shopId", dataType = "Long", required = true, value = "店铺ID")
    @GetMapping("shop/{shopId}")
    Result<BigDecimal> getShopEval(@PathVariable("shopId") Long shopId);

    /**
     * 获取店铺评价信息
     *
     * @param shopId
     * @return
     */
    @ApiOperation("获取店铺评价信息")
    @ApiImplicitParam(paramType = "path", name = "shopId", dataType = "Long", required = true, value = "店铺ID")
    @GetMapping("shop/info/{shopId}")
    Result<ShopEvalInfo> getShopEvalInfo(@PathVariable("shopId") Long shopId);

    /**
     * 分页获取店铺评价列表
     * @param shopId
     * @param pageable
     * @return
     */
    @ApiOperation("获取店铺评价列表")
    @ApiImplicitParam(paramType = "path", name = "shopId", dataType = "Long", required = true, value = "店铺ID")
    @GetMapping("shop/list/{shopId}")
    Result<Page<ConsumerEvalItem>> getShopEvalList(@PathVariable("shopId") Long shopId, Pageable pageable);

    /**
     * 消费者添加评价
     * @param token 消费者token
     * @param evalCreateDTO
     * @return
     */
    @ApiOperation("添加评价")
    @PostMapping("")
    Result<Void> createEval(@RequestHeader(SystemConstant.TOKEN) String token,
                            @RequestBody EvalCreateDTO evalCreateDTO);
}
