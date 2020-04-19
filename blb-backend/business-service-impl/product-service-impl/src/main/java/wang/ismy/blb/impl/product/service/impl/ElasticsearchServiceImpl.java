package wang.ismy.blb.impl.product.service.impl;

import lombok.AllArgsConstructor;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.ismy.blb.impl.product.service.ElasticsearchService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2020/4/19 11:58
 */
@Service
@AllArgsConstructor
public class ElasticsearchServiceImpl implements ElasticsearchService {

    private final RestHighLevelClient restHighLevelClient;

    @Override
    public List<String> getWordCloud(String text) {
        AnalyzeRequest request = new AnalyzeRequest();
        request.analyzer("ik_smart");
        request.text(text);
        try {
            var response = restHighLevelClient.indices().analyze(request, RequestOptions.DEFAULT);
            return response.getTokens().stream()
                    .map(AnalyzeResponse.AnalyzeToken::getTerm)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
