package wang.ismy.blb.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import wang.ismy.blb.common.BaseDO;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {

    @Test
    void parse() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        BaseDO baseDO = MockUtils.create(BaseDO.class);
        assertEquals(objectMapper.writeValueAsString(baseDO),JsonUtils.parse(baseDO));
    }
}