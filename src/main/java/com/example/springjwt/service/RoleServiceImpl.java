package com.example.springjwt.service;

import com.example.springjwt.entity.Role;
import com.example.springjwt.entity.User;
import com.example.springjwt.repository.RoleRepo;
import com.example.springjwt.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;
    private final UserRepo userRepo;

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAllByOrderById();
    }


    @Override
    public Role saveRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        User user = userRepo.findByUserName(username);
        Role role = roleRepo.findByName(roleName);

        user.getRoles().add(role);

    }
}
