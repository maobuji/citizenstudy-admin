package com.citizen.study.auth.service;

import com.citizen.study.auth.config.integration.IntegrationAuthentication;
import com.citizen.study.auth.config.integration.authenticator.IntegrationAuthenticationContext;
import com.citizen.study.auth.config.integration.authenticator.IntegrationAuthenticator;
import com.citizen.study.auth.config.integration.authenticator.SysUserAuthentication;
import com.citizen.study.auth.dto.UserDetailsImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 集成认证用户服务
 *
 * @author LIQIU
 * @date 2018-3-7
 **/
@Service
public class IntegrationUserDetailsService implements UserDetailsService {

//    @Autowired
//    private SysAuthorizeClient sysAuthorizeClient;

    private List<IntegrationAuthenticator> authenticators;

    @Autowired(required = false)
    public void setIntegrationAuthenticators(List<IntegrationAuthenticator> authenticators) {
        this.authenticators = authenticators;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        IntegrationAuthentication integrationAuthentication = IntegrationAuthenticationContext.get();
        //判断是否是集成登录
        if (integrationAuthentication == null) {
            integrationAuthentication = new IntegrationAuthentication();
        }
        integrationAuthentication.setUsername(username);
        SysUserAuthentication sysUserAuthentication = this.authenticate(integrationAuthentication);

        if(sysUserAuthentication == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        UserDetailsImpl user = new UserDetailsImpl();
        BeanUtils.copyProperties(sysUserAuthentication, user);
//        this.setAuthorize(user);
        return user;

    }

//    /**
//     * 设置授权信息
//     *
//     * @param user
//     */
//    public void setAuthorize(UserDetailsImpl user) {
//        Authorize authorize = this.sysAuthorizeClient.getAuthorize(user.getId());
//        user.setRoles(authorize.getRoles());
//        user.setResources(authorize.getResources());
//    }

    private SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {
        if (this.authenticators != null) {
            for (IntegrationAuthenticator authenticator : authenticators) {
                if (authenticator.support(integrationAuthentication)) {
                    return authenticator.authenticate(integrationAuthentication);
                }
            }
        }
        return null;
    }
}
