package com.mediary.Services.Implementation;

import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.DTOs.Request.ChangePasswordDto;
import com.mediary.Models.DTOs.Request.UserRegisterDto;
import com.mediary.Models.DTOs.Request.UserUpdateDto;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Repositories.UserRepository;
import com.mediary.Services.Const;
import com.mediary.Services.Interfaces.IUserService;
import com.mediary.Models.DTOs.JwtRequest;
import com.mediary.Models.DTOs.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Override
    public int registerNewUser(UserRegisterDto user) {
        if (userRepository.findUserEntitiesByUsername(user.getUsername()) != null)
            return Const.usernameAlreadyUsed;
        if (userRepository.findUserEntitiesByEmail(user.getEmail()) != null)
            return Const.emailAlreadyUsed;
        if (user.getUsername().length() > 30)
            return Const.toLongUsername;
        if (user.getEmail().length() > 254)
            return Const.toLongEmail;
        if (user.getPassword().length() > 72)
            return Const.toLongPassword;
        if (user.getFullName().length() > 40)
            return Const.toLongName;
        UserEntity newUser = new UserEntity();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFullName(user.getFullName());
        newUser.setGender(user.getGender());
        newUser.setDateofbirth(user.getDateofbirth());
        userRepository.save(newUser);
        return Const.registrationSuccess;
    }

    @Override
    public int updateUserDetails(UserUpdateDto user, Integer userId) {
        Optional<UserEntity> newUser = userRepository.findById(userId);

        if (newUser.isEmpty())
            return Const.userDoesNotExist;

        if (user.getEmail().length() > 254)
            return Const.toLongEmail;

        if (user.getEmail() != null && !user.getEmail().equals(newUser.get().getEmail())) {
            if (userRepository.findUserEntitiesByEmail(user.getEmail()) != null)
                return Const.emailAlreadyUsed;
            newUser.get().setEmail(user.getEmail());
        }
        if (user.getFullName().length() > 40)
            return Const.toLongName;
        if (!user.getFullName().equals(newUser.get().getFullName()))
            newUser.get().setFullName(user.getFullName());
        if (!user.getGender().equals(newUser.get().getGender()))
            newUser.get().setGender(user.getGender());
        if (!user.getDateofbirth().equals(newUser.get().getDateofbirth()))
            newUser.get().setDateofbirth(user.getDateofbirth());
        userRepository.save(newUser.get());

        return Const.userDetailsUpdateSuccess;
    }

    @Override
    public UserDto getUserById(int id) {
        UserDto usersDto = new UserDto();
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isEmpty())
            return null;
        usersDto.setId(user.get().getId());
        usersDto.setUsername(user.get().getUsername());
        usersDto.setFullName(user.get().getFullName());
        usersDto.setGender(user.get().getGender());
        usersDto.setEmail(user.get().getEmail());
        usersDto.setDateOfBirth(user.get().getDateofbirth());
        usersDto.setWeight(user.get().getWeight());
        return usersDto;
    }

    @Override
    public int updatePassword(ChangePasswordDto passwordDto, Integer id) {
        UserEntity user = userRepository.getUserEntityById(id);
        if (user == null)
            return Const.userDoesNotExist;
        if (!passwordEncoder.encode(passwordDto.getOldPassword()).equals(passwordEncoder.encode(user.getPassword())))
            return Const.wrongPassword;
        if (passwordDto.getNewPassword().length() > 72)
            return Const.toLongPassword;
        if (!passwordEncoder.encode(passwordDto.getNewPassword()).equals(passwordEncoder.encode(user.getPassword())))
            user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
        return Const.userDetailsUpdateSuccess;
    }

    @Override
    public ResponseEntity<?> authenticateUser(JwtRequest authenticationRequest) {

        int result = -1;

        try {
            result = authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result == Const.badCredentials) {
            return new ResponseEntity("Bad Credentials", HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        return new ResponseEntity(new JwtResponse(jwtTokenUtils.generateToken(userDetails)), HttpStatus.OK);
    }

    private int authenticate(String username, String password) throws Exception {
        int result = -1;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            return Const.badCredentials;
        }

        return result;
    }
}
