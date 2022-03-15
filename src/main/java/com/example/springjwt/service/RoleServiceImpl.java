package com.example.springjwt.service;

import com.example.springjwt.entity.Role;
import com.example.springjwt.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepo repo;

    @Override
    public List<Role> getRoles() {
        return repo.findAllByOrderById();
    }
}
