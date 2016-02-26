package com.yy.redis.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.StringUtils;

import redis.clients.jedis.Jedis;

public class JedisUtil {

	private final static Logger LOG = LoggerFactory.getLogger(JedisUtil.class);

	public static long incr(String key) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			return jedis.incr(key);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				JedisFactory.getInstance().release(jedis);
			}
		}
	}

	public static void sadd(String key, Object data) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			String json = JsonUtil.toJson(data);
			jedis.sadd(key, json);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				JedisFactory.getInstance().release(jedis);
			}
		}
	}

	public static void setex(String key, int expr, Object data) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			String json = JsonUtil.toJson(data);
			jedis.setex(key, expr, json);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				JedisFactory.getInstance().release(jedis);
			}
		}
	}

	public static void lpush(String key, Object obj) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			jedis.lpush(key, JsonUtil.toJson(obj));
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				JedisFactory.getInstance().release(jedis);
			}
		}
	}

	public static void rpush(String key, Object obj) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			jedis.rpush(key, JsonUtil.toJson(obj));
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				JedisFactory.getInstance().release(jedis);
			}
		}
	}

	public static <T> T hget(String key, String field, Class<T> classOfT) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			String json = jedis.hget(key, field);
			if (StringUtils.isEmptyOrWhitespaceOnly(json)) {
				return null;
			}
			return JsonUtil.fromJson(json, classOfT);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				JedisFactory.getInstance().release(jedis);
			}
		}
	}

	public static void hset(String key, String field, Object data) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			String json = JsonUtil.toJson(data);
			jedis.hset(key, field, json);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				JedisFactory.getInstance().release(jedis);
			}
		}
	}

	public static void hdel(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			jedis.hdel(key, field);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				JedisFactory.getInstance().release(jedis);
			}
		}
	}

	public static long scard(String key) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			return jedis.scard(key);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				JedisFactory.getInstance().release(jedis);
			}
		}
	}

	public static void del(String key) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			jedis.del(key);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				JedisFactory.getInstance().release(jedis);
			}
		}
	}

	public static void hincrByFloat(String key, String field, double score) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			jedis.hincrByFloat(key, field, score);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				JedisFactory.getInstance().release(jedis);
			}
		}
	}

	public static void expire(String key, int second) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			jedis.expire(key, second);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				JedisFactory.getInstance().release(jedis);
			}
		}
	}

	public static void set(String key, Object obj) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			jedis.set(key, JsonUtil.toJson(obj));
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				try {
					JedisFactory.getInstance().release(jedis);
				} catch (Exception e) {
					LOG.error("", e);
				}
			}
		}
	}

	public static <T> T get(String key, Class<T> classOfT) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			String json = jedis.get(key);
			if (StringUtils.isEmptyOrWhitespaceOnly(json)) {
				return null;
			}
			return JsonUtil.fromJson(json, classOfT);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				try {
					JedisFactory.getInstance().release(jedis);
				} catch (Exception e) {
					LOG.error("", e);
				}
			}
		}
	}

	public static void rpush(String key, List<?> list) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			String[] strs = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				strs[i] = JsonUtil.toJson(list.get(i));
			}
			jedis.rpush(key, strs);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				try {
					JedisFactory.getInstance().release(jedis);
				} catch (Exception e) {
					LOG.error("", e);
				}
			}
		}
	}

	public static <T> T lpop(String key, Class<T> classOfT) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			String json = jedis.lpop(key);
			if (StringUtils.isEmptyOrWhitespaceOnly(json)) {
				return null;
			}
			return JsonUtil.fromJson(json, classOfT);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				try {
					JedisFactory.getInstance().release(jedis);
				} catch (Exception e) {
					LOG.error("", e);
				}
			}
		}
	}
	
	public static <T> T rpop(String key, Class<T> classOfT) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			String json = jedis.rpop(key);
			if (StringUtils.isEmptyOrWhitespaceOnly(json)) {
				return null;
			}
			return JsonUtil.fromJson(json, classOfT);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				try {
					JedisFactory.getInstance().release(jedis);
				} catch (Exception e) {
					LOG.error("", e);
				}
			}
		}
	}

	public static long hincr(String key, String field, int incr) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			return jedis.hincrBy(key, field, incr);
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				try {
					JedisFactory.getInstance().release(jedis);
				} catch (Exception e) {
					LOG.error("", e);
				}
			}
		}
	}

	public static <T> List<T> lrange(String key, int start, int end,
			Class<T> classOfT) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			List<String> jsons = jedis.lrange(key, start, end);
			if (jsons == null || jsons.isEmpty()) {
				return null;
			}

			List<T> list = new ArrayList<T>();
			for (String json : jsons) {
				T t = JsonUtil.fromJson(json, classOfT);
				list.add(t);
			}
			return list;
		} catch (Exception e) {
			LOG.error("", e);
			throw new RuntimeException();
		} finally {
			if (jedis != null) {
				try {
					JedisFactory.getInstance().release(jedis);
				} catch (Exception e) {
					LOG.error("", e);
				}
			}
		}
	}
	
	/*  service中的用法实例：
	 *  public static List<RentUser> listRentUser(List<Long> ids) {
		Jedis jedis = null;
		try {
			jedis = JedisFactory.getInstance().getSource();
			Pipeline pipeline = jedis.pipelined();
			for (int i = 0; i < ids.size(); i++) {
				pipeline.hget(HSET_APPOINT, ids.get(i) + "");
			}

			List<Object> result = pipeline.syncAndReturnAll();
			List<RentUser> list = new ArrayList<RentUser>();
			for (int i = 0; i < ids.size(); i++) {
				String json = (String) result.get(i);
				if (StringUtils.isEmptyOrWhitespaceOnly(json)) {
					continue;
				}
				RentUser rent_user = JsonUtil.fromJson(json, RentUser.class);
				list.set(i, rent_user);
			}
			return list;
		} finally {
			JedisUtil.release(jedis);
		}
	}
	*/
}
