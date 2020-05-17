package wang.ismy.blb.aggregation.aspect;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import wang.ismy.blb.aggregation.client.AuthApiClient;
import wang.ismy.blb.common.SystemConstant;
import wang.ismy.blb.common.enums.ResultCode;
import wang.ismy.blb.common.result.Result;
import wang.ismy.blb.common.util.CurrentRequestUtils;

/**
 * @author MY
 * @date 2020/5/17 19:22
 */
@Aspect
@Component
@AllArgsConstructor
@Setter
public class NeedLoginAspect {

    private AuthApiClient authApiClient;

    @Pointcut("@annotation(wang.ismy.blb.aggregation.annotations.NeedLogin)")
    public void pointCut(){}

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String token = CurrentRequestUtils.getHeader(SystemConstant.TOKEN);
        if (StringUtils.isEmpty(token)) {
            return Result.failure(ResultCode.USER_NOT_LOGGED_IN);
        }
        var authRes = authApiClient.valid(token);
        if (!authRes.getSuccess() || authRes.getData() == null) {
            return Result.failure(ResultCode.USER_NOT_LOGGED_IN);
        }
        return joinPoint.proceed();
    }
}
