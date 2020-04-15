package wang.ismy.blb.common.util;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * 用来生成一些mock对象的工具类
 * @author MY
 * @date 2020/4/15 14:08
 */
public class MockUtils {

    public static <T> T create(Class<T> klass){
        Constructor<?>[] constructors = klass.getConstructors();
        if (constructors.length == 0){
            throw new IllegalStateException("没有构造器，无法创建");
        }
        try {
            Object o = constructors[0].newInstance();
            Method[] methods = klass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("set")){
                    Parameter[] parameters = method.getParameters();
                    if (parameters.length != 1){
                        continue;
                    }
                    Parameter methodParam = parameters[0];
                    Class<?> valType = methodParam.getType();
                    if (valType.equals(String.class)){
                        method.invoke(o,methodParam.getName()+randomStr(3));
                    }else if (valType.equals(Long.class)){
                        method.invoke(o,randomLong());
                    }else if (valType.equals(Boolean.class)){
                        method.invoke(o,randomBoolean());
                    }else if (valType.equals(BigDecimal.class)){
                        method.invoke(o,randomBigDecimal());
                    }else if (valType.equals(LocalDateTime.class)){
                        method.invoke(o,LocalDateTime.now());
                    }

                }
            }
            return klass.cast(o);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private static String randomStr(int bound){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        String uuid = UUID.randomUUID().toString();
        for (int i = 0; i < bound; i++) {
            sb.append(random.nextInt(uuid.length()));
        }
        return sb.toString();
    }

    private static Long randomLong(){
        Random random = new Random();
        return random.nextLong();
    }

    private static Boolean randomBoolean(){
        Random random = new Random();
        return random.nextBoolean();
    }

    private static BigDecimal randomBigDecimal(){
        Random random = new Random();
        return BigDecimal.valueOf(random.nextDouble());
    }
}
