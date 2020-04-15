package wang.ismy.blb.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wang.ismy.blb.common.SystemConstant;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作当前请求的工具类
 * @author MY
 * @date 2020/4/15 8:41
 */
public class CurrentRequestUtils {
    public static String getHeader(String header){
        var requestAttr = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes());
        if (requestAttr == null){
            return "";
        }
        return requestAttr.getRequest().getHeader(header);
    }

    public static String getToken(){
        return getHeader(SystemConstant.TOKEN);
    }
}
