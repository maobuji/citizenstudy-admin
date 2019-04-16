package com.citizen.study.auth.config.integration.authenticator;


import com.citizen.study.auth.config.integration.IntegrationAuthentication;

/**
 * @author LIQIU
 * @date 2018-3-31
 **/
public interface IntegrationAuthenticator {

    /**
     * 处理集成认证
     * @param integrationAuthentication
     * @return
     */
    SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication);


    /**
     * 进行预处理
     * @param integrationAuthentication
     */
    void prepare(IntegrationAuthentication integrationAuthentication);

     /**
     * 判断是否支持集成认证类型
     * @param integrationAuthentication
     * @return
     */
    boolean support(IntegrationAuthentication integrationAuthentication);

    /** 认证结束后执行
     * @param integrationAuthentication
     */
    void complete(IntegrationAuthentication integrationAuthentication);

}
