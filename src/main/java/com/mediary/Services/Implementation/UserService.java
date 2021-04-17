package com.mediary.Services.Implementation;

import com.mediary.Models.Entities.UserEntity;
import com.mediary.Repositories.UserRepository;
import com.mediary.Services.Const;
import com.mediary.Services.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public int registerNewUser(UserEntity user) {
        if(userRepository.findUserEntitiesByUsername(user.getUsername()) != null)
            return Const.usernameAlreadyUsed;
        if(userRepository.findUserEntitiesByEmail(user.getEmail()) != null)
            return Const.emailAlreadyUsed;
        if(user.getUsername().length() > 30)
            return Const.toLongUsername;
        if(user.getEmail().length() > 254)
            return Const.toLongEmail;
        if(user.getPassword().length() > 72)
            return Const.toLongPassword;
        if(user.getFullName().length() > 40)
            return Const.toLongName;
        UserEntity newUser = new UserEntity();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFullName(user.getFullName());
        newUser.setGender(user.getGender());
        newUser.setDateofbirth(user.getDateofbirth());
        newUser.setWeight(user.getWeight());
        userRepository.save(newUser);
        return Const.registrationSuccess;
    }

    @Override
    public int updateUserDetails(UserEntity user) {
        Optional<UserEntity> newUser = userRepository.findById(user.getId());

        if(newUser.isEmpty()) return Const.userDoesNotExist;

        if(user.getEmail().length() > 254)
            return Const.toLongEmail;

        if(user.getEmail() != null && !user.getEmail().equals(newUser.get().getEmail())){
            if(userRepository.findUserEntitiesByEmail(user.getEmail()) != null) return Const.emailAlreadyUsed;
            newUser.get().setEmail(user.getEmail());
        }
        if(user.getFullName().length() > 40)
            return Const.toLongName;
        if(!user.getFullName().equals(newUser.get().getFullName()))
            newUser.get().setFullName(user.getFullName());
        if(!user.getGender().equals(newUser.get().getGender()))
            newUser.get().setGender(user.getGender());
        if(!user.getDateofbirth().equals(newUser.get().getDateofbirth()))
            newUser.get().setDateofbirth(user.getDateofbirth());
        if(!user.getWeight().equals(newUser.get().getWeight()))
            newUser.get().setWeight(user.getWeight());
        userRepository.save(newUser.get());

        return Const.userDetailsUpdateSuccess;
    }
}
