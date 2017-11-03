package com.thinkhr.external.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Provide some description of this class TODO
 * 
 * @author Surabhi Bhawsar
 * @since 2017-11-01
 *
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    // Move this to DB
    @Value("${com.thr.security.tenant}")
    private String TENANT;
    
    /**
     * TODO
     */
    @Override
    public OAuth2AccessToken enhance(
            OAuth2AccessToken accessToken,
            OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("organization", TENANT);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
