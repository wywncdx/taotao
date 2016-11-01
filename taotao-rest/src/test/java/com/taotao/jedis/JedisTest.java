package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.component.JedisClient;
import com.taotao.rest.component.impl.JedisClientSingle;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	//单机版测试
	@Test
	public void testJedisSingle() {
		Jedis jedis = new Jedis("192.168.25.153", 6379);
		jedis.set("test", "hello jedis");
		String testJedis = jedis.get("test");
		System.out.println("test>>>>> : " + testJedis);
		jedis.close();
	}
	
	@Test
	public void testJedisDelete() {
		Jedis jedis = new Jedis("192.168.25.153", 6379);

		Long result = jedis.hdel("website", "google");
		jedis.close();
		
	}
	
	@Test
	public void testJedisPool() {
		//创建一个连接池对象，在系统中应该是单例的
		JedisPool jedisPool = new JedisPool("192.168.25.153", 6379);
		//获取一个链接
		Jedis jedis = jedisPool.getResource();
		jedis.set("testPool", "jedis pool");
		String jedisString = jedis.get("testPool");
		System.out.println("testPool : " + jedisString);
		jedis.close();
		
		//系统关闭时，才关闭jedisPool
		jedisPool.close(); 
	}
	
	@Test
	public void testJedisCluster() {
		//创建一个cluster对象，并把节点添加到node中
		//jedisCluster在系统中也是单例的
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.25.153", 7001));
		nodes.add(new HostAndPort("192.168.25.153", 7002));
		nodes.add(new HostAndPort("192.168.25.153", 7003));
		nodes.add(new HostAndPort("192.168.25.153", 7004));
		nodes.add(new HostAndPort("192.168.25.153", 7005));
		nodes.add(new HostAndPort("192.168.25.153", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("name", "zhangsan");
		jedisCluster.set("age", "25");
		String name = jedisCluster.get("name");
		String age = jedisCluster.get("age");
		System.out.println("testCluster>> " + "name : " + name + ", age : " + age);
		
		//当系统关闭时，cluster关闭
		jedisCluster.close();
	}
	
	@Test
	public void TestJedisClientSpring() {
		//创建spring容器
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisClient jedisClient = ctx.getBean(JedisClient.class);
		jedisClient.set("client", "1000");
		String string = jedisClient.get("client");
		System.out.println(">>> " + string);
	}
	
}
