package com.example.dashborad_pipe;

import com.example.dashborad_pipe.entities.MyUser;
import com.example.dashborad_pipe.entities.Role;
import com.example.dashborad_pipe.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class DashboradPipeApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DashboradPipeApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
	{
		return application.sources(DashboradPipeApplication.class);
	}

//@Bean
//	CommandLineRunner run(UserService userService){
//		return Args->{
//			userService.saveRole(Role.builder().name("simple_user").build());
////			userService.saveRole(Role.builder().name("Role_Manager").build());
////			userService.saveRole(Role.builder().name("Role_Admin").build());
////			userService.saveRole(Role.builder().name("Role_Super_admin").build());
////		userService.saveUser(MyUser.builder().username("achraf").name("achraf").password("admin").roles(new ArrayList<>()).build());
////		userService.saveUser(MyUser.builder().username("ahmed").name("ahmed").password("admin").roles(new ArrayList<>()).build());
////		userService.saveUser(MyUser.builder().username("said").name("said").password("admin").roles(new ArrayList<>()).build());
////		userService.addRoleToUser("achraf","Role_User");
////		userService.addRoleToUser("achraf","Role_Admin");
////		userService.addRoleToUser("ahmed","Role_User");
////		userService.addRoleToUser("said","Role_User");
//
//		};
//	}

}
