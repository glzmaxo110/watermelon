package com.xx.watermelon.user.service.impl;

import com.xx.watermelon.common.exception.BaseCoreException;
import com.xx.watermelon.user.conts.UserSession;
import com.xx.watermelon.user.dao.ICrmMemberDao;
import com.xx.watermelon.user.exception.UserException;
import com.xx.watermelon.user.mapper.CrmMemberMapper;
import com.xx.watermelon.user.service.ICrmMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0, Created by xiesx on 2018-05-24 10:22.
 * @description CrmMemberService 2018/5/24
 * @copyright 2018-05-24 10:22
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CrmMemberService implements ICrmMemberService{

    @Autowired
    private ICrmMemberDao crmMemberDao;

    @Override
    public void saveCrmMember(CrmMemberMapper crmMemberMapper, UserSession session) throws BaseCoreException {

        crmMemberDao.insert(crmMemberMapper);
    }

    @Override
    public void deleteCrmMemberById(Long memberId, UserSession session) throws BaseCoreException {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("id",memberId);
        crmMemberDao.deleteById(params);
    }

    @Override
    @Transactional(readOnly = true)
    public CrmMemberMapper getCrmMemberById(Long id) throws UserException {
        return crmMemberDao.getById(id);
    }
}
