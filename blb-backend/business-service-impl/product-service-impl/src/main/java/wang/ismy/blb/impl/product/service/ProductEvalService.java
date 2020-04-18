package wang.ismy.blb.impl.product.service;

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
}
