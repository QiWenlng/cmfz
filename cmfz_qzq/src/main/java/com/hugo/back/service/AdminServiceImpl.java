package com.hugo.back.service;

import com.hugo.common.annotations.Cache;
import com.hugo.common.annotations.FlushCache;
import com.hugo.common.dao.AdminDao;
import com.hugo.common.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    @Qualifier("adminDao")
    private AdminDao adminDao;

    @FlushCache
    @Override
    public void save(Admin admin) {
        admin.setId(UUID.randomUUID().toString());
        adminDao.save(admin);
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin queryOne(String name) {
        Admin admin = adminDao.queryOne(name);
        return admin;
    }
}
