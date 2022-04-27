package com.nowcoder.community;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author wuzexin
 * @created 2022/4/27 14:29
 */
@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStrings() {
        String redisKey = "test:count";

        redisTemplate.opsForValue().set(redisKey, 1);

        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
    }

    @Test
    public void testHashes() {
        String redisKey = "test:user";

        redisTemplate.opsForHash().put(redisKey, "id", 1);
        redisTemplate.opsForHash().put(redisKey, "username", "zhangsan");

        System.out.println(redisTemplate.opsForHash().get(redisKey, "id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey, "username"));
    }

    @Test
    public void testLists() {
        String redisKey = "test:ids";

        redisTemplate.opsForList().leftPush(redisKey, 101);
        redisTemplate.opsForList().leftPush(redisKey, 102);
        redisTemplate.opsForList().leftPush(redisKey, 103);
        redisTemplate.opsForList().leftPush(redisKey, 104);

        System.out.println(redisTemplate.opsForList().range(redisKey, 0, redisTemplate.opsForList().size(redisKey)).toString());

        redisTemplate.opsForList().rightPush(redisKey,105);
        redisTemplate.opsForList().rightPush(redisKey,106);
        redisTemplate.opsForList().rightPush(redisKey,107);
        System.out.println(redisTemplate.opsForList().range(redisKey, 0, redisTemplate.opsForList().size(redisKey)).toString());
    }

    @Test
    public void testSets() {
        String redisKey = "test:teachers";

        redisTemplate.opsForSet().add(redisKey, "zhangsan", "lisi", "wangwu");

        System.out.println(redisTemplate.opsForSet().members(redisKey).toString());
    }

    @Test
    public void testSortedSets() {
        String redisKey = "test:students";
        
        redisTemplate.opsForZSet().add(redisKey, "zhangsan", 80);
        redisTemplate.opsForZSet().add(redisKey, "lisi", 90);

        System.out.println(redisTemplate.opsForZSet().range(redisKey, 0, redisTemplate.opsForZSet().size(redisKey)));
    }

    @Test
    public void testKeys() {
        System.out.println(redisTemplate.delete("test:count"));

        redisTemplate.expire("test:students", 10, TimeUnit.SECONDS);
    }

    // 多次访问同一个key
    @Test
    public void testBoundOperations() {
        String redisKey = "test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
    }

    // 编程式事务
    @Test
    public void testTransaction() {
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String redisKey = "test:tx";
                // 启用事务
                operations.multi();

                operations.opsForSet().add(redisKey, "zhangsan");
                operations.opsForSet().add(redisKey, "lisi");
                operations.opsForSet().add(redisKey, "wangwu1");

                // redis事务中间做查询无效
                System.out.println(operations.opsForSet().members(redisKey));

                // 执行事务
                return operations.exec();
            }
        });

        System.out.println(obj);
    }
}
