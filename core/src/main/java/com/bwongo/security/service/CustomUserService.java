package com.bwongo.security.service;

import com.bwongo.commons.models.exceptions.model.ExceptionType;
import com.bwongo.user_mgt.models.jpa.TGroupAuthority;
import com.bwongo.user_mgt.models.jpa.TUser;
import com.bwongo.commons.models.utils.Validate;
import com.bwongo.security.models.CustomUserDetails;
import com.bwongo.user_mgt.repository.TGroupAuthorityRepository;
import com.bwongo.user_mgt.repository.TUserRepository;
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
        Validate.notNull(groupAuthorities, ExceptionType.INSUFFICIENT_AUTH, "user %s has no permissions, to any services", user.getUsername());

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
