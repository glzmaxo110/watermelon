package com.zc.travel.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version 1.0, Created by danvid on 2017-12-27 14:12.
 * @description MapFlattenUtil 2017/12/27  map 扁平化工具类
 */
public class MapFlattenUtils {
    /**
     * 对象扁平化输出
     * @param object
     * @return
     * @throws Exception
     */
    public static Map<String,Object> flattenObject(Object object) throws Exception{
        //object转map
        String s = JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
        JSONObject originMap = JSON.parseObject(s);
        Map<String, Object> resultMap = new HashMap<>();
        parseMap(resultMap, null, originMap);
        return resultMap;
    }

    /**
     * map扁平化输出
     * @param map
     * @return
     * @throws Exception
     */
    public static Map<String,Object> flattenMap(Map<String,Object> map) throws Exception{
        //object转map
        Map<String, Object> resultMap = new HashMap<>();
        parseMap(resultMap, null, map);
        return resultMap;
    }
    private static void parseMap(Map<String,Object> collectionMap, String mapKey,Map<String,Object> originMap)throws Exception{
        if (null != originMap&&!originMap.isEmpty()){
            if (null == collectionMap)
                collectionMap = new HashMap<>();
            Set<Map.Entry<String, Object>> entries = originMap.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                if (entry.getValue() instanceof List){
                    if(CollectionUtils.isEmpty((List)entry.getValue())){
                        if (StringUtils.isBlank(mapKey)){
                            collectionMap.put(entry.getKey(),entry.getValue());
                        }else {
                            collectionMap.put(mapKey+"."+entry.getKey(),entry.getValue());
                        }
                    }else {
                        if (StringUtils.isBlank(mapKey)) {
                            parseList(collectionMap, entry.getKey(), (List) entry.getValue());
                        } else {
                            parseList(collectionMap,mapKey+"."+entry.getKey(),(List)entry.getValue());
                        }
                    }
                }else if (entry.getValue() instanceof Map){
                    parseMap(collectionMap,entry.getKey(),(Map) entry.getValue());
                } else{
                    if (StringUtils.isBlank(mapKey)){
                        collectionMap.put(entry.getKey(),entry.getValue());
                    }else {
                        collectionMap.put(mapKey+"."+entry.getKey(),entry.getValue());
                    }
                }
            }

        }

    }
    private static void parseList(Map<String, Object> collectionMap, String listKey, List originList)throws Exception{
        if (null==collectionMap)
            throw new Exception("解析List保存容器Map为空");
        if (StringUtils.isBlank(listKey))
            throw new Exception("解析List,key为空");
        if (null == originList||CollectionUtils.isEmpty(originList)){
            collectionMap.put(listKey,originList);
        }else {
            for(int i=0;i<originList.size();i++){
                if (originList.get(i) instanceof Map){
                    parseMap(collectionMap,listKey+"["+i+"]",(Map) originList.get(i));
                }else if (originList.get(i) instanceof List){
                    parseList(collectionMap,listKey+"["+i+"]",(List) originList.get(i));
                }else {
                    collectionMap.put(listKey+"["+i+"]",originList.get(i));
                }
            }
        }
    }
}
