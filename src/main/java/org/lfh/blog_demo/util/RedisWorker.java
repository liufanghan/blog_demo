//package org.lfh.blog_demo.util;
//
//import jakarta.annotation.Resource;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.time.format.DateTimeFormatter;
//
////@Component
public class RedisWorker {
//
//    private static final long BEGIN_TIMESTAMP = 1640995200L; //2022。1.1.0.0.0的时间戳
//    private static final int COUNT_BIT = 32; //左移位数
////    @Resource
////    private StringRedisTemplate stringRedisTemplate;
//
//    /**
//     * 生成全局唯一ID
//     *
//     * @param key：订单key，如”order“
//     * @return 全局ID
//     */
//    public long nextId(String key) {
//        //1.生成时间戳
//        LocalDateTime now = LocalDateTime.now();
//        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
//        long timestamp = nowSecond - BEGIN_TIMESTAMP;
//        //2.生成序列号,使用的是redis的字增长
//        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
//        long count = stringRedisTemplate.opsForValue().increment("incr" + "key" + ":" + date);
//        //3.拼接返回
//        return nowSecond << COUNT_BIT | count;
//    }
//
//    public static void main(String[] args) {
//        LocalDateTime time = LocalDateTime.of(2022, 1, 1, 0, 0, 0);
//        long second = time.toEpochSecond(ZoneOffset.UTC);
//        System.out.println(second);
//    }
}
