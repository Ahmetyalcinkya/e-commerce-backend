package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.responseDto.LoginResponse;
import com.workintech.Ecommerce.entity.Role;
import com.workintech.Ecommerce.entity.Token;
import com.workintech.Ecommerce.entity.User;
import com.workintech.Ecommerce.repository.RoleRepository;
import com.workintech.Ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Autowired

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    //TODO change the return value
    public User register(String name, String email, String password, String role){

        Optional<User> foundUser = userRepository.findUserByEmail(email);

        if (foundUser.isPresent()){
            return null;
            //TODO Throw exception
        }
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority(role).get();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRole(userRole);
        return userRepository.save(user);
    }

    public LoginResponse login(String email, String password){
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
                    String token = tokenService.generateJwtToken(auth);

                    User foundUser = setUserToken(email);
                    Instant instant = Instant.now().plus(24, ChronoUnit.HOURS);
                    Token userToken = new Token(foundUser.getId(), token, instant);
                    tokenService.saveToken(userToken);

                    return new LoginResponse(token);
        }catch (Exception ex){
            ex.printStackTrace();
            //TODO throw exception
            throw new RuntimeException();
        }
    }

    public User setUserToken(String email){
        Optional<User> foundUser = userRepository.findUserByEmail(email);

        if (foundUser.isPresent()){
            return foundUser.get();
        }
        return null;
        //TODO Throw exception
    }
}
