package wang.ismy.blb.api.upload;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/4/11 15:20
 */
@Api(tags = "上传服务")
public interface UploadApi {

    /**
     * 文件上传
     * @param file
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping("")
    Result<String> upload(MultipartFile file);
}
