package wang.ismy.blb.impl.order.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.order.pojo.dto.OrderQuery;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderDetailDTO;
import wang.ismy.blb.api.order.pojo.dto.consumer.ConsumerOrderItemDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.order.service.OrderSellerService;
import wang.ismy.blb.impl.order.service.OrderService;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OrderSellerApiImplTest {

    private MockMvc mockMvc;
    private OrderSellerService sellerService;


    @BeforeEach
    public void setUp() throws Exception {
        sellerService = mock(OrderSellerService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderSellerApiImpl(sellerService)).build();
    }

    @Test
    void getSellerOrderList() throws Exception {
        OrderQuery query = MockUtils.create(OrderQuery.class);
        Pageable pageable = Pageable.of(1L,5L);
        String token = "token";
        var page = new Page<>(5L,MockUtils.create(ConsumerOrderItemDTO.class,5));
        when(sellerService.getSellerOrderList(any(),any(),any())).thenReturn(page);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(Result.success(page));
        System.out.println(json);
        mockMvc.perform(get("/v1/api/seller")
                .header(SystemConstant.TOKEN,token)
                .param("status",query.getStatus())
                .param("date",query.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .param("page",pageable.getPage().toString())
                .param("size",pageable.getSize().toString())
        )
                .andDo(result -> {
                    System.out.println(result.getResponse().getContentAsString());
                })
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    void getSellerOrderDetail() throws Exception {
        String token = "token";
        Long orderId = 1L;
        ConsumerOrderDetailDTO detailDTO = MockUtils.create(ConsumerOrderDetailDTO.class);
        when(sellerService.getSellerOrderDetail(eq(token),eq(orderId))).thenReturn(detailDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc.perform(get("/v1/api/seller/detail/"+orderId)
                .header(SystemConstant.TOKEN,token)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Result.success(detailDTO))));
    }
}