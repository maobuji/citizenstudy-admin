package com.citizen.study.auth.mapper;

import com.citizen.study.auth.AuthApplication;
import com.citizen.study.auth.config.MybatisScanConfiguration;
import com.citizen.study.auth.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2019/4/2.
 */

/**
 *  这里指定的classes是可选的。如果不指定classes，则spring boot会启动整个spring容器，很慢（比如说会执行一些初始化，ApplicationRunner、CommandLineRunner等等）。不推荐
 *  指定的话，就只会初始化指定的bean，速度快，推荐
 */
@RunWith(SpringRunner.class)
//@SpringBootTest
@SpringBootTest(classes={AuthApplication.class,DataSourceAutoConfiguration.class, MybatisScanConfiguration.class})
@ActiveProfiles("auth-test")
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsert() {
        User u=new User();
        u.setId(234234L);
        u.setUsername("sdfs");
        userMapper.insert(u);
    }

}