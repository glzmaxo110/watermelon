package com.xx.watermelon.user.dao;

import com.xx.watermelon.user.exception.UserException;
import com.xx.watermelon.user.mapper.CrmMemberMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @version 1.0, Created by xiesx on 2018-05-23 18:19.
 * @description ICrmMemberDao 2018/5/23
 * @copyright 2018-05-23 18:19
 */
@Mapper
@Repository("crmMemberDao")
public interface ICrmMemberDao{

    /**
     * 根据ID查询会员信息
     * @param id
     * @return
     * @throws UserException
     */
    CrmMemberMapper getById(Long id) throws UserException;

    /**
     * 新增会员信息
     * @param crmMemberMapper
     * @return
     * @throws UserException
     */
    int insert(CrmMemberMapper crmMemberMapper) throws UserException;

    /**
     * 更新会员信息
     * @param crmMemberMapper
     * @return
     * @throws UserException
     */
    int updateById(CrmMemberMapper crmMemberMapper) throws UserException;

    /**
     * 逻辑删除会员信息
     * @param params
     * @return
     * @throws UserException
     */
    int deleteById(Map<String,Object> params) throws UserException;

}
