package wang.ismy.blb.api.message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wang.ismy.blb.common.result.Result;


/**
 * @author MY
 * @date 2020/4/11 15:03
 */
@Api(tags = "消息服务接口")
public interface MessageApi {

    /**
     * 发送一条消息
     * @param message
     * @return
     */
    @ApiOperation("发送一条消息")
    @PostMapping("")
    Result<Void> sendMessage(@RequestBody Message message);
}
