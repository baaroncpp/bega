package com.bwongo.authserver.config;

import com.bwongo.authserver.model.CustomUserDetails;
import com.bwongo.authserver.repository.TGroupAuthorityRepository;
import com.bwongo.authserver.repository.TUserRepository;
import com.bwongo.authserver.service.AuthenticationService;
import com.bwongo.authserver.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class ApplicationConfig {

    private final TUserRepository userRepository;
    private final TGroupAuthorityRepository groupAuthorityRepository;
    private final AuthenticationService authenticationService;
    private final CustomUserService customUserService;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> getMappedCustomUserDetails(username);
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
