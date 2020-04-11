package wang.ismy.blb.api.cache;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonCacheSerializerTest {

    @Test
    @DisplayName("测试序列化")
    void testSerialization() throws Throwable {
        JsonCacheSerializer serializer = new JsonCacheSerializer();
        String str = serializer.serialization(Map.of("name", "cxk"));
        assertEquals("{\"name\":\"cxk\"}",str);
    }
    @Test
    @DisplayName("测试反序列化")
    void testDeserialization() throws Throwable {
        String str = "{\"name\":\"cxk\"}";
        Map<String,String> map = new JsonCacheSerializer().deserialization(str,Map.class);
        assertEquals("cxk",map.get("name"));
    }
}