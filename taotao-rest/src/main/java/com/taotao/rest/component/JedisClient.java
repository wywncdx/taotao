package com.taotao.rest.component;

public interface JedisClient {
	public String set(String key, String value);
	public String get(String key);
	public Long hset(String key, String field, String value);
	public String hget(String key, String field);
	public Long incr(String key);
	public Long decr(String key);
	public Long expire(String key, int second);
	public Long ttl(String key);
	public Long hdel(String key, String field);
}
