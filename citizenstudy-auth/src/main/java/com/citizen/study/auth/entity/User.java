package com.citizen.study.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.citizen.study.common.base.entity.BaseEntity;

/**
 * Created by Administrator on 2019/3/28.
 * <p>
 * 用户，这里的用户是系统唯一的用户，主要是为了跨租户的统一
 */

@TableName("t_user")
public class User extends BaseEntity{

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
