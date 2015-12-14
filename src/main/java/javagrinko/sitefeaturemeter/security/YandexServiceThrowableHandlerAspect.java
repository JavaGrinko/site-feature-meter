package javagrinko.sitefeaturemeter.security;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class YandexServiceThrowableHandlerAspect {

    @Pointcut("execution(* javagrinko.sitefeaturemeter.services.YandexService.*(..))")
    private void yandexServicePointcut() {
    }

    @Around("yandexServicePointcut()")
    private Object handleThrowable(ProceedingJoinPoint joinPoint) {
        Object o = null;
        try {
            o = joinPoint.proceed();
        } catch (Throwable throwable) {
            new Notification("Ошибка в " + joinPoint.getSignature().getName(),
                    throwable.getMessage(),
                    Notification.Type.TRAY_NOTIFICATION)
                    .show(Page.getCurrent());
        }
        return o;
    }
}