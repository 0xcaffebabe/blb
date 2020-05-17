package wang.ismy.blb.api.upload;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/4/11 15:20
 */
@Api(tags = "上传服务")
@RequestMapping("v1/api")
public interface UploadApi {

    /**
     * 文件上传
     * @param file
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<String> upload(MultipartFile file);

    class Fallback implements UploadApi{

        @Override
        public Result<String> upload(MultipartFile file) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 文件上传服务 上传接口失败");
        }
    }
}
