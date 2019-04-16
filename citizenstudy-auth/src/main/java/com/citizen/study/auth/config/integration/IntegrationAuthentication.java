package com.citizen.study.auth.config.integration;

import java.util.Map;

/**
 * @author LIQIU
 * @date 2018-3-30
 **/

public class IntegrationAuthentication {

    private String authType;
    private String username;
    private Map<String,String[]> authParameters;

    public String getAuthParameter(String paramter){
        String[] values = this.authParameters.get(paramter);
        if(values != null && values.length > 0){
            return values[0];
        }
        return null;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, String[]> getAuthParameters() {
        return authParameters;
    }

    public void setAuthParameters(Map<String, String[]> authParameters) {
        this.authParameters = authParameters;
    }
}
