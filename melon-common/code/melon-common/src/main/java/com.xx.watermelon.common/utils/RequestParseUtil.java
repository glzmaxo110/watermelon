package com.zc.travel.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0, Created by danvid on 2018-03-14 15:57.
 * @description RequestParamsParseUtil 2018/3/14 解析请求数据
 */
public class RequestParseUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestParseUtil.class);
    private static final String REQUEST_CURRENT_TIME = "API_requestCurrentTime";
    private static final String REQUEST_RESPONSE_TIME = "API_requestResponseTime";
    private static final String SERVLET_PATH = "API_servletPath";
    private static final String REQUEST_URI = "API_requestURI";
    private static final String LOCAL_ADDRESS = "API_localAddress";
    private static final String REQUEST_METHOD = "API_requestMethod";
    private static final String REMOTE_ADDRESS = "API_remoteAddress";
    private static final String X_FORWARDED_FOR = "x-forwarded-for";
    private static final String REQUEST_HEAD_PRE = "REQUEST_HEAD_";
    private static final String POST_METHOD_NAME = "POST";
    private static final String REQUEST_IP_Addr = "requestIpAddr";//请求来源地址,在全局请求参数中获取
    private static final String API_REQUEST_IP_Addr = "API_requestIpAddr";
    private static final String API_REQUEST_CURRENT_TIME = "API_requestCurrentTime";//请求来源地址,在全局请求参数中获取
    public static final String REQUEST_BODY_OBJECT = "requestBodyObject";//request请求体


    /**
     * 设置请求当前时间
     * @param request
     */
    public static void setRequestCurrentTime(HttpServletRequest request){
        request.setAttribute(REQUEST_CURRENT_TIME,System.currentTimeMillis());
    }
    /**
     * 解析请求信息为map
     * @param request
     * @return
     */
    public static Map<String,Object> getRequestParam(HttpServletRequest request) {
        if (null==request)
            return null;
        Map<String, Object> paramsEntity = new HashMap<>();
        try{
            JSONObject requestObject=new JSONObject();
            //获取POST请求体
            if (POST_METHOD_NAME.equals(request.getMethod())){
                String requestPostStr = RequestParseUtil.getRequestPostStr(request);
                if (StringUtils.isNotBlank(requestPostStr))
                    requestObject.putAll(JSON.parseObject(requestPostStr));
            }//GET请求参数
            else {
                Enumeration<String> parameterNames = request.getParameterNames();
                while (parameterNames.hasMoreElements()){
                    String paramName = parameterNames.nextElement();
                    requestObject.put(paramName,request.getParameter(paramName));
                }
            }
            paramsEntity.put(REQUEST_BODY_OBJECT,requestObject);
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()){
                String headerName = headerNames.nextElement();
                paramsEntity.put(REQUEST_HEAD_PRE+headerName,request.getParameter(headerName));
            }
            //当前时间统计
            if(null!=request.getAttribute(REQUEST_CURRENT_TIME)){
                paramsEntity.put(REQUEST_RESPONSE_TIME,System.currentTimeMillis()-(long)request.getAttribute(REQUEST_CURRENT_TIME));
                paramsEntity.put(API_REQUEST_CURRENT_TIME,System.currentTimeMillis());
            }
            //设置地址信息
            paramsEntity.put(API_REQUEST_IP_Addr,requestObject.getString(REQUEST_IP_Addr));
            if (!paramsEntity.isEmpty()){
                paramsEntity.put(SERVLET_PATH,request.getServletPath());
                paramsEntity.put(REQUEST_URI,request.getRequestURI());
                paramsEntity.put(LOCAL_ADDRESS,request.getLocalAddr());
                paramsEntity.put(REQUEST_METHOD,request.getMethod());
                if (request.getHeader(X_FORWARDED_FOR) == null)
                    paramsEntity.put(REMOTE_ADDRESS,request.getRemoteAddr());
                else
                    paramsEntity.put(REMOTE_ADDRESS,request.getHeader(X_FORWARDED_FOR));
            }
        }catch(Exception e){
            LOGGER.error("获取请求信息失败,原因:"+e.getMessage());
            e.printStackTrace();
        }
        return paramsEntity;
    }
    /**
     * 描述:获取 post 请求的 byte[] 数组
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {
            int readlen = request.getInputStream().read(buffer, i, contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request) throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }

}
