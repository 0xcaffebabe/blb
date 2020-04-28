package wang.ismy.blb.impl.rider.service;

import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalInfoDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalItemDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalResultDTO;
import wang.ismy.blb.api.rider.pojo.dto.eval.RiderEvalSubmitDTO;
import wang.ismy.blb.common.result.Page;
import wang.ismy.blb.common.result.Pageable;

/**
 * @author MY
 * @date 2020/4/28 9:36
 */
public interface RiderEvaluationService {
    RiderEvalResultDTO addEvaluation(String token, RiderEvalSubmitDTO evalSubmitDTO);

    RiderEvalInfoDTO getRiderEvalInfo(Long riderId);

    Page<RiderEvalItemDTO> getRiderEvalList(Long riderId, Pageable pageable);
}
