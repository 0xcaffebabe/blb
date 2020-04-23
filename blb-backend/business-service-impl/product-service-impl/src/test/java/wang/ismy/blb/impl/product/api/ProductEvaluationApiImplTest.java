package wang.ismy.blb.impl.product.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.product.pojo.dto.eval.ConsumerEvalItem;
import wang.ismy.blb.api.product.pojo.dto.eval.EvalCreateDTO;
import wang.ismy.blb.api.product.pojo.dto.eval.ShopEvalInfo;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.product.service.ProductEvalService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductEvaluationApiImplTest {

    MockMvc mockMvc;
    ProductEvalService productEvalService;

    @BeforeEach
    public void setup() {
        productEvalService = mock(ProductEvalService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductEvaluationApiImpl(productEvalService)).build();
    }

    @Test
    void testGetShopEval() throws Exception {
        BigDecimal score = new BigDecimal(11L);
        Long shopId = 1L;
        String jsonStr = new ObjectMapper().writeValueAsString(Result.success(score));
        when(productEvalService.getShopEval(eq(shopId)))
                .thenReturn(score);
        mockMvc.perform(get("/v1/api/eval/shop/" + shopId))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStr));
    }

    @Test
    void  testGetShopEvalBatch() throws Exception {
        var idList = List.of(1L,2L);
        var map = Map.of(1L,new BigDecimal("3.5"),2L,new BigDecimal("3.6"));
        String jsonStr = new ObjectMapper().writeValueAsString(Result.success(map));
        when(productEvalService.getShopEval(eq(idList)))
                .thenReturn(map);
        mockMvc.perform(get("/v1/api/eval/shop/list")
                .param("shopIdList",idList.get(0).toString(),idList.get(1).toString())
        )
                .andExpect(status().isOk())
                .andExpect(content().json(jsonStr));
    }

    @Test
    void testGetShopEvalInfo() throws Exception {
        Long shopId = 1L;
        ShopEvalInfo info = MockUtils.create(ShopEvalInfo.class);
        when(productEvalService.getShopEvalInfo(eq(shopId)))
                .thenReturn(info);
        mockMvc.perform(get("/v1/api/eval/shop/info/" + shopId))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(new ObjectMapper().writeValueAsString(Result.success(info))));
    }

    @Test
    void testGetShopEvalList() throws Exception {
        Long shopId = 1L;
        Pageable pageable = Pageable.of(1L, 5L);
        var page = new Page<ConsumerEvalItem>(100L, MockUtils.create(ConsumerEvalItem.class, 5));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonStr = objectMapper.writeValueAsString(Result.success(page));
        when(productEvalService.getShopEvalList(eq(shopId), eq(pageable)))
                .thenReturn(page);
        mockMvc.perform(get("/v1/api/eval/shop/list/" + shopId)
                .param("page", "1").param("size", "5"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    System.out.println(result.getResponse().getContentAsString());
                })
                .andExpect(content()
                        .json(jsonStr));

    }

    @Test
    void testCreateEval() throws Exception {
        String token = "token";
        EvalCreateDTO createDTO = MockUtils.create(EvalCreateDTO.class);
        mockMvc.perform(post("/v1/api/eval")
                .contentType(MediaType.APPLICATION_JSON)
                .header(SystemConstant.TOKEN, token)
                .content(new ObjectMapper().writeValueAsBytes(createDTO))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));

        verify(productEvalService).createEval(eq(token),eq(createDTO));
    }
}