package com.mshernandez.aws_upload_demo.aws_upload_demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * A configuration for (insecure) local testing purposes.
 * No HTTPS, Disable CORS
 */
@Profile("development")
@Configuration
@EnableWebSecurity
public class DevelopmentWebSecurityConfig extends WebSecurityConfigurerAdapter
{
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.cors().and().csrf().disable();
    }
}