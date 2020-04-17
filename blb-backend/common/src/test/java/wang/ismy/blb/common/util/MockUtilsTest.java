package wang.ismy.blb.common.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import wang.ismy.blb.common.BaseDO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MockUtilsTest {

    @Data
    public static class Person {
        private String username;
        private String password;
        private Long userId;
    }
    @Test
    void testCreate() {
        Person person = MockUtils.create(Person.class);
        assertTrue(person.getUsername().startsWith("username"));
        assertTrue(person.getPassword().startsWith("password"));
        assertNotNull(person.getUserId());
        log.info(person.toString());
    }

    @Test
    void testCreateList(){
        List<Person> people = MockUtils.create(Person.class, 5);
        assertEquals(5,people.size());
        for (int i = 0; i < 5; i++) {
            Person person = people.get(i);
            assertTrue(person.getUsername().startsWith("username"));
            assertTrue(person.getPassword().startsWith("password"));
            assertNotNull(person.getUserId());
        }
    }


}