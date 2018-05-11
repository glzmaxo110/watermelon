package com.zc.travel.aop;
import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.common.utils.json.JSONException;
import com.zc.travel.common.utils.json.JSONObject;
import com.zc.travel.consts.UserSession;
import com.zc.travel.enums.LogOptLevel;
import com.zc.travel.enums.LogOptStatus;
import com.zc.travel.enums.LogOptType;
import com.zc.travel.enums.SystemCode;
import com.zc.travel.log.vo.LogVo;
import com.zc.travel.msg.provider.IMsgOptProvider;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.regex.Pattern;

/**
 * 日志切面
 * Created by Majl on 2018/3/19.
 */
@Component
@Aspect
public class ServiceLogAspect {

    //查询类
    private static final Pattern selPattern = Pattern.compile("^(select|find|get)(([a-zA-z0-9]-*){1,})$", Pattern.CASE_INSENSITIVE);
    //添加类
    private static final Pattern insPattern = Pattern.compile("^(add|insert)(([a-zA-z0-9]-*){1,})$", Pattern.CASE_INSENSITIVE);
    //修改类
    private static final Pattern updPattern = Pattern.compile("^(update|edit|modify)(([a-zA-z0-9]-*){1,})$", Pattern.CASE_INSENSITIVE);
    //删除类
    private static final Pattern delPattern = Pattern.compile("^(del|remove|drop)(([a-zA-z0-9]-*){1,})$", Pattern.CASE_INSENSITIVE);

    @Autowired
    private IMsgOptProvider msgOptProvider;

    /**
     * 日志
     * @param status	日志操作状态
     * @param optType	日志操作类型
     * @param level		日志操作级别
     * @param systemCode 服务代码
     * @param optUserId	操作员ID
     * @param ipAddr	操作IP地址
     * @param keyword	关键词
     * @param content	日志内容
     */

    private static final SystemCode systemCode = SystemCode.getByIndex(Integer.parseInt(System.getProperty("systemCode")));



    private Long aroundTimeOfMethod;


    @Pointcut("execution(* com.zc.travel.*.service.impl.*Service.*(..))")
    public void serviceLog(){}


    @Around("serviceLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.currentTimeMillis();
        Object proceed = pjp.proceed();
        this.aroundTimeOfMethod = System.currentTimeMillis() - start;
        return proceed;

    }


    @AfterReturning(pointcut="serviceLog()")
    public void afterReturning(JoinPoint pjp) {
        buildMethodInfo(pjp, null);
    }

    //异常捕获 区分业务提示和系统异常
    @AfterThrowing(pointcut="serviceLog()",throwing="e")
    public void afterThrowing(JoinPoint pjp, Throwable e){
        buildMethodInfo(pjp, e);
    }

    /**
     * 组装执行方法信息
     * @param pjp
     */
    private void buildMethodInfo(JoinPoint pjp, Throwable e){
        Logger logger;
        LogOptStatus status;
        LogOptType optType;
        LogOptLevel level;
        Long optUserId = 0l;
        String ipAddr = "0.0.0.0";
        String keyword = "";
        String content = "";
        String methodName = "";
        String classNameOfMethod = "";
        String parameterInfoOfMethod = "";
        String contentOfMethod = "";

        Signature sign = pjp.getSignature();
        if (!(sign instanceof MethodSignature)){
            System.out.println("非法拦截，检查切入点");
        }else {
            MethodSignature  msig = (MethodSignature) sign;
            Object target = pjp.getTarget();
            logger = Logger.getLogger(target.getClass());
            classNameOfMethod = target.getClass().getName();

            Method currentMethod = null;
            try {
                currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
                methodName = currentMethod.getName();

                if (selPattern.matcher(methodName).matches()){
                    optType = LogOptType.LIST_QUERY;
                    //System.out.println("---------------------->>>>查询方法被拦截systemCode" + systemCode + classNameOfMethod + ":" + methodName);
                }else if (insPattern.matcher(methodName).matches()){
                    optType = LogOptType.ADD;
                    //System.out.println("---------------------->>>>新增方法被拦截" + classNameOfMethod + ":" + methodName);
                }else if (updPattern.matcher(methodName).matches()){
                    optType = LogOptType.MODIFY;
                    //System.out.println("---------------------->>>>修改方法被拦截" + classNameOfMethod + ":" + methodName);
                }else if (delPattern.matcher(methodName).matches()){
                    optType = LogOptType.DELETE;
                    //System.out.println("---------------------->>>>删除方法被拦截" + classNameOfMethod + ":" + methodName);
                }else {
                    optType = LogOptType.OTHER;
                    //System.out.println("---------------------->>>>其他方法被拦截" + classNameOfMethod + ":" + methodName);
                }

                Object[] params = pjp.getArgs();
                Parameter[] p = currentMethod.getParameters();

                if (null != e){
                    if (e instanceof BaseCoreException){
                        //系统业务异常捕获 级别为警告warn 给开发人员调优依据
                        contentOfMethod = e.getMessage();
                        level = LogOptLevel.WARN;
                    }else {
                        //系统异常捕获 级别为错误error 开发人员必须立即处理
                        contentOfMethod = e.getMessage();
                        level = LogOptLevel.ERROR;
                    }
                    status = LogOptStatus.FAILURE;
                }else {
                    level = LogOptLevel.INFO;
                    status = LogOptStatus.SUCCESS;
                }
                keyword = classNameOfMethod + "." + methodName;

                JSONObject jsonObject = new JSONObject();
                int index = 0;
                if (null != params && params.length > 0){
                    for (Object args : params){
                        try {
                            jsonObject.put(p[index].getName(), args!=null?args.toString():null);
                        } catch (JSONException je) {
                            //方法参数信息组装异常
                        }
                        if (null != args && args instanceof UserSession){
                            UserSession userSession = (UserSession) args;
                            optUserId = userSession.getMember().getId();
                            ipAddr = userSession.getIpAddr();
                        }
                        index ++;
                    }
                    parameterInfoOfMethod = jsonObject.toString();
                }
                jsonObject = new JSONObject();
                try {
                    jsonObject.put("className", classNameOfMethod);
                    jsonObject.put("methodName", methodName);
                    jsonObject.put("params", parameterInfoOfMethod);
                    jsonObject.put("castTime", aroundTimeOfMethod);
                    jsonObject.put("exception", contentOfMethod);

                    content = jsonObject.toString();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                try {
                    LogVo log = new LogVo();
                    log.setOptStatus(status.getIndex());
                    log.setOptType(optType.getIndex());
                    log.setOptLevel(level.getIndex());
                    log.setSystemCode(systemCode.getIndex());
                    log.setOptUserId(optUserId);
                    log.setIp(ipAddr);
                    log.setOptKeywords(keyword);
                    log.setContent(content);
                    logger.info(log.getContent());
                    msgOptProvider.sendLogMessage(log);
                }catch (Throwable throwable){
                    throwable.printStackTrace();
                }
            } catch (NoSuchMethodException nsme) {
                //获取不到方法名
                System.out.println("非法拦截，检查切入点");
            }
        }
    }



    public static void main(String[] args){
        System.out.println(System.getProperty("systemCode"));

    }
}
