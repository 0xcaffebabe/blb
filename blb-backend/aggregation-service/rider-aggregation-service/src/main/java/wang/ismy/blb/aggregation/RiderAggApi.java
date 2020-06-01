package wang.ismy.blb.aggregation;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.aggregation.client.OrderApiClient;
import wang.ismy.blb.aggregation.client.RiderApiClient;
import wang.ismy.blb.api.rider.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.RegisterDTO;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/6/1 9:02
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
public class RiderAggApi {
    private final RiderApiClient riderApiClient;

    @PostMapping("register")
    public Result<String> register(@RequestBody RegisterDTO registerDTO){
        return riderApiClient.register(registerDTO);
    }

    @PostMapping("login")
    public Result<LoginResultDTO> login(@RequestParam String username,@RequestParam String password){
        return riderApiClient.login(username,password);
    }


}
