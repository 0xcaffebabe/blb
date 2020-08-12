package wang.ismy.blb.impl.shop.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.shop.pojo.ShopDO;
import wang.ismy.blb.api.shop.pojo.ShopInfoDO;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.impl.shop.service.ShopService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MY
 * @date 2020/8/11 15:42
 */
@Service
@Slf4j
public class ShopSearchService {

    @Value("${elasticsearch.url}")
    private String url;

    private RestHighLevelClient client;

    @PostConstruct
    void init(){
        client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create(url))
        );
        createShopIndex();
    }

    public void createShopIndex(){
        CreateIndexRequest request = new CreateIndexRequest("shop");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        request.mapping("{\n" +
                "  \"properties\": {\n" +
                "    \"id\": {\n" +
                "      \"type\": \"long\",\n" +
                "      \"index\": false,\n" +
                "      \"store\": true\n" +
                "    },\n" +
                "    \"shopName\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"index\": true,\n" +
                "      \"store\": true,\n" +
                "      \"analyzer\":\"ik_smart\"\n" +
                "    },\n" +
                "    \"shopDesc\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"index\": true,\n" +
                "      \"store\": true,\n" +
                "      \"analyzer\":\"ik_smart\"\n" +
                "    },\n" +
                "    \"shopAddress\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"index\": true,\n" +
                "      \"store\": true,\n" +
                "      \"analyzer\":\"ik_smart\"\n" +
                "    },\n" +
                "    \"shopSlogan\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"index\": true,\n" +
                "      \"store\": true,\n" +
                "      \"analyzer\":\"ik_smart\"\n" +
                "    }\n" +
                "  }\n" +
                "}",XContentType.JSON);
        try {
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (Exception e){
            if (e.getMessage().contains("already_exists")){
                log.warn("shop 索引已存在");
            }else {
                e.printStackTrace();
            }
        }
    }

    public void addShop(ShopDO shop){
        IndexRequest request = new IndexRequest("shop");
        request.id(shop.getShopId().toString());
        String jsonString = "{" +
                "\"shopId\":\"%s\"," +
                "\"shopName\":\"%s\"," +
                "\"shopAddress\":\"%s\"," +
                "\"shopDesc\":\"%s\"," +
                "\"shopSlogan\":\"%s\"" +
                "}";
        jsonString = String.format(jsonString,
                shop.getShopId(),
                shop.getShopInfo().getShopName(),
                shop.getShopInfo().getShopAddress(),
                shop.getShopInfo().getShopDesc(),
                shop.getShopInfo().getShopSlogan());
        request.source(jsonString, XContentType.JSON);
        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            log.info(indexResponse.toString());
        } catch (IOException e){
             e.printStackTrace();
        }
    }

    public List<ShopDO> search(String kw, Pageable pageable){
        SearchRequest searchRequest = new SearchRequest("shop");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.queryStringQuery(kw));
        searchSourceBuilder.from(pageable.getPage().intValue()-1);
        searchSourceBuilder.size(pageable.getSize().intValue());
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            List<ShopDO> ret = new ArrayList<>();
            for (SearchHit hit : hits) {
                ShopDO shop = new ShopDO();
                shop.setShopId(Long.parseLong(hit.getSourceAsMap().get("shopId").toString()));
                ret.add(shop);
            }
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return List.of();
    }


}
