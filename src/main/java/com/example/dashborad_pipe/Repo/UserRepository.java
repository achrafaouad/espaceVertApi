package com.example.dashborad_pipe.Repo;

import com.example.dashborad_pipe.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser,Long> {
MyUser findByUsername(String username);
Optional<MyUser> findById(Long id);
}
