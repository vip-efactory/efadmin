package vip.efactory.utils;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

import cn.hutool.core.util.ObjectUtil;
import vip.efactory.exception.BadRequestException;

/**
 * 验证工具
 */
public class ValidationUtil {

    /**
     * 验证空
     *
     * @param optional
     */
    public static void isNull(Object obj, String entity, String parameter , Object value){
        if(ObjectUtil.isNull(obj)){
            String msg = entity + " 不存在: "+ parameter +" is "+ value;
            throw new BadRequestException(msg);
        }
    }

    /**
     * 验证是否为邮箱
     *
     * @param string
     * @return
     */
    public static boolean isEmail(String email) {
        return new EmailValidator().isValid(email, null);
    }
}
