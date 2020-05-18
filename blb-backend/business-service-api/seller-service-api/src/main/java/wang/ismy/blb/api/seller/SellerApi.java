package wang.ismy.blb.api.seller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.seller.pojo.SellerInfoDTO;
import wang.ismy.blb.api.seller.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerCreateDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerRegisterResultDTO;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/4/10 10:03
 */
@Api(tags = "商家服务接口")
@RequestMapping(value = "v1/api",produces = MediaType.APPLICATION_JSON_VALUE)
public interface SellerApi {

    /**
     * 获取商家信息
     * @param sellerId
     * @return
     */
    @ApiOperation("获取商家信息")
    @ApiImplicitParam(paramType = "path", name = "sellerId", dataType = "Long", required = true, value = "商家ID")
    @GetMapping("info/{sellerId}")
    Result<SellerInfoDTO> getSellerInfo(@PathVariable("sellerId") Long sellerId);

    /**
     * 商家注册
     * @param sellerCreateDTO
     * @return
     */
    @ApiOperation("商家注册")
    @PostMapping("register")
    Result<SellerRegisterResultDTO> register(@RequestBody SellerCreateDTO sellerCreateDTO);

    /**
     * 商家登录接口
     *
     * @param loginStr 登录字符串（可以是用户名、邮箱、手机号）
     * @param password 登录密码
     * @return 登录结果，如果登录成功token会携带数据
     */
    @PostMapping("login")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "loginStr", dataType = "String", required = true, value = "登录字符串"),
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "登录密码")
    })
    @ApiOperation("商家登录接口")
    Result<LoginResultDTO> login(@RequestParam("loginStr") String loginStr,
                                 @RequestParam("password") String password);

    /**
     * 根据用户名查询商家
     * @param username
     * @return
     */
    @ApiOperation("根据用户名查询商家")
    @GetMapping("info/username")
    Result<SellerInfoDTO> getSellerInfo(@RequestParam("username") String username);

    class Fallback implements SellerApi {

        @Override
        public Result<SellerInfoDTO> getSellerInfo(Long sellerId) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 商家服务 获取商家信息接口失败");
        }

        @Override
        public Result<SellerRegisterResultDTO> register(SellerCreateDTO sellerCreateDTO) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 商家服务 注册接口失败");
        }

        @Override
        public Result<LoginResultDTO> login(String loginStr, String password) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 商家服务 登录接口失败");
        }

        @Override
        public Result<SellerInfoDTO> getSellerInfo(String username) {
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR.getCode(),"调用 商家服务 根据用户名查询商家接口失败");
        }
    }
}
