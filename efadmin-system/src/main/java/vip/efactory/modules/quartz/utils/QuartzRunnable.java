package vip.efactory.modules.quartz.utils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import vip.efactory.utils.SpringContextHolder;

/**
 * 执行定时任务
 */
public class QuartzRunnable implements Callable {

	private Object target;
	private Method method;
	private String params;

	QuartzRunnable(String beanName, String methodName, String params)
			throws NoSuchMethodException, SecurityException {
		this.target = SpringContextHolder.getBean(beanName);
		this.params = params;

		if (StringUtils.isNotBlank(params)) {
			this.method = target.getClass().getDeclaredMethod(methodName, String.class);
		} else {
			this.method = target.getClass().getDeclaredMethod(methodName);
		}
	}

	@Override
	public Object call() throws Exception {
		ReflectionUtils.makeAccessible(method);
		if (StringUtils.isNotBlank(params)) {
			method.invoke(target, params);
		} else {
			method.invoke(target);
		}
		return null;
	}
}
