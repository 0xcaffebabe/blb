package wang.ismy.blb.impl.shop.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import wang.ismy.blb.api.shop.pojo.ShopDO;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author MY
 * @date 2020/8/11 15:42
 */
@Service
@Slf4j
@Profile("dev")
public class ShopSearchService {

    @Value("${elasticsearch.url}")
    private String url;

    private RestHighLevelClient client;

    @PostConstruct
    void init(){
        client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create(url))
        );
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
        request.id("1");
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
        } catch (IOException e){
             e.printStackTrace();
        }
    }
}
