package wang.ismy.blb.impl.rider.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalInfoDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalItemDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalSubmitDTO;
import wang.ismy.blb.api.rider.pojo.entity.RiderInfoDO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.MockUtils;
import wang.ismy.blb.impl.rider.service.RiderEvaluationService;
import wang.ismy.blb.impl.rider.service.RiderService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RiderEvaluationApiImplTest {

    private MockMvc mockMvc;
    private RiderEvaluationService evaluationService;

    @BeforeEach
    public void setUp() throws Exception {
        evaluationService = mock(RiderEvaluationService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new RiderEvaluationApiImpl(evaluationService)).build();
    }
    @Test
    void addEvaluation() throws Exception{
        String token = "token";
        RiderEvalSubmitDTO dto = MockUtils.create(RiderEvalSubmitDTO.class);
        RiderEvalResultDTO result = MockUtils.create(RiderEvalResultDTO.class);
        when(evaluationService.addEvaluation(eq(token),eq(dto))).thenReturn(result);
        mockMvc.perform(post("/v1/api/eval")
                .contentType(MediaType.APPLICATION_JSON)
                .header(SystemConstant.TOKEN,token)
                .content(new ObjectMapper().writeValueAsBytes(dto))
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(result))));
    }

    @Test
    void getRiderEvalInfo() throws Exception {
        Long riderId = 1L;
        RiderEvalInfoDTO dto = MockUtils.create(RiderEvalInfoDTO.class);
        when(evaluationService.getRiderEvalInfo(riderId)).thenReturn(dto);

        mockMvc.perform(get("/v1/api/eval/info/"+riderId)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(Result.success(dto))));
    }

    @Test
    void getRiderEvalList() throws Exception {
        Long riderId = 1L;
        Pageable pageable = Pageable.of(1L,5L);
        var list = MockUtils.create(RiderEvalItemDTO.class,5);
        var page = new Page<>(5L,list);

        when(evaluationService.getRiderEvalList(eq(riderId),eq(pageable))).thenReturn(page);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc.perform(get("/v1/api/eval/"+riderId)
                .param("page",pageable.getPage().toString())
                .param("size",pageable.getSize().toString())
        )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Result.success(page))));
    }
}