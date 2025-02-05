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

    public AuthResponseDTO login(LoginRequestDTO loginData) throws HttpException {
        try {
            Optional<User> user = userRepository.findByOwnerIdAndUserNameOrMail(loginData.getOwnerId(), loginData.getUserName(), loginData.getUserName());

            if (user.isEmpty()) {
                throw new HttpException("Not found", HttpStatus.NOT_FOUND);
            }

            boolean isValid = passwordEncoder.matches(loginData.getPassword(), user.get().getPassword());

            if(!isValid) {
                throw new HttpException("Wrong credentials", HttpStatus.UNAUTHORIZED);
            }

            if (!user.get().getActive()) throw new HttpException("Inactive user", HttpStatus.BAD_REQUEST);

            return AuthResponseDTO.builder()
                    .token(jwtService.getToken(new SecurityUser(user.get())))
                    .role(user.get().getRole())
                    .build();
        } catch (Exception e) {
            throw new HttpException(e.getMessage(), HttpStatus.UNAUTHORIZED );
        }
    }

    public AuthResponseDTO register(RegisterRequestDTO registerData) throws HttpException {
        User user = User.builder()
                .mail(registerData.getMail())
                .userName(registerData.getUserName())
                .ownerId(registerData.getOwnerId())
                .role(registerData.getRole())
                .password(passwordEncoder.encode(registerData.getPassword()))
                .active(true)
                .build();

        if (!isMailAvailable(registerData.getMail(), registerData.getOwnerId())) throw new HttpException("Email in use for this owner", HttpStatus.BAD_REQUEST);

        this.userRepository.save(user);

        String jwt = jwtService.getToken(new SecurityUser(user));

        return AuthResponseDTO.builder().token(jwt).role(registerData.getRole()).build();
    }

    private boolean isMailAvailable(String mail, String ownerId) {
        return this.userRepository.findByMailAndOwnerId(mail, ownerId).isEmpty();
    }

    public boolean verify(VerifyRequestDTO verifyData) throws HttpException {
        User userFound = this.userRepository.findById(verifyData.getUserId())
                .orElseThrow(() -> new HttpException("Invalid user", HttpStatus.BAD_REQUEST));

        userFound.setActive(true);

        this.userRepository.save(userFound);

        return true;
    }
}
