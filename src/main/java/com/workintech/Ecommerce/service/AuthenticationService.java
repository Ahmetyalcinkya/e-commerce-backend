package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.responseDto.LoginResponse;
import com.workintech.Ecommerce.entity.ConfirmationToken;
import com.workintech.Ecommerce.entity.Role;
import com.workintech.Ecommerce.entity.Token;
import com.workintech.Ecommerce.entity.User;
import com.workintech.Ecommerce.exceptions.ECommerceException;
import com.workintech.Ecommerce.repository.RoleRepository;
import com.workintech.Ecommerce.repository.UserRepository;
import com.workintech.Ecommerce.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UserService userService;
    private ConfirmationTokenService confirmationTokenService;
    private EmailService emailService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager, TokenService tokenService, UserService userService,
                                 ConfirmationTokenService confirmationTokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailService = emailService;
    }

    @Transactional
    public User register(String name, String email, String password, String role){

        userService.findUserByEmail(email);

        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority(role).get();

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRole(userRole);
        userRepository.save(user);
        String emailToken = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(emailToken, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        String text = "Congrats!\n"+
                "Your registration to E-Commerce is successful. The only thing you need to" +
                " do is to click the link below to activate your account and start shopping!\n"+
                "Activate your account: " + "http://localhost:9000/auth/confirm?emailToken="+emailToken;

        emailService.sendEmail(email, "E-Commerce Activation", text);

        return user;
    }

    @Transactional
    public String confirmEmailToken(String emailToken){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getEmailToken(emailToken)
                .orElseThrow(() ->
                        new ECommerceException(Constants.TOKEN_NOT_FOUND,HttpStatus.NOT_FOUND));
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ECommerceException(Constants.EMAIL_CONFIRMED, HttpStatus.BAD_REQUEST);
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isAfter(LocalDateTime.now())) {
            throw new ECommerceException(Constants.EMAIL_TOKEN_EXPIRED, HttpStatus.BAD_REQUEST);
        }
        confirmationTokenService.setConfirmedAt(emailToken);
        userService.enableUser(
                confirmationToken.getUser().getEmail());

        return Constants.CONFIRMED;
    }

    @Transactional
    public LoginResponse login(String email, String password){
        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
                    String token = tokenService.generateJwtToken(auth);

                    User foundUser = setUserToken(email);
                    Instant instant = Instant.now().plus(24, ChronoUnit.HOURS);
                    Token userToken = new Token();
                    userToken.setToken(token);
                    userToken.setExpiryDate(instant);
                    userService.updateUser(userToken, foundUser);
                    tokenService.saveToken(userToken);

                    return new LoginResponse(token);
        }catch (Exception ex){
            ex.printStackTrace();
            throw new ECommerceException(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public User setUserToken(String email){
        Optional<User> foundUser = userRepository.findUserByEmail(email);

        if (foundUser.isPresent()){
            return foundUser.get();
        }
        throw new ECommerceException(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
