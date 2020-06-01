package wang.ismy.blb.aggregation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.aggregation.client.OrderApiClient;

/**
 * @author MY
 * @date 2020/6/1 9:02
 */
@RestController
@AllArgsConstructor
public class RiderAggApi {
    private final OrderApiClient orderApiClient;

    @GetMapping("test")
    public String test(){
        return orderApiClient.getOrder(1L).toString();
    }
}
