package wang.ismy.blb.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MY
 * @date 2020/4/13 10:01
 */

public interface ServiceName {
    long DATA_CENTER_ID = 1L;
    String CACHE_SERVICE = "cache-service";
    String AUTH_SERVICE = "auth-service";
    String UPLOAD_SERVICE = "upload-service";
    String CART_SERVICE = "cart-service";
    String PRODUCT_SERVICE = "product-service";
    long PRODUCT_SERVICE_ID = 4L;
    String ORDER_SERVICE = "order-service";
    long ORDER_SERVICE_ID = 5L;
    String SHOP_SERVICE = "shop-service";
    long SHOP_SERVICE_ID=6L;
    String CONSUMER_SERVICE = "consumer-service";
    long CONSUMER_SERVICE_ID = 7L;
    String SELLER_SERVICE = "seller-service";
    long SELLER_SERVICE_ID = 8L;
    String PAY_SERVICE = "pay-service";
    long PAY_SERVICE_ID = 9L;
    String RIDER_SERVICE = "rider-service";
    long RIDER_SERVICE_ID = 10L;
}
