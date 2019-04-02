package com.citizen.study.auth.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019/4/2.
 */
@Configuration
@MapperScan("com.citizen.study.auth.mapper")
public class MybatisScanConfiguration {
}
