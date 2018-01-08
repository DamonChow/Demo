package com.damon;

import com.damon.service.RedisService;
import com.damon.vo.Car;
import com.damon.vo.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * @author Damon
 * @since 2018/1/4 11:25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisLockTest {

    private static final Logger log = LoggerFactory.getLogger(RedisLockTest.class);

    @Autowired
    private RedisService service;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());
    @Test
    public void test() {
        Car car = new Car();
        car.setId(33);
        car.setName("BMW");
        String call = service.callCar("Damon", car);
        log.info(call);
    }

    @Test
    public void testPhone() throws InterruptedException {
        for (int index = 0; index < 10; index++) {
            executor.submit(() -> {
                Phone phone = new Phone();
                phone.setId(33);
                phone.setName("iphone X");
                String call = service.buyPhone("Damon", phone);
                log.info(call);
            });
        }
        TimeUnit.SECONDS.sleep(100L);
        executor.shutdown();

    }


}
