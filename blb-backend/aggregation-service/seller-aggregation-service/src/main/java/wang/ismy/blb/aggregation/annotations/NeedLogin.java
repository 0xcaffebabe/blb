package wang.ismy.blb.aggregation.annotations;

/**
 * 被该注解修饰的方法，执行前会判断消费者是否登录
 * 未登录就快速失败
 * @author MY
 */
public @interface NeedLogin {
}
