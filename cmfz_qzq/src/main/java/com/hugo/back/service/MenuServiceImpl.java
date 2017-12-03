package com.hugo.back.service;

import com.hugo.common.annotations.Cache;
import com.hugo.common.dao.MenuDao;
import com.hugo.common.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Autowired
    @Qualifier("menuDao")
    private MenuDao menuDao;

    @Override
    @Cache
    public List<Menu> queryAll() {
        List<Menu> menus = menuDao.queryAll();
        return menus;
    }
}
