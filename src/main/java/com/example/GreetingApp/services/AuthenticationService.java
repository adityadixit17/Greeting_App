package com.example.GreetingApp.services;

import com.example.GreetingApp.Exception.UserException;
import com.example.GreetingApp.dto.*;
import com.example.GreetingApp.interfaces.IAuthenticationService;
import com.example.GreetingApp.model.AuthUser;
import com.example.GreetingApp.repository.AuthUserRepository;
import com.example.GreetingApp.utility.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    EmailSenderService emailSenderService;
    @Autowired
    JwtToken tokenUtil;

    @Override
    public AuthUser register(AuthUserDTO userDTO) throws Exception {
        String hashedPassword = encoder.encode(userDTO.getPassword());
        AuthUser user = new AuthUser(userDTO);
        user.setPassword(hashedPassword);
        authUserRepository.save(user);

        String token = tokenUtil.createToken(user.getUserId());
        emailSenderService.sendEmail(user.getEmail(), "Registered in Greeting App",
                "Hi " + user.getFirstName() + ",\n\nYou have been successfully registered!"
                        + "\nUser Id: " + user.getUserId()
                        + "\nEmail: " + user.getEmail()
                        + "\nToken: " + token);

        return user;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Optional<AuthUser> user = Optional.ofNullable(authUserRepository.findByEmail(loginDTO.getEmail()));

        if (user.isPresent() && encoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
            emailSenderService.sendEmail(user.get().getEmail(), "Login Successful",
                    "Hi " + user.get().getFirstName() + ",\n\nYou have successfully logged in.");
            return "Login successful!";
        } else {
            throw new UserException("Invalid email or password!");
        }
    }

    @Override
    public String forgotPassword(String email, String newPassword) {
        AuthUser user = authUserRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found: " + email);
        }

        user.setPassword(encoder.encode(newPassword));
        authUserRepository.save(user);

        emailSenderService.sendEmail(user.getEmail(), "Password Reset Successful",
                "Hi " + user.getFirstName() + ",\n\nYour password has been successfully changed!");

        return "Password changed successfully!";
    }
}
