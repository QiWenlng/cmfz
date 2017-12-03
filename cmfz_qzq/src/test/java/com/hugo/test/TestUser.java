package com.hugo.test;

import com.hugo.Application;
import com.hugo.back.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestUser {

    @Autowired
    private UserService userService;

    @Test
    public void testSave() {
        //adminService.save(new Admin("11","hugo","1100"));
        userService.queryOneByPhone("17611003900");
    }

    /*@Test
    public void testQueryAll(){
        List<Admin> users = userService.queryAll();
        for (Admin user : users) {
            System.out.println(user);
        }
    }*/
}
