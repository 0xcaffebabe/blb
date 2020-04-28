package wang.ismy.blb.impl.rider.api;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.blb.api.rider.RiderEvaluationApi;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalInfoDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalItemDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalSubmitDTO;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;
import wang.ismy.blb.impl.rider.service.RiderEvaluationService;

/**
 * @author MY
 * @date 2020/4/28 9:34
 */
@RestController
@AllArgsConstructor
public class RiderEvaluationApiImpl implements RiderEvaluationApi {
    private final RiderEvaluationService evaluationService;
    @Override
    public Result<RiderEvalResultDTO> addEvaluation(RiderEvalSubmitDTO evalSubmitDTO) {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        return Result.success(evaluationService.addEvaluation(token,evalSubmitDTO));
    }

    @Override
    public Result<RiderEvalInfoDTO> getRiderEvalInfo(Long riderId) {
        return Result.success(evaluationService.getRiderEvalInfo(riderId));
    }

    @Override
    public Result<Page<RiderEvalItemDTO>> getRiderEvalList(Long riderId, Pageable pageable) {
        return Result.success(evaluationService.getRiderEvalList(riderId,pageable));
    }
}
