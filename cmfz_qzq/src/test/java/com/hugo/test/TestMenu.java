package com.hugo.test;

import com.hugo.Application;
import com.hugo.back.service.MenuService;
import com.hugo.common.entity.Menu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestMenu {

    @Autowired
    private MenuService menuService;

    @Test
    public void testSave() {
        List<Menu> menus = menuService.queryAll();
        for (Menu menu : menus) {
            System.out.println(menu);
        }
    }
}
