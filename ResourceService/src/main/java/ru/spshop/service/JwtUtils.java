package ru.spshop.service;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.spshop.model.JwtAuthentication;
import ru.spshop.model.Role;

import java.util.*;

//@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

//    public static JwtAuthentication generate(Claims claims) {
//        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
//        jwtInfoToken.setRoles(getRoles(claims));
//        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
//        jwtInfoToken.setUsername(claims.getSubject());
//        return jwtInfoToken;
//    }

//    private static Set<Role> getRoles(Claims claims) {
//        Set<Role> roles = new HashSet<>();
//        // Получаем список ролей из объекта Claims
//        List<Map<String, String>> rolesList = (List<Map<String, String>>) claims.get("roles");
//        // Перебираем каждый элемент списка ролей
//        if (rolesList != null) {
//            for (Map<String, String> roleMap : rolesList) {
//                String authority = roleMap.get("authority");
//                if (authority != null) {
//                    // Создаем объект Role и добавляем его в набор
//                    roles.add(Role.valueOf(authority));
//                }
//            }
//        }
//        return roles;
//    }

}
