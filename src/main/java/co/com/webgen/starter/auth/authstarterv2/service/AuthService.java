package co.com.webgen.starter.auth.authstarterv2.service;

import cloud.webgen.web.starter.exeptions.HttpException;
import co.com.webgen.starter.auth.authstarterv2.DTO.AuthResponseDTO;
import co.com.webgen.starter.auth.authstarterv2.DTO.LoginRequestDTO;
import co.com.webgen.starter.auth.authstarterv2.DTO.RegisterRequestDTO;
import co.com.webgen.starter.auth.authstarterv2.DTO.VerifyRequestDTO;
import co.com.webgen.starter.auth.authstarterv2.events.RegisterEvent;
import co.com.webgen.starter.auth.authstarterv2.model.User;
import co.com.webgen.starter.auth.authstarterv2.repository.UserRepository;
import co.com.webgen.starter.auth.authstarterv2.security.SecurityUser;
import co.com.webgen.starter.auth.authstarterv2.templates.MailTemplate;
import co.com.webgen.starter.auth.authstarterv2.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public AuthResponseDTO login(LoginRequestDTO loginData) throws HttpException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUserName(), loginData.getPassword()));

        User user = userRepository.findByUserNameOrMail(loginData.getUserName(), loginData.getUserName())
                .orElseThrow(() -> new HttpException("Not found", HttpStatus.NOT_FOUND));

        if (!user.getActive()) throw new HttpException("Inactive user", HttpStatus.BAD_REQUEST);

        return AuthResponseDTO.builder().token(jwtService.getToken(new SecurityUser(user))).build();
    }

    public AuthResponseDTO register(RegisterRequestDTO registerData) throws HttpException {
        User user = User.builder()
                .mail(registerData.getMail())
                .userName(registerData.getUserName())
                .password(passwordEncoder.encode(registerData.getPassword()))
                .active(false)
                .build();

        if (!isMailAvailable(registerData.getMail())) throw new HttpException("Email in use", HttpStatus.BAD_REQUEST);

        this.userRepository.save(user);

        String jwt = jwtService.getToken(new SecurityUser(user));

        this.kafkaTemplate.send("register-topic", JsonUtils.toJson(
                new RegisterEvent(user.getId(), user.getMail(), "Registro", MailTemplate.getRegisterTemplate(jwt, user.getId()))
        ));

        return AuthResponseDTO.builder().token(jwt).build();
    }

    private boolean isMailAvailable(String mail) {
        return this.userRepository.findByMail(mail).isEmpty();
    }

    public boolean verify(VerifyRequestDTO verifyData) throws HttpException {
        User userFound = this.userRepository.findById(verifyData.getUserId())
                .orElseThrow(() -> new HttpException("Invalid user", HttpStatus.BAD_REQUEST));

        userFound.setActive(true);

        this.userRepository.save(userFound);

        return true;
    }
}
