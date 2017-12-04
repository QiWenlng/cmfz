package com.hugo.common.entity;

import java.io.Serializable;
import java.util.List;

public class Paging<T> implements Serializable {

    private Integer total;//总行数
    private List<T> rows;

    @Override
    public String toString() {
        return "Paging{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Paging() {
    }

    public Paging(Integer total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
