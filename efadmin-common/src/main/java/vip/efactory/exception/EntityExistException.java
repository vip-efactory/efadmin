package vip.efactory.exception;

import org.springframework.util.StringUtils;

/**
 * Description:
 *
 * @author dbdu
 * @date 19-7-10 上午7:11
 */
@SuppressWarnings("rawtypes")   // 压制原生类型的警告
public class EntityExistException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EntityExistException(Class clazz, String field, String val) {
        super(EntityExistException.generateMessage(clazz.getSimpleName(), field, val));
    }

    private static String generateMessage(String entity, String field, String val) {
        return StringUtils.capitalize(entity) + " with " + field + " " + val + " existed";
    }
}
