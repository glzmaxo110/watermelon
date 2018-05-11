package com.zc.travel.webboss.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.zc.travel.user.mapper.CrmResourcesMapper;

public class CrmResourcesListAndMapChangeUtil {
	
	private CrmResourcesListAndMapChangeUtil(){}
	
	public static Map<String, CrmResourcesMapper> changeListAndMap(List<CrmResourcesMapper> crmResources) {
		Map<String, CrmResourcesMapper> map = new LinkedHashMap<String, CrmResourcesMapper>();
		for (CrmResourcesMapper crmResource : crmResources) {
			if ("#".equals(crmResource.getTargetUrl())) {
				map.put(String.valueOf(crmResource.getId()), crmResource);
			} else {
				map.put(crmResource.getTargetUrl(), crmResource);
			}
		}
		return map;
	}
	
	public static List<CrmResourcesMapper> changeListAndMap(Map<String, CrmResourcesMapper> map) {
		List<CrmResourcesMapper> crmResources = new ArrayList<CrmResourcesMapper>();
		for (Map.Entry<String, CrmResourcesMapper> entry : map.entrySet()) {
			crmResources.add(entry.getValue());
		}
		return crmResources;
	}
}
