package com.bxwl.admin.sys.aop;

import com.bxwl.admin.sys.dao.SysLogsDao;
import com.bxwl.admin.sys.model.SysLogs;
import com.bxwl.admin.sys.utils.SessionUtils;
import com.bxwl.admin.sys.utils.UuidUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * Create By xueyuliang
 * Description:自定义切面类
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysLogsDao sysLogsDao;
    //在注解的位置切入代码
    @Pointcut("@annotation(com.bxwl.admin.sys.aop.Operation)")
    public void logPointCut() {

    }
    @AfterReturning("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        //保存日志
        SysLogs sysLogs = new SysLogs();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取操作
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null) {
            String value = operation.value();
            sysLogs.setOperation(value);//保存获取的操作
        }
        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        sysLogs.setId(UuidUtil.uuid());
        sysLogs.setUserName(SessionUtils.getUser(request).getUsername());
        sysLogs.setIp(SessionUtils.getIpAdrress(request));
        sysLogs.setMethod(className + "." + methodName);
        sysLogsDao.save(sysLogs);
    }
}
