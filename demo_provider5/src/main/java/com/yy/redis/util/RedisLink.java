package com.yy.redis.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by yunzed on 15/12/27.
 */
public class RedisLink {
    private JedisPool pool;
    private JedisPoolConfig config = new JedisPoolConfig();

    public RedisLink(String address, int port,String pwd) {
        config.setMaxTotal(100);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(3000);
        config.setTestWhileIdle(true);
        pool = new JedisPool(config, address, port,3000,pwd);
    }

    public boolean exist(String key) {
        Jedis jedis = null;
        boolean ret = false;
        try {
            jedis = pool.getResource();
            ret = jedis.exists(key);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }

        return ret;
    }

    public void del(String key) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
    }

    public void expire(String key, int time) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.expire(key, time);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
    }

    public byte[] get(String key) {
        byte[] value = null;
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            value = jedis.get(key.getBytes());
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
        return value;
    }

    public void set(String key, byte[] value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.set(key.getBytes(), value);
        } catch(Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
    }

    public void setex(String key, int expire, byte[] value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.setex(key.getBytes(), expire, value);
        } catch(Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
    }

    public Long incr(String key) {
        Jedis jedis = null;
        Long ret = (long)0;
        try {
            jedis = pool.getResource();
            ret = jedis.incr(key);
        } catch(Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }

        return ret;
    }

    public Map<String, byte[]> mgetByteArr(String[] keys) {
        byte[][] b = new byte[keys.length][];
        for (int i=0;i<b.length;i++){
            b[i]=keys[i].getBytes();
        }
        Jedis jedis = null;
        String value = null;
        Map<String, byte[]> ret = new HashMap<String, byte[]>();
        try {
            jedis = pool.getResource();
            List<byte[]> values = jedis.mget(b);
            for( int i=0; i<keys.length; i++ ) {
                if(values.get(i)!=null){
                    ret.put(keys[i], values.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }

        return ret;
    }


    public void rpushMulti(String key, List<byte[]> values) {
        Jedis jedis = null;
        String[] strValues = new String[values.size()];

        for( int i=0; i<values.size(); i++ ) {
            strValues[i] = new String(values.get(i));
        }
        try {
            jedis = pool.getResource();
            jedis.rpush(key, strValues);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
    }

    public void rpushMultiInString(String key, List<String> values) {
        Jedis jedis = null;
        String[] strValues = new String[values.size()];

        for( int i=0; i<values.size(); i++ ) {
            strValues[i] = values.get(i);
        }
        try {
            jedis = pool.getResource();
            jedis.rpush(key, strValues);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
    }


    public void lpushMulti(String key, List<byte[]> values) {
        Jedis jedis = null;
        String[] strValues = new String[values.size()];

        for( int i=0; i<values.size(); i++ ) {
            strValues[i] = new String(values.get(i));
        }
        try {
            jedis = pool.getResource();
            jedis.lpush(key, strValues);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
    }

    public void lpushMultiInString(String key, List<String> values) {
        Jedis jedis = null;
        String[] strValues = new String[values.size()];
        for( int i=0; i<values.size(); i++ ) {
            strValues[i] = values.get(i);
        }
        try {
            jedis = pool.getResource();
            jedis.lpush(key, strValues);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
    }

    public List<String> lrange(String key, int offset ,int limit) {
        Jedis jedis = null;
        List<String> ret = null;
        try {
            jedis = pool.getResource();
            ret = jedis.lrange(key, offset, limit);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }

        return ret;
    }

    public List<byte[]> lrangereturnbyte(String key, int offset ,int limit) {
        Jedis jedis = null;
        List<byte[]> ret = null;
        try {
            jedis = pool.getResource();
            ret = jedis.lrange(key.getBytes(), offset, limit);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
        return ret;
    }

    public byte[] hget(String key, String field) {
        Jedis jedis = null;
        byte[] value = null;

        try {
            jedis = pool.getResource();
            value = jedis.hget(key.getBytes(), field.getBytes());
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }

        return value;
    }

    public Map<String, String> hgetall(String key) {
        Jedis jedis = null;
        Map<String, String> value = null;
        try {
            jedis = pool.getResource();
            value = jedis.hgetAll(key);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
        return value;
    }

    public void hset(String key, String field, byte[] value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.hset(key.getBytes(), field.getBytes(), value);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
    }

    public void hrem(String key, String field) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.hdel(key, field);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
    }

    public Set<String> hkeys(String key) {
        Jedis jedis = null;
        Set<String> ret = null;
        try {
            jedis = pool.getResource();
            ret = jedis.hkeys(key);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
        return ret;
    }

    public void zadd(String key, long score, String value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.zadd(key, score, value);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
    }

    public void zrem(String key, String value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.zrem(key.getBytes(), value.getBytes());
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
            return;
        }
    }

    public long zremMulti(String key, String[] values) {
        Jedis jedis = null;
        long ret = 0;
        if( values == null || values.length == 0 )
            return 0;

        try {
            jedis = pool.getResource();
            ret = jedis.zrem(key, values);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }

        return ret;
    }

    public Double zscore(String key, String field) {
        Jedis jedis = null;
        Double ret = (Double)0.0;
        try {
            jedis = pool.getResource();
            ret = jedis.zscore(key.getBytes(), field.getBytes());
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }

        return ret;
    }

    public Set<String> zrangeByScore(String key, long start, long end) {
        Jedis jedis = null;
        Set<byte[]> byteRet = null;
        Set<String> ret = new HashSet<String>();

        try {
            jedis = pool.getResource();
            byteRet = jedis.zrangeByScore(key.getBytes(), start, end);
            if( byteRet != null ) {
                for( byte[] it: byteRet ) {
                    ret.add( it.toString() );
                }
            }
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
        return ret;
    }

    public Set<byte[]> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        Set<byte[]> ret = null;
        try {
            jedis = pool.getResource();
            ret = jedis.zrevrange(key.getBytes(), start, end);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }

        return ret;
    }

    public Set<String> zrevrangeRetString(String key, long start, long end) {
        Jedis jedis = null;
        Set<String> ret = null;
        try {
            jedis = pool.getResource();
            ret = jedis.zrange(key, start, end);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
        return ret;
    }

    public Set<String> zrevrangeByScoreString(String key, long start, long end, int offset, int limit) {
        Jedis jedis = null;
        Set<byte[]> byteRet = null;
        Set<String> ret = new HashSet<String>();

        try {
            jedis = pool.getResource();
            byteRet = jedis.zrevrangeByScore(key.getBytes(), start, end, offset, limit);
            if( byteRet != null ) {
                for( byte[] it: byteRet ) {
                    ret.add( it.toString() );
                }
            }
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
        return ret;
    }

    public Long zcard(String key) {
        Jedis jedis = null;
        Long ret = (long)0;
        try {
            jedis = pool.getResource();
            return jedis.zcard(key.getBytes());
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }

        return ret;
    }

    public long zcount(String key, long min, long max) {
        Jedis jedis = null;
        long ret = 0;
        try {
            jedis = pool.getResource();
            ret = jedis.zcount(key, min, max);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }

        return ret;
    }

    public void sadd(String key, String value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.sadd(key, value);
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }
    }

    public boolean sismember(String key, String field) {
        Jedis jedis = null;
        boolean ret = false;
        try {
            jedis = pool.getResource();
            ret = jedis.sismember(key.getBytes(), field.getBytes());
        } catch (Exception e) {
            returnBrokenJedis(jedis);
        } finally {
            returnJedis(jedis);
        }

        return ret;
    }

    public void close() {
        pool.close();
    }

    private void returnBrokenJedis(Jedis jedis) {
        if (jedis != null) {
            //pool.returnBrokenResource(jedis);
            jedis.close();
        }
    }

    private void returnJedis(Jedis jedis) {
        if (jedis != null) {
            //pool.returnResource(jedis);
            jedis.close();
        }
    }


    public static void main(String[] args) {

        RedisLink redisLink = new RedisLink("42.51.161.209",6379,"JhgH-J123K_-ii_zLH_mk");
        /*
        //m_body
         Set<String> zrevrange = redisLink.zrevrangeRetString("xm:msg:im:status:1:1:401:1:1", 0, 57);
        //Set<byte[]> zrevrange = redisLink.zrevrange("xm:msg:im:status:1:1:401:1:1", 0, 57);
        List<String> list = new ArrayList<String>(zrevrange);

        //List<byte[]> list = new ArrayList<byte[]>(zrevrange);

        Collections.reverse(list);

        redisLink.del("tesdteeee");

        //redisLink.lpushMulti("tesdteeee",list);

        redisLink.lpushMultiInString("tesdteeee",list);

        List<String> tesdteeee = redisLink.lrange("tesdteeee", 0, 57);

       // List<byte[]> tesdteeee = redisLink.lrangereturnbyte("tesdteeee", 0, 57);

        String[] keys=new String[tesdteeee.size()];
        for (int i=0;i<keys.length;i++){
            keys[i]="xm:cache:msg:im:m_body:"+tesdteeee.get(i);
        }

        Map<String, byte[]> stringMap = redisLink.mgetByteArr(keys);

        System.out.println(stringMap);


//        for (Map.Entry<String, byte[]> entry:stringMap.entrySet()){
//            PIMSendGroupMsg pimSendGroupMsg=new PIMSendGroupMsg();
//            pimSendGroupMsg.unmarshall(entry.getValue());
//            System.out.println(pimSendGroupMsg);
//            System.out.println(pimSendGroupMsg.getUri());
//            System.out.println(pimSendGroupMsg.getAppid());
//        }

//        for (Map.Entry<String, byte[]> entry:stringMap.entrySet()){
//            PIMSendMsg pimSendMsg=new PIMSendMsg();
//            pimSendMsg.unmarshall(entry.getValue());
//            System.out.println(pimSendMsg);
//            System.out.println(pimSendMsg.getUri());
//            System.out.println(pimSendMsg.getAppid());
//        }



        String b="AAADmwDKAAsAAAAAAAAAAAACAAYAmgAAAO+/vQHvv70AAQABAQAkODY5QzkzNkItNjdERi00NkFFLTk3RUQtNjgyQjVDQ0VGRkZGAAAAAAAAAAEAAAAAAAAAAgAAAAEAKQAAACkB77+9AAkAAQAU6Zi/5b63MDU1NTDllabllabllaYAAAAAAAAAAAAAAAAAAVLvv70677+9BXguCO+/vQAQAAABAAAAAAAAAAAAAAAAkQAAAO+/vQHvv70AAQABAQAkMzAxNUFFRUItQThENy00NzY0LTgwRDMtQzVEMjI4OTNCRjY2AAAAAAAAAAEAAAAAAAAAAgAAAAEAHgAAAB4B77+9AAkAAQAJ5ZWm5ZWm5ZWmAAAAAAAAAAAAAAAAAAFS77+9Qe+/vQV4Lgrvv73vv70QAAABAAAAAAAAAAAAAAAAjwAAAO+/vQHvv70AAQABAQAkNjE1RTQzQUEtNDA2MC00MzFELUFBRjUtOTMyMUIwODkzQ0Y2AAAAAAAAAAEAAAAAAAAAAgAAAAEAHgAAAB4B77+9AAkAAQAJ5ZWm5ZWm5ZWmAAAAAAAAAAAAAAAAAAFS77+9R++/vQV4Lgwi77+9EAAAAQAAAAAAAAAAAAAAAJAAAADvv70B77+9AAEAAQEAJDM3ODI4MjNFLTg0MzMtNEVGRC05ODQwLTFCRTBGNUNCMTAxNgAAAAAAAAABAAAAAAAAAAIAAAABAB8AAAAfAe+/vQAJAAEACiDlk6blk6blk6YAAAAAAAAAAAAAAAAAAVLvv71XbAV4Lg/vv73vv70QAAABAAAAAAAAAAAAAAAAkwAAAO+/vQHvv70AAQABAQAkMUU2RjE5MTUtNDhEQy00RUY1LUJENTEtMkZEMzI5QkNBRkM5AAAAAAAAAAEAAAAAAAAAAgAAAAEAHgAAAB4B77+9AAkAAQAJ5aC15aC15aC1AAAAAAAAAAAAAAAAAAFS77+9d++/vQV4Lu+/ve+/ve+/vRAAAAEAAAAAAAAAAAAAAACOAAAA77+9Ae+/vQABAAEBACQ0QkYwNDdDRS0wQjlFLTQwMEYtOTQ4Qy0xQzdGMkNBOEJDMzcAAAAAAAAAAQAAAAAAAAACAAAAAQAbAAAAGwHvv70ACQABAAblip/lpKsAAAAAAAAAAAAAAAAAAVLvv71+YAV4Lu+/ve+/ve+/vRAAAAEAAAAAAAAAAAAAAP////8AAAAAAAAAAAAAAAA=";


        byte[] bytes = Base64.decodeBase64(b);

        ProtoPacket protoPacket = new ProtoPacket();

        protoPacket.unmarshall(bytes);

        System.out.println(protoPacket.getUri());
        System.out.println(protoPacket.getAppid());
        */

        List<String> list=new ArrayList<String>();
        list.add("xm:cache:msg:im:m_body:394329288955924480");
        Map<String, byte[]> stringMap = redisLink.mgetByteArr(list.toArray(new String[1]));

        for (Map.Entry<String, byte[]> entry : stringMap.entrySet()){
            System.out.println(new String(entry.getValue()).getBytes());
           /* PIMSendMsg p=new PIMSendMsg();
            p.unmarshall(entry.getValue());
            System.out.println(p);*/
        }


//        byte[] bytes = redisLink.get("xm:cache:msg:im:m_body:394329288955924480");
//
//
//        PIMSendMsg p=new PIMSendMsg();
//        p.unmarshall(bytes);
//
//        System.out.println(p);
//        System.out.println(p.getUri());
//        PIMTextInfo pi=new PIMTextInfo();
//        pi.unmarshall(p.getMessage());
//        System.out.println(pi);



//        String b  = "AAACXgDKAAsAAAAAAAAAAAACAAQAiAAAAH4B77+9AAEAAQEAJDEyQTcwOENDLUEwN0UtNEFCRS1CMDdELUVGRkREODYzMkY4QwAAAAAAAAABAAAAAAAAAAIAAAABABgAAAAYAe+/vQAJAAEAA+aIkQAAAAAAAAAAAAAAAAABUu+/ve+/vQV477+9aO+/vQAQAAABAAAAAAAAAAAAAAAAjgAAAO+/vQHvv70AAQABAQAkRTlCOENCRTctM0JDMC00OEFFLUIyREEtMTg3NTdBNzI3RDgxAAAAAAAAAAEAAAAAAAAAAgAAAAEAHgAAAB4B77+9AAkAAQAJ5ZOm5ZOm5ZOmAAAAAAAAAAAAAAAAAAFS8pqwgAV477+9Zijvv70QAAABAAAAAAAAAAAAAAAAjgAAAO+/vQHvv70AAQABAQAkRDQ4NkM5RTMtMjM3Mi00M0FFLUE1NTgtM0ExMjE0QTc3NERFAAAAAAAAAAEAAAAAAAAAAgAAAAEAHgAAAB4B77+9AAkAAQAJ5ZGD5ZGD5ZGDAAAAAAAAAAAAAAAAAAFS77+9QwV477+9Y0jvv70QAAABAAAAAAAAAAAAAAAAjgAAAO+/vQHvv70AAQABAQAkMjI4RTBERTktQjU2NS00Mjg4LUIyQ0YtQkE1MzkwRjVDMTJEAAAAAAAAAAEAAAAAAAAAAgAAAAEAHgAAAB4B77+9AAkAAQAJ5ZWm5ZWm5ZWmAAAAAAAAAAAAAAAAAAFS77+9bwV477+9X++/vUAQAAABAAAAAAAAAAAAAAD/////AAAAAAAAAAAAAAAA";
//
//        byte[] bytes = Base64.decodeBase64(b);
//        ProtoPacket protoPacket = new PMsgHistoryRes();
//        protoPacket.unmarshall(bytes);
//
//        PMsgHistoryRes p =(PMsgHistoryRes)protoPacket;
//
//        System.out.print(p);
//
//        byte[][] msgs = p.getMsgs();




    }


}
