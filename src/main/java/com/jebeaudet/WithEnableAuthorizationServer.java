/**
 * Copyright (c) Coveo Solutions Inc.
 */
package com.jebeaudet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@ConditionalOnProperty(name = "test", havingValue = "withEnableAuthorizationServer")
public class WithEnableAuthorizationServer
{
    @Configuration
    @EnableAuthorizationServer
    @ConditionalOnProperty(name = "test", havingValue = "withEnableAuthorizationServer")
    public static class BaseAuthServerConfigurer extends AuthorizationServerConfigurerAdapter
    {
    }

    @Configuration
    @Order(-1)
    @ConditionalOnProperty(name = "test", havingValue = "withEnableAuthorizationServer")
    public static class AuthProviderAdder extends AuthorizationServerSecurityConfiguration
    {
        @Override
        protected void configure(HttpSecurity http) throws Exception
        {
            http.authenticationProvider(new TestAuthProvider());
            super.configure(http);
        }
    }
}
