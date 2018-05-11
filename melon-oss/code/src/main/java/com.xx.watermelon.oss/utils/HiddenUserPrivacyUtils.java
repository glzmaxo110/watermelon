package com.zc.travel.webboss.utils;

import com.zc.travel.common.utils.BeanUtils;
import com.zc.travel.common.utils.StringUtils;
import com.zc.travel.consts.WebUtils;
import com.zc.travel.dto.SuperUITableListResultDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 隐藏用户信息相关
 */
public class HiddenUserPrivacyUtils {

    public enum HiddenObjectType{
        SuperUITableListResultDto,OBJECT
    }

    public enum RegexRoleType{
        HiddenMobil,HiddenCardNo
    }

    /**
     * 隐藏隐私字段信息
     * @param inputDataObj  输入对象
     * @param objectType  对象类型
     * @param hiddenFields  要隐藏的字段
     * @return
     */
    public static Object HiddenUserPrivacyInfo(Object inputDataObj, HiddenObjectType objectType, Map<String,RegexRoleType> hiddenFields) {
        if(inputDataObj == null)return inputDataObj;

        if(WebUtils.checkAuthCode("1d182cd5-026b-11e8-9903-000c29426c51")){
            return inputDataObj;  //有查看号码的权限
        }

        Map<Integer,String[]> regexRoles = getRegexRoles();  //获取规则列表

        //以表格方式返回
        if(objectType == HiddenObjectType.SuperUITableListResultDto){
            SuperUITableListResultDto dto = (SuperUITableListResultDto)inputDataObj;
            List<Object> rows = (List<Object>)dto.rows;
            rows.forEach(p->{

                hiddenFields.forEach((k,v)->{
                    String[] regexArr = regexRoles.get(v.ordinal());

                    if(null != BeanUtils.getProperty(p,k) && StringUtils.isNotBlank(BeanUtils.getProperty(p,k).toString())) {
                        String value = BeanUtils.getProperty(p, k).toString();
                        value = value.replaceAll(regexArr[0], regexArr[1]);
                        BeanUtils.invokeSetter(p, k, value, true, true);
                    }
                });
            });
            return dto;
        }

        //以单个对象方式返回
        if(objectType == HiddenObjectType.OBJECT) {
            hiddenFields.forEach((k,v)->{
                String[] regexArr = regexRoles.get(v.ordinal());

                if(null != BeanUtils.getProperty(inputDataObj,k) && StringUtils.isNotBlank(BeanUtils.getProperty(inputDataObj,k).toString())) {
                    String value = BeanUtils.getProperty(inputDataObj, k).toString();
                    value = value.replaceAll(regexArr[0], regexArr[1]);
                    BeanUtils.invokeSetter(inputDataObj, k, value, true, true);
                }
            });
        }
        return inputDataObj;
    }

    /**
     * 获取字段列表的替换规则
     * @return
     */
    private static Map<Integer,String[]> getRegexRoles(){
        String[] phoneReplaceRegex = new String[]{"(\\d{4})\\d{5}(\\w{2})","$1*****$2"};   //用于替换电话号码
        String[] cardNoReplaceRegex = new String[]{"(\\d{3})\\d{13}(\\w{2})","$1*************$2"};   //用于替换身份证

        Map<Integer,String[]> result = new HashMap<Integer,String[]>();
        result.put(0,phoneReplaceRegex);  //手机
        result.put(1,cardNoReplaceRegex);  //身份证
        return result;
    }
}
