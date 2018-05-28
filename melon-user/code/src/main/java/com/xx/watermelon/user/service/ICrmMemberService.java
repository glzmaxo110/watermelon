package com.xx.watermelon.user.service;

import com.xx.watermelon.user.conts.UserSession;
import com.xx.watermelon.common.exception.BaseCoreException;
import com.xx.watermelon.user.exception.UserException;
import com.xx.watermelon.user.mapper.CrmMemberMapper;

/**
 * @version 1.0, Created by xiesx on 2018-05-24 10:21.
 * @description ICrmMemberService 2018/5/24
 * @copyright 2018-05-24 10:21
 */
public interface ICrmMemberService {

    /**
     * 保存用户信息
     * @param crmMemberMapper
     * @param session
     * @throws UserException
     */
    void saveCrmMember(CrmMemberMapper crmMemberMapper, UserSession session) throws BaseCoreException;

    /**
     * 根据会员ID删除会员信息
     * @param memberId
     * @param session
     * @throws UserException
     */
    void deleteCrmMemberById(Long memberId, UserSession session) throws BaseCoreException;

    /**
     * 根据id查询实力秀信息
     * @param id
     * @return CrmMemberMapper
     * @throws UserException
     */
    CrmMemberMapper getCrmMemberById(Long id) throws UserException;

}
