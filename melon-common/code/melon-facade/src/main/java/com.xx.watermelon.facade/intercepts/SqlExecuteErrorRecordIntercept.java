package com.zc.travel.intercepts;


import com.zc.travel.common.utils.StringUtils;
import com.zc.travel.enums.DeleteStatus;
import com.zc.travel.enums.SystemCode;
import com.zc.travel.msg.provider.IMsgOptProvider;
import com.zc.travel.sys.vo.SysSqlExecuteErrLogVo;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mybatis拦截器. 拦截SQL执行时的异常，写入DB.
 *
 */
@Intercepts(value = {
        @Signature(type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
                        CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class SqlExecuteErrorRecordIntercept implements Interceptor {
    @Autowired
    private IMsgOptProvider msgOptProvider;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        }catch (Exception e){
            recordSqlErrorLog(invocation,e);
            throw e;
        }
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    //region 私有方法

    /**
     * 记录SQL错误日志
     * @param invocation
     */
    private void recordSqlErrorLog(Invocation invocation,Exception e){
        try {
            // 取得各种值
            MappedStatement statement = (MappedStatement) invocation
                    .getArgs()[0];
            Object parameter = invocation.getArgs()[1];
            BoundSql boundSql = statement.getBoundSql(parameter);



            // 防止死循环
            if (statement.getId().toUpperCase()
                    .contains("sqlExceptionLogMapper".toUpperCase())) {
                throw e;
            }

            //获取SQL
            String executedSql = showSql(statement.getConfiguration(),boundSql);

            //Mapper文件名
            String mapperFilePath = statement.getResource();
            Pattern pattern = Pattern.compile("(\\w+.xml)");
            if(null != mapperFilePath && StringUtils.isNotBlank(mapperFilePath)){
                Matcher filePathMatcher = pattern.matcher(mapperFilePath);
                if(null != filePathMatcher && filePathMatcher.find()){
                    mapperFilePath = filePathMatcher.group(1);
                }
            }

            // 日志对象
            SysSqlExecuteErrLogVo sysSqlExecuteErrLogVo = new SysSqlExecuteErrLogVo();
            sysSqlExecuteErrLogVo.setSqlContent(executedSql);  //sql语句
            sysSqlExecuteErrLogVo.setLastModifyUserId(1L);   //修改人ID
            sysSqlExecuteErrLogVo.setLastModifyTime(new Date());  //修改时间
            sysSqlExecuteErrLogVo.setErrMsg(e.getCause().toString()); // 异常简要信息
            sysSqlExecuteErrLogVo.setDeleteStatus(DeleteStatus.NOT_DELETED.getIndex());
            sysSqlExecuteErrLogVo.setCreateUserId(1L);
            sysSqlExecuteErrLogVo.setCreateTime(new Date());
            sysSqlExecuteErrLogVo.setBelongModule(this.getClass().getName());  //监测点
            sysSqlExecuteErrLogVo.setSqlParams(parameter.toString());  //sql参数
            sysSqlExecuteErrLogVo.setSysCode(SystemCode.SYSTEM.getIndex());  //所属系统
            sysSqlExecuteErrLogVo.setExceptionStackTrace(getExceptionStackTrace(e));  //堆栈信息
            sysSqlExecuteErrLogVo.setSqlId(statement.getId());
            sysSqlExecuteErrLogVo.setSqlType(statement.getSqlCommandType().toString());  //sql类型
            sysSqlExecuteErrLogVo.setBelongMapper(mapperFilePath);
            msgOptProvider.sendSqlErrorLogMessage(sysSqlExecuteErrLogVo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取异常的堆栈信息.
     *
     * @param e
     * @return
     */
    private String getExceptionStackTrace(Exception e) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        e.printStackTrace(new java.io.PrintWriter(buf, true));
        String expMessage = buf.toString();
        try {
            buf.close();
        } catch (IOException ex) {
        }
        return expMessage;
    }


    String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }

    String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }
    //endregion
}
