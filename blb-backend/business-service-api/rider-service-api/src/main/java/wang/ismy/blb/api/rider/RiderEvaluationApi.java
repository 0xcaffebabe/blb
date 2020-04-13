package wang.ismy.blb.api.rider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalInfoDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalItemDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalSubmitDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;

/**
 * @author MY
 * @date 2020/4/8 13:20
 */
@Api(tags = "骑手评价服务接口")
@RequestMapping("eval")
public interface RiderEvaluationApi {

    /**
     * 增加一条骑手评价
     *
     * @param evalSubmitDTO 评价参数DTO
     * @return 评价结果DTO
     */
    @ApiOperation("增加骑手评价接口")
    @PostMapping("")
    Result<RiderEvalResultDTO> addEvaluation(@RequestBody RiderEvalSubmitDTO evalSubmitDTO);

    /**
     * 根据骑手ID取得骑手评价信息
     *
     * @param riderId
     * @return 骑手评价信息DTO
     */
    @ApiOperation("根据骑手ID取得骑手评价信息")
    @ApiImplicitParam("骑手ID")
    @GetMapping("info/{riderId}")
    Result<RiderEvalInfoDTO> getRiderEvalInfo(@PathVariable("riderId") Long riderId);

    /**
     * 根据骑手ID分页获取骑手评价列表
     * @param riderId
     * @param pageable
     * @return 评价分页结果
     */
    @ApiOperation("根据骑手ID分页获取骑手评价列表")
    @GetMapping("{riderId}")
    Result<Page<RiderEvalItemDTO>> getRiderEvalList(@PathVariable("riderId") Long riderId,
                                                    Pageable pageable);


}
