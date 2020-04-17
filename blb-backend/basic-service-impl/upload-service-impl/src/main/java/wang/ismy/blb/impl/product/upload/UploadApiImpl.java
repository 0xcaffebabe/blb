package wang.ismy.blb.impl.product.upload;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.github.tobato.fastdfs.service.GenerateStorageClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.blb.api.upload.UploadApi;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;

import java.io.IOException;

/**
 * @author MY
 * @date 2020/4/14 11:04
 */
@RestController
@Slf4j
@AllArgsConstructor
public class UploadApiImpl implements UploadApi {

    private final FastFileStorageClient storageClient;
    private ApplicationContext applicationContext;
    @Override
    public Result<String> upload(MultipartFile file) {
        try {
            String fileNameExtension = getFileNameExtension(file.getOriginalFilename());
            if (StringUtils.isEmpty(fileNameExtension)){
                log.warn("文件上传无法获取到后缀名,{}",file.getOriginalFilename());
                throw new BlbException(ResultCode.FILE_EXTENSION_NAME_NOT_EXISTS);
            }
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),fileNameExtension, null);
            return Result.success(applicationContext.getEnvironment().getProperty("image.address")+storePath.getFullPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileNameExtension(String name){
        if (StringUtils.isEmpty(name)){
            return "";
        }
        return name.substring(name.lastIndexOf(".")+1);
    }
}
