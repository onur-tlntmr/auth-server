package com.example.springjwt.service;


import com.example.springjwt.entity.Role;
import com.example.springjwt.entity.User;
import com.example.springjwt.exception.ApiRequestException;
import com.example.springjwt.repository.RoleRepo;
import com.example.springjwt.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    private boolean isOwnerOrAdmin(User attemptUser, User data) {

        var isAdmin = attemptUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        var isOwner = attemptUser.getId() == data.getId();

        return isAdmin || isOwner;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUserName(username);

        if (user == null)
            throw new UsernameNotFoundException("User is not exist!");

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();


        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));


        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(), authorities);
    }

    @Override
    public User saveUser(User user) {

        Role userRole = roleRepo.findByName("ROLE_USER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.getRoles().add(userRole);


        return userRepo.save(user);
    }

    @Override
    public User getUser(String username, Principal principal) {

        var data = userRepo.findByUserName(username);
        var principalUser = userRepo.findByUserName(principal.getName());

        if (data == null)
            throw new ApiRequestException("User is not exist!");

        if (!isOwnerOrAdmin(principalUser, data))
            throw new ApiRequestException("You are not authorized to perform this action!");


        return userRepo.findByUserName(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public void updateUser(User user, Principal principal) {

        var principalUser = userRepo.findByUserName(principal.getName());

        if (user.getId() == 0)
            throw new ApiRequestException("User id must not empty !");

        if (!userRepo.existsById(user.getId()))
            throw new ApiRequestException("User is not exist!");

        if (!isOwnerOrAdmin(principalUser, user))
            throw new ApiRequestException("You are not authorized to perform this action!");


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);
    }

    @Override
    public void deleteUser(String username, Principal principal) {

        var principalUser = userRepo.findByUserName(principal.getName());

        User user = userRepo.findByUserName(username);

        if (user == null)
            throw new ApiRequestException("User is not exist!");

        if (!isOwnerOrAdmin(principalUser, user))
            throw new ApiRequestException("You are not authorized to perform this action!");


        userRepo.delete(user);
    }


}
