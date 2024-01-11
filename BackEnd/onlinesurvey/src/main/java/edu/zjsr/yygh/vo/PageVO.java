package edu.zjsr.yygh.vo;

import java.util.List;

public class PageVO<T> {
    private Integer count;

    private List<T> list;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
