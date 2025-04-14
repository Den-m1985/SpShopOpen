package ru.spshop.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.spshop.model.enums.RoleEnum;

import java.util.Collection;
import java.util.List;
import java.util.Set;

//@Getter
//@Setter
public class JwtAuthentication /*implements Authentication*/ {

//    private boolean authenticated;
//    private String username;
//    private String firstName;
//    private Role role;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());
//        return List.of(authority);
//    }
//
//    @Override
//    public Object getCredentials() {
//        return null;
//    }
//
//    @Override
//    public Object getDetails() {
//        return null;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return username;
//    }
//
//    @Override
//    public boolean isAuthenticated() {
//        return authenticated;
//    }
//
//    @Override
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//        this.authenticated = isAuthenticated;
//    }
//
//    @Override
//    public String getName() {
//        return firstName;
//    }

}