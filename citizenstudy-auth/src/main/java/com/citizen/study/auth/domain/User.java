package com.citizen.study.auth.domain;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * Created by Administrator on 2019/3/28.
 * <p>
 * 用户，这里的用户是系统唯一的用户，主要是为了跨租户的统一
 */

@TableName("t_user")
public class User {

    private Long id;

    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
