package com.mediary;

import com.mediary.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class DbSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    //initialize database?
    boolean initializeDatabase = true;

    @Override
    public void run(String... strings){

        if(initializeDatabase == true){
            com.mediary.Models.Entities.UserEntity user = new com.mediary.Models.Entities.UserEntity();
            user.setFullName("Ywald");
            user.setDateofbirth(Date.valueOf("1998-04-19"));
            user.setGender("Male");
            user.setPassword("123testpass123");
            user.setUsername("testowylogin");
            user.setEmail("cos@tam.com");

            userRepository.save(user);

//            user = new com.mediary.Models.Entities.UserEntity();
//            user.setFullName("Testowy User");
//            user.setDateofbirth(Date.valueOf("1998-04-19"));
//            user.setGender("Male");
//            user.setUid("123ssssss123");
//
//            userRepository.save(user);
        }

    }


}