package com.butikimoti.real_estate_planner.service.util;

import com.butikimoti.real_estate_planner.model.CurrentUserDetails;
import com.butikimoti.real_estate_planner.model.entity.UserEntity;
import com.butikimoti.real_estate_planner.model.enums.UserRole;
import com.butikimoti.real_estate_planner.repository.UserEntityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class CurrentUserDetailsService implements UserDetailsService {
    private final UserEntityRepository userEntityRepository;

    public CurrentUserDetailsService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userEntityRepository
                .findByEmail(email)
                .map(CurrentUserDetailsService::mapUserEntityToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private static CurrentUserDetails mapUserEntityToUserDetails(UserEntity user) {
        return new CurrentUserDetails(
                user.getEmail(),
                user.getPassword(),
                List.of(mapUserRoleToGrantedAuthority(user.getUserRole())),
                user.getId(),
                user.getCompany().getName(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone()
        );
    }

    private static GrantedAuthority mapUserRoleToGrantedAuthority(UserRole userRole) {
        return new SimpleGrantedAuthority("ROLE_" + userRole);
    }
}
