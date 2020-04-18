package wang.ismy.blb.impl.product.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/18 9:39
 */
@Service
public class ProductEvalServiceImpl implements ProductEvalService{
    @Override
    public Map<Long, BigDecimal> getPositiveRating(List<Long> productIdList) {
        // TODO mock
        return productIdList.stream()
                .collect(Collectors.toMap(l->l,l->new BigDecimal(10L)));
    }
}
