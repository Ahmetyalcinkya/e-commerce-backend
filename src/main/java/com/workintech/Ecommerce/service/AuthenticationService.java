package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.dto.responseDto.LoginResponse;
import com.workintech.Ecommerce.entity.ConfirmationToken;
import com.workintech.Ecommerce.entity.Role;
import com.workintech.Ecommerce.entity.Token;
import com.workintech.Ecommerce.entity.User;
import com.workintech.Ecommerce.repository.RoleRepository;
import com.workintech.Ecommerce.repository.UserRepository;
//import com.workintech.Ecommerce.util.validation.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                                 AuthenticationManager authenticationManager, TokenService tokenService, UserService userService,
                                 ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
        this.confirmationTokenService = confirmationTokenService;
    }

    //TODO change the return value
    @Transactional
    public User register(String name, String email, String password, String role){

        userService.findUserByEmail(email); //user varsa hata fırlatır

        String encodedPassword = passwordEncoder.encode(password); // şifreyi encode eder
        Role userRole = roleRepository.findByAuthority(role).get(); // role bulunur

        User user = new User(); //kullanıcı setlenir
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRole(userRole);
        userRepository.save(user);
        String emailToken = UUID.randomUUID().toString(); // email token için rastgele path oluşturur
        ConfirmationToken confirmationToken = new ConfirmationToken(emailToken, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        confirmationTokenService.saveConfirmationToken(confirmationToken); // email token kısmını database içerisine setler
        //TODO Send confirmation email

        return user;
    }

//TODO: EMAIL SENDER WILL BE ADDED !!!

    @Transactional
    public String confirmEmailToken(String emailToken){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getEmailToken(emailToken)
                .orElseThrow(() ->
                        new IllegalStateException("Email token not found"));
        if (confirmationToken.getConfirmedAt() != null) {
            //TODO Throw exception
            throw new IllegalStateException("email already confirmed");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isAfter(LocalDateTime.now())) {
            //TODO Throw exception
            throw new IllegalStateException("Email token expired");
        }
        confirmationTokenService.setConfirmedAt(emailToken);
        userService.enableUser(
                confirmationToken.getUser().getEmail());

        return "confirmed";
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
