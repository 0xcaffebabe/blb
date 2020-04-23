package wang.ismy.blb.impl.product.service;

import wang.ismy.blb.api.product.pojo.dto.eval.ConsumerEvalItem;
import wang.ismy.blb.api.product.pojo.dto.eval.EvalCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.eval.ShopEvalInfo;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 定义商品评价服务应该提供的接口
 * @author MY
 * @date 2020/4/18 9:28
 */
public interface ProductEvalService {

    /**
     * 批量获取商品好评率
     * @param productIdList
     * @return
     */
    Map<Long, BigDecimal> getPositiveRating(List<Long> productIdList);

    /**
     * 根据店铺的店铺ID获取店铺评价分数
     * @param shopId
     * @return
     */
    BigDecimal getShopEval(Long shopId);

    /**
     * 获取店铺的评价信息
     * @param shopId
     * @return
     */
    ShopEvalInfo getShopEvalInfo(Long shopId);

    /**
     * 分页获取店铺评价列表
     * @param shopId
     * @param pageable
     * @return
     */
    Page<ConsumerEvalItem> getShopEvalList(Long shopId, Pageable pageable);

    /**
     * 消费者提交评价
     * @param token
     * @param evalCreateDTO
     */
    void createEval(String token, EvalCreateDTO evalCreateDTO);

    /**
     * 批量获取店铺评分
     * @param shopIdList
     * @return
     */
    Map<Long,BigDecimal> getShopEval(List<Long> shopIdList);
}
