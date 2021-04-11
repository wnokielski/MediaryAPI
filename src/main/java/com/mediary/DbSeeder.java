package com.mediary;

import com.mediary.Models.Entities.UsersEntity;
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
            com.mediary.Models.Entities.UsersEntity user = new UsersEntity();
            user.setFullname("Ywald");
            user.setDateofbirth(Date.valueOf("1998-04-19"));
            user.setGender("Male");
            user.setUid("123testuid123");

            userRepository.save(user);
        }

    }


}