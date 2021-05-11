package com.mediary.Services.Implementation;

import com.mediary.Models.DTOs.UserDto;
import com.mediary.Models.DTOs.Request.ChangePasswordDto;
import com.mediary.Models.DTOs.Request.UserRegisterDto;
import com.mediary.Models.DTOs.Request.UserUpdateDto;
import com.mediary.Models.Entities.UserEntity;
import com.mediary.Models.Enums.Gender;
import com.mediary.Repositories.UserRepository;
import com.mediary.Services.Const;
import com.mediary.Services.Exceptions.User.*;
import com.mediary.Services.Interfaces.IUserService;
import com.mediary.Models.DTOs.JwtRequest;
import com.mediary.Models.DTOs.JwtResponse;
import com.mediary.Models.DTOs.Response.LoginDto;
import com.mediary.Models.DTOs.Response.UserDataDto;
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

import lombok.extern.slf4j.Slf4j;

import com.mediary.Services.Exceptions.EntityNotFoundException;
import com.mediary.Services.Exceptions.EnumConversionException;
import com.mediary.Services.Exceptions.IncorrectFieldException;

@Slf4j
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
    public int updateUserDetails(UserUpdateDto user, String authHeader)
            throws EntityNotFoundException, EnumConversionException, IncorrectFieldException {
        UserEntity newUser = getUserByAuthHeader(authHeader);

        if (newUser == null)
            return Const.userDoesNotExist;

        // if (user.getEmail().length() > 254)
        // return Const.toLongEmail;

        // if (user.getEmail() != null && !user.getEmail().equals(newUser.getEmail())) {
        // if (userRepository.findUserEntitiesByEmail(user.getEmail()) != null)
        // return Const.emailAlreadyUsed;
        // newUser.setEmail(user.getEmail());
        // }
        if (user.getFullName().length() > 40)
            return Const.toLongName;
        if (!user.getFullName().equals(newUser.getFullName()))
            newUser.setFullName(user.getFullName());

        if (!user.getGender().equals(newUser.getGender().getCode())) {
            Gender gender = Gender.convertStringToEnum(user.getGender());
            newUser.setGender(gender);
        }
        if (!user.getDateOfBirth().equals(newUser.getDateOfBirth()))
            newUser.setDateOfBirth(user.getDateOfBirth());
        if (!user.getWeight().equals(newUser.getWeight()))
            newUser.setWeight(user.getWeight());
        if (!user.getHeight().equals(newUser.getHeight()))
            newUser.setHeight(user.getHeight());
        userRepository.save(newUser);

        return Const.userDetailsUpdateSuccess;
    }

    @Override
    public UserDto getUserDetails(String authHeader) throws EntityNotFoundException {
        UserDto usersDto = new UserDto();
        UserEntity user = getUserByAuthHeader(authHeader);
        usersDto.setId(user.getId());
        usersDto.setFullName(user.getFullName());
        usersDto.setGender(user.getGender().getCode());
        usersDto.setEmail(user.getEmail());
        usersDto.setDateOfBirth(user.getDateOfBirth());
        usersDto.setWeight(user.getWeight());
        return usersDto;
    }

    @Override
    public int updatePassword(ChangePasswordDto passwordDto, String authHeader) throws EntityNotFoundException {
        UserEntity user = getUserByAuthHeader(authHeader);
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

        UserDataDto userData = new UserDataDto();
        UserEntity userEntity = userRepository.findUserEntitiesByEmail(userDetails.getUsername());
        userData.setGender(userEntity.getGender().getCode());
        userData.setWeight(userEntity.getWeight());
        userData.setFullName(userEntity.getFullName());
        userData.setDateOfBirth(userEntity.getDateOfBirth());

        LoginDto loginDto = new LoginDto();
        loginDto.setUserData(userData);
        JwtResponse jwtToken = new JwtResponse(jwtTokenUtils.generateToken(userDetails));
        loginDto.setToken(jwtToken.getToken());

        return new ResponseEntity(loginDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> signInAfterRegistration(UserRegisterDto user)
            throws EmailToLongException, PasswordToLongException, EmailAlreadyUsedException, FullNameToLongException {
        int result = registerNewUser(user);
        if (result == Const.emailAlreadyUsed)
            throw new EmailAlreadyUsedException("E-mail is already used!");
        if (result == Const.toLongEmail)
            throw new EmailToLongException("E-mail is too long!");
        if (result == Const.toLongName)
            throw new FullNameToLongException("Typed name is too long!");
        if (result == Const.toLongPassword)
            throw new PasswordToLongException("Typed password is too long!");
        if (result == Const.registrationSuccess) {
            JwtRequest token = new JwtRequest();
            token.setPassword(user.getPassword());
            token.setEmail(user.getEmail());
            return this.authenticateUser(token);
        } else {
            return new ResponseEntity("Bad Registration", HttpStatus.UNAUTHORIZED);
        }
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

    private int registerNewUser(UserRegisterDto user) {
        if (userRepository.findUserEntitiesByEmail(user.getEmail()) != null)
            return Const.emailAlreadyUsed;
        if (user.getEmail().length() > 254)
            return Const.toLongEmail;
        if (user.getPassword().length() > 72)
            return Const.toLongPassword;
        if (user.getFullName().length() > 40)
            return Const.toLongName;
        UserEntity newUser = new UserEntity();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFullName(user.getFullName());
        userRepository.save(newUser);
        return Const.registrationSuccess;
    }

    @Override
    public ResponseEntity<?> refreshToken(String authHeader) {

        UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtTokenUtils.getEmailFromToken(authHeader.substring(7)));

        return new ResponseEntity<>(new JwtResponse(jwtTokenUtils.generateToken(userDetails)), HttpStatus.OK);
    }

    @Override
    public UserEntity getUserByAuthHeader(String authHeader) throws EntityNotFoundException {
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(jwtTokenUtils.getEmailFromToken(authHeader.substring(7)));
        String email = userDetails.getUsername();
        UserEntity user = userRepository.findByEmail(email);
        if (user != null) {
            return user;
        } else {
            throw new EntityNotFoundException("User doesn't exist");
        }
    }

}
