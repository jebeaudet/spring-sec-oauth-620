/**
 * Copyright (c) Coveo Solutions Inc.
 */
package com.jebeaudet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerSecurityConfiguration;

@Configuration
@ConditionalOnProperty(name = "test", havingValue = "withoutEnableAuthorizationServer")
public class WithoutEnableAuthorizationServer
{
    @Configuration
    @Import({ AuthorizationServerEndpointsConfiguration.class, AuthProviderAdder.class })
    @ConditionalOnProperty(name = "test", havingValue = "withoutEnableAuthorizationServer")
    public static class BaseAuthServerConfigurer extends AuthorizationServerConfigurerAdapter
    {
    }

    @Configuration
    @ConditionalOnProperty(name = "test", havingValue = "withoutEnableAuthorizationServer")
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
