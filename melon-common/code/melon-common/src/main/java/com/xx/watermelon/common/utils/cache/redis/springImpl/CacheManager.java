package com.xx.watermelon.common.utils.cache.redis.springImpl;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.data.redis.cache.RedisCache;

import java.util.Collection;

/**
 * 继承了 spring 内置的 AbstractCacheManager 管理 RedisCache 类缓存管理
 * ClassName: CacheManager <br/>
 * date: 2014-7-26 下午1:17:00 <br/>
 * 
 * @author laich
 */
public class CacheManager<T extends Object> extends AbstractCacheManager {

	private Collection<? extends RedisCache> caches;

	public void setCaches(Collection<? extends RedisCache> caches) {
		this.caches = caches;
	}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		return this.caches;
	}
}
