package wang.ismy.blb.impl.cache;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.impl.cache.CacheApiImpl;
import wang.ismy.blb.impl.cache.service.CacheService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CacheApiImplTest {

    private MockMvc mockMvc;
    private CacheService mockCacheService;

    @BeforeEach
    public void setUp() throws Exception {
        mockCacheService = mock(CacheService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CacheApiImpl(mockCacheService)).build();
    }

    @Test
    void testPut() throws Exception {
        String key = "key";
        String data = "value";
        long ttl = 60L;
        mockMvc.perform(put("/v1/api/" + key + "?ttl=" + ttl).accept(MediaType.ALL).content(data))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(Result.success())));
        verify(mockCacheService).put(eq(key), eq(data), eq(ttl));
    }

    @Test
    void testGet() throws Exception {
        String key = "key";
        String data = "value";
        when(mockCacheService.get(eq(key))).thenReturn(data);
        mockMvc.perform(get("/v1/api/" + key))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(Result.success(data))));
    }

    @Test
    void testExists() throws Exception {
        String key = "key";
        when(mockCacheService.exists(eq(key))).thenReturn(true);
        mockMvc.perform(get("/v1/api/" + key+"/exists"))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(Result.success(true))));
    }

    @Test
    void testDelete() throws Exception {
        String key = "key";
        mockMvc.perform(delete("/v1/api/" + key))
                .andExpect(status().isOk())
                .andExpect(content().json(new Gson().toJson(Result.success())));
        verify(mockCacheService).delete(eq(key));
    }
}