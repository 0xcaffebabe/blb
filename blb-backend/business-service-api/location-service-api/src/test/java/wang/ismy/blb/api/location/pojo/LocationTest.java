package wang.ismy.blb.api.location.pojo;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void testNormal(){
        String str = "117.708708,24.052089";
        BigDecimal longitude = new BigDecimal("117.708708");
        BigDecimal latitude = new BigDecimal("24.052089");
        Location location = Location.from(str);
        assertEquals(longitude,location.getLongitude());
        assertEquals(latitude,location.getLatitude());
    }

    @Test

    void testException() {
        assertThrows(IllegalArgumentException.class,()->{
            String str = "117.70870824.052089";
            Location.from(str);
        });
    }
}