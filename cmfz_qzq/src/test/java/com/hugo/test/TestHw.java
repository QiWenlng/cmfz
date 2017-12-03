package com.hugo.test;

import com.hugo.Application;
import com.hugo.back.service.HomeworkService;
import com.hugo.common.entity.Homework;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestHw {

    @Autowired
    private HomeworkService homeworkService;

    @Test
    public void testSave() {
        //homeworkService.save(new Homework("11","aaaa","1","5926ad24-1387-46cb-b3e9-0106e8d4e97c"));
        List<Homework> homeworks = homeworkService.queryAll(0, 5, "5926ad24-1387-46cb-b3e9-0106e8d4e97c");
        for (Homework homework : homeworks) {
            System.out.println(homework);
        }
        Integer count = homeworkService.count("5926ad24-1387-46cb-b3e9-0106e8d4e97c");
        System.out.println(count);
    }

    /*@Test
    public void testQueryAll(){
        List<Admin> users = userService.queryAll();
        for (Admin user : users) {
            System.out.println(user);
        }
    }*/
}
