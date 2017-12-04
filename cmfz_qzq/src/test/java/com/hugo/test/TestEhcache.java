package com.hugo.test;

import com.hugo.Application;
import com.hugo.common.dao.AdminDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestEhcache {

    @Autowired
    private AdminDao adminDao;

    @Test
    public void testEhcache() {

        long begin = System.currentTimeMillis();
        adminDao.queryOne("hugo");
        long ing = System.currentTimeMillis();
        adminDao.queryOne("hugo");
        long end = System.currentTimeMillis();
        System.out.println("第一次请求时间：" + (ing - begin) + "ms");
        System.out.println("第二次请求时间:" + (end - ing) + "ms");
    }

}
