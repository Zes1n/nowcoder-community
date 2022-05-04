package com.nowcoder.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author wuzexin
 * @created 2022/4/27 14:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
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

    // 统计20万个重复数据的独立总数
    @Test
    public void testHyperLogLog() {
        String redisKey = "test:hll:02";

        for (int i = 1; i <= 100000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey, i);
        }
        for (int i = 1; i <= 100000; i++) {
            int r = (int)(Math.random() * 100000 + 1);
            redisTemplate.opsForHyperLogLog().add(redisKey, r);
        }

        System.out.println(redisTemplate.opsForHyperLogLog().size(redisKey));
    }

    // 将3组数据合并，在统计合并后的重复数据的独立总数
    @Test
    public void testHyperLogLogUnion() {
        String redisKey2 = "test:hll:03";
        for (int i = 1; i <= 10000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey2, i);
        }

        String redisKey3 = "test:hll:04";
        for (int i = 5001; i <= 15000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey3, i);
        }

        String redisKey4 = "test:hll:05";
        for (int i = 15001; i <= 20000; i++) {
            redisTemplate.opsForHyperLogLog().add(redisKey4, i);
        }

        String unionKey = "test:hll:union";
        System.out.println(redisTemplate.opsForHyperLogLog().union(unionKey, redisKey2, redisKey3, redisKey4));
    }

    // 统计一组数据的布尔值
    @Test
    public void testBitMap() {
        String redisKey = "test:bm:01";

        // 记录
        redisTemplate.opsForValue().setBit(redisKey, 1, true);
        redisTemplate.opsForValue().setBit(redisKey, 4, true);
        redisTemplate.opsForValue().setBit(redisKey, 7, true);

        // 查询
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 1));
        System.out.println(redisTemplate.opsForValue().getBit(redisKey, 10));

        // 统计
        Object obj = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(redisKey.getBytes());
            }
        });

        System.out.println(obj);
    }

    // 统计3组数据的布尔值，并对这3组数据做OR运算.
    @Test
    public void testBitMapOperation() {
        String redisKey2 = "test:bm:02";
        redisTemplate.opsForValue().setBit(redisKey2, 0, true);
        redisTemplate.opsForValue().setBit(redisKey2, 1, true);
        redisTemplate.opsForValue().setBit(redisKey2, 2, true);

        String redisKey3 = "test:bm:03";
        redisTemplate.opsForValue().setBit(redisKey3, 2, true);
        redisTemplate.opsForValue().setBit(redisKey3, 3, true);
        redisTemplate.opsForValue().setBit(redisKey3, 4, true);

        String redisKey4 = "test:bm:04";
        redisTemplate.opsForValue().setBit(redisKey4, 4, true);
        redisTemplate.opsForValue().setBit(redisKey4, 5, true);
        redisTemplate.opsForValue().setBit(redisKey4, 6, true);

        String redisKey = "test:bm:or";
        Object obj = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.bitOp(RedisStringCommands.BitOperation.OR, redisKey.getBytes(), redisKey2.getBytes(),
                        redisKey3.getBytes(), redisKey4.getBytes());
                return connection.bitCount(redisKey.getBytes());
            }
        });

        System.out.println(obj);
    }
}
