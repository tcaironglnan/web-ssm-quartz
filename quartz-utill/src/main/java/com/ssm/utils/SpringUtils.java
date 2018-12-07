package com.ssm.utils;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author FaceFeel
 * @date 2016/10/18/0018
 * 直接从Spring容器中提取Bean对象工具类
 */
@Component
public class SpringUtils implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;
    private final Logger log = Logger.getLogger(this.getClass());

    /**
     * 获取bean方法
     * @param requiredType 需要的类型
     * @param <T> 泛型
     * @return
     */
    public static <T> T getBean(Class<T> requiredType) {
        return validate() ? applicationContext.getBean(requiredType) : null;
    }

    /**
     * 按照bean的ID获取bean方法
     * @param beanId 指定的bean的ID值
     * @param <T> 泛型
     * @return
     */
    public static <T> T getBean(String beanId) {
        return validate() ? (T) applicationContext.getBean(beanId) : null;
    }

    /**
     *维护上下文注入方法
     */
    public static void assertContextInjected() {
        Validate.validState(applicationContext != null, "沒有找到SpringApplicationContext对象！");
    }

    public static boolean validate() {
        return applicationContext != null;
    }

    /**
     * 设置上下文方法
     * @param applicationContext 需要设置的上下文参数
     * @throws BeansException bean产生的异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.debug("注入ApplicationContext到SpringContextHolder:{" + applicationContext + "}");
        if (SpringUtils.applicationContext != null) {
            log.info("SpringUtils中的ApplicationContext被覆盖, 原有ApplicationContext为:" + SpringUtils.applicationContext);
        }
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 销毁/关闭已经生成了的上下文
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("清除SpringUtils中的ApplicationContext:" + applicationContext);
        }
        applicationContext = null;
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * SpringMvc下获取request
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

    }

    /**
     * SpringMvc下获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();

    }
}
