package com.hugo.back.controller;

import com.hugo.back.service.MenuService;
import com.hugo.common.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    @Qualifier("menuService")
    private MenuService menuService;

    @RequestMapping("/queryAll")
    @ResponseBody
    public List<Menu> queryAll() {
        List<Menu> menus = menuService.queryAll();
        return menus;
    }
}
