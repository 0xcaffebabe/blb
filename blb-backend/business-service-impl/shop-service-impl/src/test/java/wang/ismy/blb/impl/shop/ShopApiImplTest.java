package wang.ismy.blb.impl.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.shop.pojo.dto.ShopCreateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoUpdateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopItemDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.shop.api.ShopApiImpl;
import wang.ismy.blb.impl.shop.service.ShopService;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShopApiImplTest {

    private MockMvc mockMvc;
    private ShopService shopService;

    @BeforeEach
    public void setup() {
        shopService = mock(ShopService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ShopApiImpl(shopService)).build();
    }

    @Test
    void getNearbyShop() throws Exception {
        String location = "117,29";
        var list = MockUtils.create(ShopItemDTO.class,5);
        Page<ShopItemDTO> page = new Page<>(5L,list);
        Pageable pageable = Pageable.of(1L,5L);
        when(shopService.getNearbyShop(eq(location),eq(pageable))).thenReturn(page);

        mockMvc.perform(get("/v1/api/vicinity")
            .param("location",location)
                .param("page","1")
                .param("size","5")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(page))));
    }

    @Test
    void searchShop() throws Exception {
        String location = "117,29";
        String kw = "蔡徐坤";
        var list = MockUtils.create(ShopItemDTO.class,5);
        Page<ShopItemDTO> page = new Page<>(5L,list);
        Pageable pageable = Pageable.of(1L,5L);
        when(shopService.searchShop(eq(location),eq(kw),eq(pageable))).thenReturn(page);

        mockMvc.perform(get("/v1/api/search")
                .param("location",location)
                .param("kw",kw)
                .param("page","1")
                .param("size","5")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(page))));
    }

    @Test
    void getShopInfo() throws Exception {
        Long shopId = 1L;
        ShopInfoDTO dto = MockUtils.create(ShopInfoDTO.class);
        when(shopService.getShopInfo(eq(shopId))).thenReturn(dto);

        mockMvc.perform(get("/v1/api/info/"+shopId))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(dto))));
    }

    @Test
    void testGetShopInfo() throws Exception {
        var shopIdList = List.of(1L,2L,3L);
        var shopMap = shopIdList.stream().collect(Collectors.toMap(l->l,l->MockUtils.create(ShopInfoDTO.class)));
        when(shopService.getShopInfo(eq(shopIdList))).thenReturn(shopMap);

        mockMvc.perform(get("/v1/api/info/list")
        .param("shopIdList",shopIdList.get(0).toString(),shopIdList.get(1).toString(),shopIdList.get(2).toString())
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(shopMap))));
    }

    @Test
    void addShop() throws Exception {
        String token = "token";
        ShopCreateDTO dto = MockUtils.create(ShopCreateDTO.class);
        when(shopService.addShop(eq(token),eq(dto))).thenReturn("1");

        mockMvc.perform(post("/v1/api")
                .header(SystemConstant.TOKEN,token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success("1"))));

    }

    @Test
    void updateShopInfo() throws Exception {
        Long shopId = 1L;
        String token = "token";
        ShopInfoUpdateDTO updateDTO = MockUtils.create(ShopInfoUpdateDTO.class);

        mockMvc.perform(put("/v1/api/info/"+shopId)
                .header(SystemConstant.TOKEN,token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(updateDTO))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success())));

        verify(shopService).updateShopInfo(eq(token),eq(shopId),eq(updateDTO));
    }

    @Test
    void getShopBySeller() throws Exception {
        Long sellerId = 1L;
        ShopInfoDTO dto = MockUtils.create(ShopInfoDTO.class);
        when(shopService.getShopBySeller(sellerId)).thenReturn(dto);

        mockMvc.perform(get("/v1/api/seller/"+sellerId)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(dto))));
    }
}