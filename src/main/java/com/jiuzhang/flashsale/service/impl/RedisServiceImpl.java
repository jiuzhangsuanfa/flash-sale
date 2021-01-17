package com.jiuzhang.flashsale.service.impl;

import java.util.Collections;

import javax.annotation.Resource;

import com.jiuzhang.flashsale.exception.RedisDistributedLockException;
import com.jiuzhang.flashsale.exception.RedisStockException;
import com.jiuzhang.flashsale.exception.RedisUserException;
import com.jiuzhang.flashsale.service.RedisService;

import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisServiceImpl implements RedisService {

    private static final String STOCK_KEY = "stock:";

    @Resource
    private JedisPool jedisPool;

    @Override
    public void addRestrictedUser(Long activityId, Long userId) throws RedisUserException {
        try (Jedis jedisClient = jedisPool.getResource()) {
            jedisClient.sadd("activity_users:" + activityId, String.valueOf(userId));
        } catch (Exception exception) {
            throw new RedisUserException(activityId, userId, exception.getMessage());
        }
    }

    @Override
    public boolean isRestrictedUser(Long activityId, Long userId) throws RedisUserException {
        try (Jedis jedisClient = jedisPool.getResource()) {
            return jedisClient.sismember("seckillActivity_users:" + activityId, String.valueOf(userId));
        } catch (Exception exception) {
            throw new RedisUserException(activityId, userId, exception.getMessage());
        }
    }

    @Override
    public void removeRestrictedUser(Long activityId, Long userId) throws RedisUserException {
        try (Jedis jedisClient = jedisPool.getResource()) {
            jedisClient.srem("seckillActivity_users:" + activityId, String.valueOf(userId));
        } catch (Exception exception) {
            throw new RedisUserException(activityId, userId, exception.getMessage());
        }
    }

    @Override
    public void revertStock(Long activityId) throws RedisStockException {
        try (Jedis jedisClient = jedisPool.getResource()) {
            jedisClient.incr(STOCK_KEY + activityId);
        } catch (Exception exception) {
            throw new RedisStockException(activityId, exception.getMessage());
        }
    }

    @Override
    public boolean deductStock(Long activityId) throws RedisStockException {
        try (Jedis jedisClient = jedisPool.getResource()) {
            String script = "if redis.call('exists',KEYS[1]) == 1 then\n"
                    + "                 local stock = tonumber(redis.call('get', KEYS[1]))\n"
                    + "                 if( stock <=0 ) then\n" + "                    return -1\n"
                    + "                 end;\n" + "                 redis.call('decr',KEYS[1]);\n"
                    + "                 return stock - 1;\n" + "             end;\n" + "             return -1;";

            Long stock = (Long) jedisClient.eval(script, Collections.singletonList(STOCK_KEY + activityId),
                    Collections.emptyList());
            return stock >= 0;
        } catch (Exception exception) {
            throw new RedisStockException(activityId, exception.getMessage());
        }
    }

    @Override
    public void setStock(Long activityId, Long availableStock) throws RedisStockException {
        try (Jedis jedisClient = jedisPool.getResource()) {
            jedisClient.set(STOCK_KEY + activityId, String.valueOf(availableStock));
        } catch (Exception exception) {
            throw new RedisStockException(activityId, exception.getMessage());
        }
    }

    @Override
    public String getStock(Long activityId) throws RedisStockException {
        try (Jedis jedisClient = jedisPool.getResource()) {
            return jedisClient.get(STOCK_KEY + activityId);
        } catch (Exception exception) {
            throw new RedisStockException(activityId, exception.getMessage());
        }
    }

    @Override
    public void takeUpDistributedLock(String lockKey, String requestId, int expireTime)
            throws RedisDistributedLockException {
        try (Jedis jedisClient = jedisPool.getResource()) {
            boolean success = "OK".equals(jedisClient.set(lockKey, requestId, "NX", "PX", expireTime));
            if (!success) {
                throw new RedisDistributedLockException(lockKey, requestId, "take up redis distributed lock failed");
            }
        } catch (RedisDistributedLockException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new RedisDistributedLockException(lockKey, requestId, exception.getMessage());
        }
    }

    @Override
    public void releaseDistributedLock(String lockKey, String requestId) throws RedisDistributedLockException {
        try (Jedis jedisClient = jedisPool.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Long result = (Long) jedisClient.eval(script, Collections.singletonList(lockKey),
                    Collections.singletonList(requestId));
            if (result != 1) {
                throw new RedisDistributedLockException(lockKey, requestId, "release redis distributed lock failed");
            }
        } catch (Exception exception) {
            throw new RedisDistributedLockException(lockKey, requestId, exception.getMessage());
        }
    }

}
