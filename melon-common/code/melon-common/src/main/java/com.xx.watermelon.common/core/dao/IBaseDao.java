package com.zc.travel.common.core.dao;

import java.util.List;
import java.util.Map;

import com.zc.travel.common.exceptions.BaseCoreException;
import com.zc.travel.common.page.PageBean;
import com.zc.travel.common.page.PageParam;

/**
 * 数据访问层操作支撑接口.
 * @date 2015年6月30日 上午11:18:49
 * @version 2.0
 */
public interface IBaseDao<T> {
	/**
	 * 获取Mapper sql命名空间
	 * @param sqlId	sql定义id标识
	 * @date 2015年6月30日 下午12:10:47
	 */
	String getStatement(String sqlId);
	
	/**
	 * 数据记录存入数据库
	 * @param dtRecord 数据记录
	 * @date 2015年6月30日 上午11:19:21
	 */
	long insert(T dtRecord) throws BaseCoreException;
	/**
	 * 数据块批量存入数据库
	 * @param dtRecords 数据记录块
	 * @date 2015年6月30日 上午11:22:30
	 */
	int insertBatch(List<T> dtRecords) throws BaseCoreException;
	/**
	 * 数据块批量存入数据库
	 * @param dtRecords 数据记录块
	 * @date 2015年6月30日 上午11:22:30
	 */
	int insertBatch(Map<String, Object> dtRecords, String sqlId) throws BaseCoreException;

	/**
	 * 操作数据记录更新到数据库
	 * @param dtRecord 数据记录
	 * @date 2015年6月30日 上午11:24:22
	 */
	int updateById(T dtRecord) throws BaseCoreException;
	/**
	 * 操作数据记录更新到数据库
	 * @param dtRecord 数据记录
	 * @param nodeId 需要执行Sql配置节点id，sqlNodeId为空使用默认：SQL_UPDATE_BY
	 * @date 2015年6月30日 上午11:24:22
	 */
	int updateBy(T dtRecord, String nodeId) throws BaseCoreException;
	/**
	 * 根据过滤条件操作数据记录更新到数据库
	 * @param params 过滤条件参数
	 * @param dtRecord 数据记录
	 * @date 2015年6月30日 上午11:24:22
	 */
	int updateBy(Map<String, Object> params) throws BaseCoreException;
	/**
	 * 根据过滤条件操作数据记录更新到数据库
	 * @param params 过滤条件参数
	 * @param dtRecord 数据记录
	 * @param nodeId 需要执行Sql配置节点id，sqlNodeId为空使用默认：SQL_UPDATE_BY
	 * @date 2015年6月30日 上午11:24:22
	 */
	int updateBy(Map<String, Object> params, String nodeId) throws BaseCoreException;

	/**
	 * 根据数据编号删除数据记录
	 * @param id 数据编号
	 * @date 2015年6月30日 上午11:28:30
	 */
	int deleteById(long id) throws BaseCoreException;
	/**
	 * 根据数据编号删除数据记录
	 * @param id 数据编号
	 * @date 2015年6月30日 上午11:28:30
	 */
	int deleteById(long id, long lastModifyUserId) throws BaseCoreException;
	/**
	 * 根据过滤条件删除数据记录
	 * @param params 过滤条件参数
	 * @date 2015年6月30日 上午11:28:30
	 */
	int deleteBy(Map<String, Object> params) throws BaseCoreException;
	/**
	 * 根据过滤条件删除数据记录
	 * @param params
	 * @param nodeId
	 * @return
	 * @throws BaseCoreException
	 */
	int deleteBy(Map<String, Object> params, String nodeId) throws BaseCoreException;

	/**
	 * 根据筛选条件和分页参数对数据记录进行分页查询
	 * @param pageParam 分页参数对象
	 * @param params 查询筛选条件
	 */
	PageBean listPage(PageParam pageParam, Map<String, Object> params, String countSql, String pageSql) throws BaseCoreException;
	/**
	 * 根据筛选条件和分页参数对数据记录进行分页查询
	 * @param pageParam 分页参数对象
	 * @param params 查询筛选条件
	 * @date 2015年6月30日 上午11:40:28
	 */
	PageBean listPage(PageParam pageParam, Map<String, Object> params) throws BaseCoreException;

	/**
	 * 根据筛选条件和分页参数对数据记录进行分页查询
	 * @param startIndex
	 * @param pageNum
	 * @param pageSize
	 * @param params
	 * @param countSql
	 * @param pageSql
	 * @return
	 * @throws BaseCoreException
	 */
	PageBean listPage(int startIndex,int pageNum,int pageSize, Map<String, Object> params, String countSql, String pageSql) throws BaseCoreException;
	/**
	 * 根据筛选条件和分页参数对数据记录进行分页查询
	 * @param startIndex 页面记录开始数
	 * @param pageNum 页码(透传)
	 * @param pageSize 页面大小
	 * @param params
	 * @return
	 * @throws BaseCoreException
	 */
	PageBean listPage(int startIndex,int pageNum,int pageSize, Map<String, Object> params) throws BaseCoreException;
	/**
	 * 根据数据编号获得数据记录
	 * @param id 数据编号
	 * @date 2015年6月30日 上午11:25:54
	 */
	T getById(long id);
	/**
	 * 根据筛选条件查询多条数据记录
	 * @param params 条件筛选参数
	 * @date 2015年6月30日 上午11:37:35
	 */
	List<T> listBy(Map<String, Object> params);
	/**
	 * 根据条件筛选多条数据记录
	 * @param params
	 * @param sqlNodeId
	 * @return
	 * @throws BaseCoreException
	 */
	List<T> listBy(Map<String, Object> params, String sqlNodeId);
	/**
	 * 根据筛选条件获得数据记录
	 * @param params 条件筛选参数
	 * @date 2015年6月30日 上午11:39:20
	 */
	T getBy(Map<String, Object> params);
	/**
	 * 根据条件筛选获得数据记录
	 * @param params
	 * @param nodeId
	 * @return Entity
	 * @throws BaseCoreException
	 */
	T getBy(Map<String, Object> params, String nodeId);
	/**
	 * 根据条件筛选获得数据记录
	 * @param params
	 * @param nodeId
	 * @return Entity
	 * @throws BaseCoreException
	 */
	T getBy(T dtRecord, String nodeId);

	/**
	 * 根据筛选条件查询数据记录条数
	 * @param params	筛选条件。条件可为null
	 * @date 2015年6月30日 上午11:50:20
	 */
	long getCntBy(Map<String, Object> params);
	/**
	 * 根据筛选条件查询数据记录条数
	 * @param nodeId	mapper中查询sql语句对应ID
	 * @param params	筛选条件。条件可为null
	 * @date 2015年6月30日 上午11:50:20
	 */
	long getCntBy(String nodeId, Map<String, Object> params);
}
