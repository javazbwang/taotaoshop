package com.taotao.rest.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	@Test
	public void testJedisSingle() {
		//创建一个Jedis对象
		Jedis jedis=new Jedis("192.168.10.20", 6379);
		jedis.set("key1", "jedis test");
		String string = jedis.get("key1");
		System.out.println(string);
		//关闭
		jedis.close();
	}
	
	/**
	 * 使用连接池
	 */
	@Test
	public void testJedisPool() {
		//创建Jedis连接池
		JedisPool pool=new JedisPool("192.168.10.20", 6379);
		//从连接池中获取Jedis对象
		Jedis jedis=pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		//关闭
		jedis.close();
		pool.close();
		
	}
	/**
	 * 集群
	 * @author dwg void
	 */
	@Test
	public void testJedisCluster() {
		HashSet<HostAndPort> nodes=new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.10.20", 7001));
		nodes.add(new HostAndPort("192.168.10.20", 7002));
		nodes.add(new HostAndPort("192.168.10.20", 7003));
		nodes.add(new HostAndPort("192.168.10.20", 7004));
		nodes.add(new HostAndPort("192.168.10.20", 7005));
		nodes.add(new HostAndPort("192.168.10.20", 7006));
		
		JedisCluster cluster=new JedisCluster(nodes);
		cluster.set("key1", "test cluster");
		String string = cluster.get("key1");
		System.out.println(string);
		
		cluster.close();
	}
	
	@Test
	public void testSpringJedisSingle() {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool pool=(JedisPool) applicationContext.getBean("redisClient");
		Jedis jedis=pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		jedis.close();
		pool.close();
	}
	
	@Test
	public void testSpringJedisCluster() {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster cluster= (JedisCluster) applicationContext.getBean("redisClient");
		String string = cluster.get("key1");
		System.out.println(string);
		cluster.close();
	}
}
