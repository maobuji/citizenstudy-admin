package com.citizen.study.auth.service;

import com.citizen.study.auth.domain.User;
import com.citizen.study.auth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/4/2.
 */
@Service
public class UserServiceImpl {

    @Autowired
    UserMapper userMapper;

    public void test() {
        User user = new User();
        userMapper.insert(user);
    }
}
