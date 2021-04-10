package com.mediary;

import com.mediary.Repositories.UserRepository;
import com.mediary.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DbSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    //initialize database?
    boolean initializeDatabase = true;

    @Override
    public void run(String... strings){

//        if(initializeDatabase == true){
//            User user = new User();
//            user.setName("Ywald");
//
//            userRepository.save(user);
//        }

    }


}