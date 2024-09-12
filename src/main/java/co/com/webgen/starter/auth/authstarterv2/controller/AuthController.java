package co.com.webgen.starter.auth.authstarterv2.controller;

import cloud.webgen.web.starter.exeptions.HttpException;
import co.com.webgen.starter.auth.authstarterv2.DTO.AuthResponseDTO;
import co.com.webgen.starter.auth.authstarterv2.DTO.LoginRequestDTO;
import co.com.webgen.starter.auth.authstarterv2.DTO.RegisterRequestDTO;
import co.com.webgen.starter.auth.authstarterv2.DTO.VerifyRequestDTO;
import co.com.webgen.starter.auth.authstarterv2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginData) throws HttpException {
        return new ResponseEntity<>(this.authService.login(loginData), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO registerData) throws HttpException {
        return new ResponseEntity<>(this.authService.register(registerData), HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verify(@RequestBody VerifyRequestDTO verifyData) throws HttpException {
        return new ResponseEntity<>(this.authService.verify(verifyData), HttpStatus.OK);
    }
}
