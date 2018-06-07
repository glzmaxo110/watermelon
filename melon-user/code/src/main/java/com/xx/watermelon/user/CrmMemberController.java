package com.xx.watermelon.user;

import com.xx.watermelon.common.utils.web.WebParamUtils;
import com.xx.watermelon.user.cache.IUserCacheService;
import com.xx.watermelon.user.exception.UserException;
import com.xx.watermelon.user.mapper.CrmMemberMapper;
import com.xx.watermelon.user.service.ICrmMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @version 1.0, Created by xiesx on 2018-05-26 18:33.
 * @description CrmMemberController 2018/5/26
 * @copyright 2018-05-26 18:33
 */
@Controller
@RequestMapping("/crmMember")
public class CrmMemberController {

    @Autowired
    private ICrmMemberService crmMemberService;
    @Autowired
    private IUserCacheService userCacheService;

    @RequestMapping("/getMember")
    @ResponseBody
    public CrmMemberMapper getMember(HttpServletRequest request, @RequestParam Map<String,Object> params) throws UserException {
        Long id = WebParamUtils.getLongValue(params.get("id"));
        CrmMemberMapper crmMember = crmMemberService.getCrmMemberById(id);
        return crmMember;
    }

    @RequestMapping("/setRedisCache")
    @ResponseBody
    public String setRedisCache(HttpServletRequest request, @RequestParam Map<String,Object> params) throws UserException {

        String key = WebParamUtils.getStringValue(params.get("key"));
        String value = WebParamUtils.getStringValue(params.get("value"));
        userCacheService.setStringValue(key,value);
        return "设置缓存成功";
    }

    @RequestMapping("/getRedisCache")
    @ResponseBody
    public String getRedisCache(HttpServletRequest request, @RequestParam Map<String,Object> params) throws UserException {

        String key = WebParamUtils.getStringValue(params.get("key"));
        String stringValue = userCacheService.getStringValue(key);
        return stringValue;
    }


}
