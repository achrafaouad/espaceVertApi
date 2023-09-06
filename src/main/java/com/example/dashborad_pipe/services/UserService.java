package com.example.dashborad_pipe.services;

import com.example.dashborad_pipe.entities.MyUser;
import com.example.dashborad_pipe.entities.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {
    MyUser saveUser(MyUser user);
    MyUser save(MyUser user);
    Role saveRole(Role Role);
    void addRoleToUser(String username,String rolName);
    MyUser getUser(String username);
    Optional<MyUser> getUserById(Long id);
    List<MyUser> getUsers();
}
