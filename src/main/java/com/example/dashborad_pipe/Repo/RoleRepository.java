package com.example.dashborad_pipe.Repo;

import com.example.dashborad_pipe.entities.MyUser;
import com.example.dashborad_pipe.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
