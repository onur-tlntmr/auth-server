package com.example.springjwt.util;

import com.example.springjwt.entity.Role;
import com.example.springjwt.entity.User;
import com.example.springjwt.service.RoleService;
import com.example.springjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultRecords implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {
        // Created roles
        List<Role> roles = List.of(
                new Role((short) 1, "ROLE_SUPER_ADMIN"),
                new Role((short) 2, "ROLE_ADMIN"),
                new Role((short) 3, "ROLE_USER"));

        // Roles save to db
        roles.forEach(roleService::saveRole);

        // Created users
        List<User> users = List.of(
                new User("root", "Super Admin", "superadmin@testuser.com",
                        "root@1234"),

                new User("admin", "Admin", "admin@testuser.com",
                        "admin@1234"),

                new User("user", "User", "user@testuser.com",
                        "user@1234")
        );

        // Users save to db
        users.forEach(userService::saveUser);

        // Roles Save to users
        roles.forEach(role -> roleService.addRoleToUser("root", role.getName()));

        roles.subList(1, 3).
                forEach(role -> roleService.addRoleToUser("admin", role.getName()));

        roleService.addRoleToUser("user", roles.get(2).getName());
    }
}
