package com.example.GreetingApp.controller;

import com.example.GreetingApp.dto.*;
import com.example.GreetingApp.model.AuthUser;
import com.example.GreetingApp.interfaces.IAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthUserController {

    private final IAuthenticationService authenticationService; // No @Autowired needed

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody AuthUserDTO userDTO) throws Exception {
        AuthUser user = authenticationService.register(userDTO);
        ResponseDTO responseUserDTO = new ResponseDTO("User details submitted!", user);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        String result = authenticationService.login(loginDTO);
        ResponseDTO responseUserDTO = new ResponseDTO("Login successful!", result);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }

    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<ResponseDTO> forgotPassword(@PathVariable String email,
                                                      @Valid @RequestBody ForgotPassword forgotPasswordDTO) {
        String responseMessage = authenticationService.forgotPassword(email, forgotPasswordDTO.getPassword());
        ResponseDTO responseDTO = new ResponseDTO(responseMessage, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<ResponseDTO> resetPassword(@PathVariable String email,
                                                     @Valid @RequestBody ResetPassword resetPasswordDTO) {
        String responseMessage = authenticationService.resetPassword(email,
                resetPasswordDTO.getCurrentPassword(),
                resetPasswordDTO.getNewPassword());
        return new ResponseEntity<>(new ResponseDTO(responseMessage, null), HttpStatus.OK);
    }
}
