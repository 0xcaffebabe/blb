package wang.ismy.blb.impl.product.service;

import java.util.List;

/**
 * 定义es服务提供的接口
 * @author MY
 * @date 2020/4/19 11:56
 */
public interface ElasticsearchService {

    /**
     * 根据传入的文本生成词云
     * @param text
     * @return
     */
    List<String> getWordCloud(String text);
}
