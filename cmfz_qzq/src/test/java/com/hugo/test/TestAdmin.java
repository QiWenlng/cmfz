package com.hugo.test;

import com.hugo.Application;
import com.hugo.back.service.AdminService;
import com.hugo.common.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestAdmin {

    @Autowired
    private AdminService adminService;

    @Test
    public void testSave() {
        //adminService.save(new Admin("11","hugo","1100"));
        Admin admin = adminService.queryOne("hugo");
        System.out.println(admin);
    }

    /*@Test
    public void testQueryAll(){
        List<Admin> users = userService.queryAll();
        for (Admin user : users) {
            System.out.println(user);
        }
    }*/
}
