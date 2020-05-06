/**
 * Copyright (c) Coveo Solutions Inc.
 */
package com.jebeaudet;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

@Component
@DependsOn("springSecurityFilterChain")
public class AuthProviderTester
{
    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void postConstruct()
    {
        FilterChainProxy filterChainProxy = context.getBean(FilterChainProxy.class);
        BasicAuthenticationFilter basicAuthenticationFilter = filterChainProxy.getFilters("/oauth/token")
                                                                              .stream()
                                                                              .filter(BasicAuthenticationFilter.class::isInstance)
                                                                              .map(BasicAuthenticationFilter.class::cast)
                                                                              .findFirst()
                                                                              .orElseThrow(() -> new RuntimeException("Couldn't find the BasicAuthenticationFilter"));

        ProviderManager authenticationManager = (ProviderManager) ReflectionTestUtils.getField(basicAuthenticationFilter,
                                                                                               "authenticationManager");
        System.out.println("\nAuthentication providers list : ");
        authenticationManager.getProviders().forEach(provider -> System.out.println(provider.getClass()));

        Optional<DaoAuthenticationProvider> daoAuthenticationProvider = authenticationManager.getProviders()
                                                                                             .stream()
                                                                                             .filter(DaoAuthenticationProvider.class::isInstance)
                                                                                             .map(DaoAuthenticationProvider.class::cast)
                                                                                             .findFirst();
        if (daoAuthenticationProvider.isPresent()) {
            System.out.println("\nUserDetailsService class of the DaoAuthenticationProvider instance : ");
            UserDetailsService userDetailsService = (UserDetailsService) ReflectionTestUtils.getField(daoAuthenticationProvider.get(),
                                                                                                      "userDetailsService");
            System.out.println(userDetailsService.getClass() + "\n");
        } else {
            System.out.println("No DaoAuthenticationProvider found!\n");
        }
        System.exit(0);
    }
}
