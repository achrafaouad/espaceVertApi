package com.example.dashborad_pipe.services;

import com.example.dashborad_pipe.Repo.RoleRepository;
import com.example.dashborad_pipe.Repo.UserRepository;
import com.example.dashborad_pipe.entities.MyUser;
import com.example.dashborad_pipe.entities.Role;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImplimentation implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepo.findByUsername(username);
        if(user == null){
            log.info("user not found   ");
            throw new UsernameNotFoundException("user not found   ");
        }else{
            log.info("user  found   ");
        }

        Collection<SimpleGrantedAuthority> auth = new ArrayList<>();
        user.getRoles().forEach(role -> {
            auth.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),auth);
    }


    @Autowired
    public UserServiceImplimentation(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public MyUser save(MyUser user) {
        return this.userRepo.save(user);
    }
    @Override
    public MyUser saveUser(MyUser user) {
        log.info("save user ");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("save role ");
        return this.roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolName) {
        log.info("adding role to users");
        MyUser myUser = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(rolName);
        log.info("adding  role {} to user {}",rolName,username);
        myUser.getRoles().add(role);
    }
    @Override
    public MyUser getUser(String username) {
        log.info("getting new user");
        return this.userRepo.findByUsername(username);
    }

    public Optional<MyUser> getUserById(Long id) {
        log.info("getting new user");
        return this.userRepo.findById(id);
    }


    @Override
    public List<MyUser> getUsers() {
        log.info("getting users");
        return this.userRepo.findAll();
    }


}
