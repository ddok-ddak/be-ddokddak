package com.ddokddak.auth.domain.oauth;

import com.ddokddak.member.domain.entity.Member;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserPrincipal implements UserDetails, OAuth2User {
    private Long id;
    private String email;
    private String password;
    private String oauth2Id;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Builder
    public UserPrincipal(Long id, String oauth2Id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.oauth2Id = oauth2Id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /* default login */
    public static UserPrincipal create(Member member, String oauth2Id) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority(member.getRole().getCode()));

        return UserPrincipal.builder()
                .id(member.getId())
                .oauth2Id(oauth2Id)
                .email(member.getEmail())
                .password(member.getPassword())
                .authorities(authorities)
                .build();
    }

    /* oauth login */
    public static UserPrincipal create(Member member, String oauth2Id, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(member, oauth2Id);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    // UserDetail Override
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // OAuth2User Override
    @Override
    public String getName() { return email; }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    private void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Long getId() {
        return id;
    }

    public String getOauth2Id() { return this.oauth2Id; }
}