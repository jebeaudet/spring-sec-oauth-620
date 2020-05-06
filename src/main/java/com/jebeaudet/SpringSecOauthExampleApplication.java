package com.jebeaudet;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringSecOauthExampleApplication
{
    @SuppressWarnings("resource")
    public static void main(String[] args)
    {
        SpringApplication application = new SpringApplication(SpringSecOauthExampleApplication.class);
        Map<String, Object> properties = new HashMap<>();

        //        properties.put("test", "withoutEnableAuthorizationServer"); //Working but I have to import my custom class and have an extra authentication provider
        properties.put("test", "withEnableAuthorizationServer"); //Working but there's an extra filter chain and an extra authentication provider
        //        properties.put("test", "withEnableAuthorizationServerNotWorking"); //Not Working because the extra filter chain has precendence on my custom one

        application.setDefaultProperties(properties);
        application.run(args);
    }
}
