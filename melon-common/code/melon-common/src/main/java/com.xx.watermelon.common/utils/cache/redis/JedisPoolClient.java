package com.zc.travel.common.utils.cache.redis;

import com.github.jedis.lock.JedisLock;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description redis缓存处理类
 * @remark
 */
public class JedisPoolClient {
	
	private static Logger logger = Logger.getLogger(JedisPoolClient.class);
	/**
	 * 默认缓存时间（单位：秒）
	 */
	private static final int DEFAULT_CACHE_SECONDS = 60 * 60;
	/**
	 * 连接池
	 */
	private static JedisPool jedisPool;
	
	public void setJedisPool(JedisPool jedisPool) {
		JedisPoolClient.jedisPool = jedisPool;
	}
	public static JedisPool getJedisSentinelPool() {
		return jedisPool;
	}
	
	/**
	 * 释放redis资源
	 * @param jedis
	 */
	private static void releaseResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 删除Redis中的所有key
	 * @throws Exception
	 */
	public static void flushAll() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.flushAll();
		} catch (Exception e) {
			logger.error("Cache清空失败：" + e);
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 保存一个对象到Redis中(缓存过期时间:使用此工具类中的默认时间)
	 * @param key		键
	 * @param object	缓存对象
	 * @return true or false
	 * @throws Exception
	 */
	public static boolean save(Object key, Object object) {
		return save(key, object, DEFAULT_CACHE_SECONDS);
	}

	/**
	 * 保存一个对象到redis中并指定过期时间
	 * @param key		键
	 * @param object	缓存对象
	 * @param seconds	过期时间（单位为秒）
	 * @return true or false
	 */
	public static boolean save(Object key, Object object, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(SerializeUtils.serialize(key), SerializeUtils.serialize(object));
			jedis.expire(SerializeUtils.serialize(key), seconds);
			return true;
		} catch (Exception e) {
			logger.error("Cache保存失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 根据缓存键获取Redis缓存中的值.<br/>
	 * @param key		键
	 * @return Object
	 * @throws Exception
	 */
	public static Object get(Object key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] obj = jedis.get(SerializeUtils.serialize(key));
			return obj == null ? null : SerializeUtils.unSerialize(obj);
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 根据缓存键清除Redis缓存中的值.<br/>
	 * @param key	键
	 * @return
	 * @throws Exception
	 */
	public static boolean del(Object key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(SerializeUtils.serialize(key));
			return true;
		} catch (Exception e) {
			logger.error("Cache删除失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 根据缓存键清除Redis缓存中的值.<br/>
	 * @param keys	键
	 * @return
	 * @throws Exception
	 */
	public static boolean del(Object... keys) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(SerializeUtils.serialize(keys));
			return true;
		} catch (Exception e) {
			logger.error("Cache删除失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}
	
	/**
	 * 根据缓存key删除Redis缓存中的值
	 * @param key	键
	 * @return
	 * @version 1.0,2018年1月23日 上午10:48:58,Liugl,Ins
	 */
	public static boolean del(byte[] key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
			return true;
		} catch (Exception e) {
			logger.error("Cache删除失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 添加一个内容到指定key的hash中
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static boolean addHash(String key, Object field, Object value) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.hset(SerializeUtils.serialize(key), SerializeUtils.serialize(field), SerializeUtils.serialize(value));
			return true;
		} catch (Exception e) {
			logger.error("Cache保存失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 从指定hash中拿一个对象
	 * @param key
	 * @param field
	 * @return
	 */
	public static Object getHash(Object key, Object field) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] obj = jedis.hget(SerializeUtils.serialize(key), SerializeUtils.serialize(field));
			return SerializeUtils.unSerialize(obj);
		} catch (Exception e) {
			logger.error("Cache读取失败：" + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 从hash中删除指定filed的值
	 * @param key
	 * @param field
	 * @return
	 */
	public static boolean delHash(Object key, Object field) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long result = jedis.hdel(SerializeUtils.serialize(key), SerializeUtils.serialize(field));
			return result == 1 ? true : false;
		} catch (Exception e) {
			logger.error("Cache删除失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 拿到缓存中所有符合pattern的key
	 * @param pattern
	 * @return
	 */
	public static Set<byte[]> keys(String pattern) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Set<byte[]> allKey = jedis.keys(("*" + pattern + "*").getBytes());
			return allKey;
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			return new HashSet<byte[]>();
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 获得hash中的所有key value
	 * @param key
	 * @return
	 */
	public static Map<byte[], byte[]> getAllHash(Object key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Map<byte[], byte[]> map = jedis.hgetAll(SerializeUtils.serialize(key));
			return map;
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			return null;
		} finally {
			releaseResource(jedis);
		}
	}

	/**
	 * 判断一个key是否存在
	 * @param key
	 * @return
	 */
	public static boolean exists(Object key) {
		Jedis jedis = null;
		boolean result = false;
		try {
			jedis = jedisPool.getResource();
			result = jedis.exists(SerializeUtils.serialize(key));
			return result;
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			return false;
		} finally {
			releaseResource(jedis);
		}
	}
	
	/**
	 * 缓存锁处理
	 * @param key		键
	 * @param seconds	超时时间（单位：秒）
	 * @return
	 */
	public static boolean expire(Object key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.expire(SerializeUtils.serialize(key), seconds);
			return true;
		} catch (Exception e) {
			logger.error("Cache设置超时时间失败：" + e);
			return false;
		} finally {
			//释放redis资源
			releaseResource(jedis);
		}
	}

	/**
	 * 缓存剩余时间查询
	 * @param key		键
	 * @return
	 */
	public static Integer ttl(Object key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Long ttl = jedis.ttl(SerializeUtils.serialize(key));
			return ttl.intValue();
		} catch (Exception e) {
			logger.error("查询缓存超时时间失败：" + e);
			return null;
		} finally {
			//释放redis资源
			releaseResource(jedis);
		}
	}
	
	/**
	 * 缓存锁处理
	 * @param key			缓存key
	 * @param timeoutMsecs	超时时间
	 * @param expireMsecs	释放时间
	 * @return
	 */
	public static JedisLock lock(String key, int timeoutMsecs, int expireMsecs) {
		Jedis jedis = null;
		JedisLock lock = null;
		try {
			jedis = jedisPool.getResource();
			lock = new JedisLock(jedis, key, timeoutMsecs, expireMsecs);
			lock.acquire();
		} catch (Exception e) {
			logger.error("Cache获取失败：" + e);
			//释放锁
			lock.release();
		}
		return lock;
	}

}
