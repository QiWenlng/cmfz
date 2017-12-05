package com.hugo.common.dao;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;

public interface BasicDao<T> {
    /**
     * 保存一个对象的方法
     *
     * @param t 当前保存的对象
     */
    @Caching(evict = {@CacheEvict(value = "baseCache", allEntries = true)})
    public void save(T t);

    /**
     * 修改一个对象的方法
     *
     * @param t 当前修改对象
     */
    @Caching(evict = {@CacheEvict(value = "baseCache", allEntries = true)})
    public void modify(T t);

    /**
     * 删除一个对象的方法
     * 删除该value下的所有缓存，整个都刷新
     * @param id 当前删除对象的id
     */
    @Caching(evict = {@CacheEvict(value = "baseCache", allEntries = true)})
    public void remove(String id);

    /**
     * 根据主键查询一个对象的方法
     *
     * @param id 当前查询的id
     * @return 根据id查询的对象
     */
    //@Cacheable(value = "baseCache", key = "#root.methodName")
    public T queryOne(String id);


    /**
     * 查询所有对象的方法
     *
     * @return 查询到的结果
     */
    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<T> queryAll();
}
