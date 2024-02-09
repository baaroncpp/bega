package com.bwongo.base.config;

import com.bwongo.security.models.CustomUserDetails;
import com.bwongo.security.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/22/23
 **/
@RequiredArgsConstructor
@Configuration
@ComponentScan("com.bwongo.security.config")
@EnableJpaRepositories({"com.bwongo.account_mgt.repository",
                        "com.bwongo.apartment_mgt.repository",
                        "com.bwongo.base.repository",
                        "com.bwongo.landlord_mgt.repository",
                        "com.bwongo.tenant_mgt.repository",
                        "com.bwongo.security.repository",
                        "com.bwongo.user_mgt.repository"})
@EntityScan({"com.bwongo.account_mgt.models.jpa",
        "com.bwongo.apartment_mgt.models.jpa",
        "com.bwongo.landlord_mgt.models.jpa",
        "com.bwongo.tenant_mgt.models.jpa",
        "com.bwongo.base.models.jpa",
        "com.bwongo.security.models.jpa",
        "com.bwongo.user_mgt.models.jpa"})
public class ApplicationConfig {

    private final CustomUserService customUserService;

    @Bean
    public UserDetailsService userDetailsService(){
        return this::getMappedCustomUserDetails;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    private CustomUserDetails getMappedCustomUserDetails(String username){
        return customUserService.getCustomUserDetails(username);
    }
}
