package wang.ismy.blb.aggregation.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.blb.aggregation.annotations.NeedLogin;
import wang.ismy.blb.aggregation.client.*;
import wang.ismy.blb.api.auth.UserTypeEnum;
import wang.ismy.blb.api.order.pojo.dto.NewOrderItemDTO;
import wang.ismy.blb.api.seller.pojo.dto.LoginResultDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerCreateDTO;
import wang.ismy.blb.api.seller.pojo.dto.SellerRegisterResultDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopCategoryDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopCreateDTO;
import wang.ismy.blb.api.shop.pojo.dto.ShopInfoDTO;
import wang.ismy.blb.common.BlbException;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Result;

import java.util.List;

/**
 * @author MY
 * @date 2020/5/18 18:45
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "商家接口")
@AllArgsConstructor
@Slf4j
public class SellerAggApi {
    private final SellerApiClient sellerApiClient;
    private final ShopApiClient shopApiClient;
    private final UploadApiClient uploadApiClient;
    private final ShopCategoryApiClient shopCategoryApiClient;
    private final AuthApiClient authApiClient;
    @ApiOperation("登录/注册")
    @PostMapping("login")
    public Result<LoginResultDTO> loginOrRegister(@RequestParam String username,@RequestParam String password){
        var sellerRes = sellerApiClient.getSellerInfo(username);
        if (!sellerRes.getSuccess()){
            log.warn("调用商家服务失败:{}",sellerRes);
            throw new BlbException("调用商家服务失败");
        }
        boolean isRegistered = sellerRes.getData()  != null;
        Result<SellerRegisterResultDTO> registerRes = null;
        if (!isRegistered) {
            SellerCreateDTO createDTO = new SellerCreateDTO();
            createDTO.setUsername(username);
            createDTO.setPassword(password);
            registerRes = sellerApiClient.register(createDTO);
            if (!registerRes.getSuccess()) {
                log.warn("调用商家服务失败：{}",registerRes);
                throw new BlbException("调用商家服务失败");
            }
        }

        // 登录
        var loginRes = sellerApiClient.login(username,password);
        if (!loginRes.getSuccess()){
            log.warn("调用商家服务失败:{}",loginRes);
            throw new BlbException("调用商家服务失败");
        }
        if (!isRegistered) {
            loginRes.getData().setGreeting(registerRes.getData().getGreeting()+",您是本系统第" + registerRes.getData().getSellerNumber()+"位商家");
        }
        return loginRes;
    }

    @ApiOperation("店铺注册")
    @PostMapping("shop/register")
    @NeedLogin
    public Result<String> registerShop(@RequestBody ShopCreateDTO shopCreateDTO){
        return shopApiClient.addShop(shopCreateDTO);
    }

    @ApiOperation("图片上传")
    @PostMapping("image")
    @NeedLogin
    public Result<String> uploadImage(MultipartFile file){
        return uploadApiClient.upload(file);
    }

    @ApiOperation("获取某一层级的目录")
    @GetMapping("shop/category")
    public Result<List<ShopCategoryDTO>> getCategory(){
        return shopCategoryApiClient.getCategoryByLevel(1);
    }

    @ApiOperation("获取当前店铺信息")
    @GetMapping("shop/info")
    @NeedLogin
    public Result<ShopInfoDTO> getShopInfo(@RequestHeader(SystemConstant.TOKEN) String token){
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess()){
            log.warn("调用认证服务失败:{}",authRes);
            throw new BlbException("调用认证服务失败");
        }
        var seller = authRes.getData();
        if (!seller.getUserType().equals(UserTypeEnum.SELLER.getType())){
            log.warn("用户{}非商家",seller);
            throw new BlbException("用户非商家");
        }
        return shopApiClient.getShopBySeller(seller.getUserId());
    }

//    @ApiOperation("获取新订单列表")
//    @GetMapping("shop/order/new")
//    public Result<List<NewOrderItemDTO>> getNewOrderList(){
//        return
//    }
}
