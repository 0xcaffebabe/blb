package wang.ismy.blb.common;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class SnowFlakeTest {

    @Test
    @DisplayName("单线程雪花算法唯一ID生成测试")
    void test() {
        Set<Long> set = new HashSet<>();
        SnowFlake snowFlake = new SnowFlake(2, 3);

        int length = 100_0000;
        for (int i = 0; i < length; i++) {
            set.add(snowFlake.nextId());
        }
        assertEquals(length,set.size());
    }

    @Test
    @DisplayName("多线程雪花算法唯一ID生成测试")
    void testWithMultiThread() throws InterruptedException {
        Set<Long> set = new ConcurrentSkipListSet<>();
        SnowFlake snowFlake = new SnowFlake(2, 3);
        int length = 100_000;
        int threadNumber = 10;
        CountDownLatch latch = new CountDownLatch(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            new Thread(()->{
                for (int i1 = 0; i1 < length; i1++) {
                    set.add(snowFlake.nextId());
                }
                latch.countDown();
            }).start();
        }
        latch.await();

        assertEquals(length*threadNumber,set.size());
    }
}