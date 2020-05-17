package wang.ismy.blb.aggregation.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import wang.ismy.blb.api.upload.UploadApi;
import wang.ismy.blb.common.enums.ServiceName;

/**
 * @author MY
 * @date 2020/5/17 19:17
 */
@FeignClient(value = ServiceName.UPLOAD_SERVICE,fallback = UploadApiClient.Fallback.class)
public interface UploadApiClient extends UploadApi {
    @Component
    class Fallback extends UploadApi.Fallback implements UploadApiClient{}
}
