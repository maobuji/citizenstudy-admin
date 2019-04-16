package com.citizen.study.auth.config.integration.authenticator;


import com.citizen.study.auth.config.integration.IntegrationAuthentication;
/**
 * @author LIQIU
 * @date 2018-3-30
 **/
public class IntegrationAuthenticationContext {

    private static ThreadLocal<IntegrationAuthentication> holder = new ThreadLocal<IntegrationAuthentication>();

    public static void set(IntegrationAuthentication integrationAuthentication){
        holder.set(integrationAuthentication);
    }

    public static IntegrationAuthentication get(){
        return holder.get();
    }

    public static void clear(){
        holder.remove();
    }
}
