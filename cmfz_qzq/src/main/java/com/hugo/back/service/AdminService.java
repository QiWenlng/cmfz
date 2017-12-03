package com.hugo.back.service;

import com.hugo.common.entity.Admin;

public interface AdminService {
    public void save(Admin admin);

    public Admin queryOne(String name);
}
