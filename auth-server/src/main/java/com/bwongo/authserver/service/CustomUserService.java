package com.bwongo.authserver.service;

import com.bwongo.authserver.model.CustomUserDetails;
import com.bwongo.authserver.repository.TGroupAuthorityRepository;
import com.bwongo.authserver.repository.TUserRepository;
import com.bwongo.commons.models.jpa.security.TGroupAuthority;
import com.bwongo.commons.models.jpa.security.TUser;
import com.bwongo.commons.models.utils.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author bkaaron
 * @Project bega
 * @Date 3/22/23
 **/
@Service
@RequiredArgsConstructor
public class CustomUserService {

    private final TUserRepository userRepository;
    private final TGroupAuthorityRepository groupAuthorityRepository;

    public CustomUserDetails getCustomUserDetails(String username) {
        TUser user = userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("username: %s not found", username))
        );

        List<TGroupAuthority> groupAuthorities = groupAuthorityRepository.findByUserGroup(user.getUserGroup());
        Validate.notNull(groupAuthorities, String.format("user %s has no permissions, to any services", user.getUsername()));

        Set<SimpleGrantedAuthority> permissions = groupAuthorities
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission().getName()))
                .collect(Collectors.toSet());

        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getEmail())
                .enabled(user.isApproved())
                .accountNonExpired(user.isAccountExpired())
                .accountNonLocked(user.isAccountLocked())
                .authorities(permissions)
                .build();
    }
}
