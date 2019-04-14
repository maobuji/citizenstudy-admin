package com.citizen.study.auth.config;

import com.citizen.study.auth.error.MssWebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import sun.security.util.SecurityConstants;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/4/3.
 *
 * 参考代码https://github.com/babylikebird/Micro-Service-Skeleton/blob/master/mss-oauth/src/main/java/com/microservice/skeleton/auth/config/AuthorizationServerConfig.java
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    RedisTokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }


    /**
     * 用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化
     * 你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。这里采用了默认的存储数据库的方式
     * 创表语句在这里https://github.com/spring-projects/spring-security-oauth/blob/master/spring-security-oauth2/src/test/resources/schema.sql
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束.
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 允许表单认证请求
                .allowFormAuthenticationForClients()
                // spel表达式 访问公钥端点（/auth/token_key）需要认证
                .tokenKeyAccess("isAuthenticated()")
                // spel表达式 访问令牌解析端点（/auth/check_token）需要认证
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // token增强链
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        // 把jwt增强，与额外信息增强加入到增强链
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(redisTokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .reuseRefreshTokens(false);
        // 添加认证异常处理器
        endpoints.exceptionTranslator(customWebResponseExceptionTranslator);
    }



    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator(){
        return new MssWebResponseExceptionTranslator();
    }

    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices()
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(SecurityConstants.SIGN_KEY);
        return jwtAccessTokenConverter;
    }

    /**
     * jwt token增强，添加额外信息
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

                // 添加额外信息的map
                final Map<String, Object> additionMessage = new HashMap<String, Object>(2);
                // 获取当前登录的用户
                UserDetailsImpl user = (UserDetailsImpl) oAuth2Authentication.getUserAuthentication().getPrincipal();
//                // 登录日志记UserDetailsImpl录
//                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//                SysLogDTO sysLogDTO = new SysLogDTO();
//                sysLogDTO
//                        .setCreateBy(user.getUsername())
//                        .setRequestUri(request.getRequestURI())
//                        .setUserAgent(request.getHeader("user-agent"))
//                        .setType(SysLogTypeEnum.LOGIN.getCode())
//                        .setStatus(OperationStatusEnum.SUCCESS.getCode())
//                        .setModuleName("auth认证模块")
//                        .setActionName("登录")
//                        .setServiceId(FisherServiceNameConstants.FISHER_AUTH)
//                        .setRemoteAddr(UrlUtil.getRemoteHost(request))
//                        .setMethod(request.getMethod());
//                rabbitTemplate.convertAndSend(MqQueueNameConstant.SYS_LOG_QUEUE, sysLogDTO);
//                log.info("当前用户为：{}", user);
//                // 如果用户不为空 则把id放入jwt token中
                if (user != null) {
                    additionMessage.put(UserConstants.USER_ID, user.getUserId());
                    additionMessage.put(UserConstants.USER_NAME, user.getUsername());
                }
                ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(additionMessage);
                return oAuth2AccessToken;
            }
        };
    }
｝