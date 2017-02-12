package com.example.hecheng.popdropdownmenu.bean;

import java.io.Serializable;

/**
 * Created by Chengbin He on 2016/12/5.
 */

public class DropDownBean implements Serializable {
    private int id;
    private String name;

    public DropDownBean() {
    }

    public DropDownBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
